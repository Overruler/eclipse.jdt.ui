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
package object_out;

public class TestSimpleWrite {
	private String field;
	
	public void foo() {
		setField("field");
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}
}

