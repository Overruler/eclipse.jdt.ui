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
	protected void m(boolean b, int i){
	}
	private void foo(){
		m(true, 2);
	}
}
class B extends A{
	protected void m(boolean b, int j){
		m(false, 6);
		super.m(true, 4);
	}
}