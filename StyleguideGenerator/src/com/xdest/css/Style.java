package com.xdest.css;

import java.util.Collection;
import java.util.Map;

/**
 * An interface used to represent a style inside a stylesheet
 * 
 * @author xDest
 *
 */
public interface Style {

	/**
	 * Get the specific target for this style
	 * 
	 * @return The target as a String of classes, elements, and ids
	 */
	String getSpecificTarget();

	/**
	 * Get a collection of elements this style affects if they exist
	 * 
	 * @return A collection of elements
	 */
	Collection<String> getElements();

	/**
	 * Get the Id that this style affects
	 * 
	 * @return The id as a String if it exists
	 */
	String getId();

	/**
	 * Get the classes that this style affects if they exist
	 * 
	 * @return A collection of classes as a String
	 */
	Collection<String> getClasses();

	/**
	 * Get the name of this style
	 * 
	 * @return The style name
	 */
	String getName();

	/**
	 * Get the map of attributes for this style
	 * 
	 * @return The map of attributes
	 */
	Map<String, String> getAttributeMap();

	/**
	 * Shortcut for {@link #getAttributeMap()}.put(String,String)
	 * 
	 * @param key
	 *            The attribute name
	 * @param value
	 *            The attribute value
	 */
	void setAttribute(String key, String value);

	/**
	 * Get the value of an attribute
	 * 
	 * @param key
	 *            The attribute name
	 * @return The value for this attribute, if it exists
	 */
	String getAttributeValue(String key);

	/**
	 * Check whether this style has an attribute
	 * 
	 * @param key
	 *            The attribute name
	 * @return true, if it contains it
	 */
	boolean hasAttribute(String key);

}
