package application;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
	private List<Memento> mementoList = new ArrayList<Memento>();
	private int index;

	public CareTaker() {
		super();
		index = mementoList.size();
	}

	public void add(Memento state) {
		if (state != null) {
			if (mementoList.size() > 0 && index != mementoList.size() - 1) {
				mementoList = mementoList.subList(0, index + 1);
			}
			mementoList.add(state);
			index = mementoList.size() - 1;
		}
	}

	public Memento getPrev() {
		if (mementoList.isEmpty() || index <= 0) {
			return null;
		}
		return mementoList.get(--index);
	}

	public Memento getNext() {
		if (mementoList.isEmpty() || index >= mementoList.size() - 1) {
			return null;
		}
		return mementoList.get(++index);
	}

	public int getIndex() {
		return index;
	}
	
	public void emptyList(){
		mementoList.clear();
	}
	}

