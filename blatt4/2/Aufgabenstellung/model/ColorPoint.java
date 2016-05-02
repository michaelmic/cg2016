package model;

import java.awt.Color;

public class ColorPoint implements Comparable<ColorPoint> {
	public static final int RED = 0;
	public static final int GREEN = 1;
	public static final int BLUE = 2; 
	
	private int red;
	private int green;
	private int blue;
	private int quantity;
	private int orderBy;
	
	public ColorPoint(Color c) {
		red = c.getRed();
		green = c.getGreen();
		blue = c.getBlue();
		quantity = 1;
		orderBy = RED;
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void orderBy(int val) {
		if(val >= RED && val <= BLUE) {
			orderBy = val;
		}
	}
	
	public void add() {
		quantity++;
	}
	
	@Override
	public int compareTo(ColorPoint o) {
		switch(orderBy) {
		case RED: 
			return red - o.getRed();
		case GREEN: 
			return green - o.getGreen();
		case BLUE: 
			return blue - o.getBlue();
		default: 
			return 0;
		}
	}
	
	public boolean same(Color other) {
		return this.getRed() == other.getRed() 
				&& this.getGreen() == other.getGreen() 
				&& this.getBlue() == other.getBlue();
	}
}
