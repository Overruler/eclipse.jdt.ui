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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class A {
	public void foo() {
		String min= Collections.min(getNames());
	}
	public List<String> getNames() {
		ArrayList<String> result= new ArrayList<String>();
		result.add("Zwyssig");
		result.add("Abaecherli");
		return result;
	}
}