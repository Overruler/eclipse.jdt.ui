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
package p2;

import p1.A;

public class B {
	public String bar= "bar";

	/**
	 * m
	 * @param a TODO
	 * @return Object
	 * @throws Exception
	 */
	public Object m(A a) throws Exception {
		System.out.println(a.foo);
		System.out.println(a.foo);
		System.out.println(bar);
		return null;
	}
}