package io;

public abstract class OutputComponent {
	protected int currentVal;
	public abstract void more(int a);
	public abstract void less(int a);
	public int currentVal() {
		return currentVal;
	}
}
