package NLTrade.indicate;

import java.util.ArrayList;
import java.util.List;

import NLTrade.Model.OHLC;
import NLTrade.Model.OHLCType;

public class MovingV {

    public MovingV(int len, OHLCType t, List<OHLC> dat) {
        value = new ArrayList<Double>();
        this.Length = len;
        this.type = t;
        this.data = dat;

    }

    private List<OHLC> data;

    private int Length;

    private OHLCType type;

    private List<Double> value;
    int tmpLength;

    public void cal(boolean isnew) {
        //if(data.size()<Length) return ;
        double tmp = 0.0;

        if (data.size() < Length) {
            tmpLength = data.size();
        } else {
            tmpLength = Length;
        }

        for (int i = 0; i < tmpLength; i++) {

            tmp += data.get(i).getValue(type);

        }

        tmp = tmp / Length;

        if (value.size() == 0 || isnew) {
            if (value.size() == 0) {
                value.add(tmp);
            } else {
                value.add(0, tmp);
            }
            if (value.size() > 500) {
                value.remove(value.size() - 1);
            }

        } else {

            value.set(0, tmp);

        }

    }

    public List<Double> getValue() {
        return value;
    }

    public void setValue(List<Double> value) {
        this.value = value;
    }

}
