/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package p;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class A {
	void foo() {
		List<String> l= new ArrayList<String>();
		l.add("Eclipse"); l.add("is"); l.add(new String("cool"));
		for (Iterator<String> iter= l.iterator(); iter.hasNext();) {
			String word= iter.next();
			System.out.print(word);
			System.out.print(" ");
		}
	}
}