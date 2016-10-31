package NLTrade.Model;


import java.util.Date;

public class Trades {
  
    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public TradeType getDirect() {
        return direct;
    }

    public void setDirect(TradeType direct) {
        this.direct = direct;
    }

    public double getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(double entryPrice) {
        this.entryPrice = entryPrice;
    }

    public double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public double getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(double takeProfit) {
        this.takeProfit = takeProfit;
    }

    public double getExitPrice() {
        return exitPrice;
    }

    public void setExitPrice(double exitPrice) {
        this.exitPrice = exitPrice;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getSltail() {
        return sltail;
    }

    public void setSltail(double sltail) {
        this.sltail = sltail;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
    private double sltail;
    private Symbol symbol;
    private Date entryTime;
    private TradeType direct;
    private double entryPrice;
    private double stopLoss;
    private double takeProfit;
    private double exitPrice;
    private Date exitDate;
    private String comment;
    private double profit;
    private long id;
    private double lot;
    private int index ;

    /**
     * @return the lot
     */
    public double getLot() {
        return lot;
    }

    /**
     * @param lot the lot to set
     */
    public void setLot(double lot) {
        this.lot = lot;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

}
