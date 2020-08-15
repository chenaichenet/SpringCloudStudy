/**
 * FileName: Payment
 * Author:   嘉平十七
 * Date:     2020/8/12 9:33
 * Description: 主实体类
 * notes：
 */
package com.springcloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
    private Long id;
    private String serial;
}
