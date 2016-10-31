package NLTrade.Strategy.king;



import NLTrade.Model.Frame;
import NLTrade.Model.OHLCType;

import NLTrade.Model.Tick;
import NLTrade.Model.Trades;
import NLTrade.Model.TradeType;
import NLTrade.Model.TrendLine;
import NLTrade.Strategy.AStrategy;
import NLTrade.indicate.Highest;
import NLTrade.indicate.Lowest;
import NLTrade.indicate.MovingV;
import NLTrade.indicate.ZigZag;
import NLTrade.util.Config;
import NLTrade.util.Jia;
import NLTrade.util.KUtil;
import NLTrade.util.TPL;
import alilibs.AliLog;
import java.util.ArrayList;

import java.util.List;

/*
 1 20
 1 20
 2 20

 3 48 
 3 24 
 6 12

 9 63
 9 32
 18 16

 27 68
 27 34
 54 17
 */
public class King extends AStrategy {

    MovingV D1MV_H;
    MovingV D1MV_L;
    double daybofu = 0.0;
    //ZigZag zz;
    Highest h;
    Lowest l;
    Config cfg;
    int mainFrame;
    final static String otherFrame = "1,5,15,30,60";

    public King() {
        super();
        cfg = new Config("../config/load.cfgx","king");

        mainFrame = new Integer(cfg.getByKey("frame"));

    }

    @Override
    public void setArgs(Frame[] f, String[] a) {

        List<Frame> fs = new ArrayList<Frame>();

        fs.add(KUtil.getFrameByInt(mainFrame));

        fs.add(Frame.D1);

        String[] framsarray = otherFrame.split(",");
        for (int i = 0; i < framsarray.length; i++) {
            fs.add(KUtil.getFrameByInt(Integer.parseInt(framsarray[i])));

        }

        super.setArgs((Frame[]) fs.toArray(new Frame[fs.size()]), a);

    }

    @Override
    public void onInit(String url) {

        D1MV_H = new MovingV(22, OHLCType.HIGH, kl.getD1());
        D1MV_L = new MovingV(22, OHLCType.LOW, kl.getD1());
        //zz = new ZigZag();
        l = new Lowest(3, OHLCType.LOW, kl.getCurrentFrame(mainFrame));
        h = new Highest(3, OHLCType.HIGH, kl.getCurrentFrame(mainFrame));
        
        
        
        
        
        
        

    }

