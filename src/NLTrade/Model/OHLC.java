package NLTrade.Model;
import java.util.Date;

public class OHLC {

    private double open;
    private double high;
    private double low;
    private double close;
    private int volumn;
    private Date date;

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public int getVolumn() {
        return volumn;
    }

    public void setVolumn(int volumn) {
        this.volumn = volumn;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getValue(OHLCType o) {

        if (o == OHLCType.CLOSE) {
            return close;
        } else if (o == OHLCType.OPEN) {
            return open;
        } else if (o == OHLCType.HIGH) {
            return high;
        } else if (o == OHLCType.LOW) {
            return low;
        } else {
            return 0.0;
        }

    }

}
