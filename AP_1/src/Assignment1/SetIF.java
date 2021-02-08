package Assignment1;

public interface SetIF {

	/*
	 * Elements: identifiers of type Identifier
	 * Structure: none
	 * Domain: 0 until 20 elements
	 *
	 * constructors
	 *
	 * SetIF()
	 * PRE-
	 * POST- a new Set object has been made and contains an empty Set
	 *
	 *
	 * SetIF(SetIF src)
	 * PRE-
	 * POST- a new Set object has been made that is a DEEP copy of the source
	 *
	 *
	 *
	 */
	void init();

	/* PRE :
	 * POST: Set is empty.
	 */

	void addElement(Identifier element) throws Exception;

	/* PRE : -
	 * POST: SUCCES -> set contains a copy of this element
	 *		 FAILURE-> set is full, element not already a member of this set.
	 */

	Identifier getElement();

	/* PRE : the set is not empty.
	 * POST: returns one element in the set.
	 */
	
	void removeElement();
	
	/* PRE : -
	 * POST: the element is not in the set.
	 */

	boolean contains(Identifier identifier);

	/* PRE : -
	 * POST: SUCCES -> the element is in the set.
	 * 		 FAILURE-> the element is not in the set.
	 */


	int getSize();

	/* PRE : -
	 * POST: returns the amount of elements in this set.
	 */

	String toString();
	/* PRE: -
	 * POST: elements of the set are represented as a string.
	 */


	Set difference(Set set) throws Exception;

	/* PRE : -
	 * POST: returns the difference of this set s.
	 */

	Set intersection(Set set) throws Exception;

	/* PRE : - 
	 * POST: returns the intersections of this set s.
	 */
	
	Set union(Set set) throws Exception;

	/* PRE : -
	 * POST: SUCCES -> returns the union of this set and s.
	 * 		 FAILURE-> contains more than the max amount of elements
	 */
	
	Set symmetric_difference(Set set) throws Exception;

	/* PRE : - 
	 * POST: SUCCES -> returns the symmetric difference of this set and s.
	 * 		 FAILURE-> contains more than the max amount of elements.
	 */

}