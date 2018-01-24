package com.xdest.css.guide;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.xdest.css.Style;
import com.xdest.css.Stylesheet;

public class TypographyOption extends GuideOption {

	private Set<CFont> fonts;
	private String[] acceptedAttributes;
	
	public TypographyOption() {
		acceptedAttributes = new String[] {"font","font-family","font-size","color","font-weight","font-style","font-variant","text-shadow","text-decoration"};
		fonts = new HashSet<CFont>();
	}
	
	@Override
	public boolean acceptStyle(Style style) {
		boolean matchesAtLeastOne = false;
		outer:for(String s : style.getAttributeMap().keySet()) {
			for(String attr : acceptedAttributes) {
				if(s.equalsIgnoreCase(attr)) {
					matchesAtLeastOne = true;
					break outer;
				}
			}
		}
		if(!matchesAtLeastOne) return false;
		//Create font
		//Add font
		CFont newFont = new CFont(style);
		for(String s : acceptedAttributes) {
			String val = style.getAttributeValue(s);
			if(val != null) {
				newFont.setAttribute(s, val);
			}
		}
		for(CFont f : fonts) {
			if(newFont.equals(f)) return false; 
		}
		return fonts.add(newFont);
	}

	@Override
	public String generateHTML() {
		String html= "";
		html+="<h1>Typography</h1><h4>Using body color as default. Backgrounds are added to fonts with color too close to white</h4><hr/>\n";
		for(CFont f : fonts) {
			html+="<div style=\"padding:8px;border-top:1px solid black;";
			boolean containsColor = false;
			for(String s : f.getFontMap().keySet()) {
				html+=s + ":" + f.getValue(s).replace("\"", "'") + ";";
				if(s.equalsIgnoreCase("color")) {
					containsColor = true;
				}
			}
			
			if(!containsColor) {
				//Find it
				//Check parent elements
				//If no elements, get body color if it exists, else ignore
				Style parentStyle = f.getParent();
				Stylesheet parentSheet = parentStyle.getParentSheets()[0];
				if(parentSheet == null) continue;
				Style matchingColorStyle = null;
				for(Style s : parentSheet.getStyles()) {
					for(String e : parentStyle.getElements()) {
						if(s.getElements().contains(e) && s.hasAttribute("color")) {
							matchingColorStyle = s;
							break;
						}
					}
				}
				
				if(matchingColorStyle == null) {
					for(Style s : parentSheet.getStyles()) {
						if(s.getElements().contains("body")) {
							matchingColorStyle = s;
							break;
						}
					}
				}
				if(matchingColorStyle != null) {
					html+="color:" + matchingColorStyle.getAttributeValue("color")+";";
				} else {
					System.out.println("Couldn't find color for " + f.getParent());
				}
			} else {
				//Check if color is too close to white
				String colorVal = f.getValue("color");
				if(colorVal.equalsIgnoreCase("grey")) colorVal = "gray";
				Color color = ColorOption.parseColor(colorVal);
				if(color != null) {
					if(color.getRed() + color.getGreen() + color.getBlue() > 688) {
						//Add slight dark background
						html+="background-color:#abc;";
					} else if (color == Color.white) {
						System.out.println(color.getRed() + color.getGreen() + color.getBlue());
					}
				}
			}
			
			html+="\">"+f.getParent().getName()+"</div>\n";
		}
		return html;
	}
	
	
	class CFont {
		
		private Map<String,String> fontMap;
		private Style parent;
		
		public CFont(Style parent) {
			fontMap = new HashMap<String,String>();
			this.parent = parent;
		}
		
		public void setAttribute(String s, String s1) {
			this.fontMap.put(s, s1);
		}
		
		public String getValue(String key) {
			return this.fontMap.get(key);
		}
		
		public Map<String,String> getFontMap() {
			return this.fontMap;
		}
		
		public Style getParent() {
			return this.parent;
		}
		
		@Override
		public String toString() {
			String r = "CFont:\n";
			for(String s : fontMap.keySet()) {
				r+=s + ": " + fontMap.get(s) + ";\n";
			}
			
			return r;
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof CFont) {
				CFont otherFont = (CFont)o;
				
				//Check keys
				if(otherFont.getFontMap().keySet().size() != this.getFontMap().keySet().size()) return false;
				for(String s : this.getFontMap().keySet()) {
					if(!otherFont.getFontMap().containsKey(s)) {
						return false;
					}
				}
				
				//Check values
				for(String s : this.getFontMap().keySet()) {
					if(!this.getFontMap().get(s).equalsIgnoreCase(otherFont.getFontMap().get(s))) {
						return false;
					}
				}
				return true;
				
			} else {
				return false;
			}
		}
	}

}
