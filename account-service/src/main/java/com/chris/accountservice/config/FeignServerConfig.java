package com.chris.accountservice.config;

import com.chris.accountservice.service.remote.UserServiceApi;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.*;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.concurrent.TimeUnit;

import static feign.FeignException.errorStatus;

/**
 * @author yaoxs@shukun.net
 * @date 2020-01-09 17:13
 */
@Slf4j
@Configuration
public class FeignServerConfig {


    @Autowired
    private ApplicationConfig applicationConfig;

    /**
     * Default errorDecoder
     */
    private static ErrorDecoder errorDecoder = new ErrorDecoder.Default();

    @Bean
    public UserServiceApi userServiceApiBean(Client feignHttpClient,
                                                RequestInterceptor requestInterceptor,
                                                ErrorDecoder errorDecoder) {
        ObjectMapper objectMapper = customizeFeignObjectMapper();
        // 设定feign处理请求的超时配置
        Request.Options requestOptions = new Request.Options(30, TimeUnit.SECONDS, 5 * 60, TimeUnit.SECONDS, true);
        return Feign.builder()
                .client(feignHttpClient)
                .decoder(new JacksonDecoder(objectMapper))
                .encoder(new JacksonEncoder(objectMapper))
                .errorDecoder(errorDecoder)
                .requestInterceptor(requestInterceptor)
                .options(requestOptions)
                .target(UserServiceApi.class, applicationConfig.getUserServer());
    }


    /**
     * 自定义object mapper处理jackson解析对象
     *
     * @return
     */
    private ObjectMapper customizeFeignObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new SimpleModule());
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    @Bean
    public Client feignHttpClient() {
        return new OkHttpClient(okHttpClient());
    }

    @Bean
    public okhttp3.OkHttpClient okHttpClient() {
        return new okhttp3.OkHttpClient.Builder()
                // 连接超时时间5s
                .connectTimeout(5, TimeUnit.SECONDS)
                // 最大读取超时时间5min
                .readTimeout(5 * 60, TimeUnit.SECONDS)
                // 最大输出超时时间60min
                .writeTimeout(5 * 60, TimeUnit.SECONDS)
                // 开启请求重试
                .retryOnConnectionFailure(true)
                .connectionPool(okHttpConnectionPool())
                .build();
    }

    /**
     * 构建最多idle connection数量为16，空闲5min的连接
     *
     * @return
     */
    @Bean
    public ConnectionPool okHttpConnectionPool() {
        return new ConnectionPool(16, 5, TimeUnit.MINUTES);
    }

    /**
     * 自定义请求拦截器处理authorization
     *
     * @return
     */
    @Bean
    public RequestInterceptor customizeRequestInterceptor() {
        return template -> {};
    }

    /**
     * 自定义处理feign请求异常
     *
     * @return
     */
    @Bean
    public ErrorDecoder customizeErrorDecoder() {
        return (methodKey, response) -> {
            // 对于请求未授权的，统一处理为UserException
            if(HttpStatus.UNAUTHORIZED.value() == response.status()) {
                FeignException exception = errorStatus(methodKey, response);
                return new Exception(exception.getMessage());
            } else if(HttpStatus.INTERNAL_SERVER_ERROR.value() == response.status()){
                FeignException exception = errorStatus(methodKey, response);
                log.error(exception.getMessage(), exception);
                // 服务异常做单独处理
                return new Exception("调用远程服务异常！");
            } else {
                // 其他异常返回RetryableException，自动触发重试逻辑
                return errorDecoder.decode(methodKey, response);
            }
        };
    }
}
