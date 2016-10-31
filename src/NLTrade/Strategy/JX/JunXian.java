/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NLTrade.Strategy.JX;

import NLTrade.Strategy.*;
import NLTrade.Model.Frame;
import NLTrade.Model.OHLC;
import NLTrade.Model.OHLCType;
import NLTrade.Model.Tick;
import NLTrade.Model.TradeType;
import NLTrade.Strategy.AStrategy;
import NLTrade.indicate.MovingV;
import NLTrade.util.KUtil;
import NLTrade.util.TPL;
import alilibs.AliLog;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ali
 */
public class JunXian extends AStrategy {

    MovingV fast;
    MovingV slow;
    int fastLen = 15;
    int slowLen = 200;
    int mainframe = 15;
    String otherFrame = mainframe + "";
    List<OHLC> data;

    @Override
    public void onInit(String url) {
        data = kl.getCurrentFrame(mainframe);
        fast = new MovingV(fastLen, OHLCType.CLOSE, data);
        slow = new MovingV(slowLen, OHLCType.CLOSE, data);

    }
    double x = 1;

    @Override
    public void onTick(Tick t) {

        //AliLog.println("fast->"+fast.getValue().get(0)+" slow->"+slow.getValue().get(0));
        if (slow.getValue().size() > slowLen && data.get(0).getVolumn() == 0 && ct.getList().size() == 0) {
            if (ct.getListHistory().size() > 0) {
                if (ct.getListHistory().get(ct.getListHistory().size() - 1).getProfit() > 0) {
                    x = 1;

                } else if (ct.getListHistory().size() == 1) {
                    x = 1;
                } else if (ct.getListHistory().size() > 1) {
                    if (ct.getListHistory().get(ct.getListHistory().size() - 2).getProfit() > 0) {
                        x = 1;
                    } else {
                        x = ct.getListHistory().get(ct.getListHistory().size() - 1).getLot() * 2;
                    }
                }
            }
            if(x>32)
            {
                x=1 ;
            }
            
            
            
            if (fast.getValue().get(1) < slow.getValue().get(1) && fast.getValue().get(0) > slow.getValue().get(0)) {
                ct.Fan(TradeType.BUY, t, symbol, x * 1.0, t.getPrice() - 0.0050, t.getPrice() + 0.0055, 0, "BUY");
            } else if (fast.getValue().get(1) > slow.getValue().get(1) && fast.getValue().get(0) < slow.getValue().get(0)) {
                //AliLog.println(slowLen+"================"+slow.getValue().size());
                ct.Fan(TradeType.SELL, t, symbol, x * 1.0, t.getPrice() + 0.0050, t.getPrice() - 0.0055, 0, "SELL");
            }

        }

    }

    @Override
    public void onCal(Tick t) {

        fast.cal(data.get(0).getVolumn() == 0);
        slow.cal(data.get(0).getVolumn() == 0);

    }

    @Override
    public void addTPL(TPL tpl) {
        tpl.addMv(fastLen, 1);
        tpl.addMv(slowLen, 1);

    }

    @Override
    public void setArgs(Frame[] f, String[] a) {
        List<Frame> fs = new ArrayList<Frame>();

        fs.add(KUtil.getFrameByInt(mainframe));

        fs.add(Frame.D1);

        String[] framsarray = otherFrame.split(",");
        for (int i = 0; i < framsarray.length; i++) {
            fs.add(KUtil.getFrameByInt(Integer.parseInt(framsarray[i])));

        }

        super.setArgs((Frame[]) fs.toArray(new Frame[fs.size()]), a);
    }

}
