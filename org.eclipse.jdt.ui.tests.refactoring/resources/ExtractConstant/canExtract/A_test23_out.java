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
//14, 12, 14, 15
package p;

import static p.Color.RED;

enum Color {
	RED, BLUE(), YELLOW() {};
	public static final Color fColor= RED;
}

class ColorUser {
	private static final Color COLOR= RED;

	void use() {
		Color c= Color.fColor;
		c= COLOR;
		switch (c) {
			case RED : //extract constant "RED"
				break;
			default :
				break;
		}
	}
}