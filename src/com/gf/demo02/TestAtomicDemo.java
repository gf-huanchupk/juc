package com.gf.demo02;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一、i++ 的原子性问题：i++ 的操作实际上为三个步骤 “读-改-写”
 * 			int i = 10;
 * 			i = i++; //结果为10
 * 			
 *          int temp = i;
 *          i = i+1;
 *          i = temp;
 * 
 * 二、原子变量：jdk1.5 后java.util.concurrent.atomic 包下提供了常用的原子变量：
 * 			1. volatile 保证内存可见性
 *          2. CAS (Compare-And-Swap) 算法保证数据的原子性
 *             CAS 算法是硬件对于并发操作共享数据的支持
 *             CAS 包括三个操作数：
 *             内存值 V
 *             预估值 A
 *             更新值 B
 *             当且仅当V==A 时，操作V=B，否则不做任何操作
 *             
 */
public class TestAtomicDemo {
	
	public static void main(String[] args) {
		AtomicDemo ad = new AtomicDemo();
		
		for(int i = 0 ; i < 10 ; i++){
			new Thread(ad).start();
		}
	}

}

class AtomicDemo implements Runnable{
	
	//private volatile int serialNumber = 0;
	//解决方法
	private AtomicInteger serialNumber = new AtomicInteger(0);
	
	@Override
	public void run() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getName() + ":" + getSerialNumner());
	}
	
	public int getSerialNumner(){
		//return serialNumber++;
		return serialNumber.incrementAndGet();
	}
	
}
