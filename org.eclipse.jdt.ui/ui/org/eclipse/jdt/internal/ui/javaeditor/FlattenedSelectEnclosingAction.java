/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.internal.ui.javaeditor;

import org.eclipse.jface.text.ITextSelection;

import org.eclipse.ui.IEditorInput;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.SourceRange;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;

import org.eclipse.jdt.internal.corext.dom.SelectionAnalyzer;

import org.eclipse.jdt.ui.SharedASTProvider;

import org.eclipse.jdt.internal.ui.JavaPlugin;

public class FlattenedSelectEnclosingAction {
	static Range expanded(JavaEditor editor) throws JavaModelException {
		Range oldRange= new Range((ITextSelection) editor.getSelectionProvider().getSelection());
		IEditorInput editorInput= editor.getEditorInput();
		if (editorInput == null) {
			return oldRange;
		}
		ITypeRoot typeRoot= JavaPlugin.getDefault().getWorkingCopyManager().getWorkingCopy(editorInput, false);
		if (typeRoot == null) {
			IJavaElement adapter= (IJavaElement) editorInput.getAdapter(IJavaElement.class);
			if (!(adapter instanceof ITypeRoot)) {
				return oldRange;
			}
			typeRoot= (ITypeRoot) adapter;
		}
		if (!typeRoot.exists()) {
			return oldRange;
		}
		Range maxRange= new Range(typeRoot.getSourceRange());
		if (!SourceRange.isAvailable(maxRange)) {
			return oldRange;
		}
		CompilationUnit root= SharedASTProvider.getAST(typeRoot, SharedASTProvider.WAIT_YES, null);
		if (root == null) {
			return oldRange;
		}
		ASTNode node= coveringNode(oldRange, root);
		if (node == null) {
			return oldRange;
		}
		int start= node.getStartPosition();
		int length= Math.min(maxRange.getLength(), start + node.getLength() - 1) - start + 1;
		return new Range(Math.max(0, start), Math.max(1, length));
	}

	static ASTNode coveringNode(Range oldRange, CompilationUnit root) {
		final boolean SELECTED_NODE_IS_TRAVERSED= true;
		SelectionAnalyzer analyzer= new SelectionAnalyzer(oldRange.toSelection(), SELECTED_NODE_IS_TRAVERSED);
		root.accept(analyzer);
		return getLastCoveringOr(analyzer, getParentNode(analyzer.getFirstSelectedNode()));
	}

	static ASTNode getLastCoveringOr(SelectionAnalyzer analyzer, ASTNode parentNode) {
		return parentNode != null ? parentNode : analyzer.getLastCoveringNode();
	}

	static ASTNode getParentNode(ASTNode firstNode) {
		return firstNode != null ? firstNode.getParent() : null;
	}
}
