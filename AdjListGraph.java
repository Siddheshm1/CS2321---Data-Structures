package cs2321;
import net.datastructures.*;

/**
 * @author Siddhesh Mahadeshwar
 * Date Modified: 11/17/2019
 */

/*
 * Implement Graph interface. A graph can be declared as either directed or undirected.
 * In the case of an undirected graph, methods outgoingEdges and incomingEdges return the same collection,
 * and outDegree and inDegree return the same value.
 * 
 * @author CS2321 Instructor
 */
public class AdjListGraph<V, E> implements Graph<V, E> {

	/**
	 * nested inner vertex class
	 */
	private class InnerVertex<V> implements Vertex<V> {
		private V element;
		private Position<Vertex<V>> pos;
		private Map<Vertex<V>, Edge<E>> outgoing, incoming;

		/** Constructs a new InnerVertex instance storing the given element. */
		public InnerVertex(V elem, boolean graphIsDirected) {
			element = elem;
			outgoing = new HashMap<>();
			if (graphIsDirected)
				incoming = new HashMap<>();
			else
				incoming = outgoing;    // if undirected, alias outgoing map
		}

		/** Validates that this vertex instance belongs to the given graph. */
		public boolean validate(Graph<V,E> graph) {
			return (AdjListGraph.this == graph && pos != null);
		}

		/** Returns the element associated with the vertex. */
		public V getElement() { return element; }

		/** Stores the position of this vertex within the graph's vertex list. */
		public void setPosition(Position<Vertex<V>> p) { pos = p; }

		/** Returns the position of this vertex within the graph's vertex list. */
		public Position<Vertex<V>> getPosition() { return pos; }

		/** Returns reference to the underlying map of outgoing edges. */
		public Map<Vertex<V>, Edge<E>> getOutgoing() { return outgoing; }

		/** Returns reference to the underlying map of incoming edges. */
		public Map<Vertex<V>, Edge<E>> getIncoming() { return incoming; }

		public void setElement(V e) {
			element = e;
		}
	}

	/**
	 * nested inner edge class
	 */
	private class InnerEdge<E> implements Edge<E> {
		private E element;
		private Position<Edge<E>> pos;
		private Vertex<V>[] endpoints;

		@SuppressWarnings({"unchecked"})
		/** Constructs InnerEdge instance from u to v, storing the given element. */
		public InnerEdge(Vertex<V> u, Vertex<V> v, E elem) {
			element = elem;
			endpoints = (Vertex<V>[]) new Vertex[]{u,v};  // array of length 2
		}

		/** Returns the element associated with the edge. */
		public E getElement() { return element; }

		/** Returns reference to the endpoint array. */
		public Vertex<V>[] getEndpoints() { return endpoints; }

		/** Validates that this edge instance belongs to the given graph. */
		public boolean validate(Graph<V,E> graph) {
			return AdjListGraph.this == graph && pos != null;
		}

		/** Stores the position of this edge within the graph's vertex list. */
		public void setPosition(Position<Edge<E>> p) { pos = p; }

		/** Returns the position of this edge within the graph's vertex list. */
		public Position<Edge<E>> getPosition() { return pos; }

		public void setElement(E e) {
			element = e;
		}
	}


