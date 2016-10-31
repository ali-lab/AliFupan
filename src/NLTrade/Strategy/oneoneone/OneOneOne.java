/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NLTrade.Strategy.oneoneone;

import NLTrade.Model.Frame;
import NLTrade.Model.OHLC;
import NLTrade.Model.Tick;
import NLTrade.Model.TradeType;
import NLTrade.Strategy.AStrategy;
import NLTrade.indicate.MovingV;
import NLTrade.util.KUtil;
import NLTrade.util.TPL;
import alilibs.AliLog;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Ali
 */
public class OneOneOne extends AStrategy {

    int mainframe = 1;
    String otherFrame = "1";
    List<OHLC> data;

    @Override
    public void onInit(String url) {
        data = kl.getCurrentFrame(mainframe);

    }

    int currentstep = 1;
    double totalLoss = 0;
    double tempLoss = 0;

    @Override
    public void onTick(Tick t) {
        //ct.Fan(TradeType.SELL, t, symbol, 1, 0, 0, 0, "sell");
        //AliLog.println("fast->"+fast.getValue().get(0)+" slow->"+slow.getValue().get(0));
        //AliLog.debug("", "current->"+currentstep+"  temploss->"+tempLoss+"  total->"+totalLoss);
        //监控当前浮亏
        if (!ct.getList().isEmpty()) {
            tempLoss = ct.getNetProfits(t);
        }
//空仓下

        if (ct.getList().isEmpty()&& data.get(0).getVolumn()==0) {

            //对totalLoss进行处理
            if (tempLoss < 0) {
                
                totalLoss += tempLoss;
            } else {
                totalLoss = 0;
            }
            
            //搞当前step
            if (totalLoss < 0) {
                currentstep++;
            } else {
                currentstep = 1;
                totalLoss = 0;
            }
//AliLog.println("totalLoss=>"+totalLoss+" step=>"+currentstep);
            //随机开单
            if (new Random().nextInt() % 2 == 0) {
                //AliLog.println("sell");
                ct.sendNew(symbol, getlots(), TradeType.SELL, t.getPrice(), t.getTime(), t.getPrice() + 0.0060, 0, 0, "111SELL");
                ct.sendNew(symbol, getlots(), TradeType.SELLLIMIT, t.getPrice() + 0.0020, t.getTime(), t.getPrice() + 0.0060, 0, 0, "222SELL");
                ct.sendNew(symbol, getlots(), TradeType.SELLLIMIT, t.getPrice() + 0.0040, t.getTime(), t.getPrice() + 0.0060, 0, 0, "333SELL");
            } else {
                //AliLog.println("buy");

                ct.sendNew(symbol, getlots(), TradeType.BUY, t.getPrice(), t.getTime(), t.getPrice() - 0.0060, 0, 0, "111buy");
                ct.sendNew(symbol, getlots(), TradeType.BUYLIMIT, t.getPrice() - 0.0020, t.getTime(), t.getPrice() - 0.0060, 0, 0, "222buy");
                ct.sendNew(symbol, getlots(), TradeType.BUYLIMIT, t.getPrice() - 0.0040, t.getTime(), t.getPrice() - 0.0060, 0, 0, "333buy");

            }

        } else if (ct.getListGua().size() == ct.getList().size())//没有成交单 有挂单
        {
            ct.closeAllGua();
        }

        //AliLog.println(totalLoss+"   "+ ct.getList().size());

        
        
        
        
        if (ct.getNetProfits(t) +totalLoss > 2) {
            
           
            
            //AliLog.debug("", "CLOSEALL > 2 ");
            ct.closeAllGua();
            ct.closeAll(t);
            totalLoss = 0 ;
            currentstep = 1 ;
        }

    }

    @Override
    public void onCal(Tick t) {
        
    }

    @Override
    public void addTPL(TPL tpl) {

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

    private double getlots() {

        switch(currentstep)
        {
            
            case 1 : return 1 ;
            case 2 : return 2 ;
            case 3 : return 4 ;
            case 4 : return 8 ;
            case 5 : return 16 ;
            case 6 : return 32;
            case 7 : return 64 ;
            case 8 : return 128 ;
            case 9 : return 256 ;
            case 10 : return 512 ;
            case 11 : return 1024 ;
            case 12 : return 2048 ;
            case 13 : return 4096 ;
            case 14 : return 8192 ;

            
            
            
            default:return 1 ;
            
            
            
        }
        
        

    }
}
