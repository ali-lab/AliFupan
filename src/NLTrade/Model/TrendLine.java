package NLTrade.Model;


import java.util.Date;

/**
 *
 * @author a
 */
public class TrendLine {

    private Date date1;
    private double value1;
    private Date date2;
    private double value2;

    public TrendLine(Date date1, double value1, Date date2, double value2) {
        this.date1 = date1;
        this.value1 = value1;
        this.date2 = date2;
        this.value2 = value2;
    }

    /**
     * @return the date1
     */
    public Date getDate1() {
        return date1;
    }

    /**
     * @return the value1
     */
    public double getValue1() {
        return value1;
    }

    /**
     * @return the date2
     */
    public Date getDate2() {
        return date2;
    }

    /**
     * @return the value2
     */
    public double getValue2() {
        return value2;
    }

}
