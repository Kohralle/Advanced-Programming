package nl.vu.labs.phoenix.ap;

/** @elements
 *    objects of type T
 *  @structure
 *    linear
 *  @domain
 *    alphanumeric characters and operators such as '+', '|', '-' and '*'
 *  @constructor
 *    there is a default constructor that creates an identifier 'x'
 *  @precondition
 *    --
 *  @postcondition
 *    a new object has been made that represents an identifier.
 *
 **/

public interface IdentifierInterface {
	/*
	 * [2] Mandatory methods. Make sure you do not modify these!
	 * 	   -- Complete the specifications of these methods
	 */

	String value();
	/** @precondition
	 *      the type of the value is not a string
	 *  @postcondition
	 *  	the type of the value has become a string
	 **/


	/*
	 * [3] Add anything else you think belongs to this interface
	 */

	public void init(char c);
	/** @precondition
	 *      --
	 *  @postcondition
	 *  	identifier contains only the parameter char c
	 **/

	public void addChar(char c);
	/** @precondition
	 *      s is a letter or number
	 *  @postcondition
	 *  	identifier now contains character c at length + 1
	 **/

	public boolean equals(Object obj);
	/** @precondition
	 *      --
	 *  @postcondition
	 *  	FALSE: contents of identifier 1 and 2 are not identical or object is not of type identifier or is null
	 *  	TRUE: contents of identifier 1 and 2 are identical
	 **/

	public int hashCode();
	/** @precondition
	 *      --
	 *  @postcondition
	 *  	the value of the hash code is returned
	 **/

	int getSize();
	/** @precondition
	 *      --
	 *  @postcondition
	 *  	the number of elements in the list are returned.
	 **/
}
