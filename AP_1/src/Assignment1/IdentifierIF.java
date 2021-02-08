package Assignment1;

public interface IdentifierIF {

	/*
	 * Elements: characters of type Char
	 * Structure: linear
	 * Domain: alphanumeric characters, at least 1 character, starts with a letter
	 *
	 * constructors
	 *
	 * IdentifierIF()
	 * PRE-
	 * POST- a new Identifier with an undefined value has been created
	 *
	 *
	 * IdentifierIF(SetInterface src)
	 * PRE-
	 * POST- a new Identifier object has been made that is a DEEP copy of the source
	 *
	 *
	 *
	 */
	void init(char c);

	/* PRE : c contains a letter.
	 * POST: contains letter c and has length 1.
	 */


	void addChar(char c);

	/* PRE : s is an alphanumeric character.
	 * POST: identifier now contains character c at length + 1.
	 */

	char getChar(int position);

	/* PRE : integer i is larger or equal to 0 and smaller or equal to length - 1.
	 * POST: returns element at index i.
	 */

	boolean equals(Object o);

	/* PRE : -
	 * POST: TRUE  -> identifiers 1 and 2 are identical.
	 *       FALSE -> identifiers 1 and 2 are not identical.
	 */

	int getSize();

	/* PRE : -
	 * POST: The number of elements in the identifier has been returned.
	 */

	String toString();
	
	char firstChar();
	
	/* PRE: the identifier is not empty
	 * POST: The first character of the identifier has been returned
	 */

}