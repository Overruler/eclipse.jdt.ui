/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.jdt.internal.ui.javadocexport;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.eclipse.jface.dialogs.Dialog;

import org.eclipse.ui.help.WorkbenchHelp;

import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import org.eclipse.jdt.ui.JavaUI;

import org.eclipse.jdt.internal.ui.IJavaHelpContextIds;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.dialogs.StatusDialog;
import org.eclipse.jdt.internal.ui.dialogs.StatusInfo;
import org.eclipse.jdt.internal.ui.dialogs.StatusUtil;
import org.eclipse.jdt.internal.ui.preferences.JavadocConfigurationBlock;
import org.eclipse.jdt.internal.ui.util.SWTUtil;
import org.eclipse.jdt.internal.ui.wizards.IStatusChangeListener;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.CheckedListDialogField;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.IListAdapter;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.ListDialogField;

public class JavadocStandardWizardPage extends JavadocWizardPage {

	private final int STYLESHEETSTATUS= 0;
	
	private JavadocOptionsManager fStore;
	private Composite fUpperComposite;

	private Group fBasicOptionsGroup;
	private Group fTagsGroup;

	private Button fTitleButton;
	private Text fTitleText;
	private Text fStyleSheetText;
	private FlaggedButton fDeprecatedList;
	private FlaggedButton fDeprecatedCheck;
	private FlaggedButton fIndexCheck;
	private FlaggedButton fSeperatedIndexCheck;
	private Button fStyleSheetBrowseButton;
	private Button fStyleSheetButton;

	private CheckedListDialogField fListDialogField;

	private StatusInfo fStyleSheetStatus;
	private ArrayList fButtonsList;
	private JavadocTreeWizardPage fFirstPage;

