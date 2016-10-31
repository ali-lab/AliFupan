package NLTrade.Strategy.SBW1565;




import NLTrade.Model.Frame;
import java.util.List;

import NLTrade.Model.OHLC;
import NLTrade.Model.OHLCType;
import NLTrade.Model.Symbol;
import NLTrade.Model.Tick;
import NLTrade.Model.TradeType;
import NLTrade.Strategy.AStrategy;
import NLTrade.indicate.Highest;
import NLTrade.indicate.Lowest;
import NLTrade.indicate.MovingV;
import NLTrade.util.KUtil;
import NLTrade.util.TPL;
import alilibs.AliLog;
import java.util.ArrayList;

public class SBW1565 extends AStrategy {

    Highest highest;
    Lowest lowest;
    //Trend trend;

    MovingV m15_h;
    MovingV m15_l;
    MovingV h1_5;
    MovingV h1_60;
    MovingV h1_20;
    MovingV m15_20;
    MovingV m15_60;
    List<OHLC> h1;
    List<OHLC> m15;
    double m15_bofu = 0;
    int h1_60_direct = 0;// 0没有方向 1 多头 -1 空头
    int h1_60_peng = 0; // 0没有碰 1 碰
    int h1_20_direct = 0;// 0没有方向 1 多头 -1 空头
    int h1_20_peng = 0; // 0没有碰 1 碰
    boolean m1565_distance = false;
    int mainFrame = 60 ;
    String otherFrame = "1,5,15";
    
    
    
    

    public SBW1565() {

        super();

    }

    @Override
    public void onInit(String url) {

        h1 = kl.getH1();
        m15 = kl.getM15();
        m15_h = new MovingV(30, OHLCType.HIGH, m15);
        m15_l = new MovingV(30, OHLCType.LOW, m15);
        h1_5 = new MovingV(5, OHLCType.CLOSE, h1);
        h1_20 = new MovingV(20, OHLCType.CLOSE, h1);
        h1_60 = new MovingV(60, OHLCType.CLOSE, h1);
        m15_20 = new MovingV(20, OHLCType.CLOSE, m15);
        m15_60 = new MovingV(60, OHLCType.CLOSE, m15);
        highest = new Highest(50, OHLCType.HIGH, m15);
        lowest = new Lowest(50, OHLCType.LOW, m15);
        //trend = new Trend(15, 65, 50, OHLCType.CLOSE, h1);

    }

    @Override
    public void onTick(Tick t) {


        if (m15_h.getValue().size() > 0) {
            m15_bofu = m15_h.getValue().get(0) - m15_l.getValue().get(0);
        }

        if (m15_60.getValue().size() > 0) {

            if (Math.abs(m15_20.getValue().get(0) - m15_60.getValue().get(0)) > m15_bofu) {
                m1565_distance = true;
            } else {

                m1565_distance = false;
            }

        }
        
        
        // 设置需要基本K先，最大均线至少有3根
        if (h1_60.getValue().size() < 3) {
            return;
        }
        
        cancelPeng();
        fan1565(t);
        //calH1_60(t);
        calH1_20(t);

    }

    /**
     * 当出现反的1565的时候，开。
     */
    void fan1565(Tick t) {


        if (h1.get(0).getVolumn() > 0) {
            return;
        }

    }

