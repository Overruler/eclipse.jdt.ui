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
import java.util.List;

class A {
	void foo() {
		List l= new ArrayList();
		l.add("Eclipse");
		boolean has= l.contains("is");
	}
	void fooObj() {
		List lObj= new ArrayList();
		lObj.add("Eclipse");
		boolean has= lObj.contains(new Object());
	}
	void fooInteger() {
		List lInteger= new ArrayList();
		lInteger.add("Eclipse");
		boolean has= lInteger.contains(new Integer(1));
	}
}
