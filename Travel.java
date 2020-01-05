package cs2321;

import net.datastructures.*;

/**
 * @author Ruihong Zhang
 * @author Siddhesh Mahadeshwar
 * Date Modified: 12/16/2019
 * Reference textbook R14.16 P14.81 
 * 
 * Program Description: This program primarily uses Breadth-first and depth-first search to scan through
 * given graphs in order to find the respective needed along with helper methods that will aid with
 * reordering edges and constructing paths. 
 *
 */
public class Travel {
	Graph<String,Integer> graph;
	
	/**
	 * @param routes: Array of routes between cities. 
	 *                routes[i][0] and routes[i][1] represent the city names on both ends of the route. 
	 *                routes[i][2] represents the cost in string type. 
	 *                Hint: In Java, use Integer.valueOf to convert string to integer. 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Travel(String [][] routes) {
		
		graph = new AdjListGraph<String, Integer>();
		
		HashMap<String, Vertex> vMap = new HashMap<>();
		
		for(int i = 0; i < routes.length; i++) {
			
			Vertex<String> u = vMap.get(routes[i][0]);
			Vertex<String> v = vMap.get(routes[i][1]);
			
			if (u == null)
			{
				u = graph.insertVertex(routes[i][0]);
				vMap.put(routes[i][0], u);
			}
			
			if (v == null)
			{
				v = graph.insertVertex(routes[i][1]);
				vMap.put(routes[i][1], v);
			}
			
			graph.insertEdge(u,v,Integer.valueOf(routes[i][2]));
		}	
	}
	
	/**
	 * @param departure: the departure city name 
	 * @param destination: the destination city name
	 * @return Return the path from departure city to destination using Depth First Search algorithm. 
	 *         The path should be represented as ArrayList or DoublylinkedList of city names. 
	 *         The order of city names in the list should match order of the city names in the path.  
	 *         
	 * @IMPORTANT_NOTE: The outgoing edges should be traversed by the order of the city names stored in
	 *                 the opposite vertices. For example, if V has 3 outgoing edges as in the picture below,
	 *                           V
	 *                        /  |  \
	 *                       /   |    \
	 *                      B    A     F  
	 *              your algorithm below should visit the outgoing edges of V in the order of A,B,F.
	 *              This means you will need to create a helper function to sort the outgoing edges by 
	 *              the opposite city names.
	 *              	              
	 *              See the method sortedOutgoingEdges below. 
	 */
	public Iterable<String> DFSRoute(String departure, String destination ) {
		Vertex<String> org = null;
		Vertex<String> dest = null;
		
		HashMap<Vertex<String>, Boolean> known = new HashMap<>();
		HashMap<Vertex<String>, Edge<Integer>> forest = new HashMap<>();
	
		for(Vertex<String> v: graph.vertices()) {
			if(v.getElement().equals(departure)){
				org = v;
			}
			if(v.getElement().equals(destination)) {
				dest = v;
			}
		}
		DFS(org, known, forest);
		return constructPath(org, dest, forest);
	}
	
	
	/**
	 * Construct path method for construction of a path between 2 vertices
	 * @param origin: the starting point
	 * @param destination: the ending point
	 * @param forest: collection of all vertices and edges in the forest
	 * @return: returns a string of doublyLinkedList type, the path
	 */
	private Iterable<String> constructPath(Vertex<String> origin, Vertex<String> destination,
			HashMap<Vertex<String>,Edge<Integer>> forest) {
		
		DoublyLinkedList<String> path = new DoublyLinkedList<>();
		
		if(forest.get(destination) != null) {
			Vertex<String> walk = destination;
			while(walk != origin) {
				path.addFirst(walk.getElement());
				Edge<Integer> edge = forest.get(walk);
				walk = graph.opposite(walk, edge);
			}
		}
		
		path.addFirst(origin.getElement());
		return path;
	}
	
	
	/**
	 * Same method as constructPath in theory but returns a different type, returns doublyLinkedList instead of Iterable String
	 * @param origin: the starting point
	 * @param destination: the ending point
	 * @param forest: collection of all vertices and edges in the forest
	 * @return: returns the path as a doublyLinkedList
	 */
	private DoublyLinkedList<String> constructPathList(Vertex<String> origin, Vertex<String> destination, 
			HashMap<Vertex<String>, Edge<Integer>> forest, DoublyLinkedList<String> path) {
	
		if(forest.get(destination) != null) {
			Vertex<String> walk = destination;
			while(walk != origin) {
				path.addFirst(walk.getElement());
				Edge<Integer> edge = forest.get(walk);
				walk = graph.opposite(walk, edge);
			}
		}
		
		path.addFirst(origin.getElement());
		return path;
	}


