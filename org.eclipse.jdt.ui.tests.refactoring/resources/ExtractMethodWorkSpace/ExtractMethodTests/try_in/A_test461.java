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
package try_in;

import java.io.IOException;

public class A_test461 {
	void f() throws IOException{
		/*[*/try{
			f();
		} catch (IOException e){
			throw new IOException();
		}/*]*/
	}
}
