package com.example.demo.dao;

import com.example.demo.domain.Question;
import com.example.demo.utils.ExecutorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/7/17
 */
@Repository
public class QuestionImpl implements IQuestionDao {
    MongoTemplate mongo;

    @Autowired
    MongoDatabaseFactory mongoDatabaseFactory;

    ExecutorService executorService = ExecutorUtils.getExecutorPool();

    @Override
    public List<Question> getAll() {
        mongo = new MongoTemplate(mongoDatabaseFactory);
        Query query = new Query();
        List<Question> questions = mongo.find(query, Question.class, "question");
        return questions;
    }
}
