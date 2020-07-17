package com.example.demo.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/7/17
 */
@Data
public class Question {
    @Id
    protected String _id;

    protected  String title;

    protected  String content;


    protected List<String> tags;
    /*
    0. collection
    1. tag
    2. name/title

    * */

}
