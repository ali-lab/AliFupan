package NLTrade;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import NLTrade.Model.Symbol;
import NLTrade.Model.Tick;
import NLTrade.Model.Trades;
import NLTrade.Model.TradeType;
import NLTrade.util.Config;
import alilibs.AliLog;
import java.text.SimpleDateFormat;

public class CTrade {

    
    Config config ;
    
    private static CTrade mtrade;
    private List<Trades> list;
    private long tempid = 0;
    private Symbol symbol;
    private SimpleDateFormat sdf;
    //double commont = 0;
    //private double profitper1 = 0 ;
    //private double onepoint = 0.0 ;
    private List<Trades> getguaList;
    public List<Trades> getList() {
        return list;
    }
    
    public List<Trades>getListGua()
    {
        getguaList.clear();
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).getDirect()!=TradeType.BUY && list.get(i).getDirect()!=TradeType.SELL)
            {
               getguaList.add(list.get(i));
            }
            
            
        }
        
        return getguaList ;
    }
    
    

    public List<Trades> getListHistory() {
        return listHistory;
    }

    private List<Trades> listHistory;

    public int OrdersToatal() {
        return list.size();
    }

    public int OrdersHistoryTotal() {
        return listHistory.size();
    }

    private CTrade() {

        getguaList = new ArrayList<Trades>();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        config = new Config("../config/load.cfgx","nomal");
        
        if (list == null) {
            list = new ArrayList<Trades>();
        }
        if (listHistory == null) {
            listHistory = new ArrayList<Trades>();
        }

    }

    public static CTrade getInstance() {

        if (mtrade == null) {
            mtrade = new CTrade();
        }

        return mtrade;
    }

    public static void setNull() {

        mtrade = null;
    }

    public void runWeituo(Tick t) {
        // sl tp
        Trades ct;
        double sl;
        double tp;
        TradeType direct;
        for (int i = list.size() - 1; i >= 0; i--) {
            ct = list.get(i);
            sl = ct.getStopLoss();
            tp = ct.getTakeProfit();
            direct = ct.getDirect();
            if (ct.getStopLoss() > 0) {
                if (direct == TradeType.BUY && t.getPrice() <= sl) {
                    AliLog.debug(this, "多单止损" + " 时间" + sdf.format(t.getTime()) + " 价格" + t.getPrice());
                    ct = closeByTrade(ct, t, ct.getStopLoss(), "sl");

                } else if (direct == TradeType.SELL && t.getPrice() >= sl) {
                    AliLog.debug(this, "空单止损" + " 时间" + sdf.format(t.getTime()) + " 价格" + t.getPrice());
                    ct = closeByTrade(ct, t, ct.getStopLoss(), "sl");
                }

            }

            if (ct.getTakeProfit() > 0) {
                if (direct == TradeType.BUY && t.getPrice() >= tp) {

                    ct = closeByTrade(ct, t, ct.getTakeProfit(), "tp");
                    AliLog.debug(this, "多单止盈" + " 时间" + sdf.format(t.getTime()) + " 价格" + t.getPrice());
                } else if (direct == TradeType.SELL && t.getPrice() <= tp) {
                    AliLog.debug(this, "空单止盈" + " 时间" + sdf.format(t.getTime()) + " 价格" + t.getPrice());
                    ct = closeByTrade(ct, t, ct.getTakeProfit(), "tp");
                }

            }

            if (ct.getSltail() > 0) {
                if (direct == TradeType.BUY && t.getPrice() - ct.getEntryPrice() > ct.getSltail()) {
                    if (t.getPrice() - ct.getStopLoss() > ct.getSltail()) {
                        ct.setStopLoss(t.getPrice() - ct.getSltail());

                    }

                }
                if (direct == TradeType.SELL && ct.getEntryPrice() - t.getPrice() > ct.getSltail()) {
                    if (ct.getStopLoss() - t.getPrice() > ct.getSltail()) {
                        ct.setStopLoss(t.getPrice() + ct.getSltail());

                    }

                }

            }
            
            //成交挂单
            
            switch (direct) {
                case BUYLIMIT:
                    if(t.getPrice()<=ct.getEntryPrice()){
                        ct.setDirect(TradeType.BUY);
                        ct.setEntryTime(t.getTime());
                    }   break;
                case BUYSTOP:
                    if(t.getPrice()>=ct.getEntryPrice()){
                        ct.setDirect(TradeType.BUY);
                        ct.setEntryTime(t.getTime());
                    }   break;
                case SELLLIMIT:
                    if(t.getPrice()>=ct.getEntryPrice()){
                        ct.setDirect(TradeType.SELL);
                        ct.setEntryTime(t.getTime());
                    }   break;
                case SELLSTOP:
                    if(t.getPrice()<=ct.getEntryPrice()){
                        ct.setDirect(TradeType.SELL);
                        ct.setEntryTime(t.getTime());
                    }   break;
                default:
                    break;
            }  
            
            
            
            
            
            
            
            
            
            

        }

    }

    private Trades closeByTrade(Trades t, Tick tick, double target, String comment) {
        t.setExitDate(tick.getTime());
        t.setExitPrice(target);
        t.setComment(t.getComment() + "[" + comment + "]");
        if (t.getDirect() == TradeType.BUY || t.getDirect() == TradeType.SELL) {
            t.setProfit(t.getDirect() == TradeType.BUY ? (t.getExitPrice() - t.getEntryPrice()) * t.getLot()*symbol.getValueper1() : (t.getEntryPrice() - t.getExitPrice()) * t.getLot()*symbol.getValueper1());
        }
                t.setProfit(t.getProfit()-t.getLot()*symbol.getCommition()*2);
        listHistory.add(t);
        list.remove(t);
        return t;

    }

    public void sendNew(Symbol symbol, double lots, TradeType type, double price, Date opentime, double sl, double tp, double sltail,
            String comment) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");

        if (sl > 0) {
            if ((type == TradeType.BUY && sl > price) || (type == TradeType.SELL && sl < price)) {
                AliLog.error(this, type + "止损设置错误！！price=" + price + "  sl=" + sl + " " + sdf.format(opentime) + " " + comment);
                return;
            }

        }
        if (tp > 0) {
            if ((type == TradeType.BUY && tp < price) || (type == TradeType.SELL && tp > price)) {
                AliLog.error(type + "止盈设置错误！！price=" + price + "  tp=" + tp + " " + sdf.format(opentime) + " " + comment);
                return;
            }

        }

        Trades t = new Trades();
        t.setSymbol(symbol);
        t.setLot(lots);
        t.setDirect(type);
        t.setEntryPrice(price);
        t.setEntryTime(opentime);
        t.setStopLoss(sl);
        t.setTakeProfit(tp);
        t.setId(++tempid);
        t.setComment(comment);
        t.setSltail(sltail);
        list.add(t);

    }

    public void closeAll(Tick tick) {
        for (int i = list.size() - 1; i >= 0; i--) {

            Trades t = list.get(i);
            // pingcang
            t.setExitDate(tick.getTime());
            t.setExitPrice(tick.getPrice());
            if (t.getDirect() == TradeType.BUY || t.getDirect() == TradeType.SELL) {
                t.setProfit(t.getDirect() == TradeType.BUY ? (t.getExitPrice() - t.getEntryPrice()) * t.getLot()*symbol.getValueper1() : (t.getEntryPrice() - t.getExitPrice()) * t.getLot()*symbol.getValueper1());
            }
            
            t.setProfit(t.getProfit()-t.getLot()*symbol.getCommition()*2);
            // YISONG
            
            
            
            listHistory.add(t);
            list.remove(i);

        }

    }

    public void closeAllGua() {
        
        TradeType t ;
        for (int i = list.size() - 1; i >= 0; i--) {
            t = list.get(i).getDirect();
            if(t!=TradeType.BUY&&t!=TradeType.SELL)
            
            list.remove(i);

        }

    }
    
    
    
    
    
    public void closeAll(TradeType type, Tick tick) {
        for (int i = list.size() - 1; i >= 0; i--) {

            if (type == list.get(i).getDirect()) {

                Trades t = list.get(i);
                // pingcang
                t.setExitDate(tick.getTime());
                t.setExitPrice(tick.getPrice());
                if (t.getDirect() == TradeType.BUY || t.getDirect() == TradeType.SELL) {
                    t.setProfit(t.getDirect() == TradeType.BUY ? (t.getExitPrice() - t.getEntryPrice()) * t.getLot()*symbol.getValueper1() : (t.getEntryPrice() - t.getExitPrice()) * t.getLot()*symbol.getValueper1());
                }
                t.setProfit(t.getProfit()-t.getLot()*symbol.getCommition()*2);
                // YISONG
                listHistory.add(t);
                list.remove(i);

            }

        }

    }

    public void modify(long id, double opentime, double sl, double tp) {
        for (int i = 0; i < list.size(); i++) {

            if (id == list.get(i).getId()) {
                Trades t = list.get(i);

                t.setStopLoss(sl);
                t.setTakeProfit(tp);
                if (!(t.getDirect() == TradeType.BUY || t.getDirect() == TradeType.SELL)) {

                    t.setEntryPrice(opentime);

                }

                break;
            }

        }

    }

    /**
     * 反手开，比如，开空的话，如有多单，就先平多再开空，如果没有多单，直接开空单，不存在同种货币不同方向的订单
     *
     * @param type
     * @param t
     * @param symbol
     * @param lots
     * @param sl
     * @param tp
     * @param sltail
     */
    public void Fan(TradeType type, Tick t, Symbol symbol, double lots, double sl, double tp, double sltail, String comm) {
        if (type == TradeType.BUY) {
            closeAll(TradeType.SELL, t);
        } else if (type == TradeType.SELL) {
            closeAll(TradeType.BUY, t);
        }

        if (OrdersToatal() == 0) {
            if (type == TradeType.BUY) {
                sendNew(symbol, lots, TradeType.BUY, t.getPrice(), t.getTime(), sl, tp, sltail, comm);
            } else if (type == TradeType.SELL) {
                sendNew(symbol, lots, TradeType.SELL, t.getPrice(), t.getTime(), sl, tp, sltail, comm);
            }
        }
    }

    public void closeById(long id, Date time, double price) {
        for (int i = list.size() - 1; i >= 0; i--) {

            if (id == list.get(i).getId()) {
                Trades t = list.get(i);
                // pingcang
                t.setExitDate(time);
                t.setExitPrice(price);
                if (t.getDirect() == TradeType.BUY || t.getDirect() == TradeType.SELL) {
                    t.setProfit(t.getDirect() == TradeType.BUY ? (t.getExitPrice() - t.getEntryPrice()) * t.getLot()*symbol.getValueper1() : (t.getEntryPrice() - t.getExitPrice()) * t.getLot()*symbol.getValueper1());
                }
                
            t.setProfit(t.getProfit()-t.getLot()*symbol.getCommition()*2);
                // YISONG
                listHistory.add(t);
                list.remove(i);

                break;
            }

        }

    }

    /**
     * @return the symbol
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;

        //profitper1 = new Double(props[0]);
        //onepoint = new Double(props[2]);
        
    }

    public int getNetLots() {
        int x = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDirect() == TradeType.BUY) {
                x += list.get(i).getLot();
            } else if (list.get(i).getDirect() == TradeType.SELL) {
                x -= list.get(i).getLot();
            }

        }

        return x;
    }

    

    
    /**
     * 获取平均价 
     * @return 
     */
    
    public double getPrice()
    {
        double d = 0 ;
        double l = 0 ;
        for(int i=0;i<list.size();i++)
        {
            
            d+= list.get(i).getEntryPrice()*list.get(i).getLot();
            l += list.get(i).getLot();
            
        }
        
        if(list.size()>0) return d/l;
        return 0.0;
        
        
    }
    
    /**
     * 获取净利润 
     * @param t
     * @return 
     */
    
    public double getNetProfits(Tick t) {
        double x = 0;
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getDirect() == TradeType.BUY) {
                x += (t.getPrice() - list.get(i).getEntryPrice()) * list.get(i).getLot() * symbol.getValueper1();
            } else if (list.get(i).getDirect() == TradeType.SELL) {
                x += (list.get(i).getEntryPrice() - t.getPrice()) * list.get(i).getLot()* symbol.getValueper1();
            }

        }

        return x;
    }
/**
 * 获取当前头寸方向
 * @return 
 */
    public TradeType getTradeType() {

        if (list.size() > 0) {
            return list.get(0).getDirect();
        }

        return null;
    }


    
    public double getavgprice()
    {
        if(list.isEmpty()) return 0 ;
        
        double x = 0;
        double y = 0 ;
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getDirect() == TradeType.BUY) {
                x += (list.get(i).getEntryPrice()) * list.get(i).getLot() ;
            } else if (list.get(i).getDirect() == TradeType.SELL) {
                x += (list.get(i).getEntryPrice()) * list.get(i).getLot();
            }

        }
        
        for (int i = 0; i < list.size(); i++) {
             if (list.get(i).getDirect() == TradeType.BUY || list.get(i).getDirect() == TradeType.SELL)
            y+=list.get(i).getLot();
            
        }
        
        return x/y;
        
    }
    
    
    public double getPointsByAmount(double lots,double amout)
    {
        return 0.0 ;
        
        
        
        
    }
    
    
    
    

}
