/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.ui.tests.refactoring;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.NullProgressMonitor;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.Signature;

import org.eclipse.jdt.internal.corext.refactoring.rename.RenameMethodProcessor;
import org.eclipse.jdt.internal.corext.refactoring.rename.RenameVirtualMethodProcessor;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring;

public class RenameVirtualMethodInClassTests extends RefactoringTest {
	
	private static final Class clazz= RenameVirtualMethodInClassTests.class;
	private static final String REFACTORING_PATH= "RenameVirtualMethodInClass/";
	
	private static final boolean BUG_79976_RippleMethodFinder= true;
	private static final boolean BUG_83083_CORE_SEARCH_NPE= true;
	private static final boolean BUG_83393_CORE_VARARG_JAVADOC= true;
	
	private static final boolean BUG_83217_IMPLICIT_ENUM_METHODS= true;
		
	public RenameVirtualMethodInClassTests(String name) {
		super(name);
	}
	
	public static Test suite() {
		return new RefactoringTestSetup(new TestSuite(clazz));
	}
	
	public static Test setUpTest(Test test) {
		return new RefactoringTestSetup(test);
	}
	
	protected String getRefactoringPath(){
		return REFACTORING_PATH;
	}

	private void helper1_not_available(String methodName, String[] signatures) throws Exception{
		IType classA= getType(createCUfromTestFile(getPackageP(), "A"), "A");
		RenameMethodProcessor processor= new RenameVirtualMethodProcessor(classA.getMethod(methodName, signatures));
		RenameRefactoring ref= new RenameRefactoring(processor);
		assertTrue(! ref.isApplicable());
	}	
	
	private void helper1_0(String methodName, String newMethodName, String[] signatures) throws Exception{
		IType classA= getType(createCUfromTestFile(getPackageP(), "A"), "A");
		RenameMethodProcessor processor= new RenameVirtualMethodProcessor(classA.getMethod(methodName, signatures));
		RenameRefactoring ref= new RenameRefactoring(processor);
		processor.setNewElementName(newMethodName);
		RefactoringStatus result= performRefactoring(ref);
		assertNotNull("precondition was supposed to fail", result);
	}
	
	private void helper1() throws Exception{
		helper1_0("m", "k", new String[0]);
	}
	
	private void helper2_0(String methodName, String newMethodName, String[] signatures, boolean shouldPass, boolean updateReferences) throws Exception{
		ICompilationUnit cu= createCUfromTestFile(getPackageP(), "A");
		IType classA= getType(cu, "A");
		RenameMethodProcessor processor= new RenameVirtualMethodProcessor(classA.getMethod(methodName, signatures));
		RenameRefactoring ref= new RenameRefactoring(processor);
		processor.setUpdateReferences(updateReferences);
		processor.setNewElementName(newMethodName);
		
		assertEquals("was supposed to pass", null, performRefactoring(ref));
		if (!shouldPass){
			assertTrue("incorrect renaming because of java model", ! getFileContents(getOutputTestFileName("A")).equals(cu.getSource()));
			return;
		}
		String expectedRenaming= getFileContents(getOutputTestFileName("A"));
		String actuaRenaming= cu.getSource();
		assertEqualLines("incorrect renaming", expectedRenaming, actuaRenaming);
		
		assertTrue("anythingToUndo", RefactoringCore.getUndoManager().anythingToUndo());
		assertTrue("! anythingToRedo", !RefactoringCore.getUndoManager().anythingToRedo());
		//assertEquals("1 to undo", 1, Refactoring.getUndoManager().getRefactoringLog().size());
		
		RefactoringCore.getUndoManager().performUndo(null, new NullProgressMonitor());
		assertEqualLines("invalid undo", getFileContents(getInputTestFileName("A")), cu.getSource());

		assertTrue("! anythingToUndo", !RefactoringCore.getUndoManager().anythingToUndo());
		assertTrue("anythingToRedo", RefactoringCore.getUndoManager().anythingToRedo());
		//assertEquals("1 to redo", 1, Refactoring.getUndoManager().getRedoStack().size());
		
		RefactoringCore.getUndoManager().performRedo(null, new NullProgressMonitor());
		assertEqualLines("invalid redo", getFileContents(getOutputTestFileName("A")), cu.getSource());
	}
	
	private void helper2_0(String methodName, String newMethodName, String[] signatures, boolean shouldPass) throws Exception{
		helper2_0(methodName, newMethodName, signatures, shouldPass, true);
	}
	
	private void helper2_0(String methodName, String newMethodName, String[] signatures) throws Exception{
		helper2_0(methodName, newMethodName, signatures, true);
	}
	
	private void helper2(boolean updateReferences) throws Exception{
		helper2_0("m", "k", new String[0], true, updateReferences);
	}

	private void helper2() throws Exception{
		helper2(true);
	}
	
	private void helper2_fail() throws Exception{
		helper2_0("m", "k", new String[0], false);
	}
	
// ----------------------------------------------------------------
	
