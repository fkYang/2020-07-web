package com.example.demo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.util.List;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/6/30
 */
@Data
//@Document("javaDemo")
public class Content  {
    @Id
    private String _id;

    private  String title;
    private  String content;
    private  String url;
    private List<String> tags;

}
