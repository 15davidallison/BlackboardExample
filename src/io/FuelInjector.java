package io;

/**
 * @author David Allison
 * Simulates a fuel injector capable of maxValue cubic centimeters / minute
 * of fuel flow at full spray.
 */
public class FuelInjector extends OutputComponent {
	public final int maxValue = 500;
	
	public FuelInjector(int val) {
		currentVal = val;
	}
	
	@Override
	public void more(int a) {
		if (currentVal + a < maxValue) {
			currentVal += a;
		} else {
			currentVal = maxValue;
		}
	}

	@Override
	public void less(int a) {
		if (currentVal - a > 1) {
			currentVal -= a;
		} else {
			currentVal = 1;
		}
	}


}
