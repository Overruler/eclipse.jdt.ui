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
package p1;

import p2.B;

public class A {
	
	public String foo= "foo";

	/**
	 * m
	 * @param b
	 * @return Object
	 * @throws Exception
	 */
	public Object m(B b) throws Exception {
		System.out.println(foo);
		System.out.println(this.foo);
		System.out.println(b.bar);
		return null;
	}
}