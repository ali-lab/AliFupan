package NLTrade.indicate;



import java.util.List;

import NLTrade.Model.OHLC;
import NLTrade.Model.Tick;
import NLTrade.Model.TrendLine;
import alilibs.AliLog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ZigZag {

    private double price_l = 0.0;
    private Date date_l;
    private double price_h = 0.0;
    private Date date_h;
    private int direct = 0;

    private List<TrendLine> list;

    public ZigZag() {
        list = new ArrayList<TrendLine>();
    }

////////////////    
    public void cal(Tick t, double bofu, SimpleDateFormat sdf) {

        if (price_h == 0 || t.getPrice() > price_h) {
            price_h = t.getPrice();
            date_h = t.getTime();

        }

        if (price_l == 0 || t.getPrice() < price_l) {
            price_l = t.getPrice();
            date_l = t.getTime();

        }
        if (date_h.getTime() > date_l.getTime()) {

            direct = 1;
        } else if (date_h.getTime() < date_l.getTime()) {
            direct = -1;
        }

        if (bofu == 0) {
            return;
        }
        //AliLog.info(this, t.getPrice() + " " + sdf.format(t.getTime()) + "  \n" + price_l + "  " + sdf.format(date_l) + "  \n" + price_h + "  " + sdf.format(date_h) + "---------------\n");

        if (direct == 1 && price_h - t.getPrice() > bofu) {
            //direct = -1 ;
            list.add(new TrendLine(date_h, price_h, date_l, price_l));
            price_l = t.getPrice();
            date_l = t.getTime();
            //AliLog.info(this, "new low->"+price_l+"  "+sdf.format(date_l)+"  high->"+price_h+"  "+sdf.format(date_h));

        } else if (direct == -1 && t.getPrice() - price_l > bofu) {
            //direct = 1 ;
            list.add(new TrendLine(date_h, price_h, date_l, price_l));
            price_h = t.getPrice();
            date_h = t.getTime();
            //AliLog.info(this, "new high->"+price_h+"  "+sdf.format(date_h)+" low->"+price_l+"  "+sdf.format(date_l));

        }

        //AliLog.println(price_h+" "+price_l+" "+direct + "  "+bofu);
    }

    public double getHigh() {
        return price_h;
    }

    public double getLow() {
        return price_l;
    }

    public int getDirect() {
        return direct;
    }

    public List<TrendLine> getList() {
        return list;
    }

    /**
     * @return the price236
     */
    public double getPrice236() {

        double price236 = 0;
        if (direct == 1) {
            price236 = price_h - (price_h - price_l) * 0.236;
        } else if (direct == -1) {
            price236 = price_l + (price_h - price_l) * 0.236;
        }

        return price236;
    }

    /**
     * @return the price382
     */
    public double getPrice382() {
        double price382 = 0;
        if (direct == 1) {
            price382 = price_h - (price_h - price_l) * 0.382;
        } else if (direct == -1) {
            price382 = price_l + (price_h - price_l) * 0.382;
        }
        return price382;
    }

    /**
     * @return the price618
     */
    public double getPrice618() {
        double price618 = 0;
        if (direct == 1) {
            price618 = price_h - (price_h - price_l) * 0.618;
        } else if (direct == -1) {
            price618 = price_l + (price_h - price_l) * 0.618;
        }
        return price618;
    }

}
