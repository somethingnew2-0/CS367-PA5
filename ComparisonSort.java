///////////////////////////////////////////////////////////////////////////////
//Main Class File:  TestSorts.java
//File:             ComparisonSort.java
//Semester:         Fall 2011
//
//Author:           Peter Collins pmcollins2@wisc.edu
//CS Login:         pcollins
//Lecturer's Name:  Beck Hasti
//Lab Section:      NA
//
///////////////////////////////////////////////////////////////////////////////

/**
 * This class implements seven different comparison sorts:
 * <ul>
 * <li>selection sort</li>
 * <li>insertion sort</li>
 * <li>merge sort</li>
 * <li>quick sort using median-of-three partitioning</li>
 * <li>heap sort</li>
 * <li>shaker sort</li>
 * <li>two-way insertion sort</li>
 * </ul>
 * It also has a method that runs all the sorts on the same input array and
 * prints out statistics.
 */

public class ComparisonSort {
	private static int moves = 0; // the moves counter

	/**
	 * Sorts the given array using the selection sort algorithm. You may use
	 * either the algorithm discussed in the on-line reading or the algorithm
	 * discussed in lecture (which does fewer data moves than the one from the
	 * on-line reading). Note: after this method finishes the array is in sorted
	 * order.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 **/
	public static <E extends Comparable<E>> void selectionSort(E[] A) {
		int i, j, minIndex, length = A.length;
		E min;

		for (i = 0; i < length; i++) {
			++moves;
			min = A[i];
			minIndex = i;
			for (j = i + 1; j < length; j++) {
				if (A[j].compareTo(min) < 0) {
					++moves;
					min = A[j];
					minIndex = j;
				}
			}
			++moves;
			A[minIndex] = A[i];
			++moves;
			A[i] = min;
		}
	}

	/**
	 * Sorts the given array using the insertion sort algorithm. Note: after
	 * this method finishes the array is in sorted order.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 **/
	public static <E extends Comparable<E>> void insertionSort(E[] A) {
		int i, j, length = A.length;
		E tmp;

		for (i = 1; i < length; i++) {
			++moves;
			tmp = A[i];
			j = i - 1;
			while ((j >= 0) && (A[j].compareTo(tmp) > 0)) {
				++moves;
				A[j + 1] = A[j]; // move one value over one place to the right
				j--;
			}
			++moves;
			A[j + 1] = tmp; // insert kth value in correct place relative
							// to previous values
		}
	}

	/**
	 * Sorts the given array using the merge sort algorithm. Note: after this
	 * method finishes the array is in sorted order.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 **/
	public static <E extends Comparable<E>> void mergeSort(E[] A) {
		mergeAux(A, 0, A.length - 1); // call the aux. function to do all the
										// work
	}

	/**
	 * Recursive method initially called by the wrapper method mergeSort which
	 * starts the merge sort algorithm.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 * @param low
	 *            the index of the low end of array to merge sort
	 * @param high
	 *            the index of the high end of array to merge sort
	 */
	private static <E extends Comparable<E>> void mergeAux(E[] A, int low,
			int high) {
		// base case
		if (low == high)
			return;

		// recursive case

		// Step 1: Find the middle of the array (conceptually, divide it in
		// half)
		int mid = (low + high) / 2;

		// Steps 2 and 3: Sort the 2 halves of A
		mergeAux(A, low, mid);
		mergeAux(A, mid + 1, high);

		// Step 4: Merge sorted halves into an auxiliary array
		E[] tmp = (E[]) (new Comparable[high - low + 1]);
		int left = low; // index into left half
		int right = mid + 1; // index into right half
		int pos = 0; // index into tmp

		while ((left <= mid) && (right <= high)) {
			// choose the smaller of the two values "pointed to" by left, right
			// copy that value into tmp[pos]
			// increment either left or right as appropriate
			// increment pos
			if (A[left].compareTo(A[right]) < 0) {
				++moves;
				tmp[pos] = A[left];
				left++;
			} else {
				++moves;
				tmp[pos] = A[right];
				right++;
			}
			pos++;
		}

		// when one of the two sorted halves has "run out" of values, but
		// there are still some in the other half, copy all the remaining
		// values to tmp
		// Note: only 1 of the next 2 loops will actually execute
		while (left <= mid) {
			++moves;
			tmp[pos] = A[left];
			left++;
			pos++;
		}
		while (right <= high) {
			++moves;
			tmp[pos] = A[right];
			right++;
			pos++;
		}

		// all values are in tmp; copy them back into A
		moves += tmp.length;
		System.arraycopy(tmp, 0, A, low, tmp.length);
	}

