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
class A{
}
class B extends A{
	public boolean m(int[] a) throws Exception {
		return true;
	}
}
class B1 extends B{
}
abstract class C extends A{
}
class D extends C{
}
class D1 extends C{
}
class E extends D{
}