package oit.is.uno.auction.model;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateInit {
  public String getTomorrowDate() {
    Date today = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(today);
    cal.add(Calendar.DAY_OF_MONTH, 1);
    String date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

    return date;
  }
}