	/**
	 * Depth-first search method for graphs
	 * @param v: Vertex parameter
	 * @param known: Checks if the vertex has been visited/known
	 * @param forest: collection of all vertices
	 */
	private void DFS(Vertex<String> v, HashMap<Vertex<String>, Boolean> known, HashMap<Vertex<String>,
			Edge<Integer>> forest) {
		
		known.put(v, true);
		HeapPQ<Integer, Edge<Integer>> outgoing = orderEdges(v); 
		
		while(!outgoing.isEmpty()) { // while outGoing edges are not empty
			Edge<Integer> e = outgoing.removeMin().getValue();
			Vertex<String> w = graph.opposite(v, e);
			if(known.get(w) == null) {
				forest.put(w, e);
				DFS(w, known, forest);
			}
		}
	}
	
	
	/**
	 * Orders the edges based on alphabetical order
	 * @param origin: the origin vertex where the graph search needs to start 
	 * @return: Returns a heap with ordered edges
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HeapPQ<Integer, Edge<Integer>> orderEdges(Vertex<String> origin) {
		
		Iterable<Edge<Integer>> edges = graph.outgoingEdges(origin);
		String[] oppNameStrings = new String[graph.outDegree(origin)];
		QuickSort sort = new QuickSort();
		HeapPQ<Integer, Edge<Integer>> outGoing = new HeapPQ<>();
		
		int i = 0;
		
		for(Edge e: edges) {
			oppNameStrings[i] = (String) graph.opposite(origin, e).getElement();
			i++;
			
		}
		
		sort.sort(oppNameStrings);
		for(int j = 0; j < oppNameStrings.length; j++) {
			for (Edge e: edges) {
				if(graph.opposite(origin, e).getElement().equals(oppNameStrings[j])) {
					outGoing.insert(j+1, e);
					break;
				}
			}
		}
		return outGoing;
	}

	/**
	 * @param departure: the departure city name 
	 * @param destination: the destination city name
     * @return Return the path from departure city to destination using Breadth First Search algorithm. 
	 *         The path should be represented as ArrayList or DoublylinkedList of city names. 
	 *         The order of city names in the list should match order of the city names in the path.  
	 *         
	 * @IMPORTANT_NOTE: The outgoing edges should be traversed by the order of the city names stored in
	 *                 the opposite vertices. For example, if V has 3 outgoing edges as in the picture below,
	 *                           V
	 *                        /  |  \
	 *                       /   |    \
	 *                      B    A     F  
	 *              your algorithm below should visit the outgoing edges of V in the order of A,B,F.
	 *              This means you will need to create a helper function to sort the outgoing edges by 
	 *              the opposite city names.
	 *              	             
	 *              See the method sortedOutgoingEdges below. 
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Iterable<String> BFSRoute(String departure, String destination ) {
		
		HashMap<Vertex<String>, Boolean> known = new HashMap<>();
		HashMap<Vertex<String>, Edge<Integer>> forest = new HashMap<>();
		DoublyLinkedList<Vertex<String>> queue = new DoublyLinkedList();
		
		Vertex<String> org = null;
		Vertex<String> dest = null;
		for(Vertex<String> v: graph.vertices()) {
			if(v.getElement().equals(departure)){
				org = v;
			}
			if(v.getElement().equals(destination)) {
				dest = v;
			}
		}
		
		BFS(org, dest, known, forest, queue);
		return constructPath(org, dest, forest);
	}
	
	
	/**
	 * Breadth-first search method for graphs
	 * @param v: takes in the starting vertex (origin)
	 * @param z: takes in the ending vertex (destination)
	 * @param known: finding out if a vertex has been visited
	 * @param forest: a forest of all vertices
	 * @param queue: takes in a queue which will be the data structure to use for this method
	 */
	public void BFS(Vertex<String> v, Vertex<String> z, HashMap<Vertex<String>, Boolean> known, 
			HashMap<Vertex<String>, Edge<Integer>> forest, DoublyLinkedList<Vertex<String>> queue) {
		
		known.put(v, true);
		queue.addLast(v);
		
		while(!queue.isEmpty()) {
			Vertex<String> w = queue.removeFirst();
			HeapPQ<Integer, Edge<Integer>> outgoing = orderEdges(w);
			
			while (!outgoing.isEmpty()) {
				Edge<Integer> e = outgoing.removeMin().getValue();
				Vertex<String> p = graph.opposite(w, e);
				if (known.get(p) == null) {
					known.put(p, true);
					forest.put(p, e);
					queue.addLast(p);
				}
			}
		}
	}
	
