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

public class Annotation2_in {
	public void foo() {
		Cell2 c= Cell2.createCell2();
	}
}
@interface Buggy {
	String value();
}
class Cell2 {
	public static Cell2 createCell2() {
		return new Cell2();
	}

	private @Buggy("doesn't work") /*[*/Cell2/*]*/() { }
}
