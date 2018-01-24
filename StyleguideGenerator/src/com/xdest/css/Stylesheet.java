package com.xdest.css;

import java.util.Collection;

/**
 * A stylesheet interface to represent a group of {@link Style Styles}.
 * 
 * @author xDest
 *
 */
public interface Stylesheet {
	/**
	 * Get all of the styles in this stylesheet
	 * @return A collection of styles
	 */
	Collection<Style> getStyles();
	
	/**
	 * Add a style to this stylesheet
	 * @param s The style to add
	 */
	void addStyle(Style s);
	
	/**
	 * Remvoe a style from this stylesheet
	 * @param s The style to remove
	 * @return The style removed
	 */
	Style removeStyle(Style s);
	
}
