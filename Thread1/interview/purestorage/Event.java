package purestorage;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.security.auth.callback.Callback;

public class Event {
	

	boolean fired = false;
	Queue<Callback> queue = new LinkedList<Callback>();
	
	//private Object lock = new Object();
	
	// mutex is the semaphore with an access count of 1.
	// Semaphore mutex = new Semaphore(1);
	
	private Lock lock = new ReentrantLock();
	
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
	 *  However, this version can cause deadlock because register
	 *  function can be callback of another register.
	 */
	public void register(Callback cb) {
		synchronized(lock) {
			if (fired) {
				queue.offer(cb);
			} else {
				cb.invoke();
			}
		}
	}
	
	public void fire() {
		synchronized(lock) {
			while (!queue.isEmpty()) {
				Callback cb = queue.poll();
				cb.invoke();
			}
			fired = true;
		}
	}

	/*
	 *  Multi-threading version 2
	 *  Using mutex to control the access of the resources.
	 *  Semaphore just control the access to a certain resource
	 *  but there is another problem that semaphore can release permit
	 *  more than the declared permits. 
	 */
	public void register(Callback cb) {
		mutex.acquire();
		if (fired) {
			queue.offer(cb);
			mutex.release();
		} else {
			// why do we put release before the invoke?
			// one callback may be the register of another event
			// in such way, if we first invoke, the register function
			// will be called, but it would never acquire the lock
			// and will cause the deadlock situation.
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
			mutex.acqurie();
		}
		fired = true;
		mutex.release();
	}
	
	/*
	 *  Multi-threading version 3
	 *  Using reentrant lock
	 *  it is not the same as the mutex because it allows one
	 *  thread access the same resources more than once.
	 *  but I think it is applicable for this situation.
	 */
	public void register(Callback cb) {
		lock.lock();
		if (fired) {
			queue.offer(cb);
			lock.unlock();
		} else {
			lock.unlock();
			cb.invoke();
		}
	}
	
	public void fire() {
		lock.lock();
		while (!queue.isEmpty()) {
			Callback cb = queue.poll();
			lock.unlock();
			cb.invoke();
			lock.lock();
		}
		fired = true;
		mutex.unlock();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Event e = new Event();
		

	}
}
