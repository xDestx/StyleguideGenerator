package com.xdest.css.guide;

import java.util.ArrayList;
import java.util.Collection;

import com.xdest.css.Style;

/**
 * An abstract class representing a guide option. A guide option is an option
 * for what should be placed into a style guide.
 * 
 * @author xDest
 *
 */
public abstract class GuideOption {

	protected Collection<Style> collectedStyles;

	/**
	 * Initialize
	 */
	public GuideOption() {
		collectedStyles = new ArrayList<Style>();
	}

	/**
	 * Decide whether to accept a style
	 * 
	 * @param s
	 *            The style in question
	 * @return Whether it should be accepted
	 */
	public abstract boolean acceptStyle(Style s);

	/**
	 * Attempt to add a style to this option. The style is checked with
	 * {@link #acceptStyle(Style)}
	 * 
	 * @param s
	 *            The style to add
	 */
	public void addStyle(Style s) {
		if (acceptStyle(s))
			collectedStyles.add(s);
	}

	/**
	 * Generate the html for this option
	 * 
	 * @return HTML inside a single div
	 */
	public abstract String generateHTML();

}
