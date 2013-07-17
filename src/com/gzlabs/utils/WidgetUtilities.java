package com.gzlabs.utils;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/**
 * Gui utilites, mostly safe widget manipulation
 * 
 * @author apavlune
 * 
 */
public class WidgetUtilities {

	/**
	 * Sets text to the Text widget
	 * 
	 * @param obj
	 *            Object to operate on
	 * @param value
	 *            Text to set it to
	 */
	public static void safeTextSet(Text obj, String value) {
		if (obj != null && value != null) {
			obj.setText(value);
		}
	}
	
	/**
	 * Sets text to the Label widget
	 * 
	 * @param obj
	 *            Object to operate on
	 * @param value
	 *            Text to set it to
	 */
	public static void safeLabelSet(Label obj, String value) {
		if (obj != null && value != null) {
			obj.setText(value);
		}
	}

	/**
	 * Sets text to the TableItem widget
	 * 
	 * @param obj
	 *            Object to operate on
	 * @param column
	 *            Column of the table item
	 * @param value
	 *            Text to set it to
	 * 
	 */
	public static void safeTableItemSet(TableItem obj, int column, String value) {
		if (obj != null && value != null) {
			obj.setText(column, value);
		}
	}

	/**
	 * Adds text to the Combo widget
	 * 
	 * @param obj
	 *            Object to operate on
	 * @param value
	 *            Text to set it to
	 */
	public static void safeComboAdd(Combo obj, String value) {
		if (obj != null && value != null) {
			obj.add(value);
		}
	}
	
	/**
	 * Adds text to the List widget
	 * 
	 * @param obj
	 *            Object to operate on
	 * @param value
	 *            Text to set it to
	 */
	public static void safeListAdd(List obj, String value) {
		if (obj != null && value != null) {
			obj.add(value);
		}
	}
	
	/**
	 * Adds text to the ArrayList widget
	 * 
	 * @param obj
	 *            Object to operate on
	 * @param value
	 *            Text to set it to
	 *@param force
	 *          Force a value to be in the list
	 *          
	 */
	public static void safeArrayStringListAdd(ArrayList<String> obj, String value, boolean force) {
		if (obj != null) {
			if(value!=null)
			{
				obj.add(value);
			}
			else if(force)
			{
				obj.add("");
			}
		}
	}

	/**
	 * Sets text to the Button widget
	 * 
	 * @param obj
	 *            Object to operate on
	 * @param value
	 *            Text to set it to
	 */
	public static void safeButtonSet(Button obj, String value) {
		if (obj != null && value != null) {
			obj.setText(value);
		}
	}

	/**
	 * Wrapper to perform safe selection of the items in a combo
	 * 
	 * @param obj
	 *           Combo to use
	 * @param value
	 *             Item to select
	 */
	public static void safeComboSelect(Combo obj, String value) {
		if (obj != null && value != null) {
			obj.select(obj.indexOf(value));
		}
	}
	
	/**
	 * Safe string replacement method
	 * @param str Source string
	 * @param what Replace what?
	 * @param withwhat Replace with what?
	 * @return String with replaced characters.
	 */
	public static String safeStringReplace(String str, String what, String withwhat)
	{
		String retstr="";
		if(str!=null)
		{
			retstr=str;
			if(what!=null && withwhat!=null)
			{
				retstr=retstr.replace(what, withwhat);
			}
		}
		return retstr;
		
	}

}
