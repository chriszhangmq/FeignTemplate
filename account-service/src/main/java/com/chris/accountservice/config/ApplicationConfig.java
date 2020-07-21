package com.chris.accountservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yaoxs@shukun.net
 * @date 2020-01-08 09:59
 */
@Data
@Component
@ConfigurationProperties(prefix = "com.chris")
public class ApplicationConfig {

    /**
     * 控制是否展示swagger
     */
    private Boolean swaggerEnable;

    /**
     * user server地址
     */
    private String userServer;

}
