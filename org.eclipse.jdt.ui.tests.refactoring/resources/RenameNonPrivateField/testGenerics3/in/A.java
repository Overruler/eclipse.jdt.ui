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

class A<T> {
    T f;
    
    public T getF() {
        return f;
    }
    
    public void setF(T f) {
        this.f = f;
    }
}

class B<E extends Number> extends A<E> {
    public E getF() {
        return super.f;
    }
    public void setF(E f) {
        super.setF(f);
    }
}