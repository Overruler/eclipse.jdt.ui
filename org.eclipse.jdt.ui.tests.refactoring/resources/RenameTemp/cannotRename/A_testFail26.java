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
//renaming to: j
package p;
class A{
	int j;
	int m(final int k){
		final int /*[*/i/*]*/= 0;
		new A(){
			int m(int o){
				return i;
			}
		};
		return i + m(m(i));
	};
}