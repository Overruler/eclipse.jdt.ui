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


/** typecomment template*/
interface Bag {

	public abstract java.util.Iterator iterator();

	public abstract Bag add(Comparable e);

	public abstract Bag addAll(Bag v1);

}