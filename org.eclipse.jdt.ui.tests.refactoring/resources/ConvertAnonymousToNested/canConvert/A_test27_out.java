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
class A {
	private final class Inner implements I {
		public void foo(){
		}
	}
	interface I{
		void foo();
	}
	static void foo1(){
		new A(){
			void foo(){
				I i = new I(){
					public void foo(){
						I i = new Inner();
					}
				};
			}
		};
	}
}
