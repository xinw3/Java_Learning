package purestorage;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

import javax.security.auth.callback.Callback;

public class Event {
	

	boolean fired = false;
	Queue<Callback> queue = new LinkedList<Callback>();
	// Object lock = new Object();
	// mutex is the semaphore with an access count of 1.
	Semaphore mutex = new Semaphore(1);
	
	/*
	 *  Sequential version
	 */
	public void register(Callback cb) {
		if (!fired) {
			queue.offer(cb);
		} else {
			cb.invoke();
		}
	}
	
	public void fire() {
		while(!queue.isEmpty()) {
			Callback cb = queue.poll();
			cb.invoke();
		}
		fired = true;
	}
	
	/*
	 *  Multi-threading version 1
	 *  We need to protect shared variables 
	 *  so that only one thread can access them at a time.
	 *  However, this version is slow because it makes the two
	 *  functions atomic so that when one thread executing a function
	 *  another function would never be executed.
	 */
//	public void register(Callback cb) {
//		synchronized(lock) {
//			if (fired) {
//				queue.offer(cb);
//			} else {
//				cb.invoke();
//			}
//		}
//	}
//	
//	public void fire() {
//		synchronized(lock) {
//			while (!queue.isEmpty()) {
//				Callback cb = queue.poll();
//				cb.invoke();
//			}
//			fired = true;
//		}
//		
//	}

	/*
	 *  Multi-threading version 2
	 *  Using mutex to control the access of the resources.
	 */
	public void register(Callback cb) {
		mutex.acquire();
			if (fired) {
				queue.offer(cb);
				mutex.release();
			} else {
				mutex.release();
				cb.invoke();
			}
		
	}
	
	public void fire() {
		mutex.acquire();
			while (!queue.isEmpty()) {
				Callback cb = queue.poll();
				mutex.release();
				cb.invoke();
			}
			fired = true;
			mutex.release();
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Event e = new Event();
		

	}

}
