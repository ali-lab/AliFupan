/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NLTrade.Strategy;

import NLTrade.CTrade;
import NLTrade.CTradeMM;
import NLTrade.KLineMM;
import NLTrade.Model.Frame;
import NLTrade.Model.Symbol;

import NLTrade.Model.TickM;
import NLTrade.util.Config;
import NLTrade.util.MultiSymbol;
import NLTrade.util.TPL;
import NLTrade.util.TPLMM;
import alilibs.AliLog;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ali
 */
public abstract class AMultiStrategy {
    
    
    protected Config normalConfig;
    protected Symbol [] symbols ;
    protected KLineMM [] klines ;
    protected List<TickM> dataList ;
    protected CTradeMM ct ; 
    public AMultiStrategy(Symbol[]symbols,Frame[] frame) throws Exception
    {
        this.symbols = symbols ;
        
        
        
        ct = CTradeMM.getInstance();
        normalConfig = new Config("../config/load.cfgx","nomal");
        
        for(int i=0;i<symbols.length;i++)
        {
            String [] tmp = normalConfig.getByKey(symbols[i].getName()).split(",");
            symbols[i].setValueper1(Double.parseDouble(tmp[0]));
            symbols[i].setCommition(Double.parseDouble(tmp[1]));
            symbols[i].setPoint(Double.parseDouble(tmp[2]));
            
            
            
            
        }
        
        
        
        
        AliLog.println(normalConfig.getCurrentsymbolStringM());
        dataList = MultiSymbol.getTickMs(normalConfig.getCurrentsymbolStringM(), symbols.length);
        this.klines = new KLineMM[symbols.length];
        for(int i=0;i<klines.length;i++)
        {
            klines[i] = new KLineMM(frame, symbols[i], i, dataList);
        }
        
        
    }
    
    public void run()
    {
        
        for(int i = 0 ;i < dataList.size();i++)
        {
            TickM t = dataList.get(i);
            
            ct.runWeituo(t);
            for(int j=0;j<klines.length;j++)
            {
                klines[j].load(t);
            }
            onTick(t);
            
            
            
        }
        
        
        TPLMM tpl;
        try {
            tpl = new TPLMM(ct);
            addTPL(tpl);

        tpl.saveToTPL();
        } catch (Exception ex) {
            Logger.getLogger(AMultiStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        

        
    }
    
    public abstract void onTick(TickM t) ;
    public abstract void onCal(TickM t);
    public abstract void addTPL(TPLMM tpl);
    
}
