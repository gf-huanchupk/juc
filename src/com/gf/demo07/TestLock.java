package com.gf.demo07;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一、用于解决多线程安全问题的方式:
 * synchronized:隐式锁
 * 1. 同步代码块
 * 2. 同步方法
 * 
 * jdk1.5之后：
 * 3. 同步锁Lock
 * 注意：是一个显示锁，需要通过lock() 方法上锁，必须通过unlock() 方法释放锁
 * 
 */
public class TestLock {

	public static void main(String[] args) {
		Ticket ticket = new Ticket();
		for (int i = 0; i < 3; i++) {
			new Thread(ticket,i+1+"号窗口").start();
		}
	}
	
}


class Ticket implements Runnable{
	
	private int tick = 100;
	
	//解决方案
	private Lock lock = new ReentrantLock();

	@Override
	public void run() {
		while(true){
			lock.lock(); // 加锁
			
			try {
				if(0 < tick){
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
				}
			} finally {
				lock.unlock(); //释放锁
			}
		}
	}
	
}
