package Assignment1;

public class Set implements SetIF {

	private static final int MAX_LENGTH_SET = 20;
	private static final String LIMIT_EXCEEDED = "The input may only contain up to " + MAX_LENGTH_SET
			+ " identifiers \n";

	private Identifier[] identifiers;
	private int numberOfElements;

	public Set() {
		identifiers = new Identifier[MAX_LENGTH_SET];
		numberOfElements = 0;
	}

	public Set(Set set) {
		this.identifiers = new Identifier[MAX_LENGTH_SET];

		for (int i = 0; i < set.numberOfElements; i++) {
			this.identifiers[i] = new Identifier(set.identifiers[i]);
		}

		this.numberOfElements = set.numberOfElements;
	}

	@Override
	public void init() {
		numberOfElements = 0;
	}

	@Override
	public void addElement(Identifier identifier) throws Exception {
		if (this.contains(identifier)) {
			return;
		}

		else {
			identifiers[numberOfElements] = new Identifier(identifier);
			numberOfElements += 1;
		}

		if (numberOfElements > MAX_LENGTH_SET) {
			throw new Exception(LIMIT_EXCEEDED);
		}
	}

	@Override
	public Identifier getElement() {
		return identifiers[numberOfElements - 1];
	}

	@Override
	public void removeElement() {
		numberOfElements -= 1;
	}

	@Override
	public int getSize() {
		return numberOfElements;
	}

	@Override
	public boolean contains(Identifier identifier) {
		for (int i = 0; i < numberOfElements; i++) {
			if (identifier.equals(identifiers[i])) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		if (numberOfElements == 0) {
			return "";
		}
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < numberOfElements; i++) {
			result.append(identifiers[i].toString());

			if (i == numberOfElements - 1) {
				continue;
			}
			else {
				result.append(" ");
			}
		}
		return result.toString();
	}

	// all elements contained in the 1st but not the 2nd set.
	@Override
	public Set difference(Set set) throws Exception {
		Set result = new Set();
		Set copy1 = new Set(this);

		for (int i = 0; i < this.getSize(); i++) {
			Identifier element = copy1.getElement();
			copy1.removeElement();
			if (!set.contains(element)) {
				result.addElement(element);
			}
		}
		return result;
	}

	@Override
	public Set intersection(Set set) throws Exception {
		Set result = new Set();
		Set copy1 = new Set(this);

		for (int i = 0; i < this.getSize(); i++) {
			Identifier element = copy1.getElement();
			copy1.removeElement();
			if (set.contains(element)) {
				result.addElement(element);
			}
		}
		return result;
	}

	@Override
	public Set union(Set set) throws Exception {
		Set result = new Set(set);
		Set copy1 = new Set(this);

		for (int i = 0; i < this.getSize(); i++) {
			Identifier element = copy1.getElement();
			copy1.removeElement();
			if (!result.contains(element)) {
				result.addElement(element);
			}
		}

		if (result.getSize() > MAX_LENGTH_SET) {
			throw new Exception(LIMIT_EXCEEDED);
		}

		return result;
	}

	@Override
	public Set symmetric_difference(Set set) throws Exception {
		return union(set).difference(intersection(set));
	}
}