/**
 * Copyright (C), 2019, 金科教育
 * FileName: NewSingleThreadExecutor
 * Author:   zyl
 * Date:     2019/8/12 10:23
 * History:
 * zyl          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author zyl
 * @create 2019/8/12
 * @since 1.0.0
 *//**
 * Created by Administrator on 2018/6/6.
 * newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，
 * 保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 * 单例线程池
 */

public class NewSingleThreadExecutor {
    public static void main(String[] args) {
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
        final int index = i;
            /*singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("newSingleThreadExecutor: " + index);
                        Thread.sleep(2*1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });*/


        singleThreadExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("newSingleThreadExecutor: " + index);
                    Thread.sleep(2*1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

        singleThreadExecutor.shutdown();
}

}
