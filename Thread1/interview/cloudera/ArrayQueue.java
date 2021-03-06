package cloudera;

import java.util.NoSuchElementException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class ArrayQueue<T> {
	
	private Object[] elements;
	private static final int DEFAULT_CAPACITY = 6;	
	private int front, back, nItems;
	
	private Lock lock = new ReentrantLock();
	private Condition notEmpty;
	private Condition notFull;
	
	public ArrayQueue() {
		elements = new Object[DEFAULT_CAPACITY];
		front = 0;
		back = -1;
		nItems = 0;
		notEmpty = lock.newCondition();
		notFull = lock.newCondition();
	}
	
	public synchronized void enqueue(T item) throws InterruptedException {
		if (isFull()) {
			notFull.await();
		}
		lock.lock();
		try {
			back++;
			elements[back % elements.length] = item;
			nItems++;
			notEmpty.signalAll();
		} finally {
			lock.unlock();
		}	
	}
	
	public T dequeue() throws InterruptedException {
		if (isEmpty()) {
			notEmpty.await();
		}
		lock.lock();
		try {
			int index = front % elements.length;
			@SuppressWarnings("unchecked")
			T item = (T) elements[index];
			elements[index] = null;
			front++;
			nItems--;
			notFull.signalAll();
			return item;
		} finally {
			lock.unlock();
		}
	}
	
	@SuppressWarnings("unchecked")
	public T peekFront() throws InterruptedException {
		if (isEmpty()) {
			notEmpty.await();
		}
		return (T) elements[front % elements.length];
		
	}
	
	public boolean isEmpty() {
		return nItems == 0;
	}
	
	private boolean isFull() {
		return nItems == elements.length;
	}

	public static void main(String[] args) throws InterruptedException {
		ArrayQueue<Integer> queue = new ArrayQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(3);
        System.out.println(queue.dequeue()); // 1
        System.out.println(queue.peekFront()); // 3
        queue.enqueue(2);
        queue.enqueue(10);
        queue.enqueue(5);
        queue.enqueue(4);
        queue.enqueue(8);
        System.out.println(queue.peekFront()); // 3
        System.out.println(queue.dequeue()); // 3
        System.out.println(queue.peekFront()); // 2
        queue.enqueue(9);
        //queue.enqueue(11); // exception (Queue is full)
	}

}
