package org.eclipse.jdt.internal.ui.javaeditor;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;

import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.SourceRange;

import org.eclipse.jdt.internal.corext.dom.Selection;

public final class Range implements ITextSelection, ISourceRange {
	public final int offset;

	public final int length;

	public Range() {
		this(-1, 0);
	}

	private Range(Range original) {
		this(original.offset, original.length);
	}

	public Range(int off, int len) {
		offset= off;
		length= len;
	}

	public Range(Selection selection) {
		this(newRange(selection));
	}

	public Range(ITextSelection selection) {
		this(newRange(selection));
	}

	public Range(ISourceRange selection) {
		this(newRange(selection));
	}

	public static Range newRange(Selection selection) {
		return new Range(selection == null ? -1 : selection.getOffset(), selection == null ? 0 : selection.getLength());
	}

	public static Range newRange(ITextSelection selection) {
		return new Range(selection == null ? -1 : selection.getOffset(), selection == null ? 0 : selection.getLength());
	}

	public static Range newRange(ISourceRange selection) {
		return new Range(selection == null ? -1 : selection.getOffset(), selection == null ? 0 : selection.getLength());
	}

	public Selection toSelection() {
		return Selection.createFromStartLength(offset, length);
	}

	public TextSelection toTextSelection() {
		return new TextSelection(offset, length);
	}

	public SourceRange toSourceRange() {
		return new SourceRange(offset, length);
	}

	@Override
	public int hashCode() {
		final int prime= 31;
		int result= 1;
		result= prime * result + length;
		result= prime * result + offset;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Range other= (Range) obj;
		if (length != other.length) {
			return false;
		}
		if (offset != other.offset) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Range [off=" + offset + ", len=" + length + "]"; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}

	@Override
	public boolean isEmpty() {
		return toTextSelection().isEmpty();
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public int getStartLine() {
		return toTextSelection().getStartLine();
	}

	@Override
	public int getEndLine() {
		return toTextSelection().getEndLine();
	}

	@Override
	public String getText() {
		return toTextSelection().getText();
	}
}