package NLTrade.util;
import NLTrade.Model.Frame;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class KUtil {

    public static Date getKm1(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();

        c.setTime(d);

        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    public static Date getKm5(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();

        c.setTime(d);
        int min = c.get(Calendar.MINUTE);
        if (min >= 0 && min < 5) {
            min = 0;
        } else if (min >= 5 && min < 10) {
            min = 5;
        } else if (min >= 10 && min < 15) {
            min = 10;
        } else if (min >= 15 && min < 20) {
            min = 15;
        } else if (min >= 20 && min < 25) {
            min = 20;
        } else if (min >= 25 && min < 30) {
            min = 25;
        } else if (min >= 30 && min < 35) {
            min = 30;
        } else if (min >= 35 && min < 40) {
            min = 35;
        } else if (min >= 40 && min < 45) {
            min = 40;
        } else if (min >= 45 && min < 50) {
            min = 45;
        } else if (min >= 50 && min < 55) {
            min = 50;
        } else if (min >= 55) {
            min = 55;
        }

        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    public static Date getKm15(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();

        c.setTime(d);
        int min = c.get(Calendar.MINUTE);
        if (min >= 0 && min < 15) {
            min = 0;
        } else if (min >= 15 && min < 30) {
            min = 15;
        } else if (min >= 30 && min < 45) {
            min = 30;
        } else if (min >= 45) {
            min = 45;
        }

        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    public static Date getKm30(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();

        c.setTime(d);
        int min = c.get(Calendar.MINUTE);
        if (min >= 0 && min < 30) {
            min = 0;
        } else if (min >= 30) {
            min = 30;
        }

        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    public static Date getKh4(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();

        c.setTime(d);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 4) {
            hour = 0;
        } else if (hour >= 4 && hour < 8) {
            hour = 4;
        } else if (hour >= 8 && hour < 12) {
            hour = 8;
        } else if (hour >= 12 && hour < 16) {
            hour = 12;
        } else if (hour >= 16 && hour < 20) {
            hour = 16;
        } else if (hour >= 20) {
            hour = 20;
        }
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    public static Date getKh1(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();

        c.setTime(d);

        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    public static Date getKd1(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();

        c.setTime(d);

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    
    
    public static Frame getFrameByInt(int x)
    {
        switch (x) {
            case 1:
                return(Frame.M1);
            case 5:
                return(Frame.M5);
            case 15:
                return(Frame.M15);
            case 30:
                return(Frame.M30);
            case 60:
                return(Frame.H1);
            case 240:
                return(Frame.H4);
            case 1440:
                return(Frame.D1);

        }
        
        return null ;
        
    }
}
