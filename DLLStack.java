package cs2321;

import net.datastructures.Stack;

/**
 * 
 * @author SiddheshDM
 * Date last modified: Sept 24, 2019
 * 
 * The Stack implementation based on a DoublyLinkedList
 * 
 */

public class DLLStack<E> implements Stack<E> {
	
	DoublyLinkedList<E> DoublyList = new DoublyLinkedList<E>();

	/**
	 * Returns the size of the stack
	 */
	@Override
	public int size() {
	
		return DoublyList.size();
	}

	/**
	 * Identifies if the stack is empty
	 */
	@Override
	public boolean isEmpty() {
	
		return size() == 0;
	}

	/**
	 * Adds elements to the stack
	 */
	@Override
	public void push(E e) {
		
		DoublyList.addFirst(e);
	}

	/**
	 * Grabs the top value in the stack
	 */
	@Override
	public E top() {
		if (DoublyList.isEmpty()) {
			return null;
		}
		return DoublyList.first().getElement();
	}

	/**
	 * Pops out/removes an element from the stack 
	 */
	@Override
	public E pop() {
		if (DoublyList.isEmpty()) {
			return null;
		}
		return DoublyList.removeFirst();
	}
	

}
