package com.gf.demo10;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一个程序，开启 3 个线程，这三个线程的ID 分别为A、B、C,每个线程将自己的ID 在屏幕上打印 10 遍 ，要求输出结果必须按顺序显示。
 * 如：ABCABCABC …… 依次递归
 *
 */
public class TestABCCAlternate {

	public static void main(String[] args) {
		AlternateDemo ad = new AlternateDemo();

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 20; i++) {
					ad.loopA(i);
				}
			}
		}, "A").start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 20; i++) {
					ad.loopB(i);
				}
			}
		}, "B").start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 20; i++) {
					ad.loopC(i);
					System.out.println("---------------------------------");
				}
			}
		}, "C").start();

	}

}

class AlternateDemo {

	private int number = 1; // 当前正在执行线程的标记

	private Lock lock = new ReentrantLock();
	private Condition condition1 = lock.newCondition();
	private Condition condition2 = lock.newCondition();
	private Condition condition3 = lock.newCondition();

	/**
	 * @param totalLoop
	 *            ：循环第几轮
	 */
	public void loopA(int totalLoop) {
		lock.lock();

		try {

			// 1. 判断
			if (1 != number) {
				condition1.await();
			}

			// 2. 打印
			for (int i = 1; i <= 5; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
			}

			// 3. 唤醒
			number = 2;
			condition2.signal();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void loopB(int totalLoop) {
		lock.lock();

		try {

			// 1. 判断
			if (2 != number) {
				condition2.await();
			}

			// 2. 打印
			for (int i = 1; i <= 5; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
			}

			// 3. 唤醒
			number = 3;
			condition3.signal();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void loopC(int totalLoop) {
		lock.lock();

		try {

			// 1. 判断
			if (3 != number) {
				condition3.await();
			}

			// 2. 打印
			for (int i = 1; i <= 5; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
			}

			// 3. 唤醒
			number = 1;
			condition1.signal();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

}
