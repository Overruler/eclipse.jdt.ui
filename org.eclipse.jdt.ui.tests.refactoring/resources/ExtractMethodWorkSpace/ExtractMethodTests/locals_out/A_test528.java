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
package locals_out;

import java.io.IOException;

public class A_test528 {
	public void foo() throws IOException {
	}

	static class B extends A_test528 {
		public void bar() throws IOException {
			extracted();
		}

		protected void extracted() throws IOException {
			/*[*/super.foo();/*]*/
		}
	}
}