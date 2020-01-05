package cs2321;

import net.datastructures.BinaryTree;
import net.datastructures.List;
import net.datastructures.Position;

/**
 * @author Siddhesh Mahadeshwar
 * Date Modified: 11/11/2019
 */

public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {
	public Position<E> sibling(Position<E> p) {
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
	public int numChildren(Position<E> p) {
		int count = 0;
		if (left(p) != null) {
			count++; 
		}
		if (right(p) != null) {
			count++; 
		}
		return count;
	}
	public Iterable<Position<E>> children(Position<E> p) {
		List<Position<E>> snapshot = new ArrayList<>(2);
		if (left(p) != null) {
			snapshot.add(snapshot.size(), left(p));
		}
		if (right(p) != null) {
			snapshot.add(snapshot.size(), right(p));
		}
		return snapshot;
	}

	private void inorderSubtree(Position<E> p, List<Position<E>> snapshot) {
		if (left(p) != null) {
			inorderSubtree(left(p), snapshot);
		}
		snapshot.add(snapshot.size(), p);
		if (right(p) != null) {
			inorderSubtree(right(p), snapshot);
		}
	}
	public Iterable<Position<E>> inorder() {
		List<Position<E>> snapshot = new ArrayList<>();
		if (!isEmpty()) {
			inorderSubtree(root(), snapshot);
		}
		return snapshot;
	}
}
