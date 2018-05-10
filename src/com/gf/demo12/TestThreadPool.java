package com.gf.demo12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一、线程池 : 提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了响应的速度。
 * 
 * 二、线程池的体系机构 : 
 *	java.util.concurrent.Executor : 负责线程的使用与调度的根接口
 *		|--ExecutorService 子接口 : 线程池的主要接口
 *			|--ThreadPoolExecutor 线程池的实现类
 *			|--ScheduledExecutorService 子接口 : 负责线程的调度
 *				|--ScheduledThreadPoolExecutor : 继承ThreadPoolExecutor ,实现ScheduledExecotorService
 *
 * 三、工具类: Executors
 * ExecutorSercice newFixedThreadPool() : 创建固定大小的线程池
 * ExecutorSercice newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
 * ExecutorSercice newSingleThreadExecutor() : 创建单个线程池，线程池中只有一个线程
 * 
 * 
 * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时执行任务。
 *
 */
public class TestThreadPool {
	
	public static void main(String[] args) {
		//1. 创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(5);
		
		ThreadPoolDemo tpd = new ThreadPoolDemo();
		
		//2. 为线程池中的线程分配任务
		for(int i = 0 ; i < 10 ; i++){
			pool.submit(tpd);
		}
		
		//3. 关闭线程池,平和的方式关闭
		pool.shutdown();
	}

}

class ThreadPoolDemo implements Runnable{
	
	private int i = 0;

	@Override
	public void run() {
		while(100 >= i){
			System.out.println(Thread.currentThread().getName() + " : " + i++);
		}
	}
	
}
