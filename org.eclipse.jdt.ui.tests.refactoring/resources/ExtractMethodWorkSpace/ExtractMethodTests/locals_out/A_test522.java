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

public class A_test522 {
	public volatile boolean flag;
	
	public void foo() {
		int i= 20;
		i = extracted(i);
		i--;
	}

	protected int extracted(int i) {
		/*[*/target: {
			if (flag)
				break target;
			i= 10;
		}/*]*/
		return i;
	}	
}

