package com.gzlabs.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

/**
 * Performs ical calendar generation tasks
 * 
 * @author apavlune
 * 
 */
public class ICalUtils {

	/**
	 * Product id string.
	 */
	private static String productID = "-//GZ Labs//Employee Calendar//EN";

	/**
	 * Initializes the calendar.
	 */
	public static Calendar createCalendar() {
		Calendar calendar = new Calendar();
		calendar.getProperties().add(new ProdId(productID));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
		return calendar;
	}
	
	public static boolean makeICal(String path, ResultSet rs)
	{
		if (rs != null && path!=null) {
			try {
				Calendar calendar=createCalendar();
				ArrayList<VEvent> events=new ArrayList<VEvent>();
				
				java.util.Calendar startdate = new GregorianCalendar();
				java.util.Calendar enddate = new GregorianCalendar();
				
				while (rs.next()) {
					java.util.Date sdate = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss", Locale.ENGLISH).parse(rs
							.getString("DUTY_START_TIME"));
					java.util.Date edate = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss", Locale.ENGLISH).parse(rs
							.getString("DUTY_END_TIME"));
					startdate.setTime(sdate);
					enddate.setTime(edate);
					
					VEvent event=createEvent(startdate, enddate,
							rs.getString("PERSON_NAME"),
							rs.getString("PLACE_NAME"));
					VEvent dup = isDuplicate(event, events);
					events.add(dup == null ? event : dup);
				}
				
				for (VEvent ev : events) {
					calendar.getComponents().add(ev);
				}

				saveCalendar(path, calendar);
				return true;

			} catch (java.sql.SQLException | ParseException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Adds new event to the calendar.
	 * 
	 * @param start
	 *            Event start date/time.
	 * @param end
	 *            Event end date/time.
	 * @param title
	 *            Title of the event.
	 * @param location
	 *            Location of the event.
	 * @param combine
	 *            Whether to combine daily duplicates
	 */
	public static VEvent createEvent(java.util.Calendar start, java.util.Calendar end,
			String title, String location) {

		// copy the parameters for local operations
		java.util.Calendar l_start = start;
		java.util.Calendar l_end = end;

		int endint = l_end.get(java.util.Calendar.HOUR);
		if (endint == 0) {
			l_end.set(java.util.Calendar.HOUR, 12);
		}
		// time zone initialization
		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance()
				.createRegistry();
		TimeZone timezone = registry.getTimeZone("America/New_York");
		VTimeZone tz = timezone.getVTimeZone();

		// set the time zone and convert time format to ical.
		l_start.setTimeZone(timezone);
		l_end.setTimeZone(timezone);
		DateTime dtstart = new DateTime(l_start.getTime());
		DateTime dtend = new DateTime(l_end.getTime());

		// create new event
		VEvent event = new VEvent(dtstart, dtend, title);
		event.getProperties().add(tz.getTimeZoneId());
		event.getProperties().add(new Location(location));

		// generate unique identifier..
		UidGenerator ug;
		try {
			ug = new UidGenerator("uidGen");
			Uid uid = ug.generateUid();
			event.getProperties().add(uid);
		} catch (SocketException e) {
			e.printStackTrace();
		}

	/*	if (combine) {
			VEvent dup = isDuplicate(event, events);
			return dup == null ? event : dup;
		} else {
			return event;
		}*/
		return event;
	}

	/**
	 * Checks if a duplicate event exits in the calendar
	 * 
	 * @param v
	 *            Event to search
	 * @return returns null if there is no duplicate, or event if one is found
	 */
	private static VEvent isDuplicate(VEvent v, ArrayList<VEvent> events) {
		VEvent ret = null;
		
		for (int i = 0; i < events.size(); i++) {
			VEvent l_ev = events.get(i);

			if (l_ev.getSummary().equals(v.getSummary())) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				try {
					Date vfullstart = v.getStartDate().getDate();
					Date evfullstart = l_ev.getStartDate().getDate();
					Date vfullend = v.getEndDate().getDate();
					Date evfullend = l_ev.getEndDate().getDate();

					Date vstart = new Date(sdf.parse(sdf.format(vfullstart)));
					Date evstart = new Date(sdf.parse(sdf.format(evfullstart)));

					if (vstart.equals(evstart)) {
						Date retstart = vfullstart.before(evfullstart) ? vfullstart
								: evfullstart;
						Date retend = vfullend.before(evfullend) ? evfullend
								: vfullend;
						ret = new VEvent(retstart, retend, v.getSummary()
								.getValue());
						ret.getProperties().add(v.getProperty("Tzid"));
						UidGenerator ug;
						try {
							ug = new UidGenerator("uidGen");
							Uid uid = ug.generateUid();
							ret.getProperties().add(uid);
						} catch (SocketException e) {
							e.printStackTrace();
						}
						events.remove(i);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		return ret;
	}


	/**
	 * Saves calendar in a file
	 * 
	 * @param filename
	 *            path to file.
	 */
	public static void saveCalendar(String filename, Calendar calendar) {
		if (calendar != null) {
			FileOutputStream fout = null;
			try {
				fout = new FileOutputStream(filename);
				CalendarOutputter outputter = new CalendarOutputter();
				outputter.output(calendar, fout);
			} catch (IOException | ValidationException e) {
				e.printStackTrace();
			} finally {
				if (fout != null)
					try {
						fout.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}

}
