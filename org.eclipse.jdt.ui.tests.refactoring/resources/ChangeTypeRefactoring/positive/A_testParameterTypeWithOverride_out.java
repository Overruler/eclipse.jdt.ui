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
import java.util.*;

class A_testParameterTypeWithOverride_in {
	static class X {
		public void foo(List v1){
			Collection c = v1;
		}
	}
	static class Y extends X {
		public void foo(List v2){
			v2 = new ArrayList();
		}
	}
}
