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
import java.util.Comparator;

class A {
    ArrayList<? super A> fSink;
}

class U extends ArrayList<A> {
    public boolean add(A arg0) {
        return false;
    }
}

class V<Q extends A> implements Comparator<Q> {
    public int compare(Q arg0, Q arg1) {
        return 0;
    }
}
