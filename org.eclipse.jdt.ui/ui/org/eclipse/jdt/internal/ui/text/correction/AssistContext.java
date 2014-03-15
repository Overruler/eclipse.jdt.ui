/*******************************************************************************
 * Copyright (c) 2000, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Timo Kinnunen <timo.kinnunen@gmail.com> - Bug 428139 [extract local] Extract local variable should be possible without selection
 *******************************************************************************/
package org.eclipse.jdt.internal.ui.text.correction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.TextInvocationContext;

import org.eclipse.ui.IEditorPart;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.NodeFinder;

import org.eclipse.jdt.ui.SharedASTProvider;
import org.eclipse.jdt.ui.text.java.IInvocationContext;


public class AssistContext extends TextInvocationContext implements IInvocationContext {

	private final ICompilationUnit fCompilationUnit;
	private final IEditorPart fEditor;

	private CompilationUnit fASTRoot;
	private final SharedASTProvider.WAIT_FLAG fWaitFlag;
	/**
	 * The cached node finder, can be null.
	 * @since 3.6
	 */
	private NodeFinder fNodeFinder;
	private List<String> fCommands= Collections.emptyList();


	/*
	 * @since 3.5
	 */
	private AssistContext(ICompilationUnit cu, ISourceViewer sourceViewer, IEditorPart editor, int offset, int length, SharedASTProvider.WAIT_FLAG waitFlag) {
		super(sourceViewer, offset, length);
		Assert.isLegal(cu != null);
		Assert.isLegal(waitFlag != null);
		fCompilationUnit= cu;
		fEditor= editor;
		fWaitFlag= waitFlag;
	}
	
	/*
	 * @since 3.5
	 */
	public AssistContext(ICompilationUnit cu, ISourceViewer sourceViewer, int offset, int length, SharedASTProvider.WAIT_FLAG waitFlag) {
		this(cu, sourceViewer, null, offset, length, waitFlag);
	}
	
	/*
	 * @since 3.5
	 */
	public AssistContext(ICompilationUnit cu, ISourceViewer sourceViewer, IEditorPart editor, int offset, int length) {
		this(cu, sourceViewer, editor, offset, length, SharedASTProvider.WAIT_YES);
	}
	
	/*
	 * Constructor for CorrectionContext.
	 * @since 3.4
	 */
	public AssistContext(ICompilationUnit cu, ISourceViewer sourceViewer, int offset, int length) {
		this(cu, sourceViewer, null, offset, length);
	}

	/*
	 * Constructor for CorrectionContext.
	 */
	public AssistContext(ICompilationUnit cu, int offset, int length) {
		this(cu, null, offset, length);
	}

	/**
	 * Returns the compilation unit.
	 * @return an <code>ICompilationUnit</code>
	 */
	public ICompilationUnit getCompilationUnit() {
		return fCompilationUnit;
	}
	
	/**
	 * Returns the editor or <code>null</code> if none.
	 * @return an <code>IEditorPart</code> or <code>null</code> if none
	 * @since 3.5
	 */
	public IEditorPart getEditor() {
		return fEditor;
	}

	/**
	 * Returns the length.
	 * @return int
	 */
	public int getSelectionLength() {
		return Math.max(getLength(), 0);
	}

	/**
	 * Returns the offset.
	 * @return int
	 */
	public int getSelectionOffset() {
		return getOffset();
	}

	public CompilationUnit getASTRoot() {
		if (fASTRoot == null) {
			fASTRoot= SharedASTProvider.getAST(fCompilationUnit, fWaitFlag, null);
			if (fASTRoot == null) {
				// see bug 63554
				fASTRoot= ASTResolving.createQuickFixAST(fCompilationUnit, null);
			}
		}
		return fASTRoot;
	}


	/**
	 * @param root The ASTRoot to set.
	 */
	public void setASTRoot(CompilationUnit root) {
		fASTRoot= root;
	}

	/*(non-Javadoc)
	 * @see org.eclipse.jdt.ui.text.java.IInvocationContext#getCoveringNode()
	 */
	public ASTNode getCoveringNode() {
		if (fNodeFinder == null) {
			fNodeFinder= new NodeFinder(getASTRoot(), getOffset(), getLength());
		}
		return fNodeFinder.getCoveringNode();
	}

	/*(non-Javadoc)
	 * @see org.eclipse.jdt.ui.text.java.IInvocationContext#getCoveredNode()
	 */
	public ASTNode getCoveredNode() {
		if (fNodeFinder == null) {
			fNodeFinder= new NodeFinder(getASTRoot(), getOffset(), getLength());
		}
		return fNodeFinder.getCoveredNode();
	}

	void setCommands(String... commands) {
		fCommands= commands == null ? Collections.<String>emptyList() : Collections.unmodifiableList(new ArrayList<String>(Arrays.asList(commands)));
	}

	List<String> getCommands() {
		return fCommands;
	}

	AssistContext adjustRange(ASTNode targetNode) {
		int offset= targetNode == null ? -1 : targetNode.getStartPosition();
		int length= targetNode == null ? 0 : targetNode.getLength();
		AssistContext assistContext= new AssistContext(fCompilationUnit, getSourceViewer(), fEditor, offset, length);
		assistContext.fCommands= fCommands;
		return assistContext;
	}

	boolean canInvokeCommand(String commandId) {
		return fCommands.size() == 0 || fCommands.contains(commandId);
	}
}
