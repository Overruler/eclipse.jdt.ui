/*
 * Licensed Materials - Property of IBM,
 * WebSphere Studio Workbench
 * (c) Copyright IBM Corp 2000
 */
package org.eclipse.jdt.internal.ui.refactoring;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.RadioGroupFieldEditor;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import org.eclipse.jdt.internal.ui.JavaPlugin;

public class RefactoringPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public RefactoringPreferencePage() {
		super(GRID);
	}
	
	public static void initDefaults(IPreferenceStore store) {
		store.setDefault(RefactoringPreferences.PREF_ERROR_PAGE_SEVERITY_THRESHOLD, RefactoringPreferences.ERROR_SEVERITY);
		store.setDefault(RefactoringPreferences.PREF_JAVA_STYLE_GUIDE_CONFORM, true);
	}

	protected IPreferenceStore doGetPreferenceStore() {
		return JavaPlugin.getDefault().getPreferenceStore();
	}

	public void createFieldEditors() {
		Composite parent= getFieldEditorParent();
		
		{
			String prefix= "RefactoringPreferencePage.errorPage.severity.";
			RadioGroupFieldEditor editor= new RadioGroupFieldEditor(
				RefactoringPreferences.PREF_ERROR_PAGE_SEVERITY_THRESHOLD,
				getResourceString(prefix + "label"),
				2,
				new String[] [] {
					{ getResourceString(prefix + "fatal"), 			RefactoringPreferences.FATAL_SEVERITY },
					{ getResourceString(prefix + "warning"), 		RefactoringPreferences.WARNING_SEVERITY },
					{ getResourceString(prefix + "error"), 			RefactoringPreferences.ERROR_SEVERITY },
					{ getResourceString(prefix + "information"),	RefactoringPreferences.INFO_SEVERITY }				
				},
				parent
				);
			addField(editor);
		}
		{
			String prefix= "RefactoringPreferencePage.javaStyleGuideConform.";
			BooleanFieldEditor editor= new BooleanFieldEditor(
				RefactoringPreferences.PREF_JAVA_STYLE_GUIDE_CONFORM,
				getResourceString(prefix + "label"),
				BooleanFieldEditor.DEFAULT,
				parent);
			addField(editor);	
		}
	}
	
	public void init(IWorkbench workbench) {
	}	
	
	private String getResourceString(String key) {
		return RefactoringResources.getResourceString(key);
	}
}
