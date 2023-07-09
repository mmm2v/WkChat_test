package com.weike.test.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池工具类
 */
public class ThreadUtils {
    private static ExecutorService executor;

    // 私有构造函数，确保只能通过 getInstance() 方法获取实例
    private ThreadUtils() {
        // 创建一个固定大小的线程池，最大线程数为 10
        executor = Executors.newFixedThreadPool(30);
    }

    private static class InstanceHolder {
        private static final ThreadUtils INSTANCE = new ThreadUtils();
    }

    // 获取单例实例
    public static ThreadUtils getInstance() {
        return InstanceHolder.INSTANCE;
    }

    // 提交任务给线程池执行
    public void executeTask(Runnable task) {
        executor.execute(task);
    }

    // 关闭线程池
    public void shutdown() {
        executor.shutdown();
    }
}

