package Assignment1;

public class Identifier implements IdentifierIF {

	private StringBuffer buffer;

	public Identifier(char c) {
		init(c);
	}

	public Identifier(Identifier identifier) {
		this.buffer = identifier.buffer;
	}

	@Override
	public void init(char c) {
		buffer = new StringBuffer("" + c);
	}

	@Override
	public String toString() {
		return buffer.toString();
	}

	@Override
	public void addChar(char c) {
		buffer.append(c);
	}

	@Override
	public char getChar(int position) {
		return buffer.charAt(position);
	}

	@Override
	public int getSize() {
		return buffer.length();
	}

	@Override
	public char firstChar() {
		return buffer.charAt(0);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj != null) {
			if (this.buffer.toString().equals(obj.toString())) {
				return true;
			}
		}
		return false;
	}

}