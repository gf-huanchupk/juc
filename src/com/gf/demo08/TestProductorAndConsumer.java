package com.gf.demo08;

/**
 * 生成者消费者案例，演示虚假唤醒问题
 *  解决方式：if 改为 while
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
	
	//进货
	public synchronized void get(){
		while(1 <= product){ // if 改为 while 解决虚假唤醒问题，应该总是使用在循环中
			System.out.println("产品已满！");
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(Thread.currentThread().getName()+" : " + ++product);
		this.notifyAll();
	}
	
	//卖货
	public synchronized void sale(){
		while(0 >= product){ // if 改为 while 解决虚假唤醒问题，应该总是使用在循环中
			System.out.println("缺货！");
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(Thread.currentThread().getName()+" : " + --product);
		this.notifyAll();
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
