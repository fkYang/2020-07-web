package com.example.demo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/6/30
 */
@Data
//@Document(collection = "java")
public class Content {
    @Id
    private String _id;

    private  String title;
    private  String content;
    private  String url;
    private List<String> tags;

    public void setTitle(String title) {
        this.title=title;
    }

    public void setUrl(String url) {
        this.url=url;
    }

    public void setTags(List<String> tags) {
        this.tags=tags;
    }

    public void setContent(String toString) {
        this.content=toString;
    }
}
