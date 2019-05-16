package org.jbox.dao;

import java.util.Collection;
import java.util.TreeSet;

/**
 * A class represents a word.
 * 
 * <p>
 * The data mapping details refer to "Word.hbm.xml".
 * 
 * @author YiBin.H
 * @version 1.0
 * @see WordHome
 */
public class Word {
	private long id;
	private String wordStr;
	private String index;
	private Collection<Integer> locations;
	private double tf;

	/**
	 * Constructs a new <code>Word</code>.
	 */
	public Word() {
		locations = new TreeSet<Integer>();
	}

	/**
	 * Return index of a word.
	 * 
	 * @return String representing index of <code>Word</code>.
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * Set index of <code>Word</code>.
	 * 
	 * @param index
	 *            String representing index of <code>Word</code>.
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * Return TF of <code>Word</code> in a page.
	 * 
	 * @return double value representing TF of a <code>Word</code> in a page.
	 */
	public double getTf() {
		return tf;
	}

	/**
	 * Set TF of <code>Word</code>.
	 * 
	 * @param tf
	 *            double value representing TF of <code>Word</code>.
	 */
	public void setTf(double tf) {
		this.tf = tf;
	}

	/**
	 * Return String of <code>Word</code>. This method is the same with
	 * {@link #toString()}.
	 * 
	 * @return a string representing <code>Word</code>.
	 */
	public String getWordStr() {
		return wordStr;
	}

	/**
	 * Set String of <code>Word</code>.
	 * 
	 * @param wordStr
	 *            string representing a <code>Word</code>.
	 */
	public void setWordStr(String wordStr) {
		this.wordStr = wordStr;
	}

	/**
	 * Return locations of <code>Word</code> in a Page.
	 * 
	 * @return Integer collection representing locations of the word in a Page
	 */
	public Collection<Integer> getLocations() {
		return locations;
	}

	/**
	 * Add a location to <code>Word</code>.
	 * 
	 * @param loc
	 *            integer value representing locations of the word in a Page.
	 */
	public void addLocation(int loc) {
		locations.add(loc);
	}

	/**
	 * Return a string representing <code>Word</code>.
	 * 
	 * @return String representing <code>Word</code>.
	 */
	public String toString() {
		return this.wordStr;
	}

	/**
	 * Return id of <code>Word</code> in data base.
	 * 
	 * @return long value representing id of <code>Word</code> in data base.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set id of <code>Word</code>.
	 * 
	 * @param id
	 *            long value representing id of <code>Word</code>.
	 */
	public void setId(long id) {
		this.id = id;
	}
}
