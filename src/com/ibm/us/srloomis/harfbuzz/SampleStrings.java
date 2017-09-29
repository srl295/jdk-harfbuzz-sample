/*
 * Copyright (c) 2014, 2016, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.ibm.us.srloomis.harfbuzz;

import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeSet;

public class SampleStrings {
	final TreeSet<String> strings = new TreeSet<String>();

	/**
	 * Create some strings to test. Anything after the : is used as the string.
	 */
	SampleStrings() {

		// add locales
		// use JDK Locale - lots of locales available, especially on 9
		Locale locs[] = Locale.getAvailableLocales();
		// use ICU Locale - lots of locales available, anywhere
		// ULocale locs[] = ULocale.getAvailableLocales();
		for (final Locale l : locs) {
			// ULocale locale = l[index];
			strings.add(l.toLanguageTag() + "—" + l.getDisplayName() + ":" + l.getDisplayName(l));
		}
	}

	/**
	 * Return the thing as an array.
	 * 
	 * @return
	 */
	String[] getArray() {
		return new ArrayList<String>(strings).toArray(new String[0]);
	}

}
