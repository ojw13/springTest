package com.ojw.java8.bean.vo;

import lombok.Data;

/**
 *
 * @author Administrator
 * @date 2020/5/9 0009
 */
@Data
public class ResultVO<T>{
    /**
     * 状态码，比如1000代表响应成功
     */
    private int code;
    /**
     * 响应信息，用来说明响应情况
     */
    private String msg;
    /**
     * 响应的具体数据
     */
    private T data;

    public ResultVO(T data) {
        this(1000, "success", data);
    }

    public ResultVO(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
