package cs2321;

/**
 * 
 * @author SiddheshDM
 * Date last modified: Sept 24, 2019
 * 
 * The Josephus game implementation with the use of DoublyLinkedLists and CircularArrayQueues
 * 
 */

public class Josephus {
	/**
	 * All persons sit in a circle. When we go around the circle, initially starting
	 * from the first person, then the second person, then the third... 
	 * we count 1,2,3,.., k-1. The next person, that is the k-th person is out. 
	 * Then we restart the counting from the next person, go around, the k-th person 
	 * is out. Keep going the same way, when there is only one person left, she/he 
	 * is the winner. 
	 *  
	 * @parameter persons  an array of string which contains all player names.
	 * @parameter k  an integer specifying the k-th person will be kicked out of the game
	 * @return return a doubly linked list in the order when the players were out of the game. 
	 *         the last one in the list is the winner.  
	 */
	public DoublyLinkedList<String> order(String[] persons, int k ) {
		
		CircularArrayQueue<String> gamePlayers = new CircularArrayQueue<String>(persons.length);

		DoublyLinkedList<String> playersList = new DoublyLinkedList<String>();

		for (int i = 0; i < persons.length; i++) {
			gamePlayers.enqueue(persons[i]);				// Keep adding people to CircularArrayQueue as long as there
															// is space
		}

		while (gamePlayers.size() != 0) {					// Checks to see if the CircularArrayQueue has contents 
															// and runs until it does
			for (int n = 0; n < k-1; n++) {
				gamePlayers.enqueue(gamePlayers.dequeue()); // Dequeues players from the list and enqueues them. Removes
															// from the front and adds them to the back of the queue
			}
			playersList.addLast(gamePlayers.dequeue());		// Removes players from the front of the CircularArrayQueue and
															// adds them to the DoublyLinkedList starting from the back
		}
		return playersList;									// @returns the DoublyLinkedList
	}		
}
