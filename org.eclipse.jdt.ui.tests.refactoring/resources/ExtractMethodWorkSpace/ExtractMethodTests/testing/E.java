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
package testing;

import java.awt.peer.LabelPeer;

public class E {
	private F f;
	public void foo() {
		try {
			f.fun();
		} catch (Exception e) {
		}
	}
}

