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

public class BoundedTypeParam_in {
	public void foo() {
		NumberCell<Integer> c1= new NumberCell<Integer>(3);
		NumberCell<Float> c2= new NumberCell<Float>(3.14F);
	}
}
class NumberCell<T extends Number> {
	T fData;
	public /*[*/NumberCell/*]*/(T t) {
		fData= t;
	}
}