	public JavadocStandardWizardPage(String pageName, JavadocTreeWizardPage firstPage, JavadocOptionsManager store) {
		super(pageName);
		fFirstPage= firstPage;
		setDescription(JavadocExportMessages.getString("JavadcoStandardWizardPage.description")); //$NON-NLS-1$

		fStore= store;
		fButtonsList= new ArrayList();
		fStyleSheetStatus= new StatusInfo();

	}
	/*
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {

		initializeDialogUnits(parent);

		fUpperComposite= new Composite(parent, SWT.NONE);
		fUpperComposite.setLayoutData(createGridData(GridData.FILL_VERTICAL | GridData.FILL_HORIZONTAL, 1, 0));

		GridLayout layout= createGridLayout(4);
		layout.marginHeight= 0;
		fUpperComposite.setLayout(layout);

		createBasicOptionsGroup(fUpperComposite);
		createTagOptionsGroup(fUpperComposite);
		createListDialogField(fUpperComposite);
		createStyleSheetGroup(fUpperComposite);

		setControl(fUpperComposite);
		Dialog.applyDialogFont(fUpperComposite);
		WorkbenchHelp.setHelp(fUpperComposite, IJavaHelpContextIds.JAVADOC_STANDARD_PAGE);
	}
	private void createBasicOptionsGroup(Composite composite) {

		fTitleButton= createButton(composite, SWT.CHECK, JavadocExportMessages.getString("JavadcoStandardWizardPage.titlebutton.label"), createGridData(1)); //$NON-NLS-1$
		fTitleText= createText(composite, SWT.SINGLE | SWT.BORDER, null, createGridData(GridData.FILL_HORIZONTAL, 3, 0));
		String text= fStore.getTitle();
		if (!text.equals("")) { //$NON-NLS-1$
			fTitleText.setText(text);
			fTitleButton.setSelection(true);
		} else
			fTitleText.setEnabled(false);

		fBasicOptionsGroup= new Group(composite, SWT.SHADOW_ETCHED_IN);
		fBasicOptionsGroup.setLayout(createGridLayout(1));
		fBasicOptionsGroup.setLayoutData(createGridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL, 2, 0));
		fBasicOptionsGroup.setText(JavadocExportMessages.getString("JavadcoStandardWizardPage.basicgroup.label")); //$NON-NLS-1$

		new FlaggedButton(fBasicOptionsGroup, JavadocExportMessages.getString("JavadcoStandardWizardPage.usebutton.label"), new GridData(GridData.FILL_HORIZONTAL), fStore.USE, true); //$NON-NLS-1$
		new FlaggedButton(fBasicOptionsGroup, JavadocExportMessages.getString("JavadcoStandardWizardPage.hierarchybutton.label"), new GridData(GridData.FILL_HORIZONTAL), fStore.NOTREE, false); //$NON-NLS-1$
		new FlaggedButton(fBasicOptionsGroup, JavadocExportMessages.getString("JavadcoStandardWizardPage.navigartorbutton.label"), new GridData(GridData.FILL_HORIZONTAL), fStore.NONAVBAR, false); //$NON-NLS-1$

		fIndexCheck= new FlaggedButton(fBasicOptionsGroup, JavadocExportMessages.getString("JavadcoStandardWizardPage.indexbutton.label"), new GridData(GridData.FILL_HORIZONTAL), fStore.NOINDEX, false); //$NON-NLS-1$

		fSeperatedIndexCheck= new FlaggedButton(fBasicOptionsGroup, JavadocExportMessages.getString("JavadcoStandardWizardPage.seperateindexbutton.label"), createGridData(GridData.GRAB_HORIZONTAL, 1, convertWidthInCharsToPixels(3)), fStore.SPLITINDEX, true); //$NON-NLS-1$
		fSeperatedIndexCheck.getButton().setEnabled(fIndexCheck.getButton().getSelection());

		fIndexCheck.getButton().addSelectionListener(new ToggleSelectionAdapter(new Control[] { fSeperatedIndexCheck.getButton()}));
		fTitleButton.addSelectionListener(new ToggleSelectionAdapter(new Control[] { fTitleText }));

	}

	private void createTagOptionsGroup(Composite composite) {
		fTagsGroup= new Group(composite, SWT.SHADOW_ETCHED_IN);
		fTagsGroup.setLayout(createGridLayout(1));
		fTagsGroup.setLayoutData(createGridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL, 2, 0));
		fTagsGroup.setText(JavadocExportMessages.getString("JavadcoStandardWizardPage.tagsgroup.label")); //$NON-NLS-1$

		new FlaggedButton(fTagsGroup, JavadocExportMessages.getString("JavadcoStandardWizardPage.authorbutton.label"), new GridData(GridData.FILL_HORIZONTAL), fStore.AUTHOR, true); //$NON-NLS-1$
		new FlaggedButton(fTagsGroup, JavadocExportMessages.getString("JavadcoStandardWizardPage.versionbutton.label"), new GridData(GridData.FILL_HORIZONTAL), fStore.VERSION, true); //$NON-NLS-1$
		fDeprecatedCheck= new FlaggedButton(fTagsGroup, JavadocExportMessages.getString("JavadcoStandardWizardPage.deprecatedbutton.label"), new GridData(GridData.FILL_HORIZONTAL), fStore.NODEPRECATED, false); //$NON-NLS-1$
		fDeprecatedList= new FlaggedButton(fTagsGroup, JavadocExportMessages.getString("JavadcoStandardWizardPage.deprecatedlistbutton.label"), createGridData(GridData.FILL_HORIZONTAL, 1, convertWidthInCharsToPixels(3)), fStore.NODEPRECATEDLIST, false); //$NON-NLS-1$
		fDeprecatedList.getButton().setEnabled(fDeprecatedCheck.getButton().getSelection());

		fDeprecatedCheck.getButton().addSelectionListener(new ToggleSelectionAdapter(new Control[] { fDeprecatedList.getButton()}));
	} //end createTagOptionsGroup

	private void createStyleSheetGroup(Composite composite) {
		Composite c= new Composite(composite, SWT.NONE);
		c.setLayout(createGridLayout(3));
		c.setLayoutData(createGridData(GridData.FILL_HORIZONTAL, 4, 0));
		((GridLayout) c.getLayout()).marginWidth= 0;

		fStyleSheetButton= createButton(c, SWT.CHECK, JavadocExportMessages.getString("JavadcoStandardWizardPage.stylesheettext.label"), createGridData(1)); //$NON-NLS-1$
		fStyleSheetText= createText(c, SWT.SINGLE | SWT.BORDER, null, createGridData(GridData.FILL_HORIZONTAL, 1, 0));
		//there really aught to be a way to specify this
		 ((GridData) fStyleSheetText.getLayoutData()).widthHint= 200;
		fStyleSheetBrowseButton= createButton(c, SWT.PUSH, JavadocExportMessages.getString("JavadocStandardWizardPage.stylesheetbrowsebutton.label"), createGridData(GridData.HORIZONTAL_ALIGN_END, 1, 0)); //$NON-NLS-1$
		SWTUtil.setButtonDimensionHint(fStyleSheetBrowseButton);

		String str= fStore.getStyleSheet();
		if (str.equals("")) { //$NON-NLS-1$
			//default
			fStyleSheetText.setEnabled(false);
			fStyleSheetBrowseButton.setEnabled(false);
		} else {
			fStyleSheetButton.setSelection(true);
			fStyleSheetText.setText(str);
		}

		//Listeners
		fStyleSheetButton.addSelectionListener(new ToggleSelectionAdapter(new Control[] { fStyleSheetText, fStyleSheetBrowseButton }) {
			public void validate() {
				doValidation(STYLESHEETSTATUS);
			}
		});

		fStyleSheetText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				doValidation(STYLESHEETSTATUS);
			}
		});

		fStyleSheetBrowseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				handleFileBrowseButtonPressed(fStyleSheetText, new String[] { "*.css" }, JavadocExportMessages.getString("JavadocSpecificsWizardPage.stylesheetbrowsedialog.title")); //$NON-NLS-1$ //$NON-NLS-2$
			}
		});

	}

	private void createListDialogField(Composite composite) {
		Composite c= new Composite(composite, SWT.NONE);
		c.setLayout(createGridLayout(3));
		c.setLayoutData(createGridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL, 4, 0));
		((GridLayout) c.getLayout()).marginWidth= 0;

		String[] buttonlabels= new String[] { JavadocExportMessages.getString("JavadcoStandardWizardPage.selectallbutton.label"), JavadocExportMessages.getString("JavadcoStandardWizardPage.clearallbutton.label"), JavadocExportMessages.getString("JavadocStandardWizardPage.configurebutton.label")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		JavadocLinkDialogLabelProvider labelProvider= new JavadocLinkDialogLabelProvider();

		fListDialogField= new CheckedListDialogField(new ListAdapter(), buttonlabels, labelProvider);
		fListDialogField.setCheckAllButtonIndex(0);
		fListDialogField.setUncheckAllButtonIndex(1);

		createLabel(c, SWT.NONE, JavadocExportMessages.getString("JavadcoStandardWizardPage.referencedclasses.label"), createGridData(GridData.HORIZONTAL_ALIGN_BEGINNING, 4, 0)); //$NON-NLS-1$
		fListDialogField.doFillIntoGrid(c, 3);

		LayoutUtil.setHorizontalGrabbing(fListDialogField.getListControl(null));

		fListDialogField.enableButton(2, false);
	}

	private List getCheckedReferences(List referencedClasses) {
		List checkedElements= new ArrayList();
		
		String hrefs[]= fStore.getHRefs();
		if (hrefs.length > 0) { //$NON-NLS-1$
			HashSet set= new HashSet();
			for (int i= 0; i < hrefs.length; i++) {
				set.add(hrefs[i]);
			}
			for (Iterator iterator= referencedClasses.iterator(); iterator.hasNext();) {
				IJavaElement element= (IJavaElement) iterator.next();
				try {
					URL url= JavaUI.getJavadocBaseLocation(element);
					if (set.contains(url.toExternalForm())) {
						checkedElements.add(element);
					}
				} catch (JavaModelException ignore) {
					// ignore
				}
			}
		}
		return checkedElements;

	}

	private void findReferencedElements(List result, IJavaProject[] checkedProjects, List visisted) throws JavaModelException {
		for (int j= 0; j < checkedProjects.length; j++) {
			IJavaProject iJavaProject= checkedProjects[j];
			findReferencedElements(result, iJavaProject, new ArrayList());
		}
		for (int i= 0; i < checkedProjects.length; i++) {
			result.remove(checkedProjects[i]);
		}
	}

	/**
	 * Method finds a list of all referenced libararies and projects.
	 */
	private void findReferencedElements(List referencedClasses, IJavaProject jproject, List visited) throws JavaModelException {

		//to avoid loops

		if (visited.contains(jproject)) {
			return;
		}
		visited.add(jproject);
		
		IWorkspaceRoot root= ResourcesPlugin.getWorkspace().getRoot();

		IClasspathEntry[] entries= jproject.getResolvedClasspath(true);
		for (int i= 0; i < entries.length; i++) {
			IClasspathEntry curr= entries[i];
			switch (curr.getEntryKind()) {
				case IClasspathEntry.CPE_LIBRARY :
					IPackageFragmentRoot el= jproject.getPackageFragmentRoot(curr.getPath().toOSString());
					if (el != null) {
						if (!referencedClasses.contains(el)) {
							referencedClasses.add(el);
						}
					}
					break;
				case IClasspathEntry.CPE_PROJECT :
					IProject reqProject= (IProject) root.findMember(curr.getPath());
					IJavaProject javaProject= JavaCore.create(reqProject);

					if (reqProject != null && reqProject.isOpen()) {
						if (!referencedClasses.contains(javaProject)) {
							findReferencedElements(referencedClasses, javaProject, visited);
						}
					}
					break;
			}
		}
	}

