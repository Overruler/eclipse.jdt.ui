/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Dmitry Stalnov (dstalnov@fusionone.com) - contributed fix for
 *       bug Encapuslate field can fail when two variables in one variable declaration (see
 *       https://bugs.eclipse.org/bugs/show_bug.cgi?id=51540).
 *******************************************************************************/
package org.eclipse.jdt.ui.tests.refactoring;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Preferences;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;

import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import org.eclipse.jdt.internal.corext.refactoring.sef.SelfEncapsulateFieldRefactoring;

public class SefTests extends AbstractSelectionTestCase {

	private static SefTestSetup fgTestSetup;
	
	private static final boolean BUG_83100= true;
	
	public SefTests(String name) {
		super(name);
	}
	
	public static Test suite() {
		fgTestSetup= new SefTestSetup(new TestSuite(SefTests.class));
		return fgTestSetup;
	}
	
	public static Test setUpTest(Test test) {
		return new SefTestSetup(test);
	}

	protected String getResourceLocation() {
		return "SefWorkSpace/SefTests/";
	}
	
	protected String adaptName(String name) {
		return Character.toUpperCase(name.charAt(0)) + name.substring(1) + ".java";
	}	
	
	protected void performTest(IPackageFragment packageFragment, String id, String outputFolder, String fieldName) throws Exception {
		ICompilationUnit unit= createCU(packageFragment, id);
		IField field= getField(unit, fieldName);
		assertNotNull(field);
		
		Preferences preferences= JavaCore.getPlugin().getPluginPreferences();
		preferences.setValue(JavaCore.CODEASSIST_FIELD_PREFIXES, "");
		preferences.setValue(JavaCore.CODEASSIST_STATIC_FIELD_PREFIXES, "");
		preferences.setValue(JavaCore.CODEASSIST_FIELD_SUFFIXES, "");
		preferences.setValue(JavaCore.CODEASSIST_STATIC_FIELD_SUFFIXES, "");

		SelfEncapsulateFieldRefactoring refactoring= SelfEncapsulateFieldRefactoring.create(field);
		performTest(unit, refactoring, COMPARE_WITH_OUTPUT, getProofedContent(outputFolder, id), true);
	}
	
	protected void performInvalidTest(IPackageFragment packageFragment, String id, String fieldName) throws Exception {
		ICompilationUnit unit= createCU(packageFragment, id);
		IField field= getField(unit, fieldName);
		assertNotNull(field);

		Preferences preferences= JavaCore.getPlugin().getPluginPreferences();
		preferences.setValue(JavaCore.CODEASSIST_FIELD_PREFIXES, "");
		preferences.setValue(JavaCore.CODEASSIST_STATIC_FIELD_PREFIXES, "");
		preferences.setValue(JavaCore.CODEASSIST_FIELD_SUFFIXES, "");
		preferences.setValue(JavaCore.CODEASSIST_STATIC_FIELD_SUFFIXES, "");


		SelfEncapsulateFieldRefactoring refactoring= SelfEncapsulateFieldRefactoring.create(field);
		if (refactoring != null) {
			RefactoringStatus status= refactoring.checkAllConditions(new NullProgressMonitor());
			assertTrue(status.hasError());
		}
	}	
	
	private static IField getField(ICompilationUnit unit, String fieldName) throws Exception {
		IField result= null;
		IType[] types= unit.getAllTypes();
		for (int i= 0; i < types.length; i++) {
			IType type= types[i];
			result= type.getField(fieldName);
			if (result != null && result.exists())
				break;
		}
		return result;
	}

	private void objectTest(String fieldName) throws Exception {
		performTest(fgTestSetup.getObjectPackage(), getName(), "object_out", fieldName);
	}
	
	private void baseTest(String fieldName) throws Exception {
		performTest(fgTestSetup.getBasePackage(), getName(), "base_out", fieldName);
	}
	
	private void invalidTest(String fieldName) throws Exception {
		performInvalidTest(fgTestSetup.getInvalidPackage(), getName(), fieldName);
	}
	
	//=====================================================================================
	// Invalid
	//=====================================================================================
	
	public void testPostfixExpression() throws Exception {
		invalidTest("field");
	}
	
	public void testInvalidOverwrite() throws Exception {
		invalidTest("field");
	}
	
	public void testAnnotation() throws Exception {
		invalidTest("field");
	}
	
	//=====================================================================================
	// Primitiv Data Test
	//=====================================================================================
	
	public void testPrefixInt() throws Exception {
		baseTest("field");
	}
	
	public void testPrefixBoolean() throws Exception {
		baseTest("field");
	}
	
	public void testPostfixInt() throws Exception {
		baseTest("field");
	}
	
	public void testThisExpression() throws Exception {
		baseTest("field");
	}
	
	public void testThisExpressionInner() throws Exception {
		baseTest("field");
	}
	
	public void testFinal() throws Exception {
		baseTest("field");
	}
	
	public void testTwoFragments() throws Exception {
		baseTest("field");
	}
	
	//=====================================================================================
	// Basic Object Test
	//=====================================================================================
	
	public void testSimpleRead() throws Exception {
		objectTest("field");
	}
	
	public void testSimpleWrite() throws Exception {
		objectTest("field");
	}
	
	public void testSimpleReadWrite() throws Exception {
		objectTest("field");
	}

	public void testEnumRead() throws Exception {
		objectTest("field");
	}

	public void testEnumReadWrite() throws Exception {
		objectTest("field");
	}
	
	public void testNestedRead() throws Exception {
		objectTest("field");
	}
	
	public void testArrayRead() throws Exception {
		objectTest("field");
	}
	
	public void testSetterInAssignment() throws Exception {
		objectTest("field");
	}
	
	public void testSetterInExpression() throws Exception {
		objectTest("field");
	}
	
	public void testSetterInInitialization() throws Exception {
		objectTest("field");
	}
	
	public void testSetterAsReceiver() throws Exception {
		objectTest("field");
	}
	
	public void testCompoundWrite() throws Exception {
		objectTest("field");
	}
	
	public void testCompoundWrite2() throws Exception {
		objectTest("field");
	}
	
	public void testCompoundWrite3() throws Exception {
		objectTest("field");
	}
	
	public void testFinalField() throws Exception {
		objectTest("field");
	}
	
	public void testGenericRead() throws Exception {
		objectTest("field");
	}

	public void testGenericRead2() throws Exception {
		if (BUG_83100) {
			System.out.println("Test generic read 2 disabled due to bug 83100");
			return;
		}
		objectTest("field");
	}

	public void testGenericReadWrite() throws Exception {
		objectTest("field");
	}
}
