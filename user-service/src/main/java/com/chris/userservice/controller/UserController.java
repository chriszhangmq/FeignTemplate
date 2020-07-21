package com.chris.userservice.controller;

import com.chris.userservice.entity.ResponseEntity;
import com.chris.userservice.service.UserService;
import com.chris.userservice.service.remote.AccountServiceApi;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Chris
 * @Date: 2020-07-21 16:18
 */
@RestController
@Slf4j
@RequestMapping(value = "/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountServiceApi accountServiceApi;

    @ApiOperation(value = "打印信息")
    @GetMapping(value = "/printUserInfo/{userName}")
    public ResponseEntity printUserInfo(@PathVariable String userName){
        return ResponseEntity.responseSuccess("0", userService.userInfo(userName));
    }

    /**
     * ResponseEntity：使用自定义的类，springboot有自带的ResponseEntity
     * ——由于FeiginServerConfig使用的是自定义的编码，因此ResponseEntity也要使用相同的编码、解码方式
     * @param accountName
     * @return
     */
    @ApiOperation(value = "调用Account Service服务的API")
    @GetMapping(value = "/printAccountInfo/{accountName}")
    public ResponseEntity printAccountInfo(@PathVariable String accountName){
        log.info("-- 远程调用：account service 接口 --");
        ResponseEntity res = accountServiceApi.printAccountInfo(accountName);
        log.info("-- success --");
        return res;
    }


}
