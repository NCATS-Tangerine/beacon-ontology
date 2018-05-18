/*-------------------------------------------------------------------------------
 * The MIT License (MIT)
 *
 * Copyright (c) 2015-18 STAR Informatics / Delphinai Corporation (Canada) - Dr. Richard Bruskiewich
 * Copyright (c) 2017    NIH National Center for Advancing Translational Sciences (NCATS)
 * Copyright (c) 2015-16 Scripps Institute (USA) - Dr. Benjamin Good
 *                       
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *-------------------------------------------------------------------------------
 */
package bio.knowledge.ontology.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class Utils {
	
	/**
	 * 
	 * @param phrase
	 * @return
	 */
	public static String toUpperCamelCase(String phrase) {
		if (phrase == null) {
			return null;
		}
		
		String upperCamelCase = "";

		for (String term : phrase.split(" ")) {
			upperCamelCase += substring(term, 0, 1).toUpperCase() + substring(term, 1, term.length()).toLowerCase();
		}
		
		return upperCamelCase;
	}
	
	/**
	 * 
	 * @param phrase
	 * @return
	 */
	public static String toLowerCamelCase(String phrase) {
		if (phrase == null) {
			return null;
		}
		
		String s = toUpperCamelCase(phrase);
		return substring(s, 0, 1).toLowerCase() + substring(s, 1);
	}
	
	/**
	 * 
	 * @param s
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	public static String substring(String s, int beginIndex, int endIndex) {
		try {
			return s.substring(beginIndex, endIndex);
		} catch (IndexOutOfBoundsException e) {
			return "";
		}
	}
	
	/**
	 * 
	 * @param s
	 * @param beginIndex
	 * @return
	 */
	public static String substring(String s, int beginIndex) {
		try {
			return s.substring(beginIndex);
		} catch (IndexOutOfBoundsException e) {
			return "";
		}
	}
	
	/**
	 * 
	 * @param namespace
	 * @return
	 */
	public static boolean isUri(String namespace) {
		try {
			return new URI(namespace.toLowerCase()) != null;
		} catch (URISyntaxException e) {
			return false;
		}
	}
	
	
	/**
	 * Simpleminded test for a curie formatted identifier.
	 * 
	 * @param namespace
	 * @return
	 */
	public static boolean isCurie(String identifier) {
		if(!getPrefix(identifier).isEmpty()) 
			return true;
		else 
			return false;
	}

	/**
	 * 
	 * @param identifier
	 * @return
	 */
	public static String getPrefix(String identifier) {
		if(identifier==null||identifier.isEmpty()) return "";
		String[] parts = identifier.split(":");
		if(parts.length==2) 
			return parts[0]+":";
		else 
			return "";
	}
}
