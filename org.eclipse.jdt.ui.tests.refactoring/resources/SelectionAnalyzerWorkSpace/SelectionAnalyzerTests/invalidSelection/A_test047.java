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
package invalidSelection;

public class A_test047 {
	public void foo() {
		for (int i= 10; i < 10; i++)
			/*]*/for (int z= 10; z < 10; z++)
				foo();
		foo()/*[*/;	
	}
}