    @Override
    public void onTick(Tick t) {

        //zz.cal(t, daybofu, sdf);

        if (l.getValue().size() < 14) {
            return;
        }

        double[] Close = new double[]{kl.getCurrentFrame(mainFrame).get(0).getClose(), kl.getCurrentFrame(mainFrame).get(1).getClose(), kl.getCurrentFrame(mainFrame).get(2).getClose(), kl.getCurrentFrame(mainFrame).get(3).getClose()};
        double[] Open = new double[]{kl.getCurrentFrame(mainFrame).get(0).getOpen(), kl.getCurrentFrame(mainFrame).get(1).getOpen(), kl.getCurrentFrame(mainFrame).get(2).getOpen(), kl.getCurrentFrame(mainFrame).get(3).getOpen()};
        double[] High = new double[]{kl.getCurrentFrame(mainFrame).get(0).getHigh(), kl.getCurrentFrame(mainFrame).get(1).getHigh(), kl.getCurrentFrame(mainFrame).get(2).getHigh(), kl.getCurrentFrame(mainFrame).get(3).getHigh()};
        double[] Low = new double[]{kl.getCurrentFrame(mainFrame).get(0).getLow(), kl.getCurrentFrame(mainFrame).get(1).getLow(), kl.getCurrentFrame(mainFrame).get(2).getLow(), kl.getCurrentFrame(mainFrame).get(3).getLow()};




        //AliLog.println(zz.getDirect()+"  "+zz.getHigh()+"  "+zz.getLow()+"  "+zz.getPrice236()+"  "+zz.getPrice382()+"  "+zz.getPrice618());
        /*
        boolean up1 = Close[2] > Close[3] && Close[2] > Open[1] && Open[2] > Open[3] && Open[2] > Close[1] && Close[2] > Open[2];
        boolean up2 = Close[2] > Open[3] && Close[2] > Close[1] && Open[2] >= Open[1] && Open[2] > Close[3] && Close[2] < Open[2];
        //boolean up3 = High[1] >= h.getValue().get(3) || High[2] >= h.getValue().get(3) ||High[3] >= h.getValue().get(3) ;
        boolean up4 = (Close[1] < zz.getPrice236() && Close[2] < zz.getPrice236() && Close[3] < zz.getPrice236())
                && (High[1] > zz.getPrice236() || High[2] > zz.getPrice236() || High[3] > zz.getPrice236());
        boolean up5 = (Close[1] < zz.getPrice382() && Close[2] < zz.getPrice382() && Close[3] < zz.getPrice382())
                && (High[1] > zz.getPrice382() || High[2] > zz.getPrice382() || High[3] > zz.getPrice382());
        boolean up6 = (Close[1] < zz.getPrice618() && Close[2] < zz.getPrice618() && Close[3] < zz.getPrice618())
                && (High[1] > zz.getPrice618() || High[2] > zz.getPrice618() || High[3] > zz.getPrice618());

        boolean down1 = Close[2] < Close[3] && Close[2] < Open[1] && Open[2] < Open[3] && Open[2] < Close[1] && Close[2] < Open[2];
        boolean down2 = Close[2] < Open[3] && Close[2] < Close[1] && Open[2] <= Open[1] && Open[2] < Close[3] && Close[2] > Open[2];
        //boolean down3 = Low[1] <= l.getValue().get(3) || Low[2] <= l.getValue().get(3) ||Low[3] <= l.getValue().get(3) ;
        boolean down4 = (Close[1] > zz.getPrice236() && Close[2] > zz.getPrice236() && Close[3] > zz.getPrice236())
                && (Low[1] < zz.getPrice236() || Low[2] < zz.getPrice236() || Low[3] < zz.getPrice236());
        boolean down5 = (Close[1] > zz.getPrice382() && Close[2] > zz.getPrice382() && Close[3] > zz.getPrice382())
                && (Low[1] < zz.getPrice382() || Low[2] < zz.getPrice382() || Low[3] < zz.getPrice382());
        boolean down6 = (Close[1] > zz.getPrice618() && Close[2] > zz.getPrice618() && Close[3] > zz.getPrice618())
                && (Low[1] < zz.getPrice618() || Low[2] < zz.getPrice618() || Low[3] < zz.getPrice618());
        if (kl.getCurrentFrame(mainFrame).get(0).getVolumn() > 0) {
            return;
        }

        if (ct.getList().size() > 0) {
            if (ct.getList().size() > 1) {
                //findProfit(t);
            }
            addLots(t);
            return;
        }

        

        if ((up1 || up2) && zz.getDirect() == -1 && (up4 || up5 || up6)) {
            //AliLog.println("up->"+sdf.format(t.getTime()));
            String str = "";
            if (up4) {
                str = "up4";
            } else if (up5) {
                str = "up5";
            } else if (up6) {
                str = "up6";
            }

            double sl = h.getValue().get(1) * 3 - t.getPrice() * 2;
            double tp = t.getPrice() * 2 - h.getValue().get(1);
            sl = t.getPrice() + 0.0060;
            tp = t.getPrice() - 0.0020;
            ct.Fan(TradeType.SELL, t, symbol, 10, sl, tp, (t.getPrice() - tp) * 0.6 * 0, str);
            AliLog.debug(this, "开空单,价格" + t.getPrice() + " 止损" + sl + " 止盈" + tp + " 高点" + h.getValue().get(1) + " 时间" + sdf.format(t.getTime()));

        } else if ((down1 || down2) && zz.getDirect() == 1 && (down4 || down5 || down6)) {

            //AliLog.println("down->"+sdf.format(t.getTime()));
            String str = "";
            if (down4) {
                str = "down4";
            } else if (down5) {
                str = "down5";
            } else if (down6) {
                str = "down6";
            }
            double sl = l.getValue().get(1) * 3 - 2 * t.getPrice();
            double tp = t.getPrice() * 2 - l.getValue().get(1);
            sl = t.getPrice() - 0.0060;
            tp = t.getPrice() + 0.0020;
            ct.Fan(TradeType.BUY, t, symbol, 10, sl, tp, (tp - t.getPrice()) * 0.6 * 0, str);
            AliLog.debug(this, "开多单,价格" + t.getPrice() + " 止损" + sl + " 止盈" + tp + " 低点" + l.getValue().get(1) + " 时间" + sdf.format(t.getTime()));

        }
        */

    }

    @Override
    public void onCal(Tick t) {
        D1MV_H.cal(kl.getD1().get(0).getVolumn() == 0);
        D1MV_L.cal(kl.getD1().get(0).getVolumn() == 0);
        l.cal(kl.getCurrentFrame(mainFrame).get(0).getVolumn() == 0);
        h.cal(kl.getCurrentFrame(mainFrame).get(0).getVolumn() == 0);
        if (D1MV_H.getValue().size() > 10) {
            daybofu = D1MV_H.getValue().get(0) - D1MV_L.getValue().get(0);
        }

    }

    @Override
    public void addTPL(TPL tpl) {

        //List<TrendLine> tls = zz.getList();
//        for (int i = 0; i < tls.size(); i++) {
//            tpl.addTrendLine(tls.get(i).getValue1(), tls.get(i).getValue2(), tls.get(i).getDate1(), tls.get(i).getDate2());
//
//        }

    }

