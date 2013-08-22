package com.gzlabs.utils.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gzlabs.utils.DateUtils;

public class TestDateUtils {

	DateTime date;
	DateTime time;
	
	String fulldate;
	String fulltime;
	String fulldatetime;
	
	Composite comp;
	
	/***************************************************************/
	//Change this before running test to the current week's Monday
	/***************************************************************/
	final int weekstartMon=24;
	final int weekstartMonth=6;
	final int weekstartYear=2013;
	
	
	@Before
	public void setUp() throws Exception {
	
		comp=new Composite(new Shell(), SWT.NONE);
		date=new DateTime(comp, SWT.NONE);
		time=new DateTime(comp, SWT.NONE);
		String month=String.format("%02d", date.getMonth()+1);
		String day=String.format("%02d", date.getDay());
		
		fulldate=date.getYear() + "-" + month + "-" + day;
		
		String hour=String.format("%02d", time.getHours());
		String minute=String.format("%02d", time.getMinutes());
		String second=String.format("%02d", time.getSeconds());
	
		fulltime=hour+":"+minute+":"+second+".0";
		
		fulldatetime=fulldate+" "+fulltime;
			
	}

	@After
	public void tearDown() throws Exception {			
		
	}

	@Test
	public void testDateStringFromWidget() {
		String dt=DateUtils.dateStringFromWidget(date, time);
		assertEquals("Dates should be equal", fulldatetime, dt);
		
		dt=DateUtils.dateStringFromWidget(date, null);
		assertEquals("Dates should be equal", fulldate, dt);
		
		dt=DateUtils.dateStringFromWidget(null, null);
		assertEquals("Date should be empty", 0, dt.length());
	
	}

