package com.gzlabs.utils.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.GregorianCalendar;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gzlabs.utils.ICalUtils;

public class TestICalUtils {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateCalendar() {
		Calendar cal=ICalUtils.createCalendar();
		assertNotNull("Calendar should be initialized", cal);
	}

	@Test
	public void testMakeICal() {
		assertFalse("Result should be false", ICalUtils.makeICal(null, null));
		assertFalse("Result should be false", ICalUtils.makeICal("test.ics", null));
	}

	@Test
	public void testCreateEvent() {
		java.util.Calendar start=new GregorianCalendar();
		java.util.Calendar end=new GregorianCalendar();
		end.add(java.util.Calendar.HOUR,2);
		String title="Test Title";
		String location="Test location";
		
		VEvent event=ICalUtils.createEvent(start, end, title, location);
		assertNotNull("Event should be initialized", event);
		
		event=ICalUtils.createEvent(start, end, title, null);
		assertNotNull("Event should be initialized", event);
		
		event=ICalUtils.createEvent(start, end, null, null);
		assertNotNull("Event should be initialized", event);
		
		event=ICalUtils.createEvent(start, null, null, null);
		assertNull("Event should not be initialized", event);
		
		event=ICalUtils.createEvent(null, null, null, null);
		assertNull("Event should not be initialized", event);

		
	}

	@Test
	public void testSaveCalendar() {
		Calendar cal=ICalUtils.createCalendar();
		String path="test.ics";
		java.util.Calendar start=new GregorianCalendar();
		java.util.Calendar end=new GregorianCalendar();
		end.add(java.util.Calendar.HOUR,2);
		String title="Test Title";
		String location="Test location";
		VEvent event=ICalUtils.createEvent(start, end, title, location);
		cal.getComponents().add(event);
		
		ICalUtils.saveCalendar(path, cal);
		File file=new File(path);
		
		assertTrue("File doesn't exist", file.isFile());
		assertTrue("File is empty", file.length()>0);
		
		file.delete();
		file=new File(path);
		ICalUtils.saveCalendar(null, cal);
		assertFalse("File exists", file.isFile());
		ICalUtils.saveCalendar(path, null);
		assertFalse("File exists", file.isFile());
		
	}

}