	/**
	 * Sorts the given array using the quick sort algorithm, using the median of
	 * the first, last, and middle values in each segment of the array as the
	 * pivot value. Note: after this method finishes the array is in sorted
	 * order.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 **/
	public static <E extends Comparable<E>> void quickSort(E[] A) {
		quickAux(A, 0, A.length - 1);
	}

	/**
	 * Recursive method initially called by the wrapper method quickSort which
	 * starts the quick sort algorithm.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 * @param low
	 *            the index of the low end of array to quick sort
	 * @param high
	 *            the index of the high end of array to quick sort
	 */
	private static <E extends Comparable<E>> void quickAux(E[] A, int low,
			int high) {
		if (high - low < 2) {
			// Base case
			// If there are two items sort manually
			if (high - low == 1) {
				if (A[low].compareTo(A[high]) > 0) {
					swap(A, low, high);
				}
			}
			// If there is only one ignore
		} else {
			// Recursive case
			int right = partition(A, low, high);
			quickAux(A, low, right);
			quickAux(A, right + 2, high);
		}
	}

	/**
	 * Partition method of quick sort which sorts based on a pivot determined by
	 * a median of three.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 * @param low
	 *            the index of the low end of array to quick sort
	 * @param high
	 *            the index of the high end of array to quick sort
	 * @return position of where the swapped pivot was moved to
	 */
	private static <E extends Comparable<E>> int partition(E[] A, int low,
			int high) {
		// precondition: A.length > 3

		E pivot = medianOfThree(A, low, high); // this does step 1
		int left = low + 1, right = high - 2;
		while (left <= right) {
			while (A[left].compareTo(pivot) < 0)
				left++;
			while (A[right].compareTo(pivot) > 0)
				right--;
			if (left <= right) {
				swap(A, left, right);
				left++;
				right--;
			}
		}
		swap(A, left, high - 1); // step 4
		return right;
	}

	/**
	 * Perform the median of three by the median of the first, middle, and last
	 * values of the array. Swaps the values when appropriate.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 * @param low
	 *            the index of the low end of array to compare
	 * @param high
	 *            the index of the high end of array to compare
	 * @return the median object
	 */
	private static <E extends Comparable<E>> E medianOfThree(E[] A, int low,
			int high) {
		// Find the middle of the array
		int middle = (low + high) / 2;
		// order low & middle
		if (A[low].compareTo(A[middle]) > 0)
			swap(A, low, middle);
		// order low & high
		if (A[low].compareTo(A[high]) > 0)
			swap(A, low, high);
		// order middle & high
		if (A[middle].compareTo(A[high]) > 0)
			swap(A, middle, high);

		swap(A, middle, high - 1); // put pivot on right
		return A[high - 1]; // return median value
	}

