package NLTrade;





import NLTrade.Model.Frame;
import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import NLTrade.Model.OHLC;
import NLTrade.Model.Symbol;
import NLTrade.Model.TickM;
import NLTrade.util.Config;
import NLTrade.util.ConsoleTable;
import NLTrade.util.KUtil;
import alilibs.AliLog;
import java.text.DecimalFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;

public class KLineMM {

    private List<TickM> prices = null;

    //private boolean isReal;

        private int currentprocess = 0;
    private int lastprocess;
    protected Config normalConfig;
    Symbol symbol ;
    int index ;
    List<TickM> dataList ;
    /**
     * 
     * @param x 这个是初始化需要组装的K线周期
     * @param symbol 货币对
     * @param index 在第几个
     * @param dataList 多货币
     */
    public KLineMM(Frame[] x, Symbol symbol,int index,List<TickM> dataList) {
        normalConfig = new Config("../config/load.cfgx","nomal");
        this.symbol = symbol ;
        this.index = index ;
        this.dataList = dataList ;
        for (int i = 0; i < x.length; i++) {
            if (x[i] == Frame.M1) {
                m1 = new ArrayList<OHLC>();
            } else if (x[i] == Frame.M5) {
                m5 = new ArrayList<OHLC>();
            } else if (x[i] == Frame.M15) {
                m15 = new ArrayList<OHLC>();
            } else if (x[i] == Frame.M30) {
                m30 = new ArrayList<OHLC>();
            } else if (x[i] == Frame.H1) {
                h1 = new ArrayList<OHLC>();
            } else if (x[i] == Frame.H4) {
                h4 = new ArrayList<OHLC>();
            } else if (x[i] == Frame.D1) {
                d1 = new ArrayList<OHLC>();
            }

        }

    }

    public List<OHLC> getCurrentFrame(int frame) {

        switch (frame) {
            case 1:
                return m1;
            case 5:
                return m5;
            case 15:
                return m15;
            case 30:
                return m30;
            case 60:
                return h1;
            case 240:
                return h4;
            case 1440:
                return d1;

            default:
                return null;
        }

    }

    public List<OHLC> getM1() {
        return m1;
    }

    public List<OHLC> getM5() {
        return m5;
    }

    public List<OHLC> getM15() {
        return m15;
    }

    public List<OHLC> getM30() {
        return m30;
    }

    public List<OHLC> getH1() {
        return h1;
    }

    public List<OHLC> getH4() {
        return h4;
    }

    public List<OHLC> getD1() {
        return d1;
    }

    private List<OHLC> m1 = null;
    private List<OHLC> m5 = null;
    private List<OHLC> m15 = null;
    private List<OHLC> m30 = null;
    private List<OHLC> h1 = null;
    private List<OHLC> h4 = null;
    private List<OHLC> d1 = null;

    public List<TickM> getPrices() {

        return prices;
    }

    

    public void loadM30(TickM t) {

        Date time = t.getTime()[index];
        if (m30.size() == 0 || KUtil.getKm30(time).getTime() != m30.get(0).getDate().getTime()) {

            OHLC ohlc = new OHLC();
            ohlc.setOpen(t.getPrice()[index]);
            ohlc.setHigh(t.getPrice()[index]);
            ohlc.setLow(t.getPrice()[index]);
            ohlc.setClose(t.getPrice()[index]);
            ohlc.setDate(KUtil.getKm30(time));
            ohlc.setVolumn(0);
            if (m30.size() == 0) {
                m30.add(ohlc);
            } else {
                m30.add(0, ohlc);
            }

            if (m30.size() > 500) {
                m30.remove(m30.size() - 1);
            }

        } else {
            OHLC o = m30.get(0);
            if (t.getPrice()[index] > o.getHigh()) {
                o.setHigh(t.getPrice()[index]);
            }
            if (t.getPrice()[index] < o.getLow()) {
                o.setLow(t.getPrice()[index]);
            }
            o.setClose(t.getPrice()[index]);
            o.setVolumn(o.getVolumn() + 1);

        }
    }

    public void load(TickM t) {
        if (m1 != null) {
            loadM1(t);
        }
        if (m5 != null) {
            loadM5(t);
        }
        if (m15 != null) {
            loadM15(t);
        }
        if (m30 != null) {
            loadM30(t);
        }
        if (h1 != null) {
            loadH1(t);
        }
        if (h4 != null) {
            loadH4(t);
        }
        if (d1 != null) {
            loadD1(t);
        }

    }

    public void loadM15(TickM t) {

        Date time = t.getTime()[index];
        if (m15.size() == 0 || KUtil.getKm15(time).getTime() != m15.get(0).getDate().getTime()) {

            OHLC ohlc = new OHLC();
            ohlc.setOpen(t.getPrice()[index]);
            ohlc.setHigh(t.getPrice()[index]);
            ohlc.setLow(t.getPrice()[index]);
            ohlc.setClose(t.getPrice()[index]);
            ohlc.setDate(KUtil.getKm15(time));
            ohlc.setVolumn(0);
            if (m15.size() == 0) {
                m15.add(ohlc);
            } else {
                m15.add(0, ohlc);
            }

            if (m15.size() > 500) {
                m15.remove(m15.size() - 1);
            }

        } else {
            OHLC o = m15.get(0);
            if (t.getPrice()[index] > o.getHigh()) {
                o.setHigh(t.getPrice()[index]);
            }
            if (t.getPrice()[index] < o.getLow()) {
                o.setLow(t.getPrice()[index]);
            }
            o.setClose(t.getPrice()[index]);
            o.setVolumn(o.getVolumn() + 1);

        }
    }

