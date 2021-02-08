package nl.vu.labs.phoenix.ap;

/** @elements
 *    objects of type T
 *  @structure
 *    none
 *  @domain
 *    all sets where all elements have the same type
 *  @constructor
 *    There is a default constructor that creates an empty set
 *  @precondition
 *    --
 *  @postcondition
 *    The new Set-object is the empty set
 *
 **/
public interface SetInterface<T extends Comparable<T>> {

	/*
	 * [2] Mandatory methods. Make sure you do not modify these!
	 * 	   -- Complete the specifications of these methods
	 */

	boolean add(T t);
	/** @precondition
	 *      --
	 *  @postcondition
	 *  	FALSE: element was already present
	 *  	TRUE: element was inserted
	 **/


	T get();
	/** @precondition
	 *  	-- // easier choice -> set is not empty (like a1)
	 *  @postcondition
	 *   	the element is returned
	 **/

	boolean remove(T t);
	/** @precondition
	 *      the set is not empty
	 *  @postcondition
	 *   	the element is removed
	 **/

	int size();
	/** @precondition
	 *  	--
	 *  @postcondition
	 *  	The number of elements in the set has been returned
	 **/


	SetInterface<T> copy();
	/** @precondition
	 *    --
	 *  @postcondition
	 *    A copy of the set has been returned
	 */


	/*
	 * [3] Methods for set operations
	 * 	   -- Add methods to perform the 4 basic set operations
	 * 		  (union, intersection, difference, symmetric difference)
	 */

	SetInterface<T> union(SetInterface<T> other);
	/** @precondition
	 *    --
	 *  @postcondition
	 *    returns the union of the sets as t
	 */

	SetInterface<T> intersection(SetInterface<T> other);
	/** @precondition
	 *    --
	 *  @postcondition
	 *    returns the intersect of the sets as t
	 */

	SetInterface<T> complement(SetInterface<T> other);
	/** @precondition
	 *    --
	 *  @postcondition
	 *    returns the complement of the sets as t
	 */

	SetInterface<T> symdiff(SetInterface<T> other);
	/** @precondition
	 *    --
	 *  @postcondition
	 *    returns the symmetric difference of the sets as t
	 */


	/*
	 * [4] Add anything else you think belongs to this interface
	 */

	public void init();
	/** @precondition
	 *    --
	 *  @postcondition
	 *    the set is empty
	 */

	boolean contains(T t);
	/** @precondition
	 *    --
	 *  @postcondition
	 *    FALSE: the element is in the set
	 *    TRUE:	 the element is not in the set
	 */

	boolean isEmpty();
	/** @precondition
	 *    --
	 *  @postcondition
	 *    FALSE: the set is not empty or null
	 *    TRUE:	 the set is empty
	 */

}