	/**
	 * Sorts the given array using the heap sort algorithm outlined below. Note:
	 * after this method finishes the array is in sorted order.
	 * 
	 * <p>
	 * The heap sort algorithm is:
	 * </p>
	 * 
	 * <pre>
	 * for each i from 1 to the end of the array
	 *     insert A[i] into the heap (contained in A[0]...A[i-1])
	 *     
	 * for each i from the end of the array up to 1
	 *     remove the max element from the heap and put it in A[i]
	 * </pre>
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 **/
	public static <E extends Comparable<E>> void heapSort(E[] A) {
		int length = A.length, size = 0;
		E[] heap = (E[]) (new Comparable[length + 1]);

		// for each i from 1 to the end of the array
		// insert A[i] into the heap (contained in A[0]...A[i-1])
		for (int i = 0; i < length; i++) {
			// Put the item in the next spot in the array/heap
			++moves;
			heap[++size] = A[i];

			// Heapify by swapping the value up
			int child = size;
			while (heap[child / 2] != null
					&& heap[child / 2].compareTo(heap[child]) < 0) {
				// Swap the value up because the parent is less
				++moves;
				E temp = heap[child / 2];
				++moves;
				heap[child / 2] = heap[child];
				++moves;
				heap[child] = temp;

				// Do we need to swap again?
				child = child / 2;
			}
		}

		// for each i from the end of the array up to 1
		// remove the max element from the heap and put it in A[i]
		for (int i = length - 1; i >= 0; i--) {
			// Save the root as the value to put at the end of the array
			++moves;
			A[i] = heap[1];

			// Set the last child as the root
			++moves;
			heap[1] = heap[size];
			// Set the old last child as null
			heap[size--] = null;

			// Heapify by swapping down
			int parent = 1;
			while (parent * 2 + 1 < heap.length
					&& ((heap[parent * 2] != null && heap[parent * 2]
							.compareTo(heap[parent]) > 0) || (heap[parent * 2 + 1] != null && heap[parent * 2 + 1]
							.compareTo(heap[parent]) > 0))) {
				// Swap the parent with the child if the children are bigger
				++moves;
				E temp = heap[parent];
				// If both children are bigger, pick the biggest and swap
				if (heap[parent * 2] != null && heap[parent * 2 + 1] != null) {
					if (heap[parent * 2].compareTo(heap[parent * 2 + 1]) > 0) {
						// The left is bigger, swap with the parent
						++moves;
						heap[parent] = heap[parent * 2];
						++moves;
						heap[parent * 2] = temp;
						parent *= 2;
					} else {
						// The right is bigger, swap with the parent
						++moves;
						heap[parent] = heap[parent * 2 + 1];
						++moves;
						heap[parent * 2 + 1] = temp;
						parent = parent * 2 + 1;
					}

				} else if (heap[parent * 2] != null) {
					// Only the left child is bigger swap with the parent
					++moves;
					heap[parent] = heap[parent * 2];
					++moves;
					heap[parent * 2] = temp;
					parent *= 2;
				} else {
					// Only the right child is bigger, swap with the parent
					++moves;
					heap[parent] = heap[parent * 2 + 1];
					++moves;
					heap[parent * 2 + 1] = temp;
					parent = parent * 2 + 1;
				}
			}

		}
	}

	/**
	 * Sorts the given array using the shaker sort algorithm outlined below.
	 * Note: after this method finishes the array is in sorted order.
	 * 
	 * <p>
	 * The shaker sort is a bi-directional bubble sort. The shaker sort
	 * algorithm is:
	 * 
	 * <pre>
	 * begin = 0, end = A.length-1
	 * 
	 * // At the beginning of every iteration of this loop, we know that the 
	 * // elements in A are in their final sorted positions from A[0] to A[begin-1]
	 * // and from A[end+1] to the end of A.  That means that A[begin] to A[end] are
	 * // still to be sorted.
	 * do
	 *     for i going from begin to end-1
	 *         if A[i] and A[i+1] are out of order, swap them
	 *     end--
	 *     
	 *     if no swaps occurred during the preceding for loop, the sort is done
	 *     
	 *     for i going from end to begin+1
	 *         if A[i] and A[i-1]  are out of order, swap them
	 *     begin++
	 *  until no swaps have occurred or begin >= end
	 * </pre>
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 **/
	public static <E extends Comparable<E>> void shakerSort(E[] A) {
		boolean swapsOccured = true;
		int begin = 0, end = A.length - 1;

		// until no swaps have occurred or begin >= end
		while (swapsOccured && begin < end) {
			swapsOccured = false;
			
			// for i going from begin to end-1
			//     if A[i] and A[i+1] are out of order, swap them
			for (int i = begin; i < end; i++) {
				if (A[i].compareTo(A[i + 1]) > 0) {
					swap(A, i, i + 1);
					swapsOccured = true;
				}
			}
			end--;
			
			// if no swaps occurred during the preceding for loop, the sort is done
			if(!swapsOccured) {
				break;
			}
			
			// for i going from end to begin+1
			//    if A[i] and A[i-1]  are out of order, swap them
			for (int i = end; i >= begin + 1; i--) {
				if (A[i].compareTo(A[i - 1]) < 0) {
					swap(A, i, i - 1);
					swapsOccured = true;

				}
			}
			begin++;
		}
	}

