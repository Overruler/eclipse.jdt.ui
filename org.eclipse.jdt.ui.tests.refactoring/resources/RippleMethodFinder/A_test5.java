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
interface I1 {
	void /*target*/m(int i);
}
interface I2 {
	void /*ripple*/m(int integer);
}
class I implements I1, I2 {
	void /*ripple*/m(int in);
}