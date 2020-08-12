package com.example.demo.dao;

import com.example.demo.domain.Question;

import java.util.List;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/7/17
 */
public interface IQuestionDao {
    List<Question> getAll();
}
