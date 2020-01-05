package cs2321;
import java.util.Iterator;
import net.datastructures.Position;
import net.datastructures.PositionalList;

/**
 * Implementation of the Doubly Linked List Data Structure
 * 
 * @author SiddheshDM
 * Date last modified: Sept 17, 2019
 * CS 2321: Fall 2019
 */

public class DoublyLinkedList<E> implements PositionalList<E> {
	public static class Node<E> implements Position<E>{
		private E element; 							// the element stored in this node
		private Node<E> prev; 						// the previous node in the list
		private Node<E> next; 						// the next/subsequent node in the list
		public Node(E e, Node<E> p, Node<E> n) {
			element = e;
			prev = p;
			next = n;
		}
		public E getElement() {return element;}
		public void setElement (E e) {
			element = e;
		}

		public Node<E> getPrev() {return prev;}
		public Node<E> getNext() {return next;}
		public void setPrev (Node<E> p) {prev = p;}
		public void setNext (Node<E> n) {next = n;}
	}

	// instance variables of the DoublyLinkedList
	private Node<E> header;						// header sentinel
	private Node<E> trailer;					// trailer sentinel
	private int size = 0;						// number of elements in the list

	/** Construct a new empty list here */
	public DoublyLinkedList() {					
		header = new Node<> (null, null, null);		// create header
		trailer = new Node<> (null, header, null);	// trailer is preceded by header
		header.setNext(trailer);					// header if followed by trailer
	}

	@Override
	/** Returns the number of elements in the list */
	public int size() {
		return size;
	}

	@Override
	/** A method to test if the list is empty */
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	/** Returns the first element in the list but does not remove it */
	public Position<E> first() {
		if (isEmpty()) return null;
		return header.getNext();			// first element is beyond the header
	}

	@Override
	/** Returns the last element in the list but does not remove it */
	public Position<E> last() {
		if (isEmpty()) return null;
		return trailer.getPrev();			// last element is before the trailer
	}

	/**
	 * Validates if position p is an instance of a Node
	 * @param p: takes in a position as a parameter
	 * @return: returns a Node by casting Node to the position parameter that was taken in
	 * @throws IllegalArgumentException
	 */
	private Node<E> validate (Position<E> p) throws IllegalArgumentException {
		if (!(p instanceof Node)) 
			throw new IllegalArgumentException("Invalid p");
		return (Node)p;

	}

	/**
	 * Used to find out the respective position of a node
	 * @param: takes in a node
	 * @return: returns the position of the node
	 */
	private Position<E> position (Node<E> node){
		if (node == header || node == trailer) {
			return null;
		}
		return node;
	}

	@Override
	/**
	 * Takes in the position of a node
	 * @return: returns the node before the node that the method was called on
	 */
	public Position<E> before(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return position(node.getPrev());
	}

	@Override
	/**
	 * Takes in the position of a node
	 * @return: returns the node after the node that the method was called on
	 */
	public Position<E> after(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return position(node.getNext());
	}

	@Override
	/** 
	 * Adds an element to the beginning of the list, right after the header
	 * @param: takes in an E type
	 * @return: returns a position
	 */
	public Position<E> addFirst(E e) {
		Node<E> newFirst = addBetween(e, header, header.getNext());
		return newFirst;// place just after the header

	}

	@Override
	/** 
	 * Add the E type element to the rear end of the list
	 * @param: takes in an E type
	 * @return: returns the position of the node
	 */
	public Position<E> addLast(E e) {
		Node<E> newLast = addBetween (e, trailer.getPrev(), trailer); 	// place just before the trailer
		return newLast;
	}


	@Override
	/**
	 * Takes in a position and an E and adds that before the Node that the method was called on
	 * @param: takes in a position and an E
	 * @return: returns a position
	 */
	public Position<E> addBefore(Position<E> p, E e)
			throws IllegalArgumentException {
		Node<E> addNodeBefore = addBetween(e, validate(p).getPrev(), validate(p) );		
		return null;
	}

	@Override
	/**
	 * Takes in a position and an E and adds that after the Node that the method was called on
	 * @param: takes in a position and an E
	 * @return: returns a position
	 */
	public Position<E> addAfter(Position<E> p, E e)
			throws IllegalArgumentException {
		Node<E> addNodeAfter = addBetween(e, validate(p), validate(p).getNext());
		return null;
	}


	@Override
	/**
	 * Serves as the setter method for this class
	 */
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		E element = p.getElement();
		validate(p).setElement(e);
		return element;
	}

	@Override
	/** Removes the given node from the list and returns its element */
	public E remove(Position<E> p) throws IllegalArgumentException {
		Node<E> predecessor = validate(p).getPrev();
		Node<E> successor = validate(p).getNext();
		predecessor.setNext(successor);
		successor.setPrev(predecessor);
		size--;
		return p.getElement();
	}

	/** Adds element e to the linked list in between the given nodes */
	private Node<E> addBetween (E e, Node<E> predecessor, Node<E> successor) {
		// create and link a new node
		Node<E> newest = new Node<> (e, predecessor, successor);
		predecessor.setNext(newest);
		successor.setPrev(newest);
		size++;
		return newest;
	}

	@Override
	/** Serves as the iterator for this class */
	public Iterator<E> iterator() {
		Iterator <E> i = new Iterator<E>() {
			Node<E> tracer = (Node<E>) first();
			@Override
			public boolean hasNext() {
				return tracer.getNext()!= null;
			}

			@Override
			public E next() {
				E temp = tracer.getElement();
				tracer = tracer.getNext();
				return temp;
			}
		};
		return i;
	}

	@Override
	/** Returns the iterable object for whatever function it is needed for */
	public Iterable<Position<E>> positions() {
		DoublyLinkedList<Position<E>> temp1 = new DoublyLinkedList<Position<E>>(); 	
		Node<E> tempNode = (Node<E>)first();
		while (tempNode != trailer) {
			temp1.addLast(tempNode);
			tempNode = tempNode.getNext();
		}
		return temp1;
	}

	/** Removes and returns the first element in the list */
	public E removeFirst() throws IllegalArgumentException {
		if (isEmpty()) return null; 						// nothing to remove
		return remove(header.getNext());					// first element is beyond header
	}
	/** Removes and returns the last element in the list */
	public E removeLast() throws IllegalArgumentException {
		if (isEmpty()) return null;							// nothing to remove
		return remove(trailer.getPrev());					// last element is before trailer
	}

}
