package cs2321;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.datastructures.List;

/**
 * @author Siddhesh Mahadeshwar
 * Date Modified: 11/11/2019
 */

public class ArrayList<E> implements List<E> {

	//instance variables
	public static final int CAPACITY = 16;
	private E[] data;
	private int size = 0;

	//Constructor
	@SuppressWarnings("unchecked")
	public ArrayList(int capacity) {
		size = 0;
		data = (E[]) new Object[capacity];
	}

	//Constructor
	public ArrayList() {
		this(CAPACITY);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public E get(int i) throws IndexOutOfBoundsException {
		//Checks to make sure the index is valid
		checkIndex(i, size);
		return data[i];
	}

	@Override
	public E set(int i, E e) throws IndexOutOfBoundsException {
		//Checks to make sure the index is valid
		checkIndex(i, size);

		//Creates temp variable for the element at given index
		E var = data[i];

		//Replaces the old element with the new element
		data[i] = e;
		return var;
	}

	@Override
	public void add(int i, E e) throws IndexOutOfBoundsException{
		//Checks to make sure the index is valid
		checkIndex(i, size+1);

		//If array is full, double it
		if (size == data.length) {
			resize(2 * data.length);
		}

		//Shift all elements after given index
		for (int k = size - 1; k >= i; k--) {
			data[k+1] = data[k];
		}

		//Replaces the old element with the new element
		data[i] = e;

		//Increases size by 1
		size++;
	}

	@Override
	public E remove(int i) throws IndexOutOfBoundsException {
		//Checks to make sure the index is valid
		checkIndex(i, size);

		//Creates temp variable to store element being removed 
		E temp = data[i];

		//Moves all elements in array list 1 step closer to the front
		for (int t = i;t < size - 1; t++) {
			data[t] = data[t+1];
		}
		//Sets last element to null
		data[size - 1] = null;

		//Decreases size by 1
		size--;
		return temp;
	}

	/**
	 * Adds element e to the beginning of the array list
	 * @param  e   the element being added
	 */

	public void addFirst(E e)  {
		//If array is full, double it
		if (size == data.length) {
			resize(2 * data.length);
		}
		//Moves each element down 1
		for (int k = size - 1; k <= 0; k--) {
			data[k] = data[k+1];
		}
		//Sets the element to the beginning of the array list
		data[0] = e;

		//Increases size by 1
		size++;
	}

	/**
	 * Returns an iterator of the elements stored in the list
	 * @return the iterator of the elements in the list
	 */

	@Override
	public Iterator<E> iterator() {
		return new ArrayIterator();
	}

	/**
	 * Adds element e to the end of the array list
	 * @param  e   the element being added
	 */

	public void addLast(E e)  {
		//If array is full, double it
		if (size == data.length) {
			resize(2 * data.length);
		}
		//Adds element to end of the array
		data[size] = e;

		//Increases size by 1
		size++;
	}

	/**
	 * Removes and returns the first element of the array list, shifting all subsequent
	 * elements in the list one position closer to the front.
	 * @return the element that had been stored at the front of the array list
	 * @throws IndexOutOfBoundsException if the index is negative or greater than size()
	 */

	public E removeFirst() throws IndexOutOfBoundsException {
		//Creates temp variable to store first element being removed
		E temp = data[0];
		//Shifts all elements up 1
		for (int t = 0;t < size - 1; t++) {
			data[t] = data[t+1];
		}
		//Setting the last element to null
		data[size - 1] = null;

		//Decreases size by 1
		size--;
		return temp;
	}

	/**
	 * Removes and returns the last element of the array list
	 * @return the last element of the array list
	 * @throws IndexOutOfBoundsException if the index is negative or greater than size()
	 */

	public E removeLast() throws IndexOutOfBoundsException {
		//Creates temp variable for the last element in the array list being removed
		E temp = data[size - 1];

		//Setting the last element to null
		data[size - 1] = null;

		//Decreases size by 1
		size--;
		return temp;
	}

	/**
	 * Resizes the array to have given capacity >= size
	 * @param  capacity	the new size of the array list
	 */

	@SuppressWarnings("unchecked")
	protected void resize(int capacity) {
		//Creates new array list with new size
		E[] temp = (E[]) new Object[capacity];

		//Moves old array to new array
		for (int t = 0; t < size; t++) {
			temp[t] = data[t];
		}
		//Returns new array
		data = temp;
	}

	/**
	 * Checks whether the given index is in the range of [0, n - 1]
	 * @param  i   the index to be checked
	 * @param  n   the size of the array
	 * @throws IndexOutOfBoundsException if the index is negative or greater than size()
	 */

	protected void checkIndex(int i, int n) throws IndexOutOfBoundsException {
		//Checks to make sure index is valid
		if (i < 0 || i >= n) {
			throw new IndexOutOfBoundsException("Illegal index: " + i);
		}
	}

	// Return the capacity of array, not the number of elements.
	// Notes: The initial capacity is 16. When the array is full, the array should be doubled. 

	/**
	 * Returns the capacity of the array list
	 * @return the length of the data stored
	 */

	public int capacity() {
		return data.length;
	}

	private class ArrayIterator implements Iterator<E> {
		//Instance variables
		private int j = 0;
		private boolean removable = false;

		@Override
		public boolean hasNext() {
			//True if j < size, false if otherwise
			return j < size;
		}

		@Override
		public E next() throws NoSuchElementException{
			//Checks if there is a next element
			if (j == size) throw new NoSuchElementException("No next element");
			//Sets removable to true indicating it can be removed
			removable = true;
			//Returns next element
			return data[j++];
		}

		public void remove() throws IllegalStateException {
			//Checks to see if element can be removed
			if (!removable) throw new IllegalStateException("Nothing to remove");
			//Remove given element
			ArrayList.this.remove(j - 1);
			//Decreases j by 1
			j--;
			//Sets removable to false
			removable = false;
		}
	}
}
