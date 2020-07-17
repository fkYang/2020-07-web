package com.example.demo.service;

import com.example.demo.dao.IQuestionDao;
import com.example.demo.domain.Question;
import com.example.demo.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/7/17
 */
@Service
public class QuestionService {
    @Autowired
    IQuestionDao questionDao;

    @Autowired
    RedisUtils redisUtils;

    String question = "question";

    public List<Question> getAll(){


        List<Question> questions =  null;
        if(redisUtils.exists(question)){
            questions =(List<Question>)redisUtils.get(question);
        }else{
            questions = questionDao.getAll();
            redisUtils.set(question, questions);
        }
        return questions;
    }
}
