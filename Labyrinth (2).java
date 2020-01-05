package cs2321;

import java.io.*;
import java.util.Scanner;
import java.util.Iterator;
import net.datastructures.*;


public class Labyrinth {
	public static final int WALL = 1;
	public static final String PARSE_CHARACTER = ",";

	private Graph<RoomCoordinate, Walkway> mGraph;
	private int mWidth = -1;
	private int mHeight = -1;

	public Labyrinth(String aFileName) {
		mGraph = setupLabyrinth(aFileName);

		// TODO: Add other necessary code to constructor
	}

	/*
	 * Width of the labyrinth (# of squares, not pixels)
	 */
	public int getWidth() {
		return mWidth;
	}

	/*
	 * Height of the labyrinth (# of squares, not pixels)
	 */
	public int getHeight() {
		return mHeight;
	}

	/*
	 * Create the graph based on the maze specification in the input file !!! Don't
	 * Change this method !!!
	 */
	public Graph<RoomCoordinate, Walkway> setupLabyrinth(String aFileName) {
		Scanner lFile = null;
		try {
			lFile = new Scanner(new File(aFileName));
			lFile.useDelimiter(",\n");
		} catch (FileNotFoundException eException) {
			System.out.println(eException.getMessage());
			eException.printStackTrace();
			System.exit(-1);
		}

		// You need to copy your Adjacent Graph implementation to this project.
		// Otherwise the following line has compiler error because AdjListGraph(...)
		// does not exist.
		Graph<RoomCoordinate, Walkway> lGraph = new AdjListGraph<RoomCoordinate, Walkway>();

		try {
			int lXSize = 0;
			int lYSize = 0;
			if (lFile.hasNext()) {
				String[] lDimensions = lFile.nextLine().split(PARSE_CHARACTER);
				lXSize = Integer.parseInt(lDimensions[0]);
				lYSize = Integer.parseInt(lDimensions[1]);
			}

			mWidth = lXSize;
			mHeight = lYSize;

			/* Create all the room coordinates */
			Vertex<?>[][] lVertices = new Vertex<?>[lXSize][lYSize];
			for (int lYIndex = 0; lYIndex < lYSize; lYIndex++) {
				for (int lXIndex = 0; lXIndex < lXSize; lXIndex++) {
					RoomCoordinate lNextRoomCoordinate = new RoomCoordinate(lXIndex, lYIndex);
					Vertex<RoomCoordinate> lNextRoom = lGraph.insertVertex(lNextRoomCoordinate);
					lVertices[lXIndex][lYIndex] = lNextRoom;
				}
			}

			for (int lYIndex = 0; lYIndex < lYSize; lYIndex++) {
				String[] lWalls = lFile.nextLine().split(PARSE_CHARACTER);
				for (int lXIndex = 0; lXIndex < lXSize; lXIndex++) {
					if (Integer.parseInt(lWalls[lXIndex]) != WALL) {
						Vertex<RoomCoordinate> lVertex1 = (Vertex<RoomCoordinate>) lVertices[lXIndex][lYIndex];
						Vertex<RoomCoordinate> lVertex2 = (Vertex<RoomCoordinate>) lVertices[lXIndex][lYIndex - 1];

						Walkway lNewWalkway = new Walkway(
								lVertex1.getElement().toString() + lVertex2.getElement().toString(),
								Integer.parseInt(lWalls[lXIndex]));
						lGraph.insertEdge(lVertex1, lVertex2, lNewWalkway);
					}
				}
			}

			for (int lYIndex = 0; lYIndex < lYSize; lYIndex++) {
				String[] lWalls = lFile.nextLine().split(PARSE_CHARACTER);
				for (int lXIndex = 0; lXIndex < lXSize; lXIndex++) {
					if (Integer.parseInt(lWalls[lXIndex]) != WALL) {
						Vertex<RoomCoordinate> lVertex1 = (Vertex<RoomCoordinate>) lVertices[lXIndex][lYIndex];
						Vertex<RoomCoordinate> lVertex2 = (Vertex<RoomCoordinate>) lVertices[lXIndex - 1][lYIndex];

						Walkway lNewWalkway = new Walkway(
								lVertex1.getElement().toString() + lVertex2.getElement().toString(),
								Integer.parseInt(lWalls[lXIndex]));
						lGraph.insertEdge(lVertex1, lVertex2, lNewWalkway);
					}
				}
			}
		} catch (Exception eException) {
			System.out.println(eException.getMessage());
			eException.printStackTrace();
			System.exit(-1);
		}

		return lGraph;
	}

