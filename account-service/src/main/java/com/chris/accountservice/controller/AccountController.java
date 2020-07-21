package com.chris.accountservice.controller;

import com.chris.accountservice.entity.ResponseEntity;
import com.chris.accountservice.service.AccountService;
import com.chris.accountservice.service.remote.UserServiceApi;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Chris
 * @Date: 2020-07-21 17:12
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserServiceApi userServiceApi;

    @ApiOperation(value = "打印信息")
    @GetMapping(value = "/printAccountInfo/{accountName}")
    public ResponseEntity printAccountInfo(@PathVariable String accountName){
        return ResponseEntity.responseSuccess("0", accountService.accountinfo(accountName));
    }

    /**
     * ResponseEntity：使用自定义的类，springboot有自带的ResponseEntity
     * ——由于FeiginServerConfig使用的是自定义的编码，因此ResponseEntity也要使用相同的编码、解码方式
     * @param userName
     * @return
     */
    @ApiOperation(value = "调用User Service服务的API")
    @GetMapping(value = "/printUserInfo/{userName}")
    public ResponseEntity printUserInfo(@PathVariable String userName){
        log.info("-- 远程调用：user service 接口 --");
        ResponseEntity res = userServiceApi.printUserInfo(userName);
        log.info("-- success --");
        return res;
    }
}
