/**
 * Copyright (C), 2019, 金科教育
 * FileName: NewScheduledThreadPool
 * Author:   zyl
 * Date:     2019/8/12 10:17
 * History:
 * zyl          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.jk.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author zyl
 * @create 2019/8/12
 * @since 1.0.0
 */
/**
 * Created by Administrator on 2018/6/6.
 * newScheduledThreadPool 创建一个固定长度线程池，支持定时及周期性任务执行。
 * 定时线程池
 */
public class NewScheduledThreadPool {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

        //testSchedule(scheduledExecutorService);

        testScheduleAtFixedRate(scheduledExecutorService);

        //testScheduleWithFixedDelay(scheduledExecutorService);

        // 终止线程池
        scheduledExecutorService.shutdown();
    }

    /**
     *
     * 跟 testScheduleAtFixedRate 非常类似，就是延迟的时间有点区别
     * 创建并执行一个在给定初始延迟后首次启用的定期操作，后续操作具有给定的周期；
     * 也就是将在 initialDelay 后开始执行，然后在 initialDelay+period 后执行，
     * 接着在 initialDelay + 2 * period 后执行，依此类推。
     *
     * 如果任务里面执行的时间大于 period 的时间，下一次的任务会推迟执行。
     * 推迟的时间 ： 等到上次的任务执行完之后再延迟period 的时间后执行。
     * @param scheduledExecutorService
     */
    private static void testScheduleWithFixedDelay(ScheduledExecutorService scheduledExecutorService) {
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("延迟2秒，再3秒执行一次");
                    //如果任务里面执行的时间大于 period 的时间，下一次的任务会推迟执行。
                    //本次任务执行完后下次的任务还需要延迟period时间后再执行
                    Thread.sleep(6*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },2,3,TimeUnit.SECONDS);
    }

    /**
     * 创建并执行一个在给定初始延迟后首次启用的定期操作，后续操作具有给定的周期；
     * 也就是将在 initialDelay 后开始执行，然后在 initialDelay+period 后执行，
     * 接着在 initialDelay + 2 * period 后执行，依此类推。
     *
     * 如果任务里面执行的时间大于 period 的时间，下一次的任务会推迟执行。
     * 推迟的时间 ： 等到上次的任务执行完就立马执行。
     * @param scheduledExecutorService
     */
    private static void testScheduleAtFixedRate(ScheduledExecutorService scheduledExecutorService) {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("延迟2秒，再3秒执行一次");
                    //如果任务里面执行的时间大于 period 的时间，下一次的任务会推迟执行。
                    //如果任务里面执行的时间大于 period 的时间，本次任务执行完后，下次任务立马执行。
                    Thread.sleep(6*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        },2,3,TimeUnit.SECONDS);
    }

    /**
     * 创建并执行在给定延迟后启用的一次性操作
     * @param scheduledExecutorService
     */
    private static void testSchedule(ScheduledExecutorService scheduledExecutorService) {
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("delay 3 seconds");
            }
        }, 3, TimeUnit.SECONDS);
    }

}