	/**
	 * Complete the dfsPath function by implementing a Depth First Search algorithm
	 * to find a path from start to end. At each vertex, the adjacent edges shall be
	 * searched in the order of NORTH, EAST, SOURTH and WEST.
	 * 
	 * @param start an RoomCoordinate object represents the start location, this
	 *              object does not exist in the graph
	 * @param end   an RoomCoordinate object represents the start location, this
	 *              object does not exist in the graph
	 * @return Return the sequence of edges traversed in order to get from the start
	 *         to the finish locations. If there is NO path, do NOT return null.
	 *         Return an empty sequence.
	 */
	@TimeComplexity("O(n + m)")
	public Iterable<Edge<Walkway>> dfsPath(RoomCoordinate start, RoomCoordinate end) {
		// #TODO: Complete and correct dfsPath()
		/* #TODO: TCJ */
		/*
		 * TCJ uses dfsPath2, so it is O(n + m)
		 */
		HashMap<Vertex<RoomCoordinate>, Edge<Walkway>> forest = new HashMap<>();
		HashMap<Vertex<RoomCoordinate>, Boolean> visited = new HashMap<>();
		Vertex<RoomCoordinate> startV = findVertex(start);
		Vertex<RoomCoordinate> endV = findVertex(end);

		dfsPath2(startV, endV, forest, visited);
		return constructPath(startV, endV, forest);
	}

	@TimeComplexity("O(n + m)")
	private void dfsPath2(Vertex<RoomCoordinate> startV, Vertex<RoomCoordinate> endV,
			HashMap<Vertex<RoomCoordinate>, Edge<Walkway>> forest, HashMap<Vertex<RoomCoordinate>, Boolean> visited) {
		/*
		 * TCJ iterates through every vertex and edge.
		 */
		// see if they are the same
		if (startV == endV) {
			return;
		}
		// start has been visited
		visited.put(startV, true);
		ArrayList<Edge<Walkway>> order = NESW(startV);
		for (int j = 0; j < order.size(); j++) {
			Edge<Walkway> e = order.get(j);
			if (e == null) {
				continue;
			}
			// opposite
			Vertex<RoomCoordinate> opposite = mGraph.opposite(startV, e);
			if (visited.get(opposite) == null) {
				visited.put(opposite, true);
				forest.put(opposite, e);
				dfsPath2(opposite, endV, forest, visited);
			}
		}
	}

	/**
	 * Complete the dfsPath function by implementing a Breadth First Search
	 * algorithm to find a path from start to end. At each vertex, the adjacent
	 * edges shall be searched in the order of NORTH, EAST, SOURTH and WEST.
	 * 
	 * @param start an RoomCoordinate object represents the start location, this
	 *              object does not exist in the graph
	 * @param end   an RoomCoordinate object represents the start location, this
	 *              object does not exist in the graph
	 * @return Return the sequence of edges traversed in order to get from the start
	 *         to the finish locations. If there is NO path, do NOT return null.
	 *         Return an empty sequence.
	 */
	@TimeComplexity("O(n + m)")
	public Iterable<Edge<Walkway>> bfsPath(RoomCoordinate start, RoomCoordinate end) {
		// #TODO: Complete and correct bfsPath()
		/* #TODO: TCJ */
		/*
		 * TCJ uses bfsPath2, so it is O(n + m)
		 */
		HashMap<Vertex<RoomCoordinate>, Edge<Walkway>> forest = new HashMap<>();
		HashMap<Vertex<RoomCoordinate>, Boolean> visited = new HashMap<>();
		Vertex<RoomCoordinate> startV = findVertex(start);
		Vertex<RoomCoordinate> endV = findVertex(end);

		bfsPath2(startV, endV, forest, visited);
		return constructPath(startV, endV, forest);
	}

