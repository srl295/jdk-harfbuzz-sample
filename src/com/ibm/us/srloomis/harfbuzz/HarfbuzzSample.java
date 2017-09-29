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

import java.awt.Container;
import java.awt.GridLayout;
import java.util.Locale;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import com.ibm.icu.util.ULocale;

public class HarfbuzzSample extends JFrame {
	private static final String TITLE = HarfbuzzSample.class.getSimpleName()+"@"+System.getProperty("java.version");
	/**
	 * 
	 */
	static final ListModel<String> STRINGS = new AbstractListModel<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3067781741130774429L;
		// use JDK Locale - lots of locales available, especially on 9
//		Locale l[] = Locale.getAvailableLocales();
		// use ICU Locale - lots of locales available, anywhere
		ULocale l[] = ULocale.getAvailableLocales();
		@Override
		public int getSize() {
			// TODO Auto-generated method stub
			return l.length;
		}

		@Override
		public String getElementAt(int index) {
			//	Locale locale = l[index];
			ULocale locale = l[index];
			return locale.toLanguageTag()+"—"+locale.getDisplayName()+"—"+locale.getDisplayName(locale);
		}
	};
	
	
	JList<String> list = new JList<String>(STRINGS);
	JScrollPane scroller = new JScrollPane(list);
	
	private static final long serialVersionUID = 3996253726718591138L;
	HarfbuzzSample() {
		super(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Container c = getContentPane();
		c.setLayout(new GridLayout(3, 3));
		c.add(new JLabel(getTitle()));
		
		c.add(scroller);
		
		pack();
		setVisible(true);
	}
	public static void main(String args[]) {
		new HarfbuzzSample();
	}
}
