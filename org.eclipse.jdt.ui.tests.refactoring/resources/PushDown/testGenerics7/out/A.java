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
abstract class A{
	public abstract void m();
}
abstract class B<S> extends A{

	public abstract void m();
}
abstract class B1<S> extends B<String>{
}
abstract class C<S> extends A{

	public abstract void m();
}