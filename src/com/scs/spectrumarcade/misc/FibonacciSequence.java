package com.scs.spectrumarcade.misc;

import ssmith.lang.NumberFunctions;

public class FibonacciSequence {
	
	private int lastNumPrev = 2;
	private int lastNum = 2;

	public FibonacciSequence(int id) {
		if (id  < 1) {
			id = 2;
		}
		lastNumPrev = NumberFunctions.fibonacciIterative(id-1);
		lastNum = NumberFunctions.fibonacciIterative(id);
	}
	
	
	public int getNext() {
		int newVal = lastNum + lastNumPrev;
		
		lastNumPrev = lastNum;
		lastNum = newVal;
		
		return newVal;
	}

}
