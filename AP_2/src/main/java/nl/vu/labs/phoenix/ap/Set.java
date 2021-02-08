package nl.vu.labs.phoenix.ap;

public class Set<T extends Comparable<T>> implements SetInterface<T> {

	private ListInterface<T> elements;

	public Set() {
		elements = new LinkedList();
		elements.init();
	}

	public Set(ListInterface<T> elements) {
		this.elements = elements;
	}

	@Override
	public boolean add(T t) {
		if(elements.find(t) == false){
			elements.insert(t);
			return true;
		}

		return false;
	}

	@Override
	public T get() {
		return elements.retrieve();
	}

	@Override
	public boolean remove(T t) {
		if (elements.find(t)) {
			elements.remove();
		}
		return false;
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public SetInterface<T> copy() {

		ListInterface<T> copyList = this.elements.copy();
		copyList.goToFirst();
		SetInterface<T> result = new Set<T>(copyList);

		return result;
	}

	@Override
	public SetInterface<T> union(SetInterface<T> other) {

		SetInterface<T> result = other.copy();
		SetInterface<T> copy = this.copy();

		for (int i = 0; i < this.size(); i++) {
			T x = copy.get();
			result.add(x);
			copy.remove(x);
		}
		return result;

	}

	@Override
	public SetInterface<T> intersection(SetInterface<T> other) {

		SetInterface<T> result = new Set<T>();
		SetInterface<T> copy = this.copy();


		for (int i = 0; i < this.size(); i++) {
			T x = copy.get();
			copy.remove(x);
			if (other.contains(x)){
				result.add(x);
			}
		}

		return result;

	}

	@Override
	public SetInterface<T> complement(SetInterface<T> other) {
		SetInterface<T> result = new Set<T>();
		SetInterface<T> copy = this.copy();

		for (int i = 0; i < this.size(); i++) {
			T element = copy.get();
			copy.remove(element);
			if (!other.contains(element)){
				result.add(element);
			}
		}

		return result;
	}

	@Override
	public SetInterface<T> symdiff(SetInterface<T> other) {
		return union(other).complement(intersection(other));
	}

	@Override
	public void init() {
		elements.init();
	}

	@Override
	public boolean contains(T t) {
		return this.elements.find(t);
	}

	@Override
	public boolean isEmpty() {
		return this.elements.isEmpty();
	}
	
}
