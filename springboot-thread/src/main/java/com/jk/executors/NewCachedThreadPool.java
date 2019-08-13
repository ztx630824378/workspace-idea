/**
 * Copyright (C), 2019, 金科教育
 * FileName: NewCachedThreadPool
 * Author:   zyl
 * Date:     2019/8/12 10:07
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
 */
public class NewCachedThreadPool {
    /**
     * Created by Administrator on 2018/6/6.
     * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，
     * 若无可回收，则新建线程。线程池的规模不存在限制。
     * 缓存线程池
     */
    public static void main(String[] args) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            try {
                Thread.sleep( 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //1- 在未来某个时间执行给定的命令。
            // 该命令可能在新的线程、已入池的线程或者正调用的线程中执行，这由 Executor 实现决定。
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(index);
                }
            });

            //2- 提交一个 Runnable 任务用于执行，并返回一个表示该任务的 Future。
            // 该 Future 的 get 方法在成功完成时将会返回给定的结果
            cachedThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(index);
                }
            });
        }
        cachedThreadPool.shutdown();
    }

}
