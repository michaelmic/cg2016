package model;

import java.awt.Color;

public class CmyColor {
	public int c, m, y;
	
	public CmyColor(int c, int m, int y) {
		this.c = c;
		this.m = m;
		this.y = y;
	}
	
	public static CmyColor fromColor(Color rgb) {
		// TODO: (A1) RGB zu CMY konvertieren 
		
		int c, m, y;
		c = 255 - rgb.getRed();
		m = 255 - rgb.getGreen();
		y = 255 - rgb.getBlue();
		
		return new CmyColor(c, m, y); 
	}
	
	public Color toColor() {
		// TODO: (A1) CMY zu RGB konvertieren 
		
		int r, g, b;
		r = 255 - this.c;
		g = 255 - this.m;
		b = 255 - this.y;
		
		return new Color(r, g, b);
	}

}
