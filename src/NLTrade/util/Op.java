package NLTrade.util;

import NLTrade.CTrade;
import NLTrade.Model.Trades;
import alilibs.AliFile;
import alilibs.AliLog;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author a
 */
public class Op {

    public static void saveToOP(CTrade ct, String[] args) throws IOException {
        DecimalFormat df = new DecimalFormat("#0.00000");
        DecimalFormat df2 = new DecimalFormat("#0.00");

        double profits = 0;
        Date from = null;
        Date end = null;
        double deep = 0.0;
        double top = 0;
        int serieslosstimes = 0;
        double serieslosspoint = 0.0;
        int tmpsserieslosstimes = 0;
        double tmpsserieslosspoint = 0.0;
        for (int i = 0; i < ct.OrdersHistoryTotal(); i++) {

            // open
            Trades c = ct.getListHistory().get(i);

            if (i == 0) {
                from = c.getEntryTime();
            }
            if (i == ct.OrdersHistoryTotal() - 1) {
                end = c.getEntryTime();
            }

            profits += c.getProfit();

            if (top == 0 || profits > top) {
                top = profits;
            }

            if (top - profits > deep) {
                deep = top - profits;
            }

            if (c.getProfit() > 0) {
                if (tmpsserieslosstimes > serieslosstimes) {
                    serieslosstimes = tmpsserieslosstimes;
                    serieslosspoint = tmpsserieslosspoint;
                    tmpsserieslosspoint = 0.0;
                    tmpsserieslosstimes = 0;

                }

            } else {

                tmpsserieslosspoint += c.getProfit();
                tmpsserieslosstimes++;

            }

        }

        Calendar cfrom = Calendar.getInstance();

        if (from == null) {

            AliLog.error("没有成交一单,请查看逻辑");

            return;

        }

        cfrom.setTime(from);
        long lform = cfrom.getTimeInMillis();

        Calendar cend = Calendar.getInstance();
        cend.setTime(end);
        long lend = cend.getTimeInMillis();
        long days = (lend - lform) / 1000 / 60 / 1440 * 5 / 7;

        ConsoleTable tables = new ConsoleTable(9, true);

        tables.appendRow();

        tables.appendColum("Trade Days").appendColum("Lots").appendColum("PL/Day").appendColum("Lots/Day")
                .appendColum("P/L").appendColum("PL/Lots").appendColum("DrawDown")
                .appendColum("DrawDown/Years").appendColum("SeriesLossTimes");
        tables.appendRow();
        tables.appendColum(days).appendColum(ct.OrdersHistoryTotal()).appendColum(df2.format((profits / days * ct.getSymbol().getPoint()))).appendColum(df2.format(ct.OrdersHistoryTotal() / 1.0 / (int) days))
                .appendColum(df2.format(profits * ct.getSymbol().getPoint())).appendColum(df2.format(profits / ct.OrdersHistoryTotal() * ct.getSymbol().getPoint())).appendColum(df2.format(deep * ct.getSymbol().getPoint()))
                .appendColum(df2.format(deep / (profits / days * 260) * 100) + "%").appendColum(serieslosstimes);

        AliLog.println(tables.toString());

        if (args.length == 1) {
            return;
        }

        String opresult = "";
        String ophead = "";
        for (int i = 1; i < args.length; i++) {
            opresult += args[i] + ",";
            ophead += "args" + i + ",";
        }
        ophead += "trade days,lots,pl/day,lots/day,P/L,PL/lots,DrawDown,DrawDown/Years,SeriesLossTimes";

        opresult += days + ",";
        opresult += ct.OrdersHistoryTotal() + ",";
        opresult += df2.format((profits / days * ct.getSymbol().getPoint())) + ",";
        opresult += df2.format(ct.OrdersHistoryTotal() / 1.0 / (int) days) + ",";
        opresult += df2.format(profits * ct.getSymbol().getPoint()) + ",";
        opresult += df2.format(profits / ct.OrdersHistoryTotal() * ct.getSymbol().getPoint()) + ",";
        opresult += df2.format(deep * ct.getSymbol().getPoint()) + ",";
        opresult += df2.format(deep / (profits / days * 260) * 100) + ",";
        opresult += serieslosstimes;

        List<String> list = new ArrayList<String>();
        if (!AliFile.isExit("result/op.csv")) {
            list.add(ophead);
        }
        list.add(opresult);
        AliFile.append("result/op.csv", list);

    }
}
