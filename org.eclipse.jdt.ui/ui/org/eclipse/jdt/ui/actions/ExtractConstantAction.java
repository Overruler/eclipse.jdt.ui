/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Timo Kinnunen - Contribution for bug 432147 - [refactoring] Extract Constant displays error message on name of local variable
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.ui.actions;

import org.eclipse.jface.text.ITextSelection;

import org.eclipse.ui.PlatformUI;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.SourceRange;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclaration;

import org.eclipse.jdt.internal.corext.dom.fragments.ASTFragmentFactory;
import org.eclipse.jdt.internal.corext.dom.fragments.IASTFragment;
import org.eclipse.jdt.internal.corext.refactoring.RefactoringAvailabilityTester;
import org.eclipse.jdt.internal.corext.refactoring.code.ExtractConstantRefactoring;
import org.eclipse.jdt.internal.corext.refactoring.code.PromoteTempToFieldRefactoring;
import org.eclipse.jdt.internal.corext.refactoring.structure.CompilationUnitRewrite;
import org.eclipse.jdt.internal.corext.refactoring.util.RefactoringASTParser;

import org.eclipse.jdt.ui.refactoring.RefactoringSaveHelper;

import org.eclipse.jdt.internal.ui.IJavaHelpContextIds;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.actions.ActionUtil;
import org.eclipse.jdt.internal.ui.actions.SelectionConverter;
import org.eclipse.jdt.internal.ui.javaeditor.JavaEditor;
import org.eclipse.jdt.internal.ui.javaeditor.JavaTextSelection;
import org.eclipse.jdt.internal.ui.refactoring.ExtractConstantWizard;
import org.eclipse.jdt.internal.ui.refactoring.PromoteTempWizard;
import org.eclipse.jdt.internal.ui.refactoring.RefactoringMessages;
import org.eclipse.jdt.internal.ui.refactoring.actions.RefactoringStarter;

/**
 * Extracts an expression into a constant field and replaces all occurrences of
 * the expression with the new constant.
 *
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 *
 * @since 2.1
 *
 * @noextend This class is not intended to be subclassed by clients.
 */
public class ExtractConstantAction extends SelectionDispatchAction {

	private final JavaEditor fEditor;

	/**
	 * Note: This constructor is for internal use only. Clients should not call this constructor.
	 * @param editor the java editor
	 *
	 * @noreference This constructor is not intended to be referenced by clients.
	 */
	public ExtractConstantAction(JavaEditor editor) {
		super(editor.getEditorSite());
		setText(RefactoringMessages.ExtractConstantAction_label);
		fEditor= editor;
		setEnabled(SelectionConverter.getInputAsCompilationUnit(fEditor) != null);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IJavaHelpContextIds.EXTRACT_CONSTANT_ACTION);
	}

	/* (non-Javadoc)
	 * Method declared on SelectionDispatchAction
	 */
	@Override
	public void selectionChanged(ITextSelection selection) {
		setEnabled((fEditor != null && SelectionConverter.getInputAsCompilationUnit(fEditor) != null));
	}

	/**
	 * Note: This method is for internal use only. Clients should not call this method.
	 * @param selection the Java text selection (internal type)
	 *
	 * @noreference This method is not intended to be referenced by clients.
	 */
	@Override
	public void selectionChanged(JavaTextSelection selection) {
		setEnabled(RefactoringAvailabilityTester.isExtractConstantAvailable(selection));
	}

	/* (non-Javadoc)
	 * Method declared on SelectionDispatchAction
	 */
	@Override
	public void run(ITextSelection selection) {
		if (!ActionUtil.isEditable(fEditor))
			return;
		ICompilationUnit unit= SelectionConverter.getInputAsCompilationUnit(fEditor);
		CompilationUnit cuNode= RefactoringASTParser.parseWithASTProvider(unit, true, null);
		int selectionStart= selection.getOffset();
		int selectionLength= selection.getLength();
		ExtractConstantRefactoring refactoring= new ExtractConstantRefactoring(cuNode, selectionStart, selectionLength);
		try {
			CompilationUnitRewrite cuRewrite= new CompilationUnitRewrite(unit, cuNode);
			SourceRange range= new SourceRange(selectionStart, selectionLength);
			IASTFragment ast= ASTFragmentFactory.createFragmentForSourceRange(range, cuRewrite.getRoot(), unit);
			ASTNode node= ast.getAssociatedNode();
			if (node instanceof SimpleName && node.getParent() instanceof VariableDeclaration) {
				ICompilationUnit cunit= SelectionConverter.getInputAsCompilationUnit(fEditor);
				PromoteTempToFieldRefactoring refactoring2= new PromoteTempToFieldRefactoring(cunit, selection.getOffset(), selection.getLength());
				refactoring2.setInitializeIn(PromoteTempToFieldRefactoring.INITIALIZE_IN_FIELD);
				refactoring2.setDeclareFinal(true);
				refactoring2.setDeclareStatic(true);
				refactoring2.setFieldName(refactoring.guessConstantName());
				refactoring2.setSelfInitializing(true);
				new RefactoringStarter().activate(new PromoteTempWizard(refactoring2), getShell(), RefactoringMessages.ConvertLocalToField_title, RefactoringSaveHelper.SAVE_NOTHING);
				return;
			}
		} catch (JavaModelException e) {
			JavaPlugin.log(e);
		}
		new RefactoringStarter().activate(new ExtractConstantWizard(refactoring), getShell(), RefactoringMessages.ExtractConstantAction_extract_constant, RefactoringSaveHelper.SAVE_NOTHING);
	}
}
