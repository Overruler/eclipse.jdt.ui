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

public class A_test568 {
	public void foo() {
		String[] args[]= null;
		extracted(args);
	}

	protected void extracted(String[][] args) {
		/*[*/for (int i = 0; i < args.length; i++) {
			args[i]= null;
		}/*]*/
	}
}

