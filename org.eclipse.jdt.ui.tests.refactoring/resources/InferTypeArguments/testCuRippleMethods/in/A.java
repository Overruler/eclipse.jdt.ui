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

import java.util.Enumeration;
import java.util.Vector;

public class A {
	private void createTestList(TestCollector collector) {
		Enumeration each= collector.collectTests();
		while (each.hasMoreElements()) {
			String s= (String) each.nextElement();
		}
	}
}

interface TestCollector {
	public Enumeration collectTests();
}

class Collector implements TestCollector {
	public Enumeration collectTests() {
		Vector v= new Vector();
		v.add("Test1");
		return v.elements();
	}
	
}