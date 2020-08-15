/**
 * FileName: CommonRest
 * Author:   嘉平十七
 * Date:     2020/8/12 9:35
 * Description: Json封装体
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
    private T       data;

    public CommonResult(Integer code,String message)
    {
        this(code,message,null);
    }
}