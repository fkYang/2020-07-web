package com.example.demo.utils;

import java.sql.Time;
import java.util.Date;
import java.util.concurrent.*;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/7/3
 */
public class ExecutorUtils {


    private static final ExecutorService executorService ;//= Executors.newFixedThreadPool(15);

    static {
        executorService = new ThreadPoolExecutor(10,
                15, 60, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1000));
    }
    static public ExecutorService getExecutorPool(){
        return executorService;
    }


 //   static long time = 100000;

}
