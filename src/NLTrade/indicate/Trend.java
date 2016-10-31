package NLTrade.indicate;

import java.util.ArrayList;
import java.util.List;

import NLTrade.Model.OHLC;
import NLTrade.Model.OHLCType;

public class Trend {

    MovingV q;
    MovingV s;

    public Trend(int quick, int slow, int count, OHLCType t, List<OHLC> dat) {
        value = new ArrayList<Double>();
        this.type = t;
        this.data = dat;

        this.Length = count;

        q = new MovingV(quick, t, data);
        s = new MovingV(slow, t, data);

    }

    private List<OHLC> data;

    private int Length;

    private OHLCType type;

    private List<Double> value;

    public void cal(boolean isnew) {

        q.cal(isnew);
        s.cal(isnew);
        double tmp = 0.0;
        if (s.getValue().size() < Length) {
            return;
        }
        double double_q = q.getValue().get(0);
        double double_s = s.getValue().get(0);
        int count = 0;

        for (int i = 0; i < Length; i++) {
            if (q.getValue().get(i) > s.getValue().get(i)) {
                count++;
            } else if (q.getValue().get(i) < s.getValue().get(i)) {
                count--;
            }

        }

        if (count == Length) {
            tmp = 1;
        } else if (count == Length * -1) {
            tmp = -1;
        } else {
            tmp = 0;
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