	@TimeComplexity("O(n + m)")
	private void bfsPath2(Vertex<RoomCoordinate> startV, Vertex<RoomCoordinate> endV,
			HashMap<Vertex<RoomCoordinate>, Edge<Walkway>> forest, HashMap<Vertex<RoomCoordinate>, Boolean> visited) {
		/*
		 * checks every vertex and edge (n + m)
		 */
		// vertices list
		DoublyLinkedList<Vertex<RoomCoordinate>> listV = new DoublyLinkedList<>();
		visited.put(startV, true);
		listV.addLast(startV);

		while (!listV.isEmpty()) {
			DoublyLinkedList<Vertex<RoomCoordinate>> newListV = new DoublyLinkedList<>();
			for (Vertex<RoomCoordinate> v : listV) {
				for (Edge<Walkway> e : NESW(v)) {
					Vertex<RoomCoordinate> w = null;
					// checks edges
					if (e != null) {
						w = mGraph.opposite(v, e);
					}
					if (!(w == null)) {
						if (visited.get(w) == null) {
							visited.put(w, true);
							forest.put(w, e);
							newListV.addLast(w);
						}
					}
				}
			}
			listV = newListV;
		}
	}

	/**
	 * Complete the shortestPath function by implementing Dijkstra's algorithm to
	 * find a path from start to end. At each vertex, the adjacent edges shall be
	 * searched in the order of NORTH, EAST, SOURTH and WEST.
	 * 
	 * @param start an RoomCoordinate object represents the start location, this
	 *              object does not exist in the graph
	 * @param end   an RoomCoordinate object represents the start location, this
	 *              object does not exist in the graph
	 * @return Return the sequence of edges traversed in order to get from the start
	 *         to the finish locations. If there is NO path, do NOT return null.
	 *         Return an empty sequence.
	 */
	@TimeComplexity("O((n + m) * log(n))")
	public Iterable<Edge<Walkway>> shortestPath(RoomCoordinate start, RoomCoordinate end) {
		/*
		 * TCJ uses Dijkstra algorithm
		 */
		HashMap<Vertex<RoomCoordinate>, Integer> cloud = new HashMap<>();
		Vertex<RoomCoordinate> startV = findVertex(start);
		Vertex<RoomCoordinate> endV = findVertex(end);
		cloud = Dijkstra(startV, endV);
		return countructShortestPath(startV, endV, cloud);
	}

	@TimeComplexity("O(n)")
	private Iterable<Edge<Walkway>> countructShortestPath(Vertex<RoomCoordinate> startV, Vertex<RoomCoordinate> endV,
			HashMap<Vertex<RoomCoordinate>, Integer> cloud) {
		/*
		 * TCJ iterates through the vertices and outgoing edges
		 */
		HashMap<Vertex<RoomCoordinate>, Edge<Walkway>> path = new HashMap<>();
		// iterates through the vertices
		for (Vertex<RoomCoordinate> shorter : cloud.keySet()) {
			if (shorter != startV) {
				// iterates through all of the outgoing edges
				for (Edge<Walkway> edge : mGraph.outgoingEdges(shorter)) {
					if (edge != null) {
						Vertex<RoomCoordinate> t = mGraph.opposite(shorter, edge);
						if (cloud.get(t) != null) {
							if (cloud.get(shorter) == cloud.get(t) + edge.getElement().getDistance()) {
								path.put(shorter, edge);
							}
						}
					}
				}
			}
		}
		return constructPath(startV, endV, path);
	}

	@TimeComplexity("O(n)")
	private Iterable<Edge<Walkway>> constructPath(Vertex<RoomCoordinate> startV, Vertex<RoomCoordinate> endV,
			HashMap<Vertex<RoomCoordinate>, Edge<Walkway>> forest) {
		/*
		 * TCJ iterates through the forest
		 */
		DoublyLinkedList<Edge<Walkway>> path = new DoublyLinkedList<>();

		Vertex<RoomCoordinate> vFinal = endV;
		if (forest.get(vFinal) != null) {
			while (vFinal != startV) {
				path.addFirst(forest.get(vFinal));
				vFinal = mGraph.opposite(vFinal, forest.get(vFinal));
			}
		}
		return path;
	}

