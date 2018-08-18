package mystical.cup.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Rzc on 2018/8/23.
 */
public class Utility{
    public static Logger logger = LoggerFactory.getLogger(Utility.class);
    public static Date getToday23HourDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        logger.debug("getToday23HourDate, date:{}", sdf.format(cal.getTime()));
        return cal.getTime();
    }

}
