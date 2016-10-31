package NLTrade.Model;


import java.io.Serializable;
import java.util.Date;

public class TickM implements Serializable {

    private double[] price;

    public double []getPrice() {
        return price;
    }

    public void setPrice(double[] price) {
        this.price = price;
    }

    public Date[] getTime() {
        return time;
    }

    public void setTime(Date []time) {
        this.time = time;
    }

    public int[] getVolumn() {
        return volumn;
    }

    public void setVolumn(int[] volumn) {
        this.volumn = volumn;
    }
    private Date []time;
    private int []volumn;

}
