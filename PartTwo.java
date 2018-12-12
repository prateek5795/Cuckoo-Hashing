/*
 * 	Prateek Sarna : pxs180012
 *	Bharath Rudra : bxr180008
 */

package pxs180012;

import java.util.HashSet;
import java.util.Random;

public class PartTwo {

	static <T> int distinctElementsCuckoo(T[] arr) {
		int count = 0;
		Cuckoo<Integer> c = new Cuckoo<>();
		for (int i = 0; i < arr.length; i++) {
			//	Check if elements is already present in hashmap, add if it is not
			if (!c.contains((Integer) arr[i])) 
            { 
                c.add((Integer) arr[i]); 
                count++;
            } 
		}
		return count;
	}

	static <T> int distinctElementsJavaHashSet(T[] arr) {
        int count = 0;
		HashSet<Integer> h = new HashSet<>(); 
        for (int i=0; i<arr.length; i++) 
        { 
        	//	Check if elements is already present in hashmap, add if it is not
            if (!h.contains(arr[i])) 
            { 
                h.add((Integer) arr[i]); 
                count++;
            } 
        } 
        return count;
	}

	public static void main(String[] args) {
		// PART 2: Generate an array of random integers, and calculate how many distinct
		// numbers it has: static<T> int distinctElements(T[ ] arr) { ... }
		// Compare running times of HashSet and your hashing implementation, for large
		// n.
		System.out.println(
				"PART 2: Generate an array of random integers, and calculate how many distinct numbers it has. Compare running times of HashSet and your hashing implementation, for large n.");

		Random random = new Random();
		int n = 10000; // Large n
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		}
		Object[] ar = new Integer[n];

		for (int i = 0; i < n; ++i) {
			ar[i] = random.nextInt(100); // Generating a random number till 100
		}

		System.out.println("\nRandom Array: ");
		for (int i = 0; i < n; ++i) {
			System.out.print(ar[i] + " ");
		}
		System.out.println();

		long result = 0;
		int modValue = 999983;
		Timer timer = new Timer();
		System.out.println("\nNumber of Distinct Elements using Cuckoo: " + distinctElementsCuckoo((ar)));
		timer.end();

		System.out.println("\nCuckoo Hashing Performance: ");
		//System.out.println(result);
		System.out.println(timer);

		timer = new Timer();
		System.out.println("\nNumber of Distinct Elements using JavaHashset: " + distinctElementsJavaHashSet(ar));
		timer.end();

		System.out.println("\nJava Hashset Performance: ");
		//System.out.println(result);
		System.out.println(timer);

	}

}
