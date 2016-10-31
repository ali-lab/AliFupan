package NLTrade.Strategy.Duichong;

import NLTrade.Model.Symbol;
import NLTrade.Model.TickM;
import NLTrade.Model.Trades;
import NLTrade.Model.TradeType;
import alilibs.AliFile;
import alilibs.AliHTTP;
import alilibs.AliLog;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ali
 */
public class DuiChong {

    /*
    1.将数据全部load进 tickm[]中
    2.循环载入数据
    3.
     */
    List<Trades> trades = null;
    List<Trades> history = null;

    public static void main(String[] args) throws Exception {
        new DuiChong().loadTick();
    }

    public void loadTick() throws Exception {

        trades = new ArrayList<Trades>();
        history = new ArrayList<Trades>();
        String s[];
        Double price1;
        Double price2;
        String d ;
        List<String> strings = AliFile.getString("/Users/ali/NetBeansProjects/NLTrade/data/AUDUSDNZDUSD.txt");
        for (int i = 0; i < strings.size(); i++) {
            s = strings.get(i).split(",");
            price1 = Double.parseDouble(s[2]);
            price2 = Double.parseDouble(s[3]);
            d = s[0]+","+s[1];
            trade(d,price1, price2);
            //AliLog.println(price1+"--"+price2);
        }
        
        AliLog.println(history.size()+"   "+trades.size());
        double ddd = 0.0 ;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d H:mm");
        AliFile.delete("/Users/ali/NetBeansProjects/NLTrade/data/result.txt");
        AliFile.delete("/Users/ali/NetBeansProjects/NLTrade/data/result2.csv");
        for(int i=0;i<history.size();i++)
        {
            Trades t = history.get(i) ;
            ddd += t.getProfit();
            AliFile.appendString("/Users/ali/NetBeansProjects/NLTrade/data/result.txt",
                    "t="+t.getComment()
                    +" id="+t.getId()
                    +" p= "+df5.format(t.getProfit())
                    +" s="+t.getSymbol().getName()
                    +" e="+t.getEntryPrice()
                    +" x="+t.getExitPrice()
                    +" l="+t.getLot()
                    +" d="+t.getDirect()
                    +" all="+df5.format(ddd)+"\n"
            
            
            );
            AliFile.appendString("/Users/ali/NetBeansProjects/NLTrade/data/result2.csv",df5.format(ddd)+"\n");
            //AliFile.appendString("/Users/ali/NetBeansProjects/NLTrade/data/result4.txt", ""+ddd+"\n");
            //df.format(result) + (i == ct.OrdersHistoryTotal() - 1 ? "" : ",")
            //"'" + sdf.format(ct.getListHistory().get(i).getEntryTime()) + "'" + (i == ct.OrdersHistoryTotal() - 1 ? "" : ",")
            //AliFile.appendString("/Users/Ali/AUDNZD_dt.txt", "'" + t.getComment() + "'" + (i == history.size() - 1 ? "" : ","));
            //AliFile.appendString("/Users/Ali/AUDNZD.txt", ""+ddd + ""+(i == history.size() - 1 ? "" : ","));
            
            
            
        }
        AliLog.println(ddd+"-----------");
        //AliLog.println("上传1->" + AliHTTP.uploadFile("/Users/Ali/AUDNZD.txt", "http://linux.9hlh.com/nltrade/fupan/upload.php"));
        //AliLog.println("上传2->" + AliHTTP.uploadFile("/Users/Ali/AUDNZD_dt.txt", "http://linux.9hlh.com/nltrade/fupan/upload.php"));

    }

