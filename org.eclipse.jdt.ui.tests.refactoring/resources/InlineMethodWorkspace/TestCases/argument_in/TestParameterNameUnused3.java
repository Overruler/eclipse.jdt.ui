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
package argument_in;

public class TestParameterNameUnused3 {
	public void main() {
		class T {
			int x;
		}
		/*]*/foo(10);/*[*/
	}
	
	public void foo(int x) {
		x= 20;
		bar(x);
	}
	
	public void bar(int z) {
	}
}
