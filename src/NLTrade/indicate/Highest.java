package NLTrade.indicate;

import java.util.ArrayList;
import java.util.List;

import NLTrade.Model.OHLC;
import NLTrade.Model.OHLCType;

public class Highest {

    /**
     *
     * @param len 周期
     * @param t K线点
     * @param dat K 线组
     */
    public Highest(int len, OHLCType t, List<OHLC> dat) {
        value = new ArrayList<Double>();
        this.Length = len;
        this.type = t;
        this.data = dat;

    }

    private List<OHLC> data;

    private int Length;

    private OHLCType type;

    private List<Double> value;

    public void cal(boolean isnew) {
        if (data.size() < Length) {
            return;
        }
        double tmp = 0.0;
        for (int i = 0; i < Length; i++) {
            if (tmp == 0.0 || data.get(i).getValue(type) > tmp) {
                tmp = data.get(i).getValue(type);
            }

        }

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
