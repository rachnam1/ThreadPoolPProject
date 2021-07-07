package com.concurrency.threadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Worker extends Thread{
	
	BlockingQueue<Task> blockingQueue;
	
	
	Worker(BlockingQueue<Task> blockingQueue){
		this.blockingQueue = blockingQueue;
	}
	
	public void run(){
		while(!MyThreadPool.isStopped){
			try {
				Task task = blockingQueue.take();
				task.run();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}


class Task implements Runnable{

	String taskName;
	
	Task(String taskname){
		this.taskName = taskname;
	}
	
	@Override
	public void run() {
		System.out.println("Task executed" + Thread.currentThread().getName() + "Task Name" + taskName);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

public class MyThreadPool {

	Worker[] workerThread;
	BlockingQueue<Task> blockingQueuue = new LinkedBlockingQueue<Task>();
	static volatile boolean isStopped = false;
	
	MyThreadPool(int maxThreads){
		
		workerThread = new Worker[maxThreads];
		
		int i=0;
		while(maxThreads >i){
			workerThread[i] = new Worker(blockingQueuue);
			workerThread[i].start();
			i++;
		}
		
	}
	
	public void executeTask(Task task){
		try {
			blockingQueuue.put(task);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void shutDownPool(){
		
		while(!blockingQueuue.isEmpty()){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		isStopped = true;
		for(Worker worker : workerThread){
			worker.interrupt();
		}
	}
	
	
	
	public static void main(String[] args) {
		MyThreadPool threadPool = new MyThreadPool(10);
		threadPool.executeTask(new Task("Task1"));
		threadPool.executeTask(new Task("Task2"));
		threadPool.executeTask(new Task("Task3"));
		threadPool.executeTask(new Task("Task4"));
		threadPool.executeTask(new Task("Task5"));
		
		threadPool.executeTask(new Task("Task6"));
		threadPool.executeTask(new Task("Task7"));
		threadPool.executeTask(new Task("Task8"));
		threadPool.executeTask(new Task("Task9"));
		
		
		threadPool.executeTask(new Task("Task10"));
		threadPool.executeTask(new Task("Task11"));
		threadPool.executeTask(new Task("Task12"));
		threadPool.executeTask(new Task("Task13"));
		
		threadPool.executeTask(new Task("Task14"));
		threadPool.executeTask(new Task("Task15"));
		threadPool.executeTask(new Task("Task16"));
		threadPool.executeTask(new Task("Task17"));

	}

}
