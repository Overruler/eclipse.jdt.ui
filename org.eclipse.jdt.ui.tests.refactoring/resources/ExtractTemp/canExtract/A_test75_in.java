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

class A {
	void f(){
		for (int i= 0; i < 5; i++) {
			for (int k= 0, p= 17; p < i; k++) {
				System.out.println(i++ + " " + k);
			}
		}
	}
}
