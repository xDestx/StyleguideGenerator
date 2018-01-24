package com.xdest.css;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSStyleSheet;

import com.steadystate.css.parser.CSSOMParser;
import com.xdest.css.guide.GuideOption;

/**
 * The main class in the Styleguide Generator project.
 * 
 * @author xDest
 *
 */
public class StyleguideGenerator {

	public static void main(String[] args) {
		File styleFile = getFileToLoad();
		Stylesheet stylesheet = createStylesheet(styleFile);
		GuideOption[] options = getOptions();
		String html = generateStyleguide(options, stylesheet);
		displayHTML(html);
	}

	/**
	 * Display the HTML to the user (May be plaintext html)
	 * 
	 * @param html
	 *            The html to display
	 */
	public static void displayHTML(String html) {
		System.out.println(html);
	}

	/**
	 * Generate a styleguide for the specified stylesheet using given guide options.
	 * 
	 * @param options
	 *            The options used for this guide
	 * @param styles
	 *            The styles for this guide
	 * @return A String of html text
	 */
	public static String generateStyleguide(GuideOption[] options, Stylesheet styles) {
		String html = "<!DOCTYPE html>\n<html>";
		html += "\t<head>\n";
		html += "\t\t<title>Generated Styleguide</title>\n";
		html += "\t\t<style>* {font-family:\"Arial\";}</style>\n";
		html += "\t</head>\n\t<body>\n";
		// Populate options with their specific styles
		for (GuideOption option : options) {
			for (Style s : styles.getStyles()) {
				option.addStyle(s);
			}
		}
		// Add each option
		for (GuideOption option : options) {
			html += option.generateHTML();
		}

		html += "\t</body>\n";
		html += "</html>\n";
		return html;
	}

	/**
	 * Get the GuideOptions for this generation
	 * 
	 * @return An Array of {@link com.xdest.css.guide.GuideOption GuideOptions}
	 */
	public static GuideOption[] getOptions() {
		// TODO this
		return null;
	}

	/**
	 * Create a stylesheet instance from the given file
	 * 
	 * @param f
	 *            The file with the styles
	 * @return A stylesheet instance
	 */
	public static Stylesheet createStylesheet(File f) {
		// TODO this
		CSSOMParser cp = new CSSOMParser();
		InputSource is;
		try {
			is = new InputSource(new InputStreamReader(new FileInputStream(f)));
			CSSStyleSheet css = cp.parseStyleSheet(is, null, "");
			return parseSheet(css);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected static Stylesheet parseSheet(CSSStyleSheet css) {
		Stylesheet newSheet = new CascadingStyleSheet();
		
		return newSheet;
	}

	/**
	 * Get the Style file to load
	 * 
	 * @return The File
	 */
	public static File getFileToLoad() {
		return null;
	}

}