	private void doValidation(int VALIDATE) {
		File file= null;
		String ext= null;
		Path path= null;

		switch (VALIDATE) {
			case STYLESHEETSTATUS :
				fStyleSheetStatus= new StatusInfo();
				if (fStyleSheetButton.getSelection()) {
					path= new Path(fStyleSheetText.getText());
					file= new File(fStyleSheetText.getText());
					ext= path.getFileExtension();
					if ((file == null) || !file.exists()) {
						fStyleSheetStatus.setError(JavadocExportMessages.getString("JavadcoStandardWizardPage.stylesheetnopath.error")); //$NON-NLS-1$
					} else if ((ext == null) || !ext.equalsIgnoreCase("css")) { //$NON-NLS-1$
						fStyleSheetStatus.setError(JavadocExportMessages.getString("JavadcoStandardWizardPage.stylesheetnotcss.error")); //$NON-NLS-1$
					}
				}
				break;
		}

		updateStatus(findMostSevereStatus());

	}

	private IStatus findMostSevereStatus() {
		return StatusUtil.getMostSevere(new IStatus[] { fStyleSheetStatus });
	}

	public void updateStore() {

		if (fTitleButton.getSelection())
			fStore.setTitle(fTitleText.getText());
		else
			fStore.setTitle(""); //$NON-NLS-1$

		//don't store the buttons if they are not enabled
		//this will change when there is a single page aimed at the standard doclet
		if (true) {
			Object[] buttons= fButtonsList.toArray();
			for (int i= 0; i < buttons.length; i++) {
				FlaggedButton button= (FlaggedButton) buttons[i];
				if (button.getButton().getEnabled())
					fStore.setBoolean(button.getFlag(), !(button.getButton().getSelection() ^ button.show()));
				else
					fStore.setBoolean(button.getFlag(), false == button.show());
			}
		}

		if (fStyleSheetText.getEnabled())
			fStore.setStyleSheet(fStyleSheetText.getText());
		else
			fStore.setStyleSheet(""); //$NON-NLS-1$

		fStore.setHRefs(getHRefs());
	}

