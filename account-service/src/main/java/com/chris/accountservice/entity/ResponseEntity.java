package com.chris.accountservice.entity;


import com.chris.accountservice.constant.ResponseConstants;
import lombok.Data;

import java.util.List;

@Data
public class ResponseEntity<T> {

    private int status;
    private String msg;
    private T value;
    private Long total;

    public ResponseEntity() {
    }

    public ResponseEntity(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResponseEntity(Integer status, String msg, T value) {
        this.status = status;
        this.msg = msg;
        this.value = value;
    }

    public ResponseEntity(Integer status, String msg, T value, Long total) {
        this.status = status;
        this.msg = msg;
        this.value = value;
        this.total = total;
    }

    public static ResponseEntity responseSuccess(String msg, Object value) {
        return new ResponseEntity(ResponseConstants.SUCCESS, msg, value);
    }

    public static ResponseEntity responseListSuccess(String msg, List list) {
        Long size = 0L;
        if (list != null) {
            size = new Long((long) list.size());
        }
        return new ResponseEntity(ResponseConstants.SUCCESS, msg, list, size);
    }

    public static ResponseEntity responseListSuccess(String msg, List list, Long total) {
        return new ResponseEntity(ResponseConstants.SUCCESS, msg, list, total);
    }

    public static ResponseEntity responseFail(String msg) {
        return new ResponseEntity(ResponseConstants.FAIL, msg);
    }

    public static ResponseEntity responseFail(Integer status, String msg) {
        return new ResponseEntity(status, msg);
    }

}
