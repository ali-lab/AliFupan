package NLTrade.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author a
 */
public class Jia {

    public static void main(String[] args) {

    }

    static SimpleDateFormat sdf = null;

    public static boolean isjia(boolean iseu, Date d) {
        if (sdf == null) {
            sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        }

        if (iseu) {
            return haveiteu(sdf.format(d));
        }
        return false;

    }

    static boolean haveiteu(String s) {
        if (s.equals("01/21/1999 10:00:00")) {
            return true;
        } else if (s.equals("03/16/1999 13:45:00")) {
            return true;
        } else if (s.equals("04/27/1999 12:45:00")) {
            return true;
        } else if (s.equals("05/10/1999 22:01:00")) {
            return true;
        } else if (s.equals("11/17/1999 05:00:00")) {
            return true;
        } else if (s.equals("11/29/1999 08:45:00")) {
            return true;
        } else if (s.equals("11/30/1999 18:15:00")) {
            return true;
        } else if (s.equals("02/08/1999 17:00:00")) {
            return true;
        } else if (s.equals("05/04/1999 10:30:00")) {
            return true;
        } else if (s.equals("07/12/2000 01:45:00")) {
            return true;
        } else if (s.equals("05/09/2001 20:45:00")) {
            return true;
        } else if (s.equals("05/17/2001 18:15:00")) {
            return true;
        } else if (s.equals("06/28/2001 06:00:00")) {
            return true;
        } else if (s.equals("07/11/2001 19:45:00")) {
            return true;
        } else if (s.equals("07/20/2001 15:30:00")) {
            return true;
        } else if (s.equals("08/03/2001 21:00:00")) {
            return true;
        } else if (s.equals("09/25/2001 13:30:00")) {
            return true;
        } else if (s.equals("09/25/2001 13:30:00")) {
            return true;
        } else if (s.equals("10/01/2001 05:30:00")) {
            return true;
        } else if (s.equals("01/15/2002 03:00:00")) {
            return true;
        } else if (s.equals("01/23/2002 13:00:00")) {
            return true;
        } else if (s.equals("04/09/2002 11:30:00")) {
            return true;
        } else if (s.equals("04/15/2002 21:30:00")) {
            return true;
        } else if (s.equals("04/23/2002 15:30:00")) {
            return true;
        } else if (s.equals("05/03/2002 10:45:00")) {
            return true;
        } else if (s.equals("09/27/2002 18:00:00")) {
            return true;
        } else if (s.equals("11/06/2002 12:15:00")) {
            return true;
        } else if (s.equals("04/20/2004 00:45:00")) {
            return true;
        } else if (s.equals("04/22/2004 11:45:00")) {
            return true;
        } else if (s.equals("04/26/2004 07:15:00")) {
            return true;
        } else if (s.equals("05/10/2005 15:15:00")) {
            return true;
        } else if (s.equals("05/11/2005 11:30:00")) {
            return true;
        } else if (s.equals("05/19/2005 21:00:00")) {
            return true;
        } else if (s.equals("05/19/2005 08:15:00")) {
            return true;
        } else if (s.equals("12/16/2005 06:45:00")) {
            return true;
        } else if (s.equals("01/18/2006 15:45:00")) {
            return true;
        } else if (s.equals("05/16/2006 12:30:00")) {
            return true;
        } else if (s.equals("05/29/2006 18:45:00")) {
            return true;
        } else if (s.equals("05/31/2006 10:45:00")) {
            return true;
        } else if (s.equals("06/26/2006 05:15:00")) {
            return true;
        } else if (s.equals("07/26/2006 16:30:00")) {
            return true;
        } else if (s.equals("08/09/2006 06:30:00")) {
            return true;
        } else if (s.equals("11/03/2006 19:00:00")) {
            return true;
        } else if (s.equals("02/12/2007 22:00:00")) {
            return true;
        } else if (s.equals("04/06/2007 09:00:00")) {
            return true;
        } else if (s.equals("04/04/2007 03:15:00")) {
            return true;
        } else if (s.equals("05/01/2007 07:45:00")) {
            return true;
        } else if (s.equals("07/30/2007 13:45:00")) {
            return true;
        } else if (s.equals("07/24/2007 22:18:00")) {
            return true;
        } else if (s.equals("08/08/2007 18:45:00")) {
            return true;
        } else if (s.equals("08/07/2007 12:46:00")) {
            return true;
        } else if (s.equals("11/28/2007 17:15:00")) {
            return true;
        } else if (s.equals("12/13/2007 10:00:00")) {
            return true;
        } else if (s.equals("01/16/2008 01:15:00")) {
            return true;
        } else if (s.equals("01/17/2008 17:00:00")) {
            return true;
        } else if (s.equals("04/22/2008 04:15:00")) {
            return true;
        } else if (s.equals("05/27/2008 17:30:00")) {
            return true;
        } else if (s.equals("05/19/2008 14:45:00")) {
            return true;
        } else if (s.equals("07/18/2008 15:15:00")) {
            return true;
        } else if (s.equals("07/21/2008 18:30:00")) {
            return true;
        } else if (s.equals("10/17/2008 15:30:00")) {
            return true;
        } else if (s.equals("02/10/2009 15:00:00")) {
            return true;
        } else if (s.equals("03/31/2009 16:00:00")) {
            return true;
        } else if (s.equals("03/30/2010 13:30:00")) {
            return true;
        } else if (s.equals("04/14/2010 21:30:00")) {
            return true;
        } else if (s.equals("04/28/2010 12:45:00")) {
            return true;
        } else if (s.equals("08/23/2011 15:15:00")) {
            return true;
        } else if (s.equals("08/23/2011 00:15:00")) {
            return true;
        } else if (s.equals("09/12/2011 16:00:00")) {
            return true;
        } else if (s.equals("09/06/2011 11:30:00")) {
            return true;
        } else if (s.equals("09/09/2014 14:45:00")) {
            return true;
        } else if (s.equals("09/08/2014 14:30:00")) {
            return true;
        } else if (s.equals("10/15/2014 09:30:00")) {
            return true;
        } else if (s.equals("10/27/2014 22:45:00")) {
            return true;
        } else if (s.equals("11/04/2014 20:00:00")) {
            return true;
        } else if (s.equals("11/18/2014 10:15:00")) {
            return true;
        } else if (s.equals("10/06/2014 11:30:00")) {
            return true;
        } else if (s.equals("09/24/2014 03:45:00")) {
            return true;
        } else if (s.equals("09/22/2014 16:30:00")) {
            return true;
        } else if (s.equals("02/24/2010 16:30:00")) {
            return true;
        } else if (s.equals("02/24/2010 10:00:00")) {
            return true;
        } else if (s.equals("07/20/2010 10:15:00")) {
            return true;
        } else if (s.equals("07/20/2010 03:45:00")) {
            return true;
        } else if (s.equals("08/31/2010 22:00:00")) {
            return true;
        } else if (s.equals("10/11/2010 12:30:00")) {
            return true;
        } else if (s.equals("11/21/2012 11:00:00")) {
            return true;
        } else if (s.equals("11/19/2012 01:15:00")) {
            return true;
        } else if (s.equals("11/16/2012 08:45:00")) {
            return true;
        } else if (s.equals("02/12/1999 12:45:00")) {
            return true;
        } else if (s.equals("09/16/2008 05:30:00")) {
            return true;
        } else if (s.equals("09/16/2008 05:00:00")) {
            return true;
        } else if (s.equals("09/16/2008 04:45:00")) {
            return true;
        } else if (s.equals("09/16/2008 19:15:00")) {
            return true;
        } else if (s.equals("09/17/2008 23:15:00")) {
            return true;
        } else if (s.equals("09/17/2008 22:30:00")) {
            return true;
        } else if (s.equals("09/17/2008 22:00:00")) {
            return true;
        } else if (s.equals("09/19/2008 14:30:00")) {
            return true;
        } else if (s.equals("09/19/2008 14:15:00")) {
            return true;
        } else if (s.equals("09/19/2008 13:45:00")) {
            return true;
        } else if (s.equals("09/22/2008 08:45:00")) {
            return true;
        } else if (s.equals("09/23/2008 11:30:00")) {
            return true;
        } else if (s.equals("09/24/2008 11:30:00")) {
            return true;
        } else if (s.equals("09/25/2008 09:15:00")) {
            return true;
        } else if (s.equals("09/30/2008 08:00:00")) {
            return true;
        } else if (s.equals("10/08/2008 14:15:00")) {
            return true;
        } else if (s.equals("10/13/2008 07:45:00")) {
            return true;
        } else if (s.equals("10/15/2008 01:00:00")) {
            return true;
        } else if (s.equals("10/14/2008 22:30:00")) {
            return true;
        } else if (s.equals("10/14/2008 21:15:00")) {
            return true;
        } else if (s.equals("10/15/2008 17:30:00")) {
            return true;
        } else if (s.equals("10/15/2008 17:00:00")) {
            return true;
        } else if (s.equals("10/15/2008 16:45:00")) {
            return true;
        } else if (s.equals("10/16/2008 18:45:00")) {
            return true;
        } else if (s.equals("10/17/2008 09:00:00")) {
            return true;
        } else if (s.equals("10/20/2008 03:00:00")) {
            return true;
        } else if (s.equals("10/20/2008 01:45:00")) {
            return true;
        } else if (s.equals("10/17/2008 21:30:00")) {
            return true;
        } else if (s.equals("10/24/2008 17:15:00")) {
            return true;
        } else if (s.equals("10/24/2008 17:00:00")) {
            return true;
        } else if (s.equals("07/23/2010 17:30:00")) {
            return true;
        } else if (s.equals("07/23/2010 16:45:00")) {
            return true;
        } else if (s.equals("07/27/2010 16:15:00")) {
            return true;
        } else if (s.equals("07/27/2010 16:00:00")) {
            return true;
        } else if (s.equals("07/29/2010 00:15:00")) {
            return true;
        } else if (s.equals("07/30/2010 12:00:00")) {
            return true;
        } else if (s.equals("07/30/2010 11:45:00")) {
            return true;
        } else if (s.equals("07/30/2010 10:45:00")) {
            return true;
        } else if (s.equals("08/09/2010 02:30:00")) {
            return true;
        } else if (s.equals("08/20/2010 09:45:00")) {
            return true;
        } else if (s.equals("08/23/2010 14:30:00")) {
            return true;
        } else if (s.equals("08/25/2010 10:00:00")) {
            return true;
        } else if (s.equals("08/25/2010 09:15:00")) {
            return true;
        } else if (s.equals("08/26/2010 14:30:00")) {
            return true;
        } else if (s.equals("08/27/2010 16:45:00")) {
            return true;
        } else if (s.equals("08/31/2010 02:15:00")) {
            return true;
        } else if (s.equals("08/30/2010 20:45:00")) {
            return true;
        } else if (s.equals("09/01/2010 08:45:00")) {
            return true;
        } else if (s.equals("09/01/2010 05:45:00")) {
            return true;
        } else if (s.equals("09/01/2010 04:15:00")) {
            return true;
        } else if (s.equals("09/02/2010 15:45:00")) {
            return true;
        } else if (s.equals("09/02/2010 14:45:00")) {
            return true;
        } else if (s.equals("09/08/2010 16:45:00")) {
            return true;
        } else if (s.equals("09/20/2010 08:45:00")) {
            return true;
        } else if (s.equals("09/20/2010 03:00:00")) {
            return true;
        } else if (s.equals("09/17/2010 17:30:00")) {
            return true;
        } else if (s.equals("09/23/2010 15:15:00")) {
            return true;
        } else if (s.equals("09/23/2010 15:00:00")) {
            return true;
        } else if (s.equals("09/23/2010 13:15:00")) {
            return true;
        } else if (s.equals("09/24/2010 07:00:00")) {
            return true;
        } else if (s.equals("09/28/2010 14:30:00")) {
            return true;
        } else if (s.equals("10/08/2010 15:00:00")) {
            return true;
        } else if (s.equals("10/15/2010 16:00:00")) {
            return true;
        } else if (s.equals("10/15/2010 15:45:00")) {
            return true;
        } else if (s.equals("10/15/2010 15:30:00")) {
            return true;
        } else if (s.equals("10/19/2010 13:30:00")) {
            return true;
        } else if (s.equals("10/19/2010 13:15:00")) {
            return true;
        } else if (s.equals("10/19/2010 12:45:00")) {
            return true;
        } else if (s.equals("04/29/2013 08:15:00")) {
            return true;
        } else if (s.equals("04/29/2013 00:00:00")) {
            return true;
        } else if (s.equals("04/26/2013 15:15:00")) {
            return true;
        } else if (s.equals("04/30/2013 09:45:00")) {
            return true;
        } else if (s.equals("05/01/2013 18:15:00")) {
            return true;
        } else if (s.equals("05/03/2013 17:30:00")) {
            return true;
        } else if (s.equals("05/07/2013 13:00:00")) {
            return true;
        } else if (s.equals("05/07/2013 12:15:00")) {
            return true;
        } else if (s.equals("05/07/2013 03:15:00")) {
            return true;
        } else if (s.equals("05/08/2013 11:00:00")) {
            return true;
        } else if (s.equals("05/08/2013 08:15:00")) {
            return true;
        } else if (s.equals("05/07/2013 23:30:00")) {
            return true;
        } else if (s.equals("05/21/2013 03:30:00")) {
            return true;
        } else if (s.equals("05/21/2013 13:15:00")) {
            return true;
        } else if (s.equals("05/21/2013 11:00:00")) {
            return true;
        } else if (s.equals("05/24/2013 10:15:00")) {
            return true;
        } else if (s.equals("05/28/2013 02:15:00")) {
            return true;
        } else if (s.equals("05/27/2013 19:15:00")) {
            return true;
        } else if (s.equals("05/28/2013 13:30:00")) {
            return true;
        } else if (s.equals("05/28/2013 11:30:00")) {
            return true;
        } else if (s.equals("05/28/2013 11:15:00")) {
            return true;
        } else if (s.equals("06/03/2013 03:00:00")) {
            return true;
        } else if (s.equals("05/31/2013 21:15:00")) {
            return true;
        } else if (s.equals("05/31/2013 19:15:00")) {
            return true;
        } else if (s.equals("06/05/2013 02:30:00")) {
            return true;
        } else if (s.equals("06/05/2013 09:30:00")) {
            return true;
        } else if (s.equals("06/07/2013 18:00:00")) {
            return true;
        } else if (s.equals("06/07/2013 16:45:00")) {
            return true;
        } else if (s.equals("06/13/2013 16:30:00")) {
            return true;
        } else if (s.equals("06/17/2013 02:00:00")) {
            return true;
        } else if (s.equals("06/14/2013 18:00:00")) {
            return true;
        } else if (s.equals("06/17/2013 21:30:00")) {
            return true;
        } else if (s.equals("06/17/2013 10:30:00")) {
            return true;
        } else if (s.equals("06/18/2013 13:45:00")) {
            return true;
        } else if (s.equals("06/24/2013 20:45:00")) {
            return true;
        } else if (s.equals("07/01/2013 09:45:00")) {
            return true;
        } else if (s.equals("07/01/2013 09:00:00")) {
            return true;
        } else if (s.equals("07/04/2013 10:30:00")) {
            return true;
        } else if (s.equals("07/12/2013 09:45:00")) {
            return true;
        } else if (s.equals("07/12/2013 07:15:00")) {
            return true;
        } else if (s.equals("07/12/2013 00:45:00")) {
            return true;
        } else if (s.equals("07/15/2013 05:00:00")) {
            return true;
        } else if (s.equals("07/15/2013 14:45:00")) {
            return true;
        } else if (s.equals("07/15/2013 13:45:00")) {
            return true;
        } else if (s.equals("07/18/2013 14:45:00")) {
            return true;
        } else if (s.equals("07/18/2013 13:00:00")) {
            return true;
        } else if (s.equals("07/18/2013 11:00:00")) {
            return true;
        } else if (s.equals("07/25/2013 14:45:00")) {
            return true;
        } else if (s.equals("08/05/2013 16:15:00")) {
            return true;
        } else if (s.equals("08/05/2013 13:45:00")) {
            return true;
        } else if (s.equals("08/05/2013 23:15:00")) {
            return true;
        } else if (s.equals("08/07/2013 11:45:00")) {
            return true;
        } else if (s.equals("08/07/2013 13:30:00")) {
            return true;
        } else if (s.equals("08/12/2013 09:30:00")) {
            return true;
        } else if (s.equals("08/12/2013 00:00:00")) {
            return true;
        } else if (s.equals("08/09/2013 16:00:00")) {
            return true;
        } else if (s.equals("08/14/2013 10:15:00")) {
            return true;
        } else if (s.equals("09/05/2013 07:30:00")) {
            return true;
        } else if (s.equals("09/05/2013 01:45:00")) {
            return true;
        } else if (s.equals("09/06/2013 16:00:00")) {
            return true;
        } else if (s.equals("09/06/2013 15:45:00")) {
            return true;
        } else if (s.equals("09/06/2013 15:15:00")) {
            return true;
        } else if (s.equals("09/09/2013 16:30:00")) {
            return true;
        } else if (s.equals("09/25/2013 17:30:00")) {
            return true;
        } else if (s.equals("09/25/2013 11:30:00")) {
            return true;
        } else if (s.equals("09/24/2013 16:15:00")) {
            return true;
        } else if (s.equals("09/27/2013 14:00:00")) {
            return true;
        } else if (s.equals("09/27/2013 10:45:00")) {
            return true;
        } else if (s.equals("09/26/2013 16:15:00")) {
            return true;
        } else {
            return false;
        }

    }

}