	public void testEnum1() throws Exception {
		helper2_0("getNameLength", "getNameSize", new String[0]);
	}
	
	public void testEnum2() throws Exception {
		helper2_0("getSquare", "get2ndPower", new String[0]);
	}
	
	public void testEnum3() throws Exception {
		helper2_0("getSquare", "get2ndPower", new String[0]);
	}
	
	public void testEnumFail1() throws Exception {
		if (BUG_83217_IMPLICIT_ENUM_METHODS)
			return;
		helper1_0("value", "valueOf", new String[]{"QString;"});
	}
	
	public void testGenerics1() throws Exception {
		if (BUG_79976_RippleMethodFinder || BUG_83083_CORE_SEARCH_NPE) {
			printTestDisabledMessage("Bugs 79976, 83083");
			return;
		}
		helper2_0("m", "k", new String[]{"QG;"});
	}
	
	public void testGenerics2() throws Exception {
		if (BUG_79976_RippleMethodFinder || BUG_83083_CORE_SEARCH_NPE) {
			printTestDisabledMessage("Bugs 79976, 83083");
			return;
		}
		helper2_0("add", "addIfPositive", new String[]{"QE;"});
	}
	
	public void testGenerics3() throws Exception {
		if (BUG_79976_RippleMethodFinder || BUG_83083_CORE_SEARCH_NPE) {
			printTestDisabledMessage("Bugs 79976, 83083");
			return;
		}
		helper2_0("add", "addIfPositive", new String[]{"QE;"});
	}
	
	public void testGenerics4() throws Exception {
		helper2_0("takeANumber", "doit", new String[]{"QNumber;"});
	}
	
	public void testVarargs1() throws Exception {
		helper2_0("runall", "runThese", new String[]{"[QRunnable;"});
	}
	
	public void testVarargs2() throws Exception {
		if (BUG_83393_CORE_VARARG_JAVADOC) {
			printTestDisabledMessage("BUG_83393_CORE_VARARG_JAVADOC");
			return;
		}
		helper2_0("m", "k", new String[]{"[QString;"});
	}
	
	public void testFail0() throws Exception{
		helper1();
	}
	
	public void testFail1() throws Exception{
		helper1_not_available("toString", new String[0]);
	}
	
	public void testFail2() throws Exception{
		helper1();
	}
	
	public void testFail3() throws Exception{
		helper1();
	}
	
	public void testFail4() throws Exception{
		helper1();
	}
	
	public void testFail5() throws Exception{
		helper1();
	}
	
	public void testFail6() throws Exception{
		helper1();
	}
	
	public void testFail7() throws Exception{
		helper1();
	}
	
	public void testFail8() throws Exception{
		helper1();
	}
	
	public void testFail9() throws Exception{
		helper1_0("m", "k", new String[]{Signature.SIG_INT});
	}
	
	public void testFail10() throws Exception{
		helper1();
	}
	
	public void testFail11() throws Exception{
		helper1();
	}

	public void testFail12() throws Exception{
		helper1();
	}
	
	public void testFail13() throws Exception{
		helper1();
	}
	
	public void testFail14() throws Exception{
		helper1_0("m", "k", new String[]{Signature.SIG_INT});
	}
	
	public void testFail15() throws Exception{
		helper1();
	}
	
	public void testFail17() throws Exception{
		helper1();
	}
	
	public void testFail18() throws Exception{
		helper1();
	}
	
	public void testFail19() throws Exception{
		helper1();
	}
	
	public void testFail20() throws Exception{
		helper1();
	}
	
	public void testFail21() throws Exception{
		helper1();
	}
	
	public void testFail22() throws Exception{
		helper1();
	}
	
	public void testFail23() throws Exception{
		helper1();
	}
	
	public void testFail24() throws Exception{
		helper1();
	}
	
	public void testFail25() throws Exception{
		helper1();
	}
	
	public void testFail26() throws Exception{
		helper1();
	}
	
	public void testFail27() throws Exception{
		helper1();
	}
	
	public void testFail28() throws Exception{
		helper1();
	}
	
	public void testFail29() throws Exception{
		helper1();
	}
		
	public void testFail30() throws Exception{
		helper1();
	}
	
	public void testFail31() throws Exception{
		helper1_0("m", "k", new String[]{"QString;"});
	}
	
	public void testFail32() throws Exception{
		helper1_0("m", "k", new String[]{"QObject;"});
	}
	
	public void testFail33() throws Exception{
		helper1_not_available("toString", new String[0]);
	}
	
	public void testFail34() throws Exception{
		helper1_0("m", "k", new String[]{"QString;"});
	}
	
//	//test removed - was invalid
//	public void testFail35() throws Exception{
//	}
	
	public void testFail36() throws Exception{
		helper1();
	}
	
	public void testFail37() throws Exception{
		helper1();
	}
	