    void calH1_20(Tick t) {
        

        // H1判断方向逻辑,用5均线交叉来判断，交叉后重置碰
        if (h1_5.getValue().get(2) < h1_20.getValue().get(2) && h1_5.getValue().get(1) > h1_20.getValue().get(1)) {
            h1_20_direct = 1;
            h1_20_peng = 0;
        } else if (h1_5.getValue().get(2) > h1_20.getValue().get(2)
                && h1_5.getValue().get(1) < h1_20.getValue().get(1)) {
            h1_20_direct = -1;
            h1_20_peng = 0;
        }
        // 碰
        if (h1_20_direct == 1) {
            if (t.getPrice() < h1_20.getValue().get(0) + (isjpy ? 0.05 : 0.0005)) {
                h1_20_peng = 1;
            }

        } else if (h1_20_direct == -1) {
            if (t.getPrice() > h1_20.getValue().get(0) - (isjpy ? 0.05 : 0.0005)) {
                h1_20_peng = 1;
            }

        }



        // 交易(一开盘且15分钟的均线距离大于平均波幅)
        if (m15.get(0).getVolumn() > 0 || m1565_distance == false) {
            return;
        }
        if (h1_20_peng == 1) {
            if (h1_20_direct == 1) {
                // 阳线 - 15 在65 下 横穿 15均线 价格在 20上
                if (m15.get(1).getClose() > m15.get(1).getOpen() && m15_20.getValue().get(1) < m15_60.getValue().get(1)//阳线，20在60下
                        && m15.get(1).getLow() < m15_20.getValue().get(1)
                        && m15.get(1).getClose() > m15_20.getValue().get(1)
                        && t.getPrice() > h1_20.getValue().get(0)) {
                    //AliLog.error("", sdf.format(t.getTime())+"     m15 ->"+m15.get(1).getOpen()+"  "+m15.get(1).getHigh()+"  "+m15.get(1).getLow()+"  "+m15.get(1).getClose());
                    trade(t, TradeType.BUY, "h1_20->b");
                }

            } else if (h1_20_direct == -1) {
                if (m15.get(1).getClose() < m15.get(1).getOpen() && m15_20.getValue().get(1) > m15_60.getValue().get(1)
                        && m15.get(1).getClose() < m15_20.getValue().get(1)
                        && m15.get(1).getHigh() > m15_20.getValue().get(1)
                        && t.getPrice() < h1_20.getValue().get(0)) {

                    trade(t, TradeType.SELL, "h1_20->s");
                }
            }

        }

    }

    /**
     *
     * @param t
     * @param isbuy
     */
    void trade(Tick t, TradeType isbuy, String comment) {

        if (isbuy==TradeType.BUY) {
            if (lowest.getValue().get(0) > 0) {
                ct.Fan(TradeType.BUY, t, symbol, 1, lowest.getValue().get(0) - (isjpy ? 0.03 : 0.0003), t.getPrice() * 2 - lowest.getValue().get(0), 0, comment);
            }

        } else if (isbuy==TradeType.SELL) {
            if (highest.getValue().get(0) > 0) {
                ct.Fan(TradeType.SELL, t, symbol, 1, highest.getValue().get(0) + (isjpy ? 0.03 : 0.0003), t.getPrice() * 2 - highest.getValue().get(0), 0, comment);
            }
        }

    }

    @Override
    public void onCal(Tick t) {

        m15_h.cal(m15.get(0).getVolumn() == 0);
        m15_l.cal(m15.get(0).getVolumn() == 0);
        h1_5.cal(h1.get(0).getVolumn() == 0);
        h1_20.cal(h1.get(0).getVolumn() == 0);
        h1_60.cal(h1.get(0).getVolumn() == 0);
        m15_20.cal(m15.get(0).getVolumn() == 0);
        m15_60.cal(m15.get(0).getVolumn() == 0);
        highest.cal(m15.get(0).getVolumn() == 0);
        lowest.cal(m15.get(0).getVolumn() == 0);

    }

    @Override
    public void addTPL(TPL tpl) {
        tpl.addMv(20, 1);
        tpl.addMv(60, 1);
    }
    
    private void cancelPeng()
    {
        
        // 取消碰（15分钟交叉掉）
        boolean flag1 = m15_20.getValue().get(2) < m15_60.getValue().get(2)
                && m15_20.getValue().get(1) > m15_60.getValue().get(1);

        boolean flag2 = m15_20.getValue().get(2) > m15_60.getValue().get(2)
                && m15_20.getValue().get(1) < m15_60.getValue().get(1);

        if (flag1 || flag2) {
            h1_20_peng = 0;
            h1_60_peng = 0 ;
        }
        
        
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
    
    
    

}
