package com.gzlabs.utils.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gzlabs.utils.WidgetUtilities;

public class TestWidgetUtilities {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSafeTextSet() {
		Composite comp=new Composite(new Shell(), SWT.NONE);
		Text text=new Text(comp, SWT.NONE);
		String value="Test value";
		WidgetUtilities.safeTextSet(text, value);
		assertEquals("Text is not equal", value, text.getText());
		
		text=new Text(comp, SWT.NONE);
		WidgetUtilities.safeTextSet(null, value);
		assertEquals("Text should not be set", 0,text.getText().length());
		
		text=new Text(comp, SWT.NONE);
		WidgetUtilities.safeTextSet(text, null);
		assertEquals("Text should not be set", 0,text.getText().length());
	}

	@Test
	public void testSafeLabelSet() {
		Composite comp=new Composite(new Shell(), SWT.NONE);
		Label text=new Label(comp, SWT.NONE);
		String value="Test value";
		WidgetUtilities.safeLabelSet(text, value);
		assertEquals("Text is not equal", value, text.getText());
		
		text=new Label(comp, SWT.NONE);
		WidgetUtilities.safeLabelSet(null, value);
		assertEquals("Text should not be set", 0,text.getText().length());
		
		text=new Label(comp, SWT.NONE);
		WidgetUtilities.safeLabelSet(text, null);
		assertEquals("Text should not be set", 0,text.getText().length());
	}

	@Test
	public void testSafeTableItemSet() {
		Composite comp=new Composite(new Shell(), SWT.NONE);
		Table table=new Table(comp, SWT.NONE);
		TableItem text=new TableItem(table, SWT.NONE);
		String value="Test value";
		WidgetUtilities.safeTableItemSet(text, 0, value);
		assertEquals("Text is not equal", value, text.getText(0));
		
		text=new TableItem(table, SWT.NONE);
		WidgetUtilities.safeTableItemSet(null, 0, value);
		assertEquals("Text should not be set", 0,text.getText(0).length());
		
		text=new TableItem(table, SWT.NONE);
		WidgetUtilities.safeTableItemSet(text, 0, null);
		assertEquals("Text should not be set", 0,text.getText(0).length());
		
		text=new TableItem(table, SWT.NONE);
		WidgetUtilities.safeTableItemSet(text, -1, value);
		assertEquals("Text should not be set", 0,text.getText(-1).length());
	}

	@Test
	public void testSafeComboAdd() {
		Composite comp=new Composite(new Shell(), SWT.NONE);
		Combo text=new Combo(comp, SWT.NONE);
		String value="Test value";
		WidgetUtilities.safeComboAdd(text, value);
		assertEquals("Text is not equal", value, text.getItem(0));
		
		text=new Combo(comp, SWT.NONE);
		WidgetUtilities.safeComboAdd(null, value);
		assertEquals("Text should not be set", 0,text.getItemCount());
		
		text=new Combo(comp, SWT.NONE);
		WidgetUtilities.safeComboAdd(text, null);
		assertEquals("Text should not be set", 0,text.getItemCount());
	}

	@Test
	public void testSafeListAdd() {
		Composite comp=new Composite(new Shell(), SWT.NONE);
		List text=new List(comp, SWT.NONE);
		String value="Test value";
		WidgetUtilities.safeListAdd(text, value);
		assertEquals("Text is not equal", value, text.getItem(0));
		
		text=new List(comp, SWT.NONE);
		WidgetUtilities.safeListAdd(null, value);
		assertEquals("Text should not be set", 0,text.getItemCount());
		
		text=new List(comp, SWT.NONE);
		WidgetUtilities.safeListAdd(text, null);
		assertEquals("Text should not be set", 0,text.getItemCount());
	}

	@Test
	public void testSafeArrayStringListAdd() {
		ArrayList<String> text=new ArrayList<String>();
		String value="Test value";
		WidgetUtilities.safeArrayStringListAdd(text, value, false);
		assertEquals("Text is not equal", value, text.get(0));
		
		text=new ArrayList<String>();
		WidgetUtilities.safeArrayStringListAdd(null, value, false);
		assertEquals("Text should not be set", 0,text.size());
		
		text=new ArrayList<String>();
		WidgetUtilities.safeArrayStringListAdd(text, null, false);
		assertEquals("Text should not be set", 0,text.size());
		
		text=new ArrayList<String>();
		WidgetUtilities.safeArrayStringListAdd(text, null, true);
		assertEquals("Text should not be set", 1,text.size());
	}

	@Test
	public void testSafeButtonSet() {
		Composite comp=new Composite(new Shell(), SWT.NONE);
		Button text=new Button(comp, SWT.NONE);
		String value="Test value";
		WidgetUtilities.safeButtonSet(text, value);
		assertEquals("Text is not equal", value, text.getText());
		
		text=new Button(comp, SWT.NONE);
		WidgetUtilities.safeButtonSet(null, value);
		assertEquals("Text should not be set", 0,text.getText().length());
		
		text=new Button(comp, SWT.NONE);
		WidgetUtilities.safeButtonSet(text, null);
		assertEquals("Text should not be set", 0,text.getText().length());
	}

	@Test
	public void testSafeComboSelect() {
		Composite comp=new Composite(new Shell(), SWT.NONE);
		Combo text=new Combo(comp, SWT.NONE);
		String value="Test value";
		WidgetUtilities.safeComboAdd(text, value);
		WidgetUtilities.safeComboSelect(text, value);
		assertEquals("Text is not equal", value, text.getItem(text.getSelectionIndex()));
		
		WidgetUtilities.safeComboSelect(null, value);
		assertEquals("Text is not equal", value, text.getItem(text.getSelectionIndex()));
		

		WidgetUtilities.safeComboSelect(text, null);
		assertEquals("Text is not equal", value, text.getItem(text.getSelectionIndex()));
	}

}
