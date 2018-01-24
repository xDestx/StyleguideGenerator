package com.xdest.css;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;

import com.steadystate.css.parser.CSSOMParser;
import com.xdest.css.guide.ColorOption;
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
		html += "\t\t<style>* {font-family:\"Arial\";color:#42bcf4;margin:2px;}h1 {margin:20px;}</style>\n";
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
		return new GuideOption[] {new ColorOption()};
	}

	/**
	 * Create a stylesheet instance from the given file
	 * 
	 * @param f
	 *            The file with the styles
	 * @return A stylesheet instance
	 */
	public static Stylesheet createStylesheet(File f) {
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
		CSSRuleList crules = css.getCssRules();
		
		for(int i = 0; i < crules.getLength(); i++) {
			CSSRule rule = crules.item(i);
			
			String identifierText = rule.getCssText().substring(0, rule.getCssText().indexOf('{'));
			String[] possibleMultiple = identifierText.split(",");
			for(int k = 0; k < possibleMultiple.length; k++) {
				possibleMultiple[k]+= rule.getCssText().substring(rule.getCssText().indexOf('{'));
				possibleMultiple[k] = cleanString(possibleMultiple[k]);
			}
			for(String s : possibleMultiple) {
				newSheet.addStyle(parseStyle(s));
			}
			
		}
		
		return newSheet;
	}
	
	
	/**
	 * Remove all double spaces from a string, trim the string
	 * @return A nice string
	 */
	public static String cleanString(String s) {
		s = s.trim();
		String newStr = "";
		boolean lastWasSpace = false;
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == ' ' && lastWasSpace) {
				//Ignore
			} else if (c == ' ' && !lastWasSpace) {
				newStr+=c;
				lastWasSpace = true;
			} else {
				newStr+=c;
				lastWasSpace = false;
			}
		}
		s = newStr;
		return s;
	}
	
	
	protected static Style parseStyle(String style) {
		Style newStyle = new CSSStyle();
		//Set specific target
		newStyle.setSpecificTarget(style.substring(0, style.indexOf('{')));
		//Find classes
		//Find ids
		//Find elements
		String titleText = style.substring(0, style.indexOf('{'));
		//Classes
		while(titleText.indexOf('.') != -1) {
			int startIndex = titleText.indexOf('.');
			char currentChar = '.';
			String className = "";
			while(currentChar != ' ' && startIndex+1 < titleText.length()) {
				className+=currentChar;
				startIndex++;
				currentChar = titleText.charAt(startIndex);
			}
			newStyle.addClass(className.substring(1));
			titleText = titleText.replace(className, "");
		}
		//Ids
		int startIndex = titleText.indexOf('#');
		if(startIndex != -1) {
			char currentChar = '#';
			String idName = "";
			while(currentChar != ' ') {
				idName+=currentChar;
				startIndex++;
				currentChar = titleText.charAt(startIndex);
			}
			newStyle.setId(idName.substring(1));
			titleText.replace(idName, "");
		}
		//Elements
		titleText = cleanString(titleText);
		String[] elements = titleText.split(" ");
		for(String s : elements) {
			newStyle.addElement(s);
		}
		
		String styleBody = style.substring(style.indexOf('{')+1).replace("}", " ");
		styleBody = cleanString(styleBody);
		String[] attributes = styleBody.split(";");
		for(String attr : attributes) {
			String[] keyValPair = attr.split(":");
			newStyle.setAttribute(keyValPair[0].trim(), keyValPair[1].trim());
		}
		return newStyle;
	}

	/**
	 * Get the Style file to load
	 * 
	 * @return The File
	 */
	public static File getFileToLoad() {
		return new File(System.getProperty("user.home") + File.separatorChar + "Documents" + File.separatorChar + "main.css");
		//return null;
	}

}
