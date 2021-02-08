package nl.vu.labs.phoenix.ap;

public class LinkedList<E extends Comparable<E>> implements ListInterface<E> {

	private class Node {

		E data;
		Node prior, next;

		public Node(E data) {
			this(data, null, null);
		}

		public Node(E data, Node prior, Node next) {
			this.data = data == null ? null : data;
			this.prior = prior;
			this.next = next;
		}

	}
	private int size = 0;
	private Node current = new Node (null, null, null);;
	private Node begin = new Node (null, null, null);;
	private Node end = new Node (null, null, null);;



	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public ListInterface<E> init() {
		size = 0;
		current = new Node(null, null, null);
		begin = new Node (null, null, null);
		end = new Node (null, null, null);
		return this;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public ListInterface<E> insert(E d) {
		Node newNode = new Node(d);
		if (size == 0) {
			begin = end = newNode;
			current = newNode;
		}
		else {
			find(d);
			if (current == begin && d.compareTo(current.data) <= 0) {
				newNode.next = current;
				current.prior = newNode;
				current = begin = newNode;
			}
			else {
				if(current.data.compareTo(d) == 0) {
					goToPrevious();
				}
				if (current != end) {
					current = current.next = current.next.prior = new Node(d, current, current.next);
				}
				else {
					current.next = newNode;
					newNode.prior = current;
					end = current = newNode;
				}
			}
		}
		size++;
		return this;
	}

	@Override
	public E retrieve() {
		return current.data;
	}

	@Override
	public ListInterface<E> remove() {
		if (size == 1) {
			return this.init();
		}

		else if (current.next == null) {
			goToPrevious();
			end = current;
			current.next = null;
		}

		else if(current.prior == null) {
			goToNext();
			begin = current;

		}
		else {
			current.prior = current.prior.prior;
			current = current.next;
		}


		size--;
		return this;
	}

	@Override
	public boolean find(E d) {
		if (size() == 0) {
			return false;
		}
		goToFirst();
		while (current != end && d.compareTo(current.data) > 0 && d.compareTo(current.next.data) >= 0) {
			current = current.next;
		}
		return (d.compareTo(current.data) == 0);
	}

	@Override
	public boolean goToFirst() {
		if (this.isEmpty()) {
			return false;
		}
		while (current.prior != null) {
			current = current.prior;
		}
		begin = current;
		return true;
	}

	@Override
	public boolean goToLast() {
		if (this.isEmpty()) {
			return false;
		}
		while (current.next != null) {
			current = current.next;
		}
		return true;
	}

	@Override
	public boolean goToNext() {
		if (current.next == null) {
			return false;
		}
		current = current.next;
		return true;

	}

	@Override
	public boolean goToPrevious() {
		if (current.prior == null) {
			return false;
		}
		current = current.prior;
		return true;

	}

	@Override
	public ListInterface<E> copy() {
		ListInterface<E> newList = new LinkedList<E>();
		newList.init();
		if(this.size() > 0) {
			this.goToFirst();

			do {
				newList.insert(this.retrieve());

			} while(this.goToNext());
		}
		return newList;
	}
}