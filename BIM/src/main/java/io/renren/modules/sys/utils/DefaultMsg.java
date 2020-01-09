package io.renren.modules.sys.utils;

import lombok.Data;

/**
 * 默认的返回结果
 */
@Data
public class DefaultMsg {
    private Integer code;
    private String msg;
    private Object data;

}