	private String[] getHRefs() {
		HashSet res= new HashSet();
		List checked= fListDialogField.getCheckedElements();
		for (Iterator iterator= checked.iterator(); iterator.hasNext();) {
			try {
				IJavaElement element= (IJavaElement) iterator.next();
				URL url= JavaUI.getJavadocBaseLocation(element);
				if (url != null) {
					res.add(url.toExternalForm());
				}
			} catch (JavaModelException e) {
				JavaPlugin.log(e);
				continue;
			}
		}
		return (String[]) res.toArray(new String[res.size()]);
	}

	//get the links

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			doValidation(STYLESHEETSTATUS);
			updateHRefList(fFirstPage.getCheckedProjects());
		} else {
			fStore.setHRefs(getHRefs());
		}
	}

	/**
	 * Method will refresh the list of referenced libraries and projects
	 * depended on the projects or elements of projects selected in the
	 * TreeViewer on the JavadocTreeWizardPage.
	 */
	private void updateHRefList(IJavaProject[] checkedProjects) {

		List references= new ArrayList();
		try {

			findReferencedElements(references, checkedProjects, new ArrayList());
			fListDialogField.setElements(references);
			
			List checked= getCheckedReferences(references);
			fListDialogField.setCheckedElements(checked);

		} catch (JavaModelException e) {
			JavaPlugin.log(e);
		}
	}

	public void init() {
		updateStatus(new StatusInfo());
	}

	protected class FlaggedButton {

		private Button fButton;
		private String fFlag;
		private boolean fShowFlag;

		public FlaggedButton(Composite composite, String message, GridData gridData, String flag, boolean show) {
			fFlag= flag;
			fShowFlag= show;
			fButton= createButton(composite, SWT.CHECK, message, gridData);
			fButtonsList.add(this);
			setButtonSettings();
		}

		public Button getButton() {
			return fButton;
		}

		public String getFlag() {
			return fFlag;
		}
		public boolean show() {
			return fShowFlag;
		}

		private void setButtonSettings() {

			fButton.setSelection(!(fStore.getBoolean(fFlag) ^ fShowFlag));
		}

	} //end class FlaggesButton

	private class ListAdapter implements IListAdapter {

		/**
		 * @see IListAdapter#customButtonPressed(ListDialogField, int)
		 */
		public void customButtonPressed(ListDialogField field, int index) {
			if (index == 2)
				doEditButtonPressed();
		}

		/**
		 * @see IListAdapter#selectionChanged(ListDialogField)
		 */
		public void selectionChanged(ListDialogField field) {
			List selection= fListDialogField.getSelectedElements();
			if (selection.size() != 1) {
				fListDialogField.enableButton(2, false);
			} else {
				fListDialogField.enableButton(2, true);
			}
		}
		
		public void doubleClicked(ListDialogField field) {
		}		

	}

	/**
	 * Method doEditButtonPressed.
	 */
	private void doEditButtonPressed() {

		List selected= fListDialogField.getSelectedElements();
		if (selected.isEmpty()) {
			return;
		}
		Object obj= selected.get(0);
		if (obj instanceof IJavaElement) {
			JavadocPropertyDialog jdialog= new JavadocPropertyDialog(getShell(), (IJavaElement) obj);
			jdialog.open();
		}
	}

	private class JavadocPropertyDialog extends StatusDialog implements IStatusChangeListener {

		private JavadocConfigurationBlock fJavadocConfigurationBlock;
		private IJavaElement fElement;

		public JavadocPropertyDialog(Shell parent, IJavaElement selection) {
			super(parent);
			setTitle(JavadocExportMessages.getString("JavadocStandardWizardPage.javadocpropertydialog.title")); //$NON-NLS-1$

			fElement= selection;
			URL initialLocation= null;
			try {
				initialLocation= JavaUI.getJavadocBaseLocation(selection);
			} catch (JavaModelException e) {
				JavaPlugin.log(e);
			}
			boolean forProject= selection.getElementType() == IJavaElement.JAVA_PROJECT;
			fJavadocConfigurationBlock= new JavadocConfigurationBlock(parent, this, initialLocation, forProject);
		}

		protected Control createDialogArea(Composite parent) {
			Composite composite= (Composite) super.createDialogArea(parent);
			Control inner= fJavadocConfigurationBlock.createContents(composite);
			inner.setLayoutData(new GridData(GridData.FILL_BOTH));
			applyDialogFont(composite);		
			return composite;
		}

		public void statusChanged(IStatus status) {
			updateStatus(status);

		}

		/**
		 * @see Dialog#okPressed()
		 */
		protected void okPressed() {
			URL javadocLocation= fJavadocConfigurationBlock.getJavadocLocation();
			if (fElement instanceof IJavaProject) {
				JavaUI.setProjectJavadocLocation((IJavaProject) fElement, javadocLocation);
			} else {
				JavaUI.setLibraryJavadocLocation(fElement.getPath(), javadocLocation);
			}
			fListDialogField.refresh();
			super.okPressed();
		}

		/*
		 * @see org.eclipse.jface.window.Window#configureShell(Shell)
		 */
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			WorkbenchHelp.setHelp(newShell, IJavaHelpContextIds.JAVADOC_PROPERTY_DIALOG);
		}
	}
}
