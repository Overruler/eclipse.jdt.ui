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
package validSelection;

public class A_test190_ {
	public int foo() {
		int i= 10;
		/*]*/switch(i) {
			case 1:
				foo();
				break;
			case 2:
				foo();
			default:
				return 10;
		}/*[*/
		
		return 20;
	}
}