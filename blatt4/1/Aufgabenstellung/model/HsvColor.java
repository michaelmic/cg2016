package model;

import java.awt.Color;

public class HsvColor {
	public int h;
	public double s, v;
	
	public HsvColor(int h, double s, double v) {
		this.h = h;
		this.s = s;
		this.v = v;
	}
	
	public static HsvColor fromColor(Color rgb) {
		// TODO: (A1) RGB zu HSV konvertieren

		double red = (double) rgb.getRed() / (double) 255;
		double green = (double) rgb.getGreen() / (double) 255;
		double blue = (double) rgb.getBlue() / (double) 255;
		int hue;
		
		double value = Math.max(red, Math.max(green, blue));
		
		double min = Math.min(red, Math.min(green, blue));
		double max = Math.max(red, Math.max(green, blue));
		double sat = (value-min) / value;
               
        if(min == max) {
        	hue = 0;
        } else if(max == red) { // rot
            hue = (int)( 60 * ((green - blue)/(max-min)) );
            
        } else if(max == blue) { // blau
            hue = (int)( 60 * (4 + (red - green)/(max-min)) );
            
        } else if(max == green) { // gruen
            hue = (int)( 60 * (2 +(blue - red)/(max/min)) );
            
        } else {
            System.out.println(
                "Fehler bei der Bestimmung der Dominanten!");
            return new HsvColor(0,0,0);
        }
        while(hue < 0) hue += 360;
        
        System.out.println("Hue: " + hue + ", Sat: " + sat + ", value: " + value + ".");
	
		return new HsvColor(hue, sat, value); // dummy object
	}
	
	public Color toColor() {
		// TODO: (A1) HSV zu RGB konvertieren
		double red, green, blue;
		
		int hue_int = (int) (this.h / 60);
		double f = this.h / 60 - hue_int;
		
		double p = this.v * (1 - this.s);
		double q = this.v * (1 - this.s * f);
		double t = this.v * (1 - this.s * (1 - f));
		
		if(hue_int == 1) {
			red = q;
			green = this.v;
			blue = p;
		} else if(hue_int == 2) {
			red = p;
			green = this.v;
			blue = t;
		} else if(hue_int == 3) {
			red = p;
			green = q;
			blue = this.v;
		} else if(hue_int == 4) {
			red = t;
			green = p;
			blue = this.v;
		} else if(hue_int == 5) {
			red = this.v;
			green = p;
			blue = q;
		} else {
			red = this.v;
			green = t;
			blue = p;
		}
		
		return new Color((int) (red * 255), (int) (green * 255), (int) (blue * 255));	
	}

}
