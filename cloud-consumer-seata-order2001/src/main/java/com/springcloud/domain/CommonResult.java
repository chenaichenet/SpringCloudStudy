/**
 * FileName: CommonResult
 * Author:   嘉平十七
 * Date:     2020/8/17 16:29
 * Description:
 * notes：
 */
package com.springcloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String  message;
    private T data;

    public CommonResult(Integer code, String message) {
        this(code,message,null);
    }
}