	/**
	 * @param departure: the departure city name 
	 * @param destination: the destination city name
	 * @param itinerary: an empty DoublylinkedList object will be passed in to the method. 
	 * 	       When a shorted path is found, the city names in the path should be added to the list in the order. 
	 * @return return the cost of the shortest path from departure to destination. 
	 *         
	 * @IMPORTANT_NOTE: The outgoing edges should be traversed by the order of the city names stored in
	 *                 the opposite vertices. For example, if V has 3 outgoing edges as in the picture below,
	 *                           V
	 *                        /  |  \
	 *                       /   |    \
	 *                      B    A     F  
	 *              your algorithm below should visit the outgoing edges of V in the order of A,B,F.
	 *              This means you will need to create a helper function to sort the outgoing edges by 
	 *              the opposite city names.
	 *              
	 *              See the method sortedOutgoingEdges below. 
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int DijkstraRoute(String departure, String destination, DoublyLinkedList<String> itinerary ) {
		HashMap<Vertex<String>, Integer> distanceMap = new HashMap<>();
		HashMap<Vertex<String>, Edge<Integer>> forest = new HashMap<>();
		HeapPQ<Integer, Vertex<String>> pq = new HeapPQ<>();
		HashMap<Vertex<String>, Entry<Integer, Vertex<String>>> pqTokens = new HashMap<>();
		Vertex<String> start = null;
		Vertex<String> dest = null;
		
		for(Vertex v: graph.vertices()) {
			if(v.getElement().equals(departure)) {
				distanceMap.put(v, 0);
				start = v;
			}else {
				distanceMap.put(v, Integer.MAX_VALUE);
				if(v.getElement().equals(destination)) {
					dest = v;
				}
			}
			pqTokens.put(v, pq.insert(distanceMap.get(v), v) );	
		}
		
		while(!pq.isEmpty()) {
			Entry<Integer, Vertex<String>> entry = pq.removeMin();
			int key = entry.getKey();
			Vertex<String> u = entry.getValue();
			pqTokens.remove(u);
			Iterable<Edge<Integer>> edges = orderEdgesToIterable(u);
			
			for(Edge<Integer> e: edges) {
				Vertex<String> v = graph.opposite(u, e);
				 int newDist = key + e.getElement();
				 int oldDist = distanceMap.get(v);
				 if (newDist < oldDist) {
					 pq.replaceKey(pqTokens.get(v), newDist);
					 distanceMap.put(v, newDist);
					 forest.put(v,  e);
				 }
			}
		}
		itinerary = constructPathList(start, dest, forest, itinerary);
		
		return distanceMap.get(dest);
	}
	
	/**
	 * Make the edges to an iterable edge type so it can be used by other methods
	 * @param w: takes in a vertex parameter to convert
	 * @return: return the edge of iterable type
	 */
	private Iterable<Edge<Integer>> orderEdgesToIterable(Vertex<String> w) {
		HeapPQ<Integer, Edge<Integer>> outgoing = orderEdges(w);
		DoublyLinkedList<Edge<Integer>> list = new DoublyLinkedList<Edge<Integer>>();
		while(!outgoing.isEmpty()) {
			list.addLast(outgoing.removeMin().getValue());
		}
		return list;
	}
}