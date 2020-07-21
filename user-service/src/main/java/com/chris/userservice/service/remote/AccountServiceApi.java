package com.chris.userservice.service.remote;

import com.chris.userservice.entity.ResponseEntity;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * 远程调用account service的api
 * @Author: Chris
 * @Date: 2020-07-21 16:13
 */
@Headers("Accept: application/json")
public interface AccountServiceApi {
    /**
     * 调用account service的/account/printAccountInfo 接口
     *
     * ResponseEntity<?> : ?，表示通用的解析
     *
     * @param accountName
     * @return
     */
    @Headers("Content-Type: application/json")
    @RequestLine("GET /api/v1/account/printAccountInfo/{accountName}")
    ResponseEntity printAccountInfo(@Param("accountName")String accountName);
}
