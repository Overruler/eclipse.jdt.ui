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
package object_in;

public class TestSetterInAssignment {
	String field;
	
	public void foo() {
		TestSetterInAssignment a= null;
		String t= null;
		t= a.field= "d";
	}
}
