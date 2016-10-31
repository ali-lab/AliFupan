/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NLTrade;

import NLTrade.Model.Frame;
import NLTrade.Model.Symbol;
import NLTrade.Model.Tick;
import NLTrade.Model.TickM;
import NLTrade.Model.TradeType;
import NLTrade.Model.Trades;
import NLTrade.Strategy.AMultiStrategy;
import NLTrade.util.TPL;
import NLTrade.util.TPLMM;
import alilibs.AliLog;
import alilibs.Data;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author ali
 */
public class TEST extends AMultiStrategy {

    KLineMM kl = null;

    public TEST(Symbol[] symbols, Frame[] frame) throws Exception {
        super(symbols, frame);
        kl = klines[1];

    }

    
    
    boolean isnew = false ;
    Date lastdate = null ;
    @Override
    public void onTick(TickM t) {
        //AliLog.println(t.getPrice()[0]+" "+t.getPrice()[1]+" "+t.getPrice()[2]+" ");
        isnew = lastdate == null || t.getTime()[0].after(lastdate);
        
        if(isnew)
        {
            //AliLog.println("isnew "+t.getTime()[0]);
        }else{
           //AliLog.println(" is not new "+t.getTime()[0]); 
        }
        
        lastdate = t.getTime()[0] ;
        
        //run(t, false);
        run(t, true);
        

    }

    String x = "buy" ;
    public void run(TickM t,boolean  isBuy)
    {
        if(!isBuy) x = "sell" ;
        
        
        if (getcount(isBuy) == 0) {
            TradeType type1 = TradeType.BUY;
            TradeType type2 = TradeType.SELL;
            ct.setFlag(0);
            if (isBuy) {
                type1 = TradeType.SELL;
                type2 = TradeType.BUY;
                ct.setFlag(1);
            }

            ct.sendNew(super.symbols[0], 1, type1, t.getPrice()[0], t.getTime()[0], 0, 0, 0, x+"AU", 0);
            ct.sendNew(super.symbols[1], 1, type2, t.getPrice()[1], t.getTime()[1], 0, 0, 0, x+"NU", 1);

        } else if ((getProfits(t, isBuy) > 10 || getProfits(t, isBuy) < -100)&& ct.getList().size()>1) {

            
            
            
            
            if (getProfits(t, isBuy) < -100) {
                ct.closeAll(t);
                /*
                TradeType fan = TradeType.BUY;
                double sl = t.getPrice()[0]-0.0050 ;
                double tp = t.getPrice()[0]+0.0008 ;
                if(!isBuy) {
                 fan = TradeType.SELL;
                 sl = t.getPrice()[0]+0.0050 ;
                 tp = t.getPrice()[0]-0.0008 ;
                    
                }
                
                
                ct.sendNew(super.symbols[0], 200, fan, t.getPrice()[0], t.getTime()[0], sl, tp, 0, x+"fan", 0);
                */
                
                
                
            }else if (getProfits(t, isBuy) >1.2 && isnew) {
                ct.closeAll(t);
            }

            
            //AliLog.println(super.symbols[1].getCommition());
            
            

        } else if (getcount(isBuy) == 2 && getProfits(t, isBuy) < -5 && isnew) {
            TradeType type1 = TradeType.BUY;
            TradeType type2 = TradeType.SELL;

            if (ct.getFlag() == 1) {
                type1 = TradeType.SELL;
                type2 = TradeType.BUY;

            }
            ct.sendNew(super.symbols[0], 1, type1, t.getPrice()[0], t.getTime()[0], 0, 0, 0, x+"AU+1", 0);
            ct.sendNew(super.symbols[1], 1, type2, t.getPrice()[1], t.getTime()[1], 0, 0, 0, x+"NU+1", 1);

        } else if (getcount(isBuy) == 4 && getProfits(t, isBuy) < -15&& isnew) {
            TradeType type1 = TradeType.BUY;
            TradeType type2 = TradeType.SELL;

            if (ct.getFlag() == 1) {
                type1 = TradeType.SELL;
                type2 = TradeType.BUY;

            }
            ct.sendNew(super.symbols[0], 2, type1, t.getPrice()[0], t.getTime()[0], 0, 0, 0, x+"AU+2", 0);
            ct.sendNew(super.symbols[1], 2, type2, t.getPrice()[1], t.getTime()[1], 0, 0, 0, x+"NU+2", 1);

        } else if (getcount(isBuy) == 6 && getProfits(t, isBuy) < -45&& isnew) {
            TradeType type1 = TradeType.BUY;
            TradeType type2 = TradeType.SELL;

            if (ct.getFlag() == 1) {
                type1 = TradeType.SELL;
                type2 = TradeType.BUY;

            }
            ct.sendNew(super.symbols[0], 4, type1, t.getPrice()[0], t.getTime()[0], 0, 0, 0, x+"AU+4", 0);
            ct.sendNew(super.symbols[1], 4, type2, t.getPrice()[1], t.getTime()[1], 0, 0, 0, x+"NU+4", 1);

        }
        
        
        
    }
    
    
    
    
    
    
    public static void main(String[] args) throws Exception {
        Symbol one = new Symbol("AUDUSD");
        Symbol two = new Symbol("NZDUSD");
        
        Symbol[] s = new Symbol[]{one, two};
        Frame[] f = new Frame[0];
        TEST t = new TEST(s, f);
        t.run();
        AliLog.println(t.ct.getListHistory().size() + "   <<<<<");
    }

    
    public int getcount(boolean isBuy)
    {
        /*
        int result = 0 ;
        Trades t = null ;
        for(int i=0;i<ct.getList().size();i++)
        {
            t = ct.getList().get(i);
            if((t.getComment().contains("buy")&& isBuy)||(t.getComment().contains("sell")&& !isBuy)){
                result++;
            }
            
            
            
        }
        */
        return  ct.getList().size() ;
        
    }
    
    public double getProfits(TickM t,boolean  isBuy) {
        /*
        double x = 0;
        int index ;
        Trades tt =null;
        for (int i = 0; i < ct.getList().size(); i++) {
            
            tt = ct.getList().get(i);
            
            index = tt.getIndex();
            
            
            
            if((tt.getComment().contains("buy")&& isBuy)||(tt.getComment().contains("sell")&& !isBuy)){
                if (tt.getDirect() == TradeType.BUY) {
                x += (t.getPrice()[index] - tt.getEntryPrice()) * tt.getLot() * tt.getSymbol().getValueper1();
            } else if (tt.getDirect() == TradeType.SELL) {
                x += (tt.getEntryPrice() - t.getPrice()[index]) * tt.getLot()* tt.getSymbol().getValueper1();
            }
            }

        }
*/
        return ct.getNetProfits(t);
    }
    
    
    @Override
    public void onCal(TickM t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTPL(TPLMM tpl) {
    }

}
