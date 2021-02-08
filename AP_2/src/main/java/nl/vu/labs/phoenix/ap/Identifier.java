package nl.vu.labs.phoenix.ap;

public class Identifier implements IdentifierInterface {

	private StringBuffer myString;

	@Override
	public String value() {
		return myString.toString();
	}

	@Override
	public void init(char c) {
		myString = new StringBuffer("" + c);
	}

	@Override
	public void addChar(char c) {
		myString.append(c);
	}

	@Override
	public int getSize() {
		return myString.length();
	}

	@Override
	public boolean equals(Object obj) {

		if ( this instanceof Object) {
			if (obj != null) {
				Identifier i = (Identifier) obj;
				if (this.myString.toString().equals(i.value())) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return myString.toString().hashCode();
	}
}
