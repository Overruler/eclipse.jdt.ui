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
// rename A.m() -> k(): reference in B will be shadowed by B#k()
import static p.A.m;

public class A {
     public static void m() { }
     public static int m;
}

class B {
    void use() {
        int t= m;
        m();
    }
    void k() {}
}