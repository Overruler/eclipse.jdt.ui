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

class A {
    void call(Ex<String> ex) {
        ex.method("Eclipse1", new Integer(1));
        Top<String> top= ex;
        top.method("Eclipse2", new Integer(2));
    }
}

class Top<TC> {
    <TM> void method(TC cTop, TM mTop) {}
}

class Ex<C> extends Top<C> {
    <M> void method(C cEx, M mEx) {}
}
