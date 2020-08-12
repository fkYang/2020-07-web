package com.example.demo.domain;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/7/13
 */
@Data
public class Seen {
    private String tag;
    private Set<ListContent> contents;
}
