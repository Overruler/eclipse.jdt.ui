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
class Sup{
	static int CONSTANT= 0;
}
class B extends Sup {
}

class Test {
  public static void main(String[] arguments) {
    System.out.println(B.CONSTANT);
  }
}