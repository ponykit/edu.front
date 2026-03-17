package com.edu.front.model;

import com.edu.front.model.common.BasePaging;
import lombok.Data;

@Data
public class ApiResult<T> extends BasePaging {

    private String code;

    private String message;

    private T data;
    //bootgrid 용
    private T rows;

    public ApiResult() {

    }

    public ApiResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