	@TimeComplexity("O((n + m) * log(n))")
	private HashMap<Vertex<RoomCoordinate>, Integer> Dijkstra(Vertex<RoomCoordinate> startV,
			Vertex<RoomCoordinate> endV) {
		/*
		 * TCJ at the worst case, O(n + m) comes from the while loop and the O(log(n))
		 * comes from the for loop.
		 */
		HeapPQ<Integer, Vertex<RoomCoordinate>> pq = new HeapPQ<>();
		HashMap<Vertex<RoomCoordinate>, Integer> d = new HashMap<>();
		HashMap<Vertex<RoomCoordinate>, Integer> cloud = new HashMap<>();
		HashMap<Vertex<RoomCoordinate>, Edge<Walkway>> forest = new HashMap<>();
		HashMap<Vertex<RoomCoordinate>, Entry<Integer, Vertex<RoomCoordinate>>> pqTokens = new HashMap<>();

		// iterates through the vertices
		for (Vertex<RoomCoordinate> v : mGraph.vertices()) {
			if (v == startV) {
				d.put(v, 0);
			} else {
				d.put(v, Integer.MAX_VALUE);
			}
			pqTokens.put(v, pq.insert(d.get(v), v));
		}
		// checks to see if the pq is not empty between start and end
		while (!pq.isEmpty()) {
			Entry<Integer, Vertex<RoomCoordinate>> entry = pq.removeMin();
			int key = entry.getKey();
			Vertex<RoomCoordinate> u = entry.getValue();
			cloud.put(u, key);
			pqTokens.remove(u);
			if (u == endV) {
				break;
			}
			for (Edge<Walkway> e : mGraph.outgoingEdges(u)) {
				Vertex<RoomCoordinate> v = mGraph.opposite(u, e);
				if (cloud.get(v) == null) {
					int wgt = e.getElement().getDistance();
					// shortest one will be added
					if (d.get(u) + wgt < d.get(v)) {
						d.put(v, d.get(u) + wgt);
						pq.replaceKey(pqTokens.get(v), d.get(v));
					}
				}

			}
		}
		return cloud;
	}

	/*
	 * Complete the totalPathDistance function, which calculates how far the given
	 * path traverses.
	 */
	@TimeComplexity("O(m)")
	public static double totalPathDistance(Iterable<Edge<Walkway>> path) {
		/*
		 * TCJ iterates through the edges.
		 */
		// # TODO: Complete totalPathDistance function
		int totalPath = 0;
		for (Edge<Walkway> e : path) {
			totalPath += e.getElement().getDistance();
		}
		return totalPath;
	}

	@TimeComplexity("O(n)")
	public Vertex<RoomCoordinate> findVertex(RoomCoordinate roomCoordinates) {
		/*
		 * TCJ has to iterate through every vertex.
		 */
		int xCoordinate = roomCoordinates.getX();
		int yCoordinate = roomCoordinates.getY();
		Vertex<RoomCoordinate> foundV = null;
		// iterates through the vertices, looks for matches
		for (Vertex<RoomCoordinate> v : mGraph.vertices()) {
			int room1 = v.getElement().getX();
			int room2 = v.getElement().getY();
			if (xCoordinate == room1 && yCoordinate == room2) {
				foundV = v;
			}

		}
		return foundV;
	}

	@TimeComplexity("O(n)")
	public ArrayList<Edge<Walkway>> NESW(Vertex<RoomCoordinate> roomCoordinates) throws IllegalArgumentException {
		/*
		 * TCJ has to iterate through the edges and elements.
		 */
		ArrayList<Edge<Walkway>> origin = new ArrayList<Edge<Walkway>>();
		// Copies the outgoing edges into an Array List
		for (Edge<Walkway> e : mGraph.outgoingEdges(roomCoordinates)) {
			origin.addLast(e);
		}

		ArrayList<Edge<Walkway>> order = new ArrayList<Edge<Walkway>>();
		order.addLast(null);
		order.addLast(null);
		order.addLast(null);
		order.addLast(null);

		int rX = roomCoordinates.getElement().getX();
		int rY = roomCoordinates.getElement().getY();
		// puts edges in NORTH, EAST, SOUTH, WEST
		for (int i = 0; i < origin.size(); i++) {
			Vertex<RoomCoordinate> v = mGraph.opposite(roomCoordinates, origin.get(i));
			int newX = v.getElement().getX();
			int newY = v.getElement().getY();

			// up
			if (rY - 1 == newY)
				order.set(0, origin.get(i));

			// right
			else if (rX + 1 == newX)
				order.set(1, origin.get(i));

			// down
			else if (rY + 1 == newY)
				order.set(2, origin.get(i));

			// left
			else if (rX - 1 == newX)
				order.set(3, origin.get(i));
		}
		return order;

	}

	public static void main(String[] aArguments) {
		Labyrinth lLabyrinth = new Labyrinth("SmallLabyrinth.txt");

		// TODO: Testing
	}

}
