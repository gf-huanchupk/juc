package com.gf.demo04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList/CopyOnWriteArrySet : “写入并复制”
 * 注意 : 添加操作多时，效率低，因为每次添加都会进行复制，开销很大。并发迭代操作多时可以选择。
 */
public class TestCopyOnWriteArrayList {
	
	public static void main(String[] args) {
		HelloThead ht = new HelloThead();
		
		for (int i = 0; i < 10; i++) {
			new Thread(ht).start();
		}
	}

}

class HelloThead implements Runnable{
	
	//private static List<String> list = Collections.synchronizedList(new ArrayList<String>());//存在ConcurrentModificationException : 并发修改异常 
	
	private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
 	
	static{
		list.add("AA");
		list.add("BB");
		list.add("CC");
	}

	@Override
	public void run() {
		Iterator<String> it = list.iterator();
		
		while (it.hasNext()) {
			System.out.println(it.next());
			list.add("AA");
		}
	}
	
}