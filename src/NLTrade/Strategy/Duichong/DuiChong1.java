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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ali
 */
public class DuiChong1 {

    /*
    1.将数据全部load进 tickm[]中
    2.循环载入数据
    3.
     */
    List<Trades> trades = null;
    List<Trades> history = null;

    public static void main(String[] args) throws Exception {
        new DuiChong1().loadTick();
    }

    public void loadTick() throws Exception {
AliFile.delete("/Users/ali/NetBeansProjects/NLTrade/data/result3.txt");
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
    double eee = 0.0 ;
    public void trade(String dString,Double p1, Double p2) {
        
        getcurrent(p1, p2);
        
        //止盈逻辑
        if(profit>0.5 || profit<-100){
            closeAll( p1, p2);
           
            if(profit>0.5 )
            {
                eee += 0.5 ;
            }else if( profit<-100){
                eee += -100 ;
            }
            //AliLog.println(eee+"");
            try {
                AliFile.appendString("/Users/ali/NetBeansProjects/NLTrade/data/result3.txt", eee+"\n");
            } catch (IOException ex) {
                Logger.getLogger(DuiChong1.class.getName()).log(Level.SEVERE, null, ex);
            }
             getcurrent(p1, p2);
            
            
        }
        
        
        if((profit <=-4&&count==2) || (profit <=-12&&count==4) || (profit <=-28&&count==6)|| (profit <=-60&&count==8))
        {
            double lots = 1000 ;
            if(profit <=-12) lots = 2000 ; 
            if(profit <=-28) lots = 4000 ; 
            if(profit <=-60) lots = 8000 ; 
            Trades t1 = new Trades();
            t1.setLot(lots);
            t1.setDirect(p1direct);
            t1.setEntryPrice(p1);
            t1.setId(++id);
            t1.setSymbol(new Symbol("p1"));
            t1.setComment(dString);
            Trades t2 = new Trades();
            t2.setLot(lots);
            t2.setDirect(p2direct);
            t2.setEntryPrice(p2);
            t2.setId(++id);
            t2.setSymbol(new Symbol("p2"));
            t2.setComment(dString);
            trades.add(t1);
            trades.add(t2);
            
            getcurrent(p1, p2);
            
            
        }
        
        
        
        
        
        
        //开仓逻辑
        if(count==0)
        {
            
            int num = new Random().nextInt();
            TradeType t1direct ,t2direct ;
            
            
            if(num%2==0)
            {
                t1direct = TradeType.BUY;
                t2direct = TradeType.SELL;
            }else{
                
                t2direct = TradeType.BUY;
                t1direct = TradeType.SELL;
            }
            
            
            Trades t1 = new Trades();
            t1.setLot(1000);
            t1.setDirect(t1direct);
            t1.setEntryPrice(p1);
            t1.setId(++id);
            t1.setSymbol(new Symbol("p1"));
            t1.setComment(dString);
            Trades t2 = new Trades();
            t2.setLot(1000);
            t2.setDirect(t2direct);
            t2.setEntryPrice(p2);
            t2.setId(++id);
            t2.setSymbol(new Symbol("p2"));
            t2.setComment(dString);
            trades.add(t1);
            trades.add(t2);
            
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    }
    int id = 0 ;
    int count = 0;

    double profit = 0.0;
    TradeType p1direct ;
    TradeType p2direct ;
    Trades currTrade = null;
    
    void getcurrent(Double p1, Double p2) {
        count = 0;

        profit = 0.0;
        for (int i = 0; i < trades.size(); i++) {
            currTrade = trades.get(i);
            count++;
            if (currTrade.getSymbol().getName().equals("p1")) {
                
                if (currTrade.getDirect() == TradeType.BUY) {
                    p1direct = TradeType.BUY ;
                    profit += currTrade.getLot()*(p1-currTrade.getEntryPrice()-0.00035);
                    
                }else if (currTrade.getDirect() == TradeType.SELL) {
                    p1direct = TradeType.SELL ;
                    profit += currTrade.getLot()*(currTrade.getEntryPrice()-p1-0.00035);
                    
                    
                }
                
            }else if (currTrade.getSymbol().getName().equals("p2")) {
                
                if (currTrade.getDirect() == TradeType.BUY) {
                    p2direct = TradeType.BUY ;
                    profit += currTrade.getLot()*(p2-currTrade.getEntryPrice()-0.00035);
                    
                }else if (currTrade.getDirect() == TradeType.SELL) {
                    p2direct = TradeType.SELL ;
                    profit += currTrade.getLot()*(currTrade.getEntryPrice()-p2-0.00035);
                    
                    
                }
                
            }

        }

    }
int closeall = 0 ;
    void closeAll(Double p1,Double p2)
    {
        closeall++;
        for (int i = trades.size()-1; i >= 0; i--) {
            currTrade = trades.get(i);

            if (currTrade.getSymbol().getName().equals("p1")) {
                
                
                if (currTrade.getDirect() == TradeType.BUY) {
                    currTrade.setComment(currTrade.getComment()+"["+(closeall)+"]");
                    currTrade.setExitPrice(p1);
                    currTrade.setProfit(currTrade.getLot()*(p1-currTrade.getEntryPrice()-0.00035));
                    history.add(currTrade);
                    trades.remove(i);
                    
                    
                }else if (currTrade.getDirect() == TradeType.SELL) {
                    currTrade.setComment(currTrade.getComment()+"["+(closeall)+"]");
                    currTrade.setExitPrice(p1);
                    currTrade.setProfit(currTrade.getLot()*(currTrade.getEntryPrice()-p1-0.00035));
                    history.add(currTrade);
                    trades.remove(i);
                    
                    
                }
                
            }else if (currTrade.getSymbol().getName().equals("p2")) {
                
                
                if (currTrade.getDirect() == TradeType.BUY ) {
                    currTrade.setComment(currTrade.getComment()+"["+(closeall)+"]");
                    currTrade.setExitPrice(p2);
                    currTrade.setProfit(currTrade.getLot()*(p2-currTrade.getEntryPrice()-0.00035));
                    history.add(currTrade);
                    trades.remove(i);
                    
                    
                }else if (currTrade.getDirect() == TradeType.SELL ) {
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