    void findProfit(Tick t) {

        if (ct.getList().size() > 1) {
            if (ct.getNetProfits(t) > 0) {
                AliLog.debug(this, "平仓所有单,价格" + t.getPrice() + " 时间" + sdf.format(t.getTime()));

                ct.closeAll(t);
            }

        }
        /*
         String temp = "";
         for(int i=0;i<ct.getList().size();i++)
         {
         Trade trade = ct.getList().get(i);
         temp += " price="+trade.getEntryPrice()+" lots="+trade.getLot()+" |" ;
            
            
            
            
         }
        
        
         AliLog.error("debug", "当前价格:"+t.getPrice()+"当前仓位:"+ct.getList().size()+" 当前方向"+ct.getTradeType()+" 当前净利润"+ct.getNetProfits(t)+" 当前均价"+ct.getPrice()+ " 头寸情况"+temp);
        
         */

    }

    void addLots(Tick t) {

        if (ct.getList().size() == 1) {

            if (ct.getTradeType() == TradeType.BUY) {

                double qujian = ct.getList().get(0).getTakeProfit() - ct.getList().get(0).getEntryPrice();
                qujian = 0.0020;
                //
                if (t.getPrice() < ct.getList().get(0).getEntryPrice() - qujian) {
                    ct.sendNew(symbol, 10, TradeType.BUY, t.getPrice(), t.getTime(), ct.getList().get(0).getStopLoss(), 0, 0, "down-jiacang1");
                    setTp();
                    AliLog.info(this, "第一次加多单,价格" + t.getPrice() + " 止损" + ct.getList().get(0).getStopLoss() + " 止盈" + 0 + " 时间" + sdf.format(t.getTime()));

                }

            } else if (ct.getTradeType() == TradeType.SELL) {
                double qujian = ct.getList().get(0).getEntryPrice() - ct.getList().get(0).getTakeProfit();
                //
                qujian = 0.0020;
                if (t.getPrice() > ct.getList().get(0).getEntryPrice() + qujian) {
                    ct.sendNew(symbol, 10, TradeType.SELL, t.getPrice(), t.getTime(), ct.getList().get(0).getStopLoss(), 0, 0, "up-jiacang1");
                    setTp();
                    AliLog.info(this, "第一次加空单,价格" + t.getPrice() + " 止损" + ct.getList().get(0).getStopLoss() + " 止盈" + 0 + " 时间" + sdf.format(t.getTime()));

                }

            }

        } else if (ct.getList().size() == 2) {
            if (ct.getTradeType() == TradeType.BUY) {

                double qujian = ct.getList().get(0).getTakeProfit() - ct.getList().get(0).getEntryPrice();
                qujian = 0.0020;
                if (t.getPrice() < ct.getList().get(0).getEntryPrice() - qujian * 2) {

                    ct.sendNew(symbol, 10, TradeType.BUY, t.getPrice(), t.getTime(), ct.getList().get(0).getStopLoss(), 0, 0, "down-jiacang2");
                    setTp();
                    AliLog.info(this, "第二次加多单,价格" + t.getPrice() + " 止损" + ct.getList().get(0).getStopLoss() + " 止盈" + 0 + " 时间" + sdf.format(t.getTime()));

                }

            } else if (ct.getTradeType() == TradeType.SELL) {

                double qujian = ct.getList().get(0).getEntryPrice() - ct.getList().get(0).getTakeProfit();
                qujian = 0.0020;
                if (t.getPrice() > ct.getList().get(0).getEntryPrice() + qujian * 2) {

                    ct.sendNew(symbol, 10, TradeType.SELL, t.getPrice(), t.getTime(), ct.getList().get(0).getStopLoss(), 0, 0, "up-jiacang2");
                    setTp();
                    AliLog.info(this, "第二次加空单,价格" + t.getPrice() + " 止损" + ct.getList().get(0).getStopLoss() + " 止盈" + 0 + " 时间" + sdf.format(t.getTime()));

                }

            }
        }

    }

    void setTp() {
        for (int i = 0; i < ct.getList().size(); i++) {

            Trades trade = ct.getList().get(i);
            trade.setTakeProfit(getTPPrice());

            //AliLog.error("debug","平均价:"+ct.getPrice()+" 总手数:"+ct.getNetLots());
        }

    }

    double getTPPrice() {
        double profit = 2.0;
        double lots = ct.getNetLots();
        double price = ct.getPrice();
        if (lots > 0) {

            return profit / lots / ct.getSymbol().getValueper1() + price;

        } else if (lots < 0) {

            return price - profit / lots * -1 / ct.getSymbol().getValueper1();

        }

        return -1.0;
    }

}
