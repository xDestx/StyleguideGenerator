package com.xdest.css;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of a CSS Style sheet
 * @author xDest
 *
 */
public class CSSStyle implements Style {

	private Collection<String> elements, classes;
	private Map<String, String> attributes;
	private String id, target;
	private Stylesheet parent;

	public CSSStyle(Stylesheet parent) {
		this.parent = parent;
		elements = new ArrayList<String>();
		classes = new ArrayList<String>();
		attributes = new HashMap<String, String>();
		id = "";
		target = "";
	}

	@Override
	public String getSpecificTarget() {
		return target;
	}

	@Override
	public Collection<String> getElements() {
		return elements;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Collection<String> getClasses() {
		return classes;
	}

	@Override
	public String getName() {
		String text = "";
		text+="Target: " + this.target;
		return text;
	}

	@Override
	public Map<String, String> getAttributeMap() {
		return attributes;
	}

	@Override
	public void setAttribute(String key, String value) {
		this.attributes.put(key, value);
	}

	@Override
	public String getAttributeValue(String key) {
		return this.attributes.get(key);
	}

	@Override
	public boolean hasAttribute(String key) {
		return this.attributes.keySet().contains(key);
	}

	@Override
	public void addClass(String s) {
		this.classes.add(s);
	}

	@Override
	public void setId(String s) {
		this.id = s;
	}

	@Override
	public void addElement(String s) {
		this.elements.add(s);
	}

	@Override
	public void setSpecificTarget(String s) {
		this.target = s;
	}
	
	@Override
	public String toString() {
		String str = "";
		
		str+=this.target + " { \n";
		for(String s : getAttributeMap().keySet()) {
			str+=s+":" + attributes.get(s) + ";\n";
		}
		str+="}\n";
		
		return str;
	}

	@Override
	public Stylesheet[] getParentSheets() {
		return new Stylesheet[] {parent};
	}

}
