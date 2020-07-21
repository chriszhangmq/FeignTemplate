package com.chris.accountservice.service;

import org.springframework.stereotype.Service;

/**
 * @Author: Chris
 * @Date: 2020-07-21 17:06
 */
@Service
public class AccountService {
    public String accountinfo(String accountName){
        return "log in Account service !  AccountName = " + accountName;
    }
}
