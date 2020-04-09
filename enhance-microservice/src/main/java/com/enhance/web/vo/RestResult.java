package com.enhance.web.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RestResult {
    private String code;
    private Object data;
    private String msg;
}