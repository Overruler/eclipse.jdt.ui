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
//6, 16, 6, 32
package p;

class A<E> {
	private static final A<Integer> INT= new A<Integer>();

	static A<Integer> getInt() {
		return A.INT;
	}
}