package com.example.demo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
//mport org.hibernate.validator.constraints.NotBlank;
/**
 * 类描述
 *
 * @author yfk
 * @date 2020/6/26
 */
@Data
@Document("users")
public class User {
    @Id
    String _id;
    @NotBlank(message = "不能为空")
    private String username;
  //  private Integer age;

    @Size(min = 6 ,message = "用户名password al least 6 char")
    private String password;

    @NotEmpty(message = "不能为空")
    private  Address address;

    private List<Seen> seen;
}
