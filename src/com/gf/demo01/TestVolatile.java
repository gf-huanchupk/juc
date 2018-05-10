package com.gf.demo01;

/**
 * 一、volatile 关键字：但多个线程进行操作共享数据时，可以保证内存中数据可见性。
 *
 */
public class TestVolatile {

	public static void main(String[] args) {
		ThreadDemo td = new ThreadDemo();
		new Thread(td).start();
		
		while(true){
			if(td.isFlag()){
				System.out.println("----------");
				break;
			}
		}
		
		//解决方法1: 加同步锁,但是效率低
		/**
		while(true){
			synchronized(td){
				if(td.isFlag()){
					System.out.println("----------");
					break;
				}
			}
			
		}*/
		
	}
	
}

class ThreadDemo implements Runnable{

	//private boolean flag = false;
	
	//解决方法二 : 使用volatile
	private volatile boolean flag = false;
	
	@Override
	public void run() {
		try {
			Thread.sleep(200);
			flag = true;
			System.out.println(flag);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
}

