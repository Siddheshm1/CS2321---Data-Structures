package cs2321;

import java.util.Comparator;

import net.datastructures.Entry;
import net.datastructures.Position;

/**
 * @author Siddhesh Mahadeshwar
 * Date Modified: 11/12/2019
 */

public class BinarySearchTree<K,V> extends AbstractSortedMap<K,V> {

	
	// nested helper class to aid the functions of BinarySearchTree Class
	protected static class BalanceableBinaryTree<K,V> extends LinkedBinaryTree<Entry<K,V>> {
		protected static class BSTNode<E> extends Node<E> {
			int aux = 0;
			BSTNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
				super(e, parent, leftChild, rightChild);
			}
			public int getAux() {
				return aux;
			}
			public void setAux(int value) {
				aux = value;
			}
		}
		public int getAux(Position<Entry<K,V>> p) {
			return ((BSTNode<Entry<K,V>>) p).getAux();
		}
		public void setAux(Position<Entry<K,V>> p, int value) {
			((BSTNode<Entry<K,V>>) p).setAux(value);
		}
		protected Node<Entry<K,V>> createNode(Entry<K,V> e, Node<Entry<K,V>> parent,
				Node<Entry<K,V>> left, Node<Entry<K,V>> right) {
			return new BSTNode<>(e, parent, left, right);
		}
	}

	/* all the data will be stored in tree*/
	protected BalanceableBinaryTree<K,V> tree = new BalanceableBinaryTree<>(); 
	int size;  //the number of entries (mappings)

	/* 
	 * default constructor
	 */
	public BinarySearchTree() {
		super();
		tree.addRoot(null);
	}

	public BinarySearchTree(Comparator<K> comp) {
		super(comp);
		tree.addRoot(null);
	}
	/* 
	 * Return the tree. The purpose of this method is purely for testing. 
	 * You don't need to make any change. Just make sure to use object tree to store your entries. 
	 */
	public LinkedBinaryTree<Entry<K,V>> getTree() {
		return tree;
	}

	@Override
	public int size(){
		return (tree.size() - 1) / 2;
	}
	protected Position<Entry<K,V>> root() {
		return tree.root();
	}
	protected Position<Entry<K,V>> parent(Position<Entry<K,V>> p) {
		return tree.parent(p);
	}
	protected Position<Entry<K,V>> left(Position<Entry<K,V>> p) {
		return tree.left(p);
	}
	protected Position<Entry<K,V>> right(Position<Entry<K,V>> p) {
		return tree.right(p);
	}
	protected Position<Entry<K,V>> sibling(Position<Entry<K,V>> p) {
		return tree.sibling(p);
	}
	protected boolean isRoot(Position<Entry<K,V>> p) {
		return tree.isRoot(p);
	}
	protected boolean isExternal(Position<Entry<K,V>> p) {
		return tree.isExternal(p);
	}
	protected boolean isInternal(Position<Entry<K,V>> p) {
		return tree.isInternal(p);
	}
	protected void set(Position<Entry<K,V>> p, Entry<K,V> e) {
		tree.set(p, e);
	}
	protected Entry<K,V> remove(Position<Entry<K,V>> p) { 
		return tree.remove(p); 
	}
	private void expandExternal(Position<Entry<K,V>> p, Entry<K,V> entry) {
		tree.set(p, entry);
		tree.addLeft(p, null);
		tree.addRight(p, null);
	}

	private Position<Entry<K,V>> treeSearch(Position<Entry<K,V>> p, K key) {
		if (isExternal(p)) {
			return p;
		}
		int comp = compare(key, p.getElement());
		if (comp == 0) {
			return p;
		} else if (comp < 0) {
			return treeSearch(left(p), key);
		} else {
			return treeSearch(right(p), key);
		}
	}

	
	/** Returns the value associated with the specified key (or else null). */
	@Override
	@TimeComplexity("O(n)")
	public V get(K key) throws IllegalArgumentException{
		/**
		 * TCJ
		 * 
		 * n complexity as it is a tree function and that is the require time to go through the entire tree
		 * and find a place to get the new key, then re-balance the tree as well.
		 */
		checkKey(key);
		Position<Entry<K,V>> p = treeSearch(root(), key);
		rebalanceAccess(p);
		if (isExternal(p)) {
			return null;
		}
		return p.getElement().getValue();
	}

	/** Associates the given value with the given key, returning any overridden value.*/
	@Override
	@TimeComplexity("O(n)")
	public V put(K key, V value) throws IllegalArgumentException {
		/**
		 * TCJ
		 * 
		 * n complexity as it is a tree function and that is the require time to go through the entire tree
		 * and find a place to add the new key, then re-balance the tree as well.
		 */
		checkKey(key);
		Entry<K,V> newEntry = new mapEntry<>(key, value);
		Position<Entry<K,V>> p = treeSearch(root(), key);
		if (isExternal(p)) {
			expandExternal(p, newEntry);
			rebalanceInsert(p);
			return null;
		} else {
			V old = p.getElement().getValue();
			set(p, newEntry);
			rebalanceAccess(p);
			return old;
		}
	}

	
	/** Removes the entry having key k (if any) and returns its associated value. */
	@Override
	@TimeComplexity("O(n)")
	public V remove(K key) throws IllegalArgumentException {
		/**
		 * TCJ
		 * 
		 * n complexity as it is a tree function and that is the require time to go through the entire tree
		 * and find a place to remove the desired key from, then re-balance the tree as well.
		 */
		checkKey(key);
		Position<Entry<K,V>> p = treeSearch(root(), key);
		if (isExternal(p)) {
			rebalanceAccess(p);
			return null;
		} else {
			V old = p.getElement().getValue();
			if (isInternal(left(p)) && isInternal(right(p))) {
				Position<Entry<K,V>> replacement = treeMax(right(p));
				set(p, replacement.getElement());
				p = replacement;
			}
			Position<Entry<K,V>> leaf = (isExternal(left(p)) ? left(p) : right(p));
			Position<Entry<K,V>> sib = sibling(leaf);
			remove(leaf);
			remove(p);
			rebalanceDelete(sib);
			return old;
		}
	}

	protected Position<Entry<K,V>> treeMax(Position<Entry<K,V>> p) {
		Position<Entry<K,V>> walk = p;
		while (isInternal(walk)) {
			walk = right(walk);
		}
		return parent(walk);
	}

	protected Position<Entry<K,V>> treeMin(Position<Entry<K,V>> p) {
		Position<Entry<K,V>> walk = p;
		while (isInternal(walk)) {
			walk = left(walk);
		}
		return parent(walk);
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());
		for (Position<Entry<K,V>> p : tree.inorder()) {
			if (isInternal(p)) {
				buffer.addLast(p.getElement());
			}
		}
		return buffer;
	}

	@Override
	public Entry<K, V> firstEntry() {
		if (isEmpty()) {
			return null;
		}
		return treeMin(root()).getElement();
	}

	@Override
	public Entry<K, V> lastEntry() {
		if (isEmpty()) {
			return null;
		}
		return treeMax(root()).getElement();
	}

	@Override
	public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
		checkKey(key);
		Position<Entry<K,V>> p = treeSearch(root(), key);
		if (isInternal(p)) {
			return p.getElement();
		}
		while (!isRoot(p)) {
			if (p == left(parent(p))) {
				return parent(p).getElement();
			} else {
				p = parent(p);
			}
		}
		return null;
	}

	@Override
	public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
		checkKey(key);
		Position<Entry<K,V>> p = treeSearch(root(), key);
		if (isInternal(p)) {
			return p.getElement();
		}
		while(!isRoot(p)) {
			if (p == right(parent(p))) {
				return parent(p).getElement();
			} else {
				p = parent(p);
			}
		}
		return null;
	}

	@Override
	public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
		checkKey(key);
		Position<Entry<K,V>> p = treeSearch(root(), key);
		if (isInternal(p) && isInternal(left(p))) {
			return treeMax(left(p)).getElement();
		}
		while (!isRoot(p)) {
			if (p == right(parent(p))) {
				return parent(p).getElement();
			} else {
				p = parent(p);
			}
		}
		return null;
	}

	@Override
	public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
		checkKey(key);
		Position<Entry<K,V>> p = treeSearch(root(), key);
		if (isInternal(p) && isInternal(right(p))) {
			return treeMin(right(p)).getElement();
		}
		while (!isRoot(p)) {
			if (p == left(parent(p))) {
				return parent(p).getElement();
			} else {
				p = parent(p);
			}
		}
		return null;
	}

	private void subMapRecurse(K fromKey, K toKey, Position<Entry<K,V>> p, ArrayList<Entry<K,V>> buffer) {
		if (isInternal(p)) {
			if (compare(p.getElement(), fromKey) < 0) {
				subMapRecurse(fromKey, toKey, right(p), buffer);
			} else {
				subMapRecurse(fromKey, toKey, left(p), buffer);
				if (compare(p.getElement(), toKey) < 0) {
					buffer.addLast(p.getElement());
					subMapRecurse(fromKey, toKey, right(p), buffer);
				}
			}
		}
	}

	@Override
	public Iterable<Entry<K, V>> subMap(K fromKey, K toKey)
			throws IllegalArgumentException {
		ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());
		if (compare(fromKey, toKey) < 0) {
			subMapRecurse(fromKey, toKey, root(), buffer);
		}
		return buffer;
	}

	@Override
	public boolean isEmpty() {
		return tree.size() == 0;
	}

	protected void rebalanceAccess(Position<Entry<K,V>> p) { }
	protected void rebalanceDelete(Position<Entry<K,V>> p) { }
	protected void rebalanceInsert(Position<Entry<K,V>> p) { }


}
