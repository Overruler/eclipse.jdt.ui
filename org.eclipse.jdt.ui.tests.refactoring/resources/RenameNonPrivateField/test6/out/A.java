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
	protected int g;
	void m(){
		g++;
	}
}

class AA extends A{
	protected int f;
}

class B{
	A a;
	AA b;
	A ab= new AA();
	void m(){
		a.g= 0;
		b.f= 0;
		ab.g= 0;
	}
}