	/* 
	 * replace the element in edge object, return the old element
	 */
	public E replace(Edge<E> e, E o) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		edge.setElement(o);
		return o;
	}

	/* 
	 * replace the element in vertex object, return the old element
	 */
	public V replace(Vertex<V> v, V o) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		vert.setElement(o);
		return o;
	}

	private boolean isDirected;

	private PositionalList<Vertex<V>> vertices = new DoublyLinkedList<>( );

	private PositionalList<Edge<E>> edges = new DoublyLinkedList<>( );


	public AdjListGraph(boolean directed) {
		isDirected = directed; 
	}

	public AdjListGraph() { 
	}


	/* (non-Javadoc)
	 * @see net.datastructures.Graph#edges()
	 */
	// returns the edges 
	@TimeComplexity("O(m)")
	public Iterable<Edge<E>> edges() {
		/*
		 * TCJ Iterates throughout to find the edges, complexity would depend on the number of edges
		 */
		return edges;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#endVertices(net.datastructures.Edge)
	 */
	// returns the end vertices
	@TimeComplexity("O(1)")
	public Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException {
		/*
		 * TCJ Returns the end points of the edges and no major method calls, hence, constant time
		 */
		InnerEdge<E> edge = validate(e);
		return edge.getEndpoints();
	}


	/* (non-Javadoc)
	 * @see net.datastructures.Graph#insertEdge(net.datastructures.Vertex, net.datastructures.Vertex, java.lang.Object)
	 */
	// inserts edges 
	@TimeComplexity("O(1)")
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E o)
			throws IllegalArgumentException {
		/*
		 * TCJ Simply inserts an edge and this can be done anywhere, no major method calls, recursion, or iteration necessary,
		 * hence, constant time
		 */
		if (getEdge(u,v) == null) {
			InnerEdge<E> e = new InnerEdge<>(u, v, o);
			e.setPosition(edges.addLast(e));
			InnerVertex<V> origin = validate(u);
			InnerVertex<V> dest = validate(v);
			origin.getOutgoing().put(v, e);
			dest.getIncoming().put(u, e);
			return e;
		} else
			throw new IllegalArgumentException("Edge from u to v exists");
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#insertVertex(java.lang.Object)
	 */
	// inserts vertices
	@TimeComplexity("O(1)")
	public Vertex<V> insertVertex(V o) {
		/*
		 * TCJ Simply inserts a vertex and this can be done anywhere, no major method calls, recursion, or iteration necessary,
		 * hence, constant time
		 */
		InnerVertex<V> v = new InnerVertex<>(o, isDirected);
		v.setPosition(vertices.addLast(v));
		return v;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#numEdges()
	 */
	// returns the number of edges
	public int numEdges() {
		return edges.size();
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#numVertices()
	 */
	// returns the number of vertices
	public int numVertices() {
		return vertices.size();
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#opposite(net.datastructures.Vertex, net.datastructures.Edge)
	 */
	// returns the opposite vertex taking in a vertex and edge
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e)
			throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		Vertex<V>[] endpoints = edge.getEndpoints();
		if (endpoints[0] == v)
			return endpoints[1];
		else if (endpoints[1] == v)
			return endpoints[0];
		else
			throw new IllegalArgumentException("v is not incident to this edge");
	}

	@SuppressWarnings({"unchecked"})
	/* (non-Javadoc)
	 * @see net.datastructures.Graph#removeEdge(net.datastructures.Edge)
	 */
	// removes an edge
	@TimeComplexity("O(1)")
	public void removeEdge(Edge<E> e) throws IllegalArgumentException {
		/*
		 * TCJ Only removes an edge so this can be done in constant time
		 */
		InnerEdge<E> edge = validate(e);
		// remove this edge from vertices' adjacencies
		Vertex<V>[] verts = edge.getEndpoints();
		((AdjListGraph<V,E>.InnerVertex<V>) verts[0]).getOutgoing().remove(verts[1]);
		((AdjListGraph<V,E>.InnerVertex<V>) verts[1]).getOutgoing().remove(verts[0]);
		// remove this edge from the list of edges
		edges.remove(edge.getPosition());
		edge.setPosition(null);             // invalidates the edge
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#removeVertex(net.datastructures.Vertex)
	 */
	// removes a vertex
	@TimeComplexity("O(deg(v))")
	public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
		/*
		 * TCJ Searches for the degree of v so this depends on how many vertices must be searched
		 */
		InnerVertex<V> vert = validate(v);
		// remove all incident edges from the graph
		for (Edge<E> e : vert.getOutgoing().values())
			removeEdge(e);
		for (Edge<E> e : vert.getIncoming().values())
			removeEdge(e);
		// remove this vertex from the list of vertices
		vertices.remove(vert.getPosition());
		//vert.setPosition(null);             // invalidates the vertex
	}


	/* (non-Javadoc)
	 * @see net.datastructures.Graph#vertices()
	 */

	/**
	 * returns the vertices
	 */
	@TimeComplexity("O(n)")
	public Iterable<Vertex<V>> vertices() {
		/*
		 * TCJ Iterates throughout to find and return the vertices, hence, O(n) as it will depend on how much
		 * iteration needs to be done to find the vertex
		 */
		return vertices;
	}

	/**
	 * returns the number of outGoing vertices from a specific vertex
	 */
	@Override
	@TimeComplexity("O(1)")
	public int outDegree(Vertex<V> v) throws IllegalArgumentException {
		/*
		 * TCJ Gets the out-degree size
		 */
		InnerVertex<V> vert = validate(v);
		return vert.getOutgoing().size();
	}

	/**
	 * returns the number of inComing vertices from a specific vertex
	 */
	@Override
	@TimeComplexity("O(1)")
	public int inDegree(Vertex<V> v) throws IllegalArgumentException {
		/*
		 * TCJ Gets the in-degree size
		 */
		InnerVertex<V> vert = validate(v);
		return vert.getIncoming().size();
	}

	/**
	 * returns the number of outgoing edges
	 */
	@Override
	@TimeComplexity("O(deg(v))")
	public Iterable<Edge<E>> outgoingEdges(Vertex<V> v)
			throws IllegalArgumentException {
		/*
		 * TCJ Searches for degree(v) hence, deg(v) complexity as it will depend on how many vertices
		 * need to searched
		 */
		InnerVertex<V> vert = validate(v);
		return vert.getOutgoing().values();
	}

	/**
	 * returns the number of incoming edges
	 */
	@Override
	@TimeComplexity("O(deg(v))")
	public Iterable<Edge<E>> incomingEdges(Vertex<V> v)
			throws IllegalArgumentException {
		/*
		 * TCJ Searches for degree(v) hence, deg(v) complexity as it will depend on how many vertices
		 * need to searched
		 */
		InnerVertex<V> vert = validate(v);
		return vert.getIncoming().values();
	}

	/**
	 * get method for the edges
	 */
	@Override
	@TimeComplexity("O(1)")
	public Edge<E> getEdge(Vertex<V> u, Vertex<V> v)
			throws IllegalArgumentException {
		/*
		 * TCJ O(1) complexity as a hashMap is being used
		 */
		InnerVertex<V> origin = validate(u);
		return origin.getOutgoing().get(v);
	}


	/**
	 * validate method for vertices
	 */
	//@SuppressWarnings({"unchecked"})
	private InnerVertex<V> validate(Vertex<V> v) {
		if (!(v instanceof InnerVertex)) throw new IllegalArgumentException("Invalid vertex");
		InnerVertex<V> vert = (InnerVertex<V>) v;     // safe cast
		if (!vert.validate(this)) throw new IllegalArgumentException("Invalid vertex");
		return vert;
	}

	/**
	 * validate method for edges
	 */
	@SuppressWarnings({"unchecked"})
	private InnerEdge<E> validate(Edge<E> e) {
		if (!(e instanceof InnerEdge)) throw new IllegalArgumentException("Invalid edge");
		InnerEdge<E> edge = (InnerEdge<E>) e;     // safe cast
		if (!edge.validate(this)) throw new IllegalArgumentException("Invalid edge");
		return edge;
	}


	/** A vertex of an adjacency map graph representation. */
}
