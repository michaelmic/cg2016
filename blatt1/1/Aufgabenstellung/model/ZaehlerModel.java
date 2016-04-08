package model;

import java.util.Observable;

/**
 * 
 * @author Nicolas Neubauer
 *
 * Model, das einen Integer-Wert verwalten kann
 *
 */
public class ZaehlerModel extends Observable {

	private int wert;
	private int min;
	private int max;
	
	// falls true: fehler ist aufgetreten, andernfalls false
	private boolean errInterval;
	private boolean errFormat;
	
	/**
	 * Legt ein neues Model an.
	 * 
	 * @param wert der initiale Zahlwert
	 * @param min das minimal erlaubte Zahlwert
	 * @param max der maximal erlaubte Zahlwert
	 */
	public ZaehlerModel(int wert, int min, int max){
		
		this.min = min;
		this.max = max;
		
		this.errInterval = false;
		this.errFormat = false;
			
		setWert(wert);
		
	}

		
	/**
	 * @return the wert
	 */
	public int getWert() {
		return wert;
	}

	/**
	 * Setzen des Zahlwerts
	 * @param wert der zu setzende Wert
	 */
	
	public void setWert(int wert) throws IllegalArgumentException {
		
		if(wert >= this.min && wert <= this.max) {
			this.wert = wert;
			setChanged();
			notifyObservers();
		} 
		else
		{
			errInterval = true;
			setChanged();
			notifyObservers();
			throw new IllegalArgumentException();
		}
	}
	
	public void setWert(String wert) throws IllegalArgumentException {
		
		try {
			int parsed = Integer.parseInt(wert);
			setWert(parsed);
		} catch (NumberFormatException ex) {
			setChanged();
			notifyObservers();
			errFormat = true;
			throw new IllegalArgumentException();
		}
	}

	/**
	 * @return the min
	 */
	public int getMin() {
		return min;
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}
	public boolean getInvalidAction() {
		if(this.errInterval) {
			this.errInterval = false;
			return true;
		} 
		else 
		{
			return false;
		}
	}
	public boolean getInvalidFormat() {
		if(this.errFormat) {
			this.errFormat = false;
			return true;
		} 
		else 
		{
			return false;
		}
	}
}