	/**
	 * Sorts the given array using the two-way insertion sort algorithm outlined
	 * below. Note: after this method finishes the array is in sorted order.
	 * <p>
	 * The two-way insertion sort is a bi-directional insertion sort that sorts
	 * the array from the center out towards the ends. The two-way insertion
	 * sort algorithm is:
	 * </p>
	 * 
	 * <pre>
	 * precondition: A has an even length
	 * left = element immediately to the left of the center of A
	 * right = element immediately to the right of the center of A
	 * if A[left] > A[right]
	 *     swap A[left] and A[right]
	 * left--, right++ 
	 * 
	 * // At the beginning of every iteration of this loop, we know that the elements
	 * // in A from A[left+1] to A[right-1] are in relative sorted order.
	 * do
	 *     if (A[left] > A[right])
	 *         swap A[left] and A[right]
	 *     
	 *     starting with with A[right] and moving to the left, use insertion sort 
	 *     algorithm to insert the element at A[right] into the correct location 
	 *     between A[left+1] and A[right-1]
	 *     
	 *     starting with A[left] and moving to the right, use the insertion sort 
	 *     algorithm to insert the element at A[left] into the correct location 
	 *     between A[left+1] and A[right-1]
	 *     
	 *     left--, right++
	 * until left has gone off the left edge of A and right has gone off the right 
	 *       edge of A
	 * </pre>
	 * <p>
	 * This sorting algorithm described above only works on arrays of even
	 * length. If the array passed in as a parameter is not even, the method
	 * throws an IllegalArgumentException
	 * </p>
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to sort
	 * @throws IllegalArgumentException
	 *             if the length or A is not even
	 **/
	public static <E extends Comparable<E>> void twoWayInsertSort(E[] A) {
		int length = A.length, left = length / 2 - 1, right = length / 2, i;
		E tmp;

		// Sort the initial left and right values
		if (A[left].compareTo(A[right]) > 0) {
			swap(A, left, right);
		}
		left--;
		right++;
		
		// until left has gone off the left edge of A and right has gone off the right 
		while (left >= 0 && right < length) {
			if (A[left].compareTo(A[right]) > 0) {
				swap(A, left, right);
			}

			
			// starting with with A[right] and moving to the left, use insertion sort 
			// algorithm to insert the element at A[right] into the correct location 
			// between A[left+1] and A[right-1]
			++moves;
			tmp = A[right];
			i = right - 1;
			while ((i >= left + 1) && (A[i].compareTo(tmp) > 0)) {
				++moves;
				A[i + 1] = A[i]; // move one value over one place to the right
				i--;
			}
			++moves;
			A[i + 1] = tmp;

			// starting with A[left] and moving to the right, use the insertion sort 
			// algorithm to insert the element at A[left] into the correct location 
			// between A[left+1] and A[right-1]
			++moves;
			tmp = A[left];
			i = left + 1;
			while ((i < right) && (A[i].compareTo(tmp) < 0)) {
				++moves;
				A[i - 1] = A[i]; // move one value over one place to the left
				i++;
			}
			++moves;
			A[i - 1] = tmp;

			left--;
			right++;
		}

	}

	/**
	 * A helper method which uses three data moves to swap to members of the
	 * given array at the given location.
	 * 
	 * @param <E>
	 *            the type of values to be sorted
	 * @param A
	 *            the array to swap value on
	 * @param left
	 *            the index of the left object to swap
	 * @param right
	 *            the index of the right object to swap
	 */
	private static <E extends Comparable<E>> void swap(E[] A, int left,
			int right) {
		// Swap the values with a tmp variable and record the movements
		++moves;
		E tmp = A[left];
		++moves;
		A[left] = A[right];
		++moves;
		A[right] = tmp;
	}
	
	/**
	 * Uses a test similar to one iteration of bubble sort to test if
	 * the array given was correctly sorted. 
	 * 
	 * @param <E>
	 *            the type of values that were sorted
	 * @param A
	 *            the array with the sorted values
	 * @return
	 * 			whether the array was correctly sorted
	 */
	private static <E extends Comparable<E>> boolean testSort(E[] A) {
		int length = A.length;
		for (int i = 0; i < length - 1; i++) {
			if (A[i].compareTo(A[i+1]) > 0) {
				return false;
			}
		}		
		return true;
	}

	/**
	 * Resets the moves counter to 0.
	 */
	public static void resetMoves() {
		moves = 0;
	}

	/**
	 * Returns the value of the moves counter.
	 * 
	 * @return the value of the moves counter
	 */
	public static int getMoves() {
		return moves;
	}

