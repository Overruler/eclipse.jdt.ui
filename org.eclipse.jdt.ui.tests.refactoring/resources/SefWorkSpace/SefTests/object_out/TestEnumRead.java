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

public enum TestEnumRead {
	TEST;
	private String field;

	public void foo() {
		String s = getField();
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}
}