    public void loadM5(TickM t) {

        Date time = t.getTime()[index];
        if (m5.size() == 0 || KUtil.getKm5(time).getTime() != m5.get(0).getDate().getTime()) {

            OHLC ohlc = new OHLC();
            ohlc.setOpen(t.getPrice()[index]);
            ohlc.setHigh(t.getPrice()[index]);
            ohlc.setLow(t.getPrice()[index]);
            ohlc.setClose(t.getPrice()[index]);
            ohlc.setDate(KUtil.getKm5(time));
            ohlc.setVolumn(0);
            if (m5.size() == 0) {
                m5.add(ohlc);
            } else {
                m5.add(0, ohlc);
            }

            if (m5.size() > 500) {
                m5.remove(m5.size() - 1);
            }

        } else {
            OHLC o = m5.get(0);
            if (t.getPrice()[index] > o.getHigh()) {
                o.setHigh(t.getPrice()[index]);
            }
            if (t.getPrice()[index] < o.getLow()) {
                o.setLow(t.getPrice()[index]);
            }
            o.setClose(t.getPrice()[index]);
            o.setVolumn(o.getVolumn() + 1);

        }
    }

    public void loadM1(TickM t) {

        Date time = t.getTime()[index];
        if (m1.size() == 0 || KUtil.getKm1(time).getTime() != m1.get(0).getDate().getTime()) {

            OHLC ohlc = new OHLC();
            ohlc.setOpen(t.getPrice()[index]);
            ohlc.setHigh(t.getPrice()[index]);
            ohlc.setLow(t.getPrice()[index]);
            ohlc.setClose(t.getPrice()[index]);
            ohlc.setDate(KUtil.getKm1(time));
            ohlc.setVolumn(0);
            if (m1.size() == 0) {
                m1.add(ohlc);
            } else {
                m1.add(0, ohlc);
            }

            if (m1.size() > 500) {
                m1.remove(m1.size() - 1);
            }

        } else {
            OHLC o = m1.get(0);
            if (t.getPrice()[index] > o.getHigh()) {
                o.setHigh(t.getPrice()[index]);
            }
            if (t.getPrice()[index] < o.getLow()) {
                o.setLow(t.getPrice()[index]);
            }
            o.setClose(t.getPrice()[index]);
            o.setVolumn(o.getVolumn() + 1);

        }
    }

    public void loadD1(TickM t) {

        Date time = t.getTime()[index];
        if (d1.size() == 0 || KUtil.getKd1(time).getTime() != d1.get(0).getDate().getTime()) {

            OHLC ohlc = new OHLC();
            ohlc.setOpen(t.getPrice()[index]);
            ohlc.setHigh(t.getPrice()[index]);
            ohlc.setLow(t.getPrice()[index]);
            ohlc.setClose(t.getPrice()[index]);
            ohlc.setDate(KUtil.getKd1(time));
            ohlc.setVolumn(0);
            if (d1.size() == 0) {
                d1.add(ohlc);
            } else {
                d1.add(0, ohlc);
            }

            if (d1.size() > 500) {
                d1.remove(d1.size() - 1);
            }

        } else {
            OHLC o = d1.get(0);
            if (t.getPrice()[index] > o.getHigh()) {
                o.setHigh(t.getPrice()[index]);
            }
            if (t.getPrice()[index] < o.getLow()) {
                o.setLow(t.getPrice()[index]);
            }
            o.setClose(t.getPrice()[index]);
            o.setVolumn(o.getVolumn() + 1);

        }
    }

    public void loadH4(TickM t) {

        Date time = t.getTime()[index];
        if (h4.size() == 0 || KUtil.getKh4(time).getTime() != h4.get(0).getDate().getTime()) {

            OHLC ohlc = new OHLC();
            ohlc.setOpen(t.getPrice()[index]);
            ohlc.setHigh(t.getPrice()[index]);
            ohlc.setLow(t.getPrice()[index]);
            ohlc.setClose(t.getPrice()[index]);
            ohlc.setDate(KUtil.getKh4(time));
            ohlc.setVolumn(0);
            if (h4.size() == 0) {
                h4.add(ohlc);
            } else {
                h4.add(0, ohlc);
            }

            if (h4.size() > 500) {
                h4.remove(h4.size() - 1);
            }

        } else {
            OHLC o = h4.get(0);
            if (t.getPrice()[index] > o.getHigh()) {
                o.setHigh(t.getPrice()[index]);
            }
            if (t.getPrice()[index] < o.getLow()) {
                o.setLow(t.getPrice()[index]);
            }
            o.setClose(t.getPrice()[index]);
            o.setVolumn(o.getVolumn() + 1);

        }
    }

    public void loadH1(TickM t) {

        Date time = t.getTime()[index];
        if (h1.size() == 0 || KUtil.getKh1(time).getTime() != h1.get(0).getDate().getTime()) {

            OHLC ohlc = new OHLC();
            ohlc.setOpen(t.getPrice()[index]);
            ohlc.setHigh(t.getPrice()[index]);
            ohlc.setLow(t.getPrice()[index]);
            ohlc.setClose(t.getPrice()[index]);
            ohlc.setDate(KUtil.getKh1(time));
            ohlc.setVolumn(0);
            if (h1.size() == 0) {
                h1.add(ohlc);
            } else {
                h1.add(0, ohlc);
            }

            if (h1.size() > 500) {
                h1.remove(h1.size() - 1);
            }

        } else {
            OHLC o = h1.get(0);
            if (t.getPrice()[index] > o.getHigh()) {
                o.setHigh(t.getPrice()[index]);
            }
            if (t.getPrice()[index] < o.getLow()) {
                o.setLow(t.getPrice()[index]);
            }
            o.setClose(t.getPrice()[index]);
            o.setVolumn(o.getVolumn() + 1);

        }
    }

}