	/**
	 * Internal helper for printing rows of the output table.
	 * 
	 * @param sort
	 *            name of the sorting algorithm
	 * @param compares
	 *            number of comparisons performed during sort
	 * @param moves
	 *            number of data moves performed during sort
	 * @param milliseconds
	 *            time taken to sort, in milliseconds
	 */
	private static void printStatistics(String sort, int compares, int moves,
			long milliseconds) {
		System.out.format("%-15s%,15d%,15d%,15d\n", sort, compares, moves,
				milliseconds);
	}

	/**
	 * Sorts the given array using the seven different sorting algorithms and
	 * prints out statistics. The sorts performed are:
	 * <ul>
	 * <li>selection sort</li>
	 * <li>insertion sort</li>
	 * <li>merge sort</li>
	 * <li>quick sort using median-of-three partitioning</li>
	 * <li>heap sort</li>
	 * <li>shaker sort</li>
	 * <li>two-way insertion sort</li>
	 * </ul>
	 * <p>
	 * The statistics displayed for each sort are: number of comparisons, number
	 * of data moves, and time (in milliseconds).
	 * </p>
	 * <p>
	 * Note: each sort is given the same array (i.e., in the original order) and
	 * the input array A is not changed by this method.
	 * </p>
	 * 
	 * @param A
	 *            the array to sort
	 **/
	static public void runAllSorts(InstrumentedInt[] A) {
		System.out.format("%-15s%15s%15s%15s\n", "algorithm", "data compares",
				"data moves", "milliseconds");
		System.out.format("%-15s%15s%15s%15s\n", "---------", "-------------",
				"----------", "------------");

		InstrumentedInt[] testA = A.clone();
		long before = System.currentTimeMillis();
		selectionSort(testA);
		long after = System.currentTimeMillis();
		printStatistics("selection", InstrumentedInt.getCompares(), getMoves(),
				after - before);
		if (!testSort(testA)) {
			System.err.println("^ This sort was incorrect! ^");
		}
		InstrumentedInt.resetCompares();
		resetMoves();

		testA = A.clone();
		before = System.currentTimeMillis();
		insertionSort(testA);
		after = System.currentTimeMillis();
		printStatistics("insertion", InstrumentedInt.getCompares(), getMoves(),
				after - before);
		if (!testSort(testA)) {
			System.err.println("^ This sort was incorrect! ^");
		}
		InstrumentedInt.resetCompares();
		resetMoves();

		testA = A.clone();
		before = System.currentTimeMillis();
		mergeSort(testA);
		after = System.currentTimeMillis();
		printStatistics("merge", InstrumentedInt.getCompares(), getMoves(),
				after - before);
		if (!testSort(testA)) {
			System.err.println("^ This sort was incorrect! ^");
		}
		InstrumentedInt.resetCompares();
		resetMoves();

		testA = A.clone();
		before = System.currentTimeMillis();
		quickSort(testA);
		after = System.currentTimeMillis();
		printStatistics("quick", InstrumentedInt.getCompares(), getMoves(),
				after - before);
		if (!testSort(testA)) {
			System.err.println("^ This sort was incorrect! ^");
		}
		InstrumentedInt.resetCompares();
		resetMoves();

		testA = A.clone();
		before = System.currentTimeMillis();
		heapSort(testA);
		after = System.currentTimeMillis();
		printStatistics("heap", InstrumentedInt.getCompares(), getMoves(),
				after - before);
		if (!testSort(testA)) {
			System.err.println("^ This sort was incorrect! ^");
		}
		InstrumentedInt.resetCompares();
		resetMoves();

		testA = A.clone();
		before = System.currentTimeMillis();
		shakerSort(testA);
		after = System.currentTimeMillis();
		printStatistics("shaker", InstrumentedInt.getCompares(), getMoves(),
				after - before);
		if (!testSort(testA)) {
			System.err.println("^ This sort was incorrect! ^");
		}
		InstrumentedInt.resetCompares();
		resetMoves();

		testA = A.clone();
		before = System.currentTimeMillis();
		twoWayInsertSort(testA);
		after = System.currentTimeMillis();
		printStatistics("2-way insertion", InstrumentedInt.getCompares(),
				getMoves(), after - before);
		if (!testSort(testA)) {
			System.err.println("^ This sort was incorrect! ^");
		}
		InstrumentedInt.resetCompares();
		resetMoves();
	}
}
