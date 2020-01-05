package cs2321;

import java.util.Iterator;
import cs2321.DoublyLinkedList.Node;
import net.datastructures.List;

/**
 * Implementation of the Array List Data Structure
 * 
 * @author SiddheshDM
 * Date last modified: Sept 17, 2019
 * CS 2321: Fall 2019
 */

public class ArrayList<E> implements List<E> {

	// instance variables
	public int CAPACITY = 16;		// default array capacity
	public static int DEFAULTCAPACITY = 16;
	private E[] data;							// generic array used for storage
	private int size = 0;						// current number of elements

	public ArrayList() {						// constructs list with default capacity
		this(DEFAULTCAPACITY);
	}

	public ArrayList(int capacity) {			// constructs list with given capacity
		data = (E[]) new Object[capacity];
	}

	@Override
	/** Returns the number of elements in the ArrayList */
	public int size() {
		return size;
	}

	@Override
	/** Returns whether the ArrayList is empty */
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	/** Returns, but does not remove, the element at index i */
	public E get(int i) throws IndexOutOfBoundsException {
		checkIndex(i, size);
		return data[i];
	}

	@Override
	/** Replaces the element at index i with e, and returns the replaced element */
	public E set(int i, E e) throws IndexOutOfBoundsException {
		checkIndex(i, size);
		E temp = data[i];
		data[i] = e;
		return temp;
	}

	@Override
	/** Inserts element e to be at index i, shifting all subsequent elements later */
	public void add(int i, E e) throws IndexOutOfBoundsException, IllegalStateException {
		checkIndex(i, size + 1);	
		doubleCAP();								// not enough capacity
		if(size == data.length) {
			throw new IllegalStateException("Array is full");
		}
		for (int k = size - 1;k>= i; k--) {							// start by shifting rightmost
			data[k+1] = data[k];
			// ready to place the new element
		}
		data[i] = e;
		size++;		
	}

	@Override
	/** Removes/returns the element at index i, shifting subsequent elements earlier */
	public E remove(int i) throws IndexOutOfBoundsException {
		checkIndex(i, size);
		E temp = data[i];
		for (int k = i; k < size - 1; k++) {						// shift elements to fill hole
			data[k] = data[k+1];
			// help garbage collection

		}
		data[size-1] = null;	
		size--;
		return temp;
	}

	@Override
	/** Serves as the iterator method for this class */
	public Iterator<E> iterator() {
		Iterator <E> iterator1 = new Iterator<E>() {
			private int location = 0;
			@Override
			public boolean hasNext() {
				return location < size;
			}
			@Override
			public E next() {
				E ret = data[location];
				location++;
				return ret;
			}
		};
		return iterator1;
	}

	/**
	 * Used to add an element to the front of the list
	 * @param e: takes in an E type parameter
	 */
	public void addFirst(E e)  {
		doubleCAP();
		add (0,e);
	}

	/**
	 * Used to add an element to the rear end of the list
	 * @param e: takes in an E type parameter
	 */
	public void addLast(E e)  {
		doubleCAP();
		add (size, e);
	}

	/**
	 * Used to remove the first element of the list
	 * @return: returns the new first element E type
	 * @throws IndexOutOfBoundsException: might throw this exception if the list is empty
	 */
	public E removeFirst() throws IndexOutOfBoundsException {
		E temp = data[0];
		remove(0);
		return temp;
	}

	/**
	 * Used to remove the last element of the list
	 * @return: returns the new last element E type
	 * @throws IndexOutOfBoundsException: might throw this exception if the list is empty
	 */
	public E removeLast() throws IndexOutOfBoundsException {
		E temp = data[size-1];
		remove(size-1);
		return temp;
	}

	/** Doubles the capacity of the list if the maximum capacity has been reached */
	public void doubleCAP() {
		if (size == CAPACITY) {
			CAPACITY *= 2;
			E [] temp;
			temp = (E[])new Object[CAPACITY];

			for ( int i = 0; i < size; i++) {
				temp[i] = data [i];
			}

			data = temp;
		}
	}



	/** Checks whether the given index is in the range [0, n-1] */
	protected void checkIndex(int i, int n) throws IndexOutOfBoundsException {
		if (i < 0 || i >= n) {
			throw new IndexOutOfBoundsException("Illegal index: " + i);
		}
	}

	public Object capacity() {
		return CAPACITY;
	}

}
