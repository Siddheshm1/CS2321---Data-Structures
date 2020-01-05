package cs2321;

import net.datastructures.Position;
import net.datastructures.Tree;

/**
 * @author Siddhesh Mahadeshwar
 * Date Modified: 11/11/2019
 */

public abstract class AbstractTree<E> implements Tree<E> {
	public boolean isInternal(Position<E> p) {
		return numChildren(p) > 0; 
	}
	public boolean isExternal(Position<E> p) {
		return numChildren(p) == 0; 
	}
	public boolean isRoot(Position<E> p) {
		return p == root();
	}
	public boolean isEmpty() {
		return size() == 0;
	}
}
