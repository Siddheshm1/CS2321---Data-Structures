package cs2321;
import java.util.Iterator;

import net.datastructures.*;

/**
 * @author Siddhesh Mahadeshwar
 * Date Modified: 11/11/2019
 */

public class LinkedBinaryTree<E> extends AbstractBinaryTree<E>{

	protected static class Node<E> implements Position<E> {
		private E element;
		private Node<E> parent;
		private Node<E> left;
		private Node<E> right;

		public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
			element = e;
			parent = above;
			left = leftChild;
			right = rightChild;
		}
		@Override
		public E getElement() {
			return element;
		}
		public Node<E> getParent() {
			return parent;
		}
		public Node<E> getLeft() {
			return left;
		}
		public Node<E> getRight() {
			return right;
		}
		public void setElement(E e) {
			element = e;
		}
		public void setParent(Node<E> parentNode) {
			parent = parentNode;
		}
		public void setLeft(Node<E> leftChild) {
			left = leftChild;
		}
		public void setRight(Node<E> rightChild) {
			right = rightChild;
		}
	}

	protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
		return new Node<E>(e, parent, left, right);
	}

	protected Node<E> root = null;
	private int size = 0;

	public LinkedBinaryTree() { }

	/** Validates the position and returns it as a node. */
	protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
		if (!(p instanceof Node)) {
			throw new IllegalArgumentException("Not valid position type");
		}
		Node<E> node = (Node<E>) p;
		if (node.getParent() == node) {
			throw new IllegalArgumentException("p is no longer in the tree");
		}
		return node;
	}

	@Override
	public Position<E> root() {
		return root;
	}

	/** Returns the Position of p's parent (or null if p is root). */
	@Override
	public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getParent();
	}

	@Override
	public Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException {
		List<Position<E>> snapshot = new ArrayList<>(2);
		if (left(p) != null) {
			snapshot.add(snapshot.size(), left(p));
		}
		if (right(p) != null) {
			snapshot.add(snapshot.size(), right(p));
		}
		return snapshot;
	}

	@Override
	/* count only direct child of the node, not further descendant. */
	public int numChildren(Position<E> p) throws IllegalArgumentException {
		int count = 0;
		if (left(p) != null) {
			count++; 
		}
		if (right(p) != null) {
			count++; 
		}
		return count;
	}

	@Override
	public boolean isInternal(Position<E> p) throws IllegalArgumentException {
		return numChildren(p) > 0; 
	}

	@Override
	public boolean isExternal(Position<E> p) throws IllegalArgumentException {
		return numChildren(p) == 0; 
	}

	@Override
	public boolean isRoot(Position<E> p) throws IllegalArgumentException {
		return p == root();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Position<E>> positions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position<E> left(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getLeft();
	}

	@Override
	public Position<E> right(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getRight();
	}

	@Override
	public Position<E> sibling(Position<E> p) throws IllegalArgumentException {
		Position<E> parent = parent(p);
		if (parent == null) {
			return null;
		}
		if (p == left(parent)) {
			return right(parent);
		} else {
			return left(parent);
		}
	}

	/* creates a root for an empty tree, storing e as element, and returns the 
	 * position of that root. An error occurs if tree is not empty. 
	 */
	public Position<E> addRoot(E e) throws IllegalStateException {
		if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
		root = createNode(e, null, null, null);
		size = 1;
		return root;
	}

	/* creates a new left child of Position p storing element e, return the left child's position.
	 * If p has a left child already, throw exception IllegalArgumentExeption. 
	 */
	public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);
		if (parent.getLeft() != null) {
			throw new IllegalArgumentException("p already has a left child");
		}
		Node<E> child = createNode(e, parent, null, null);
		parent.setLeft(child);
		size++;
		return child;
	}

	/* creates a new right child of Position p storing element e, return the right child's position.
	 * If p has a right child already, throw exception IllegalArgumentExeption. 
	 */
	public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);
		if (parent.getRight() != null) {
			throw new IllegalArgumentException("p already has a right child");
		}
		Node<E> child = createNode(e, parent, null, null);
		parent.setRight(child);
		size++;
		return child;
	}

	/* Attach trees t1 and t2 as left and right subtrees of external Position. 
	 * if p is not external, throw IllegalArgumentExeption.
	 */
	public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2)
			throws IllegalArgumentException {
		Node<E> node = validate(p);
		if (isInternal(p)) throw new IllegalArgumentException("p must be a leaf");
		size += t1.size() + t2.size();
		if (!t1.isEmpty()) {
			t1.root.setParent(node);
			node.setLeft(t1.root);
			t1.root = null;
			t1.size = 0;
		}
		if (!t2.isEmpty()) {
			t2.root.setParent(node);
			node.setLeft(t2.root);
			t2.root = null;
			t2.size = 0;
		}

	}

	public E remove(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		if(numChildren(p) == 2) {
			throw new IllegalArgumentException("p has two children");
		}
		Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
		if (child != null) {
			child.setParent(node.getParent());
		}
		if (node == root) {
			root = child;
		} else {
			Node<E> parent = node.getParent();
			if (node == parent.getLeft()) {
				parent.setLeft(child);
			} else {
				parent.setRight(child);
			}
		}
		size--;
		E temp = node.getElement();
		node.setElement(null);
		node.setLeft(null);
		node.setRight(null);
		node.setParent(null);
		return temp;
	}
	
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		E temp = node.getElement();
		node.setElement(e);
		return temp;
	}
}
