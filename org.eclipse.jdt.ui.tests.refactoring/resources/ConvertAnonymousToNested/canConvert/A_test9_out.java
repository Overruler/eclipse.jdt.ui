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
//private, nonstatic, final
class A{
	private final class Inner extends A {
		private Inner(int i) {
			super(i);
		}
		void f(){
			y= 0;
		}
	}
	int y;
	A(int i){
	}
	void f(){
		new Inner(1);
	}
}