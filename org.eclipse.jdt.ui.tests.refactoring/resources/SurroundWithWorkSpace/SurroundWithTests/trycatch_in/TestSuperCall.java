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
package trycatch_in;

import java.io.IOException;

public class TestSuperCall {
	public void foo() throws IOException {
	}
	
	static class A extends TestSuperCall {
		public void bar() {
			/*[*/super.foo();/*]*/
		}
	}
}

