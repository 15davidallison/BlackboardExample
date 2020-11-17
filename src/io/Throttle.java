package io;

/**
 * @author David Allison
 * Simulates a throttle body capable of maxValue cubic centimeters / minute
 * of air flow at wide open throttle.
 */
public class Throttle extends OutputComponent {
	public final int maxValue = 7500;
	
	public Throttle(int val) {
		currentVal = val;
	}

	@Override
	public void more(int a) {
		if (currentVal + a <= maxValue) {
			currentVal += a;
		} else {
			currentVal = maxValue;
		}
	}

	@Override
	public void less(int a) {
		if (currentVal - a >= 0) {
			currentVal -= a;
		} else {
			currentVal = 0;
		}
	}
	
}
