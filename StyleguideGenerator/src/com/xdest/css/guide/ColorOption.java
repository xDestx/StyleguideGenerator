package com.xdest.css.guide;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xdest.css.Style;

/**
 * A guide option which attempts to filter out unique colors from styles
 * @author xDest
 *
 */
public class ColorOption extends GuideOption {

	private Set<Color> colors;
	private final String[] acceptedAttributes;
	
	public ColorOption() {
		colors = new HashSet<Color>();
		acceptedAttributes = new String[] {"color","background-color","background","border-color"};
	}
	
	@Override
	public boolean acceptStyle(Style style) {
		boolean accept = false;
		//Check for any matching attributes
		outer:for(String s : style.getAttributeMap().values()) {
			for(String acceptedAttribute : acceptedAttributes) {
				if(s.equalsIgnoreCase(acceptedAttribute)) {
					accept = true;
					break outer;
				}
			}
		}
		if(!accept) return false;
		accept = false;
		//check for unique colors
		List<Color> newColors = new ArrayList<Color>();
		for(String a : acceptedAttributes) {
			if(style.getAttributeValue(a) != null) {
				String value = style.getAttributeValue(a).trim();
				Color newColor = parseColor(value);
				newColors.add(newColor);
			}
		}
		for(Color c : newColors) {
			if(colors.add(c)) {
				accept = true;
			}
		}
		return accept;
	}
	
	
	/**
	 * Get the hex representation of this color. Does not include '#'
	 * @param c The color
	 * @return A hex string. For example, white: 'ffffff`
	 */
	public static String getColorHex(Color c) {
		return ColorOption.intToHexVal(c.getRed()) + ColorOption.intToHexVal(c.getGreen()) + ColorOption.intToHexVal(c.getBlue());
	}
	
	public static String intToHexVal(int i) {
		String[] hexValues = new String[] {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
		int hexIndex1 = i/16;
		int hexIndex0 = i%16;
		return hexValues[hexIndex1] + hexValues[hexIndex0];
	}
	
	/**
	 * Parse the String for a color. Checks for rgb(...) rgba(...) and #rrggbb. Ignores 'a' value
	 * @param s The color string
	 * @return A color if it can be determined, or null
	 */
	protected Color parseColor(String s) {
		if(s.contains("#")) {
			s = s.replace("#", "");
			s = s.trim();
			int length = s.length();
			//Should be 3 or 6
			if(length != 3 && length != 6) {
				System.out.println("Invalid color lenght");
				return null;
			}
			if(length == 3) {
				String newRgb = "";
				for(int i = 0; i < 3; i++) {
					char c = s.charAt(0);
					newRgb+=""+c+c;
				}
				s = newRgb;
			}
			int[] rgb = new int[3];
			for(int i = 0; i < 6; i+=2) {
				String hexVal = s.substring(i,i+2);
				int v = ColorOption.hexStringToInt(hexVal);
				rgb[i/2] = v;
			}
			Color newColor = new Color(rgb[0],rgb[1],rgb[2]);
			return newColor;
		} else if (s.contains("rgba")) {
			s = s.replace("(", "").replace(")","").replace(" ", "").replace("rgba","");
			String[] vals = s.split(",");
			//Ignore a
			int[] rgb = new int[3];
			for(int i = 0; i < 3; i++) {
				int x = Integer.parseInt(vals[i]);
				rgb[i] = x;
			}
			Color newColor = new Color(rgb[0],rgb[1],rgb[2]);
			return newColor;
		} else if (s.contains("rgb")) {
			s = s.replace("(", "").replace(")","").replace(" ", "").replace("rgb","");
			String[] vals = s.split(",");
			//Ignore a
			int[] rgb = new int[3];
			for(int i = 0; i < 3; i++) {
				int x = Integer.parseInt(vals[i]);
				rgb[i] = x;
			}
			Color newColor = new Color(rgb[0],rgb[1],rgb[2]);
			return newColor;
		} else {
			//Whoops, unsupported 
			return null;
		}
	}
	
	/**
	 * Convert a hex String to an int val
	 * @param hexVal Two character hex string
	 * @return An integer representation
	 */
	public static int hexStringToInt(String hexVal) {
		int pos1 = Integer.parseInt(""+hexVal.charAt(0));
		int pos0 = Integer.parseInt(""+hexVal.charAt(1));
		return (pos1*16) + (pos0);
	}

	@Override
	public String generateHTML() {
		String html= "";
		html+="<h1>Colors</h1><hr/>\n";
		for(Color c : colors) {
			html+="<div style=\"background-color:#"+ColorOption.getColorHex(c)+";display:block;color:black;\">#" + ColorOption.getColorHex(c) + "</div>\n";
		}
		return html;
	}

}
