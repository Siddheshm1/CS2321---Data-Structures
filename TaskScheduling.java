package cs2321;

import net.datastructures.Entry;

/**
 * This is the TaskScheduling problem implementing the Greedy Algorithm.
 * @author Siddhesh Mahadeshwar
 * Date Modified: 10/17/2019
 */

public class TaskScheduling {
	/**
	 * Goal: Perform all the tasks using a minimum number of machines. 
	 * 
	 *       
	 * @param tasks tasks[i][0] is start time for task i
	 *              tasks[i][1] is end time for task i
	 * @return The minimum number or machines
	 */

	private static class Task {
		int sTime = 0;
		int eTime = 0;

		private Task(int s, int e) {
			sTime = s;
			eTime = e;
		}
	}

	/**
	 * Finds out the minimum number of machines required to have all the tasks done
	 * @param tasks
	 * @return
	 */
	public static int NumOfMachines(int[][] tasks) {
		int start;
		int end;
		int machineCounter = 0;
		HeapPQ<Integer, Task> PriorityQ = new HeapPQ<Integer, Task>();
		HeapPQ<Integer, Integer> machinesPriorityQ = new HeapPQ<Integer, Integer>();

		for(int i = 0; i <tasks.length;i++) { // continue to perform for loop until 
			start = tasks[i][0];
			end = tasks[i][1];
			Task task = new Task(start, end);
			PriorityQ.insert(task.sTime, task); 
		}

		while(PriorityQ.size()>0) { // while priority queue has elements, continue to execute
			Entry<Integer, Task> e = PriorityQ.removeMin();
			start = e.getKey();
			end = e.getValue().eTime;


			if(machinesPriorityQ.size()>0) { // while there are machines in the priority queue, continue to execute
				Entry<Integer, Integer> e2 = machinesPriorityQ.min();

				int mEnd = e2.getKey();

				if (mEnd <= start ) {
					machinesPriorityQ.replaceKey(e2, end);
				} else {
					machinesPriorityQ.insert(end,  0);
					machineCounter++;
				}

			} else {
				machinesPriorityQ.insert(end,  0);
				machineCounter++; 
			}
		}
		return machineCounter; // return the number of machines used
	}
}
