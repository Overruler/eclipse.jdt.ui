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
//4, 59 -> 4, 59  replaceAll == true, removeDeclaration == true
package p;

enum Letter { A, B, C { }; public static final Letter MAIN= A; }

class EnumRef {
    Letter l= Letter.MAIN;
}
