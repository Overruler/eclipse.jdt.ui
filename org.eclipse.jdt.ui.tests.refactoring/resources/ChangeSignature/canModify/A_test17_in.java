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

class A1 implements A{
	public void m(int i, boolean b){
	}
	private void foo(){
		m(2, true);
	}
}
class B extends A1{
	public void m(int j, boolean b){
		m(6, false);
		super.m(4, true);
	}
}
interface A {
	public void m(int i, boolean b);
}