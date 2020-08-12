package com.example.demo.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/6/26
 */
@Data
public class Address {
    @NotBlank(message = "不能为空")
    private String province;
    @NotBlank(message = "不能为空")
    private String city;
}
