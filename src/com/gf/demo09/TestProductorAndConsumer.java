package com.gf.demo09;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition : 接口描述了可能会与锁有关联的条件变量。这些变量在用上与使用 Object.wait 访问的隐式监视器类似，但提供了更强大的功能。
 *             需要特别指出的是单个Lock可能与多个Condition对象关联。为了避免兼容性问题，Condition方法名称与对应的Object版本的不同。
 * 
 *             在Condition 对象中，与wait、notify和notifyAll方法对应的分别是await、signal和 signalAll。
 * 
 *             Condition实例实质上被绑定到一个锁上。要为特定Lock实例获得 Condition 实例 请使用 newCondition()方法。
 * 
 * 
 */
public class TestProductorAndConsumer {

	public static void main(String[] args) {
		Clerk clerk = new Clerk();
		
		Productor pro = new Productor(clerk);
		Consumer cus = new Consumer(clerk);
		
		new Thread(pro,"生产者A").start();
		new Thread(cus,"消费者B").start();
		
		new Thread(pro,"生产者C").start();
		new Thread(cus,"消费者D").start();
	}
	
}

//店员
class Clerk{
	private int product = 0;
	
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	//进货
	public void get(){
		lock.lock();
		
		while(1 <= product){ // if 改为 while 解决虚假唤醒问题，应该总是使用在循环中
			System.out.println("产品已满！");
			try {
				condition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(Thread.currentThread().getName()+" : " + ++product);
		condition.signalAll();
	}
	
	//卖货
	public void sale(){
		lock.lock();
		
		while(0 >= product){ // if 改为 while 解决虚假唤醒问题，应该总是使用在循环中
			System.out.println("缺货！");
			try {
				condition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(Thread.currentThread().getName()+" : " + --product);
		condition.signalAll();
	}
}

//生成者
class Productor implements Runnable{
	
	private Clerk clerk;
	
	public Productor(Clerk clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(200); // 模拟网络延迟
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			clerk.get();
		}
	}
	
}

//消费者
class Consumer implements Runnable{
	
	private Clerk clerk;
	
	public Consumer(Clerk clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			clerk.sale();
		}
	}
	
}
