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
//5, 18 -> 5, 18  replaceAll == true, removeDeclaration == true
package p;

@interface Annot {
	String value();
}
@Annot("Paul")
class Test {
	@Annot(value="Paul")
	String m(Annot a) {
		return "Paul";
	}
}