    public void trade(String dString,Double p1, Double p2) {

        getcurrent(p1, p2);
        
        //止盈逻辑
        if(buysprofit>0.5 || buysprofit<-100){
            closeAll(true, p1, p2);
            getcurrent(p1, p2);
        }
        
        if(sellsprofit>0.5|| sellsprofit<-100){
            closeAll(false, p1, p2);
            getcurrent(p1, p2);
        }
        
        if((buysprofit <=-5&&buyscount==1) || (buysprofit <=-15&&buyscount==2) || (buysprofit <=-45&&buyscount==3))
        {
            double lots = 1000 ;
            if(buysprofit <=-15) lots = 2000 ; 
            if(buysprofit <=-45) lots = 4000 ; 
            Trades t1 = new Trades();
            t1.setLot(lots);
            t1.setDirect(TradeType.BUY);
            t1.setEntryPrice(p1);
            t1.setId(++id);
            t1.setSymbol(new Symbol("p1"));
            t1.setComment(dString);
            Trades t2 = new Trades();
            t2.setLot(lots);
            t2.setDirect(TradeType.SELL);
            t2.setEntryPrice(p2);
            t2.setId(++id);
            t2.setSymbol(new Symbol("p2"));
            t2.setComment(dString);
            trades.add(t1);
            trades.add(t2);
            
            getcurrent(p1, p2);
            
            
        }
        
        if((sellsprofit <=-5&&sellscount==1) || (sellsprofit <=-15&&sellscount==2) || (sellsprofit <=-45&&sellscount==3))
        {
            double lots = 1000 ;
            if(sellsprofit <=-15) lots = 2000 ; 
            if(sellsprofit <=-45) lots = 4000 ; 
            
            Trades t1 = new Trades();
            t1.setLot(lots);
            t1.setDirect(TradeType.SELL);
            t1.setEntryPrice(p1);
            t1.setId(++id);
            t1.setSymbol(new Symbol("p1"));
            t1.setComment(dString);
            Trades t2 = new Trades();
            t2.setLot(lots);
            t2.setDirect(TradeType.BUY);
            t2.setEntryPrice(p2);
            t2.setId(++id);
            t2.setSymbol(new Symbol("p2"));
            t2.setComment(dString);
            trades.add(t1);
            trades.add(t2);
            getcurrent(p1, p2);
        }
        
        
        
        
        //开仓逻辑
        if(buyscount==0)
        {
            Trades t1 = new Trades();
            t1.setLot(1000);
            t1.setDirect(TradeType.BUY);
            t1.setEntryPrice(p1);
            t1.setId(++id);
            t1.setSymbol(new Symbol("p1"));
            t1.setComment(dString);
            Trades t2 = new Trades();
            t2.setLot(1000);
            t2.setDirect(TradeType.SELL);
            t2.setEntryPrice(p2);
            t2.setId(++id);
            t2.setSymbol(new Symbol("p2"));
            t2.setComment(dString);
            trades.add(t1);
            trades.add(t2);
            
        }
        if(sellscount==0)
        {
            Trades t1 = new Trades();
            t1.setLot(1000);
            t1.setDirect(TradeType.SELL);
            t1.setEntryPrice(p1);
            t1.setId(++id);
            t1.setSymbol(new Symbol("p1"));
            t1.setComment(dString);
            Trades t2 = new Trades();
            t2.setLot(1000);
            t2.setDirect(TradeType.BUY);
            t2.setEntryPrice(p2);
            t2.setId(++id);
            t2.setSymbol(new Symbol("p2"));
            t2.setComment(dString);
            trades.add(t1);
            trades.add(t2);
            
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
    }
    int id = 0 ;
    int buyscount = 0;
    int sellscount = 0;
    double buysprofit = 0.0;
    double sellsprofit = 0.0;
    Trades currTrade = null;
    
    void getcurrent(Double p1, Double p2) {
        buyscount = 0;
        sellscount = 0;
        buysprofit = 0.0;
        sellsprofit = 0.0;
        for (int i = 0; i < trades.size(); i++) {
            currTrade = trades.get(i);

            if (currTrade.getSymbol().getName().equals("p1")) {
                if (currTrade.getDirect() == TradeType.BUY) {
                    buyscount++;
                    buysprofit += currTrade.getLot()*(p1-currTrade.getEntryPrice()-0.00035);
                    
                }else if (currTrade.getDirect() == TradeType.SELL) {
                    sellscount++;
                    sellsprofit += currTrade.getLot()*(currTrade.getEntryPrice()-p1-0.00035);
                    
                    
                }
                
            }else if (currTrade.getSymbol().getName().equals("p2")) {
                if (currTrade.getDirect() == TradeType.BUY) {
                    
                    sellsprofit += currTrade.getLot()*(p2-currTrade.getEntryPrice()-0.00035);
                    
                }else if (currTrade.getDirect() == TradeType.SELL) {
                    
                    buysprofit += currTrade.getLot()*(currTrade.getEntryPrice()-p2-0.00035);
                    
                    
                }
                
            }

        }

    }
int closeall = 0 ;
    void closeAll(boolean isBuy,Double p1,Double p2)
    {
        closeall++;
        for (int i = trades.size()-1; i >= 0; i--) {
            currTrade = trades.get(i);

            if (currTrade.getSymbol().getName().equals("p1")) {
                
                
                if (currTrade.getDirect() == TradeType.BUY && isBuy) {
                    currTrade.setComment(currTrade.getComment()+"["+(closeall)+"]");
                    currTrade.setExitPrice(p1);
                    currTrade.setProfit(currTrade.getLot()*(p1-currTrade.getEntryPrice()-0.00035));
                    history.add(currTrade);
                    trades.remove(i);
                    
                    
                }else if (currTrade.getDirect() == TradeType.SELL && !isBuy) {
                    currTrade.setComment(currTrade.getComment()+"["+(closeall)+"]");
                    currTrade.setExitPrice(p1);
                    currTrade.setProfit(currTrade.getLot()*(currTrade.getEntryPrice()-p1-0.00035));
                    history.add(currTrade);
                    trades.remove(i);
                    
                    
                }
                
            }else if (currTrade.getSymbol().getName().equals("p2")) {
                
                
                if (currTrade.getDirect() == TradeType.BUY && !isBuy) {
                    currTrade.setComment(currTrade.getComment()+"["+(closeall)+"]");
                    currTrade.setExitPrice(p2);
                    currTrade.setProfit(currTrade.getLot()*(p2-currTrade.getEntryPrice()-0.00035));
                    history.add(currTrade);
                    trades.remove(i);
                    
                    
                }else if (currTrade.getDirect() == TradeType.SELL && isBuy) {
                    currTrade.setComment(currTrade.getComment()+"["+(closeall)+"]");
                    currTrade.setExitPrice(p2);
                    currTrade.setProfit(currTrade.getLot()*(currTrade.getEntryPrice()-p2-0.00035));
                    history.add(currTrade);
                    trades.remove(i);
                    
                    
                }
                
            }

        }
        
    }
    DecimalFormat df5 = new DecimalFormat("#.00");
    List<String> lishijilu = new ArrayList<String>();
    void uploadHistory(String id,double profit,String dt,String comment,String lots)
    {
        
        
        
        
        
        String str = "";
        str += "<tr>";
        str += "<td style=\"text-align:center;\">";
        
        str += id;
        str += "</td>";
        str += "<td style=\"text-align:center;\">";
        str += dt;
        str += "</td>";
        str += "<td style=\"text-align:center;\">";
        str += lots;
        str += "</td>";
        str += "<td style=\"text-align:center;\">";
        str += df5.format(profit);
        str += "</td>";
        str += "<td style=\"text-align:center;\">";
        str += "<a href=\"javascript:run(";
        str += id;
        str += ",'";
        str += dt;
        str += "')\" id=\"comment";
        str += id;
        str += "\">";
        str += comment;
        str += "</a></td></tr>";
        
        lishijilu.add(str);
                                          
 
    }
    
    
}