	@Test
	public void testCalendarFromWidget() {
		Calendar cal=DateUtils.calendarFromWidget(date, time);
		Calendar datecal=new GregorianCalendar();
		assertEquals("Year should be equal", datecal.get(Calendar.YEAR), cal.get(Calendar.YEAR));
		assertEquals("Month should be equal", datecal.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals("Day should be equal", datecal.get(Calendar.DATE), cal.get(Calendar.DATE));
		assertEquals("Hour should be equal", datecal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.HOUR_OF_DAY));
		assertEquals("Minute should be equal", datecal.get(Calendar.MINUTE), cal.get(Calendar.MINUTE));
		assertEquals("Second should be equal", datecal.get(Calendar.SECOND), cal.get(Calendar.SECOND));
		
		cal=DateUtils.calendarFromWidget(date, null);
		assertEquals("Year should be equal", datecal.get(Calendar.YEAR), cal.get(Calendar.YEAR));
		assertEquals("Month should be equal", datecal.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals("Day should be equal", datecal.get(Calendar.DATE), cal.get(Calendar.DATE));
		assertEquals("Hour should be equal", datecal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.HOUR_OF_DAY));
		assertEquals("Minute should be equal", datecal.get(Calendar.MINUTE), cal.get(Calendar.MINUTE));
		assertEquals("Second should be equal", datecal.get(Calendar.SECOND), cal.get(Calendar.SECOND));
		
		cal=DateUtils.calendarFromWidget(null, null);
		assertEquals("Year should be equal", datecal.get(Calendar.YEAR), cal.get(Calendar.YEAR));
		assertEquals("Month should be equal", datecal.get(Calendar.MONTH), cal.get(Calendar.MONTH));
		assertEquals("Day should be equal", datecal.get(Calendar.DATE), cal.get(Calendar.DATE));
		assertEquals("Hour should be equal", datecal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.HOUR_OF_DAY));
		assertEquals("Minute should be equal", datecal.get(Calendar.MINUTE), cal.get(Calendar.MINUTE));
		assertEquals("Second should be equal", datecal.get(Calendar.SECOND), cal.get(Calendar.SECOND));		
	}

	@Test
	public void testSetWidgetFromCalendar() {
		Calendar cal=new GregorianCalendar();
		cal.add(Calendar.DATE, 1000);
		
		Composite comp=new Composite(new Shell(), SWT.NONE);
		DateTime date1=new DateTime(comp, SWT.NONE);
		
		DateUtils.setWidgetFromCalendar(date1, cal);
		
		Calendar cal2=DateUtils.calendarFromWidget(date1, date1);

		assertEquals("Year should be equal", cal.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
		assertEquals("Month should be equal", cal.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
		assertEquals("Day should be equal", cal.get(Calendar.DATE), cal2.get(Calendar.DATE));
		assertEquals("Hour should be equal", cal.get(Calendar.HOUR_OF_DAY), cal2.get(Calendar.HOUR_OF_DAY));
		assertEquals("Minute should be equal", cal.get(Calendar.MINUTE), cal2.get(Calendar.MINUTE));
		assertEquals("Second should be equal", cal.get(Calendar.SECOND), cal2.get(Calendar.SECOND));
	}

	@Test
	public void testCalendarFromString() {
		Calendar cal=DateUtils.calendarFromString(fulldatetime);
		Calendar cal2=DateUtils.calendarFromWidget(date, time);
		assertEquals("Year should be equal", cal.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
		assertEquals("Month should be equal", cal.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
		assertEquals("Day should be equal", cal.get(Calendar.DATE), cal2.get(Calendar.DATE));
		assertEquals("Hour should be equal", cal.get(Calendar.HOUR_OF_DAY), cal2.get(Calendar.HOUR_OF_DAY));
		assertEquals("Minute should be equal", cal.get(Calendar.MINUTE), cal2.get(Calendar.MINUTE));
		assertEquals("Second should be equal", cal.get(Calendar.SECOND), cal2.get(Calendar.SECOND));
	}

	@Test
	public void testIsCalendarBetweenCalendarCalendarCalendarBoolean() {
		Calendar cal=new GregorianCalendar();
		cal.add(Calendar.DATE, 1000);
		
		Calendar cal2=new GregorianCalendar();
		cal2.add(Calendar.DATE, -1000);
		
		Calendar cal3=new GregorianCalendar();
		
		assertTrue("Calendar should be in between", DateUtils.isCalendarBetween(cal2, cal, cal3, true));
		assertTrue("Calendar should be in between", DateUtils.isCalendarBetween(cal2, cal, cal2, true));
		assertTrue("Calendar should be in between", DateUtils.isCalendarBetween(cal2, cal, cal, true));
		
		assertFalse("Calendar should not be in between", DateUtils.isCalendarBetween(cal2, cal, cal, false));
		assertFalse("Calendar should not be in between", DateUtils.isCalendarBetween(cal2, cal, cal2, false));
		
		cal3.add(Calendar.DATE, 1001);
		assertFalse("Calendar should not be in between", DateUtils.isCalendarBetween(cal2, cal, cal3, false));
		assertFalse("Calendar should not be in between", DateUtils.isCalendarBetween(cal2, cal, cal3, true));
		
		assertFalse("Calendar should not be in between", DateUtils.isCalendarBetween(cal2, cal, null, true));
		assertFalse("Calendar should not be in between", DateUtils.isCalendarBetween(null, cal, cal3, true));
		assertFalse("Calendar should not be in between", DateUtils.isCalendarBetween(cal2, null, cal3, true));
		assertFalse("Calendar should not be in between", DateUtils.isCalendarBetween(null, null, cal3, true));
		
		
	}

	@Test
	public void testIsCalendarBetweenStringStringStringBoolean() {
		String date1="2013-01-01 01:00:00.0";
		String date2="2013-12-01 01:00:00.0";
		String date3="2013-06-01 01:00:00.0";
		
		assertTrue("Calendar should be in between", DateUtils.isCalendarBetween(date1, date2, date3, true));
		assertTrue("Calendar should be in between", DateUtils.isCalendarBetween(date1, date2, date1, true));
		assertTrue("Calendar should be in between", DateUtils.isCalendarBetween(date1, date2, date2, true));
		
		assertFalse("Calendar should not be in between", DateUtils.isCalendarBetween(date1, date2, date1, false));
		assertFalse("Calendar should not be in between", DateUtils.isCalendarBetween(date1, date2, date2, false));
		
		date3="2014-06-01 01:00:00.0";
		assertFalse("Calendar should not be in between", DateUtils.isCalendarBetween(date1, date2, date3, false));
		assertFalse("Calendar should not be in between", DateUtils.isCalendarBetween(date1, date2, date3, true));
		
		assertFalse("Calendar should not be in between", DateUtils.isCalendarBetween(date1, date2, null, true));
		assertFalse("Calendar should not be in between", DateUtils.isCalendarBetween(null, date2, date3, true));
		assertFalse("Calendar should not be in between", DateUtils.isCalendarBetween(date1, null, date3, true));
		assertFalse("Calendar should not be in between", DateUtils.isCalendarBetween(null, null, date3, true));
	}

	@Test
	public void testGetSpanMinutesStringString() {
		String date1="2013-01-01 01:00:00.0";
		String date2="2013-01-01 02:00:00.0";
		
		int result=(int)DateUtils.getSpanMinutes(date1, date2);
		assertEquals("Time span is not correct", 60, result);
		result=(int)DateUtils.getSpanMinutes(date2, date1);
		assertEquals("Time span is not correct", 0, result);
		result=(int)DateUtils.getSpanMinutes(date1, null);
		assertEquals("Time span is not correct", 0, result);
		result=(int)DateUtils.getSpanMinutes(null, date2);
		assertEquals("Time span is not correct", 0, result);		
	}

	@Test
	public void testCompareToWidget() {
		
		assertEquals("Comparison Failed", 0, DateUtils.compareToWidget(date, time));
		
		time.setDay(time.getDay()+1);

		assertEquals("Comparison Failed", -1, DateUtils.compareToWidget(date, time));
		assertEquals("Comparison Failed", 1, DateUtils.compareToWidget(time, date));
		
		assertEquals("Comparison Failed", 0, DateUtils.compareToWidget(date, null));
		assertEquals("Comparison Failed", 0, DateUtils.compareToWidget(null, time));
		assertEquals("Comparison Failed", 0, DateUtils.compareToWidget(null, null));
		
		time.setDay(time.getDay()-1);
	}

	@Test
	public void testGetWeekStart() {
		Calendar cal=DateUtils.getWeekStart(false, null);
		int ws=weekstartMon;
		
		assertEquals("Week start dates should match", ws, cal.get(Calendar.DATE));
		
		cal=DateUtils.getWeekStart(false, DateUtils.calendarFromWidget(date, time));	
		assertEquals("Week start dates should match", ws, cal.get(Calendar.DATE));		
		
		cal=DateUtils.getWeekStart(true, null);		
		assertEquals("Week start dates should match", ws-1, cal.get(Calendar.DATE));
		
		cal=DateUtils.getWeekStart(true, DateUtils.calendarFromWidget(date, time));	
		assertEquals("Week start dates should match", ws-1, cal.get(Calendar.DATE));
		
	}

	@Test
	public void testGetWeekEnd() {
		
		Calendar cal=DateUtils.getWeekEnd(false, null);
		int ws=weekstartMon+6;
		
		assertEquals("Week start dates should match", ws, cal.get(Calendar.DATE));
		
		cal=DateUtils.getWeekEnd(false, DateUtils.calendarFromWidget(date, time));	
		assertEquals("Week start dates should match", ws, cal.get(Calendar.DATE));		
		
		cal=DateUtils.getWeekEnd(true, null);		
		assertEquals("Week start dates should match", ws-1, cal.get(Calendar.DATE));
		
		cal=DateUtils.getWeekEnd(true, DateUtils.calendarFromWidget(date, time));	
		assertEquals("Week start dates should match", ws-1, cal.get(Calendar.DATE));
	}

	@Test
	public void testGetWeekStartDay() {
		assertEquals("Week start dates should match", weekstartMon, DateUtils.getWeekStartDay(false, fulldatetime));
		assertEquals("Week start dates should match", weekstartMon-1, DateUtils.getWeekStartDay(true, fulldatetime));
		
		assertEquals("Week start dates should match", weekstartMon, DateUtils.getWeekStartDay(false, null));
		assertEquals("Week start dates should match", weekstartMon-1, DateUtils.getWeekStartDay(true, null));
	}

	@Test
	public void testGetWeekEndDay() {
		assertEquals("Week end dates should match", weekstartMon+6, DateUtils.getWeekEndDay(false, fulldatetime));
		assertEquals("Week end dates should match", weekstartMon+5, DateUtils.getWeekEndDay(true, fulldatetime));
		
		assertEquals("Week end dates should match", weekstartMon+6, DateUtils.getWeekEndDay(false, null));
		assertEquals("Week end dates should match", weekstartMon+5, DateUtils.getWeekEndDay(true, null));
	}

	@Test
	public void testGetWeekStartMonth() {
		assertEquals("Week start dates should match", weekstartMonth-1, DateUtils.getWeekStartMonth(false, fulldatetime));
		assertEquals("Week start dates should match", weekstartMonth-1, DateUtils.getWeekStartMonth(true, fulldatetime));
		
		assertEquals("Week start dates should match", weekstartMonth-1, DateUtils.getWeekStartMonth(false, null));
		assertEquals("Week start dates should match", weekstartMonth-1, DateUtils.getWeekStartMonth(true, null));
	}

	@Test
	public void testGetWeekEndMonth() {
		assertEquals("Week end dates should match", weekstartMonth-1, DateUtils.getWeekEndMonth(false, fulldatetime));
		assertEquals("Week end dates should match", weekstartMonth-1, DateUtils.getWeekEndMonth(true, fulldatetime));
		
		assertEquals("Week end dates should match", weekstartMonth-1, DateUtils.getWeekEndMonth(false, null));
		assertEquals("Week end dates should match", weekstartMonth-1, DateUtils.getWeekEndMonth(true, null));
	}

	@Test
	public void testGetWeekStartYear() {
		assertEquals("Week end dates should match", weekstartYear, DateUtils.getWeekStartYear(false, fulldatetime));
		assertEquals("Week end dates should match", weekstartYear, DateUtils.getWeekStartYear(true, fulldatetime));
		
		assertEquals("Week end dates should match", weekstartYear, DateUtils.getWeekStartYear(false, null));
		assertEquals("Week end dates should match", weekstartYear, DateUtils.getWeekStartYear(true, null));
	}

	@Test
	public void testGetWeekEndYear() {
		assertEquals("Week end dates should match", weekstartYear, DateUtils.getWeekEndYear(false, fulldatetime));
		assertEquals("Week end dates should match", weekstartYear, DateUtils.getWeekEndYear(true, fulldatetime));
		
		assertEquals("Week end dates should match", weekstartYear, DateUtils.getWeekEndYear(false, null));
		assertEquals("Week end dates should match", weekstartYear, DateUtils.getWeekEndYear(true, null));
	}

	@Test
	public void testDateToString() {
		assertEquals("Date strings should be equal", fulldatetime, DateUtils.DateToString(new Date()));
		assertNull("Date string should be null", DateUtils.DateToString(null));
	}

	@Test
	public void testIsCalendarBetweenDateDateStringStringBoolean() {
		Date start=new Date();
		Date end=new Date();
		end.setMonth(end.getMonth()+1);
		
		String date1=DateUtils.DateToString(start);
		String date2=DateUtils.DateToString(end);
		
		
		assertTrue("Calendar should be in between", DateUtils.isCalendarBetween(start, end, date1, date2,true));
		assertTrue("Calendar should be in between", DateUtils.isCalendarBetween(start, end, date1, null,true));
		assertTrue("Calendar should be in between", DateUtils.isCalendarBetween(start, end, null, date2,true));
		
		assertFalse("Calendar should be in between", DateUtils.isCalendarBetween(null, end, date1, date2,true));
		assertFalse("Calendar should be in between", DateUtils.isCalendarBetween(start, null, date1, date2,true));	
		
		assertFalse("Calendar should be in between", DateUtils.isCalendarBetween(start, end, date1, date2,false));
		assertFalse("Calendar should be in between", DateUtils.isCalendarBetween(start, end, date1, null,false));
		assertFalse("Calendar should be in between", DateUtils.isCalendarBetween(start, end, null, date2,false));
	}

	@Test
	public void testStringToDate() {
		assertNull("Date should be null", DateUtils.StringToDate(null));
		Date dt=DateUtils.StringToDate(fulldatetime);
		String ts=DateUtils.DateToString(dt);
		assertEquals("Dates should be equal", fulldatetime, ts);
	}

	@Test
	public void testGetSpanMinutesDateDate() {
		Date start=new Date();
		Date end=new Date();
		end.setHours(end.getHours()+1);
		int result=(int)DateUtils.getSpanMinutes(start, end);
		assertEquals("Span should be equal", 60, result);
		
		result=(int)DateUtils.getSpanMinutes(null, end);
		assertEquals("Span should be 0", 0, result);
		
		result=(int)DateUtils.getSpanMinutes(start, null);
		assertEquals("Span should be 0", 0, result);
	}

	@Test
	public void testGetTimeSpan() {
		
		ArrayList<Object> retval=DateUtils.getTimeSpan(null, null, null);
		assertEquals("Span length should be 0", 0, retval.size());
		
		retval=DateUtils.getTimeSpan("8", null, null);
		assertEquals("Span length should be 0", 0, retval.size());
		
		retval=DateUtils.getTimeSpan(null, "8", null);
		assertEquals("Span length should be 0", 0, retval.size());
		
		retval=DateUtils.getTimeSpan("10", "2", null);
		assertEquals("Span length should be 0", 0, retval.size());	
		
		retval=DateUtils.getTimeSpan("8", "9", null);
		assertEquals("Span length should be 0", 0, retval.size());
		
		retval=DateUtils.getTimeSpan("8", "9", "15");
		assertEquals("Span length should be 5", 5, retval.size());
		
		retval=DateUtils.getTimeSpan("8", "9", "1");
		assertEquals("Span length should be 61", 61, retval.size());
		
	}

	@Test
	public void testGetCurrentDateString() {
		assertEquals("Time should equal", new Date().toString(), DateUtils.getCurrentDateString());
	}


}
