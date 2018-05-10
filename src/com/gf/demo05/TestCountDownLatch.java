package com.gf.demo05;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch : 闭锁，在完成某些操作时，只有其他所有的线程运算全部完成，当前运算才继续向下执行。
 *
 */
public class TestCountDownLatch {

	public static void main(String[] args) {
		
		CountDownLatch latch = new CountDownLatch(5);
		LatchDemo ld =  new LatchDemo(latch);
		
		long start = System.currentTimeMillis();
		
		for (int i = 0; i < 5; i++) {
			new Thread(ld).start();
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("耗费时间为：" + (end - start));
		

	}

}

class LatchDemo implements Runnable {

	private CountDownLatch latch;

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	public LatchDemo(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			
			for (int i = 0; i < 5000; i++) {
				if (i % 2 == 0) {
					System.out.println(i);
				}
			}
		} finally {
			latch.countDown();
		}
	}

}