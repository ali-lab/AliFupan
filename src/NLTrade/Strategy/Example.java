/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NLTrade.Strategy;

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
public class Example extends AStrategy{

    
    MovingV fast  ;
    MovingV slow ;
    int fastLen = 15 ;
    int slowLen = 65 ;
    int mainframe = 1 ;
    String otherFrame = "1" ;
    List<OHLC> data;
    
    
    @Override
    public void onInit(String url) {
        data = kl.getCurrentFrame(mainframe);
        fast = new MovingV(fastLen, OHLCType.CLOSE, data);
        slow = new MovingV(slowLen, OHLCType.CLOSE, data);

    }

    @Override
    public void onTick(Tick t) {
        
        //AliLog.println("fast->"+fast.getValue().get(0)+" slow->"+slow.getValue().get(0));
        if(slow.getValue().size()>slowLen)
        {
            
            if(fast.getValue().get(1)<slow.getValue().get(1) &&fast.getValue().get(0)>slow.getValue().get(0) )
            {
                ct.Fan(TradeType.BUY, t, symbol, 1, 0, 0, 0, "buy");
            }else  if(fast.getValue().get(1)>slow.getValue().get(1) &&fast.getValue().get(0)<slow.getValue().get(0) ) {
                //AliLog.println(slowLen+"================"+slow.getValue().size());
                ct.Fan(TradeType.SELL, t, symbol, 1, 0, 0, 0, "sell");
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
        tpl.addMv(15, 1);
        tpl.addMv(65, 1);


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
