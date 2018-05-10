package com.gf.demo06;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 一、创建线程的方式三：实现Callable接口。相较于实现 Runnable 接口的方式，方法可以有返回值，并且可以抛出异常。
 * 
 * 二、执行Callable 方式，需要FutureTask 实现类的支持，用于接受运算结果。FutureTask 是Future接口的实现类
 *
 */
public class TestCallable {
	
	public static void main(String[] args) {
		
		ThreadDemo t = new ThreadDemo();
		
		// 1. 执行Callable方式，需要FutureTask实现类的支持，用于接受运算结果。
		FutureTask<Integer> result = new FutureTask<>(t);
		new Thread(result).start();
		
		//2. 接收线程运算后的结果
		try {
			Integer sum = result.get(); //FutureTask 可用于闭锁
			System.out.println(sum);
			System.out.println("---------------------------");
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

}


class ThreadDemo implements Callable<Integer>{

	@Override
	public Integer call() throws Exception {
		int num = 0;
		for (int i = 0; i < Long.MAX_VALUE; i++) {
			num = num + i;
		}
		return num;
	}
	
}