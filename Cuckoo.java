/*
 * 	Prateek Sarna : pxs180012
 *	Bharath Rudra : bxr180008
 */

package pxs180012;

public class Cuckoo<T> {

	// For marking elements
	enum Status {
		FILLED, DELETED;
	}

	class Entry<E> {
		E element;
		Status status;

		public Entry(E element) {
			this.element = element;
			this.status = Status.FILLED;
		}
	}

	int k;	// Number of Hash functions
	int tableSize;
	int size;	//	Number of actual elements in hashtable
	Entry<T>[] hashTable;	// Version with 1 table and k hash functions
	float loadFactor = (float) 0.75;
	int threshold;

	public Cuckoo() {
		size = 0;
		k = 2; 
		tableSize = 1024;
		hashTable = new Entry[tableSize]; 
		threshold = tableSize;
	}

	// Code extracted from Java’s HashMap:
	static int hash(int h) {
		// This function ensures that hashCodes that differ only by
		// constant multiples at each bit position have a bounded
		// number of collisions (approximately 8 at default load factor).
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	static int indexFor(int h, int length) {
		return h & (length - 1);
	}

	private int hashFunction(int i, T x) {
		switch (i) {
		case 1:
			return indexFor(hash(x.hashCode()), tableSize);		//	Hash Function 1
		default:
		 return 1 + x.hashCode() % 9;	//	Hash Function 2
		}
	}

	public boolean add(T x) {

		if (size / (float) tableSize > loadFactor) {
			rehash();
		}

		if (contains(x))
			return false;

		//	checking for 2 locations, if first is filled, calculate the second one
		for (int i = 1; i <= k; i++) {
			if (hashTable[hashFunction(i, x)] == null) {
				hashTable[hashFunction(i, x)] = new Entry<T>(x);
				size++;
				return true;
			} else if (hashTable[hashFunction(i, x)].status == Status.DELETED) {
				hashTable[hashFunction(i, x)] = new Entry<T>(x);
				size++;
				return true;
			}
		}

		// In case of double collision, exchange elements and add again by finding new position
		int i = 1, count = 0;
		while (count++ < threshold) {
			int loc = hashFunction(i, x);
			if (hashTable[loc] != null) {
				if (hashTable[loc].status == Status.DELETED) {
					hashTable[loc].element = (T) new Entry<T>(x);
					size++;
					return true;
				} else {
					T temp = (T) hashTable[loc].element;
					hashTable[loc].element = x;
					x = temp;
				}
				i = i == k ? 1 : (i + 1);
			}
		}
		// Too many steps (possible infinite loop). Rebuild hash
		// table with new hash functions.
		rehash();
		return false;
	}

	public boolean contains(T x) {
		for (int i = 1; i <= k; i++) {
			if (hashTable[hashFunction(i, x)] == null)
				return false;
			if (hashTable[hashFunction(i, x)].status == Status.DELETED)
				return false;
			if (hashTable[hashFunction(i, x)].element == x)
				return true;
		}
		return false;
	}

	public T remove(T x) {
		if (contains(x)) {
			T e = null;
			for (int i = 1; i <= k; i++) {
				if (hashTable[hashFunction(i, x)] != null) {
					if (hashTable[hashFunction(i, x)].element == x) {
						e = (T) hashTable[hashFunction(i, x)].element;
						hashTable[hashFunction(i, x)].status = Status.DELETED;
						size--;
						return e;
					}
				}
			}
		}
		return null;
	}

	//	Rehashing will double the table size
	private void rehash() {
		
		//	store all elements in a new array, double the size and enter again by finding new locations
		Entry[] e = new Entry[size];
		for (int i = 0, j = 0; i < tableSize; i++) {
			if (hashTable[i] != null && hashTable[i].status == Status.FILLED) {
				e[j++] = hashTable[i];
				hashTable[i] = null;
			}
		}

		tableSize = 2 * tableSize;
		size = 0;
		hashTable = new Entry[tableSize];

		for (int i = 0; i < e.length; i++) {
			add((T) e[i].element);
		}
	}

	public void printTable() {
		System.out.println("\n\nHash Table: ");
		for (int i = 0; i < tableSize; i++) {
			if (hashTable[i] == null || hashTable[i].status == Status.DELETED)
				System.out.println("" + i + " -> " + "null");
			else
				System.out.println("" + i + " -> " + hashTable[i].element);
		}
	}

}