	public void testFail38() throws Exception{
		//printTestDisabledMessage("must fix - nested type");
		helper1();
	}
	
	public void testFail39() throws Exception{
		helper1();
	}
	
	public void testFail40() throws Exception{
		//Autoboxing -> calls to methods can be redirected due to overloading
		helper1_0("m", "k", new String[]{Signature.SIG_INT});
	}
	
	public void test1() throws Exception{
		ParticipantTesting.reset();
		helper2();
	}	

	public void test10() throws Exception{
		helper2();
	}	
	
	public void test11() throws Exception{
		helper2();
	}	

	public void test12() throws Exception{
		helper2();
	}	

	public void test13() throws Exception{
		helper2();
	}	
	
	public void test14() throws Exception{
		helper2();
	}
	
	public void test15() throws Exception{
		helper2_0("m", "k", new String[]{Signature.SIG_INT});
	}		
	
	public void test16() throws Exception{
		helper2_0("m", "fred", new String[]{Signature.SIG_INT});
	}		
	
	public void test17() throws Exception{
		//printTestDisabledMessage("overloading");
		helper2_0("m", "kk", new String[]{Signature.SIG_INT});
	}		
	
	public void test18() throws Exception{
		ICompilationUnit cu= createCUfromTestFile(getPackageP(), "A");
		ICompilationUnit cuC= createCUfromTestFile(getPackageP(), "C");
		
		IType classB= getType(cu, "B");
		RenameMethodProcessor processor= new RenameVirtualMethodProcessor(classB.getMethod("m", new String[]{"I"}));
		RenameRefactoring ref= new RenameRefactoring(processor);
		processor.setNewElementName("kk");
		assertEquals("was supposed to pass", null, performRefactoring(ref));
		assertEqualLines("invalid renaming A", getFileContents(getOutputTestFileName("A")), cu.getSource());
		assertEqualLines("invalid renaming C", getFileContents(getOutputTestFileName("C")), cuC.getSource());
		
	}
	
	public void test19() throws Exception{
		helper2_0("m", "fred", new String[0]);
	}		
	
	public void test2() throws Exception{
		helper2_0("m", "fred", new String[0]);
	}		
	
	public void test20() throws Exception{
		helper2_0("m", "fred", new String[]{Signature.SIG_INT});
	}		
	
	public void test21() throws Exception{
		helper2_0("m", "fred", new String[]{Signature.SIG_INT});
	}
	
	public void test22() throws Exception{
		helper2();
	}		
	
	//anonymous inner class
	public void test23() throws Exception{
		helper2_fail();
	}		
	
	public void test24() throws Exception{
		helper2_0("m", "k", new String[]{"QString;"});
	}		
	
	public void test25() throws Exception{
		//printTestDisabledMessage("waiting for 1GIIBC3: ITPJCORE:WINNT - search for method references - missing matches");
		helper2();
	}		
	
	public void test26() throws Exception{
		helper2();
	}		
	
	public void test27() throws Exception{
		helper2();
	}		
	
	public void test28() throws Exception{
		helper2();
	}		
	
	public void test29() throws Exception{
		helper2();
	}		
	
	public void test30() throws Exception{
		helper2();
	}
	
	public void test31() throws Exception{
		helper2();
	}
	
	public void test32() throws Exception{
		helper2(false);
	}
	
	public void test33() throws Exception{
		helper2();
	}

	public void test34() throws Exception{
		printTestDisabledMessage("test for bug#18553");
//		helper2_0("A", "foo", new String[0], true, true);
	}

	public void test35() throws Exception{
		helper2_0("foo", "bar", new String[] {"QObject;"}, true);
	}

	public void test36() throws Exception{
		helper2_0("foo", "bar", new String[] {"QString;"}, true);
	}

	public void test37() throws Exception{
		helper2_0("foo", "bar", new String[] {"QA;"}, true);
	}
	
	public void test38() throws Exception {
		printTestDisabledMessage("difficult to set up test in current testing framework");
//		helper2();
	}
	
	public void test39() throws Exception {
		helper2();
	}
	
	public void test40() throws Exception { // test for bug 68592
		ICompilationUnit cu= createCUfromTestFile(getPackageP(), "A");
		IType localClass= cu.getType("A").getMethod("doit", new String[0]).getType("LocalClass", 1);
		IMethod method= localClass.getMethod("method", new String[]{"I"});
		RenameMethodProcessor processor= new RenameVirtualMethodProcessor(method);
		RenameRefactoring ref= new RenameRefactoring(processor);
		processor.setNewElementName("method2");
		assertEquals("was supposed to pass", null, performRefactoring(ref));
		assertEqualLines("invalid renaming A", getFileContents(getOutputTestFileName("A")), cu.getSource());
	}
	
	//anonymous inner class
	public void testAnon0() throws Exception{
		helper2();
	}		
	
	public void testLocal0() throws Exception{
		helper2();
	}
	
}
