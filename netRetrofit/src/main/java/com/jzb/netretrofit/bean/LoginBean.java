package com.jzb.netretrofit.bean;

import com.jzb.netretrofit.base.BaseBean;

/**
 * created by jzb
 * on 2020/10/21
 **/
public class LoginBean extends BaseBean {

    /**
     * code : 0
     * status : 成功
     */

    private String code;
    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "code='" + code + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
