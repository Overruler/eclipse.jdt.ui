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
package p; //7, 32 - 7, 33

class A {
	void f(String bar) {
		try {
		} catch (Exception e) {
			Exception temp= e;
			Exception another= temp;
		}
	}
}