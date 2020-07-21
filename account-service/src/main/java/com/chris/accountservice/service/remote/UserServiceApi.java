package com.chris.accountservice.service.remote;

import com.alibaba.fastjson.JSONArray;
import com.chris.accountservice.entity.ResponseEntity;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * @Author: Chris
 * @Date: 2020-07-21 17:06
 */
@Headers("Accept: application/json")
public interface UserServiceApi {
    /**
     * 远程调用user service的/user/printUserInfo 接口
     *
     * ResponseEntity<?> : ?，表示通用的解析
     *
     * @param userName
     * @return
     */
    @Headers("Content-Type: application/json")
    @RequestLine("GET /api/v1/user/printUserInfo/{userName}")
    ResponseEntity printUserInfo(@Param("userName")String userName);
}
