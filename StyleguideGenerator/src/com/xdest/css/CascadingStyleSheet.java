package com.xdest.css;

import java.util.ArrayList;
import java.util.Collection;

public class CascadingStyleSheet implements Stylesheet {

	private Collection<Style> styles;
	
	public CascadingStyleSheet() {
		styles = new ArrayList<Style>();
	}
	
	@Override
	public Collection<Style> getStyles() {
		return styles;
	}

	@Override
	public void addStyle(Style s) {
		styles.add(s);
	}

	@Override
	public Style removeStyle(Style s) {
		styles.remove(s);
		return s;
	}

}
