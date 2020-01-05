package cs2321;

import net.datastructures.Queue;

/**
 * Implementation of the Circular Array Queue Data Structure
 * 
 * @author ruihong-adm
 * @author Siddhesh Mahadeshwar
 * Date last modified: Sept 17, 2019
 * CS 2321: Fall 2019
 */

public class CircularArrayQueue<E> implements Queue<E> {

	// Instance variables
	private E[] data;		// generic array used for storage
	private int frontIndex = 0;		// index of the front element
	private int size1 = 0;	// current number of elements
	public static final int QUEUESIZE = 16;		// current size of queue

	public CircularArrayQueue() {
		this(QUEUESIZE);	// constructs queue with default queue size
	}

	public CircularArrayQueue(int queueSize) {		//constructs queue with given capacity
		data = (E[])new Object[queueSize];	
	}

	@Override
	/** Returns the number of elements in the queue */
	public int size() {	
		return size1;
	}

	@Override
	/** Tests whether the queue is empty */
	public boolean isEmpty() {
		return (size1 == 0);
	}

	@Override
	/** Inserts an element at the rear end of the queue */
	public void enqueue(E e) throws IllegalStateException {
		if (size1 == data.length) throw new IllegalStateException("Queue is full");
		int available = (frontIndex + size1) % data.length;
		data[available] = e;
		size1++;

	}

	@Override
	/** Returns, but does not remove, the first element of the queue */
	public E first() {
		if (isEmpty()) {
			return null;
		}
		return data[frontIndex];
	}

	@Override
	/** Removes and returns the first element of the queue (null if empty) */
	public E dequeue() {
		if (isEmpty()) {
			return null;
		}
		E answer = data[frontIndex];
		data[frontIndex] = null;
		frontIndex = (frontIndex + 1) % data.length;
		size1--;
		return answer;
	}
}
