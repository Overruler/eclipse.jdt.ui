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

abstract class A {
	public @interface Annotation {
		String name() default "foo";
	}
	private int bar() {
		return foo();
	}
	@Annotation (
		name= "bar"
	)
	public abstract int foo();
}
class B extends A {

	public int foo() {
		return 2;
	}
}