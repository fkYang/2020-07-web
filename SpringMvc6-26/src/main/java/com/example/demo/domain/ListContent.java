package com.example.demo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/7/9
 */
@Data
public class ListContent {
    @Id
    private String _id;

    private  String title;
    private  String url;
    private List<String> tags;
}
