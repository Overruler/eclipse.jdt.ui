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
package cast_out;

class Base3 {
	public void foo(int i) {
	}
}

class Derived3 extends Base3 {
	private void foo(char c) {
	}
}

public class TestHierarchyOverloadedPrivate {
	public int goo() {
		return 'a';
	}
	public void main(Derived3 d) {
		d.foo((int) 'a');
	}
}
