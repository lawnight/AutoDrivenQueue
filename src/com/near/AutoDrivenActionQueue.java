package com.near;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * self driven action queue. any action put in this queue.it's will
 * automatically execute.different selfDrivenActionQueue will Concurrent
 * execution,but all actionQueue will use the same ExecutorService.
 * 
 * @author near
 */
public class AutoDrivenActionQueue implements Runnable {

	private static ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
			new SynchronousQueue<Runnable>());

	private Queue<IAction> taskQueue = null;

	private ReentrantLock runningLock = null;

	// the value specify the action is too slow
	private static int SlowTime = 100;

	/**
	 *
	 * 
	 * @param exeService
	 */
	public AutoDrivenActionQueue() {
		this.taskQueue = new LinkedList<IAction>();
		this.runningLock = new ReentrantLock();
	}

	/**
	 * 
	 * 
	 */
	public void add(final IAction task) {
		try {
			this.runningLock.lock();

			if (this.taskQueue.isEmpty()) {
				// the taskqueue is empty so there's no task is running.
				this.taskQueue.add(task);
				this.executorService.execute(this);
			} else {
				// if some task is runting just add the queue and wait
				this.taskQueue.add(task);
			}
		} finally {
			this.runningLock.unlock();
		}
	}

	/*
	 * 
	 * self drive function
	 */
	@Override
	public void run() {
		if (!taskQueue.isEmpty()) {
			long startTime = System.currentTimeMillis();
			IAction action = this.taskQueue.peek();
			try {
				action.execute();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				long endTime = System.currentTimeMillis();
				complete();
				long currentRuntime = endTime - startTime;
				if (currentRuntime > SlowTime) {
					System.err.println("action run too slow:" + action);
				}
			}
		}
	}

	/**
	 * 
	 */
	private void complete() {
		try {
			this.runningLock.lock();
			this.taskQueue.poll();
			if (!this.taskQueue.isEmpty()) {
				this.executorService.execute(this);
			}
		} finally {
			this.runningLock.unlock();
		}
	}
}
