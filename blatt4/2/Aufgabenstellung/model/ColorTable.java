package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ColorTable {
	HashMap<Color, Color> table;
	
	public ColorTable(BufferedImage img, int numColors) {
		// TODO: (A2) Bild analysieren und Ergebnis speichern
		System.out.println("With: " + numColors);

		List<RgbCube> cubes = new LinkedList<>();
		RgbCube first = new RgbCube();
		
		for(int x = 0; x < img.getWidth(); x++) {
			for(int y = 0; y < img.getHeight(); y++) {
				first.addColor(new Color(img.getRGB(x, y) & 0xffffff));
			}
		}
		cubes.add(first);
		
		int steps = 1;
		List<RgbCube> next;
		RgbCube left, right;
		while(2*steps <= numColors) {
			next = new LinkedList<>();
			
			for(RgbCube c : cubes) {
				left = new RgbCube(); 
				right = new RgbCube();
				
				c.cut(left, right);
				next.add(left); next.add(right);
			}
			
			cubes = next;
			steps *= 2;
		}
		
		int index = 0;
		next = new LinkedList<>();
		for (RgbCube c : cubes) {
			if(steps < numColors) {
				left = new RgbCube(); 
				right = new RgbCube();
				
				c.cut(left, right);
				next.add(left); next.add(right);
			} else {
				next.add(c);
			}
			
			steps++;
		}
		
		cubes = next;
		
		table = new HashMap<>();
		for(RgbCube c : cubes) {
			table.putAll(c.getMap());
		}
	}
	
	/**
	 * Gibt die Farbe der erstellten Farbtabelle zurück, mit der
	 * die originale Farbe dargestellt werden soll.
	 * @param orig Die originale Farbe
	 * @return
	 */
	public Color getReducedColor(Color orig) {
		// TODO: (A2) Auf die generierten Daten zugreifen, um eine
		// passende Farbe zurück zu geben.
		
		return table.get(orig);
	}

}
