/*
 * 	Prateek Sarna : pxs180012
 *	Bharath Rudra : bxr180008
 */

package pxs180012;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class PartOne {

	public static void main(String[] args) throws FileNotFoundException {
		// PART 1: Compare cuckoo hashing's performance with Java's
		// HashMap/HashSet on millions of operations: add, contains, and remove.

		System.out.println(
				"PART 1: Compare cuckoo hashing's performance ith Java HashMap/HashSet on millions of operations: add, contains, and remove.");
		Scanner sc;
		if (args.length > 0) {
			File file = new File(args[0]);
			sc = new Scanner(file);
		} else {
			sc = new Scanner(System.in);
		}

		String operation = "";
		int operand = 0;
		long result = 0;
		int modValue = 999983;

		Timer timer = new Timer();
		Cuckoo<Integer> ck = new Cuckoo<Integer>();

		System.out.println("\nEnter Operations for Cuckoo Hashing: ");

		while (!((operation = sc.next()).equals("End"))) {
			switch (operation) {
			case "Add":
				operand = sc.nextInt();
				if (ck.add(operand)) {
					result = (result + 1) % modValue;
				}
				break;
			case "Remove":
				operand = sc.nextInt();
				if (ck.remove(operand) != null) {
					result = (result + 1) % modValue;
				}
				break;
			case "Contains":
				operand = sc.nextInt();
				if (ck.contains(operand)) {
					result = (result + 1) % modValue;
				}
				break;
			}
		}

		timer.end();

		System.out.print("Cuckoo Hashing Performance: ");
		System.out.println(result);
		System.out.println(timer);
		System.out.println();

		// For HashMap/Hashset
		if (args.length > 0) {
			File file = new File(args[0]);
			sc = new Scanner(file);
		} else {
			sc = new Scanner(System.in);
		}

		operation = "";
		operand = 0;
		result = 0;
		modValue = 999983;

		timer = new Timer();
		HashSet<Integer> hs = new HashSet<>();
		System.out.println("\nEnter Operations for Java Hashmap: ");
		while (!((operation = sc.next()).equals("End"))) {
			switch (operation) {
			case "Add":
				operand = sc.nextInt();
				if (hs.add(operand)) {
					result = (result + 1) % modValue;
				}
				break;
			case "Remove":
				operand = sc.nextInt();
				if (hs.remove(operand)) {
					result = (result + 1) % modValue;
				}
				break;
			case "Contains":
				operand = sc.nextInt();
				if (hs.contains(operand)) {
					result = (result + 1) % modValue;
				}
				break;
			}
		}

		timer.end();

		System.out.print("Java Hashset Performance: ");
		System.out.println(result);
		System.out.println(timer);

	}

}
