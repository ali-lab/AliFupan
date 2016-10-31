package NLTrade.util;




import NLTrade.CTradeMM;
import NLTrade.Model.Trades;
import NLTrade.Model.TradeType;
import alilibs.AliFile;
import alilibs.AliLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TPLMM {

    /*
     * 
     * <object> type=101 name=fuck descr=Text style=1 angle=0 date1=1419339600
     * value1=0.781700 fontsz=10 fontnm=Arial anchorpos=0 </object>
     * 
     */
    //String[] args;
    CTradeMM ct;
    String url = "";
    Config cfg ;
List<String> lishijilu = new ArrayList<String>();
SimpleDateFormat sdf222 ;
    public static String getHead() {
        String x = "";

        x += "<chart>\n";
        x += "id=130819358195771932\n";
        x += "symbol=EURGBP\n";
        x += "period_type=1\n";
        x += "period_size=1\n";
        x += "digits=5\n";
        x += "tick_size=0.000000\n";
        x += "position_time=0\n";
        x += "scale_fix=0\n";
        x += "scale_fixed_min=0.693300\n";
        x += "scale_fixed_max=0.714100\n";
        x += "scale_fix11=0\n";
        x += "scale_bar=0\n";
        x += "scale_bar_val=1.000000\n";
        x += "scale=8\n";
        x += "mode=1\n";
        x += "fore=0\n";
        x += "grid=0\n";
        x += "volume=0\n";
        x += "scroll=0\n";
        x += "shift=0\n";
        x += "shift_size=20.069505\n";
        x += "fixed_pos=0.000000\n";
        x += "ohlc=0\n";
        x += "one_click=0\n";
        x += "bidline=1\n";
        x += "askline=0\n";
        x += "lastline=1\n";
        x += "days=0\n";
        x += "descriptions=0\n";
        x += "tradelines=1\n";
        x += "window_left=0\n";
        x += "window_top=0\n";
        x += "window_right=0\n";
        x += "window_bottom=0\n";
        x += "window_type=1\n";
        x += "background_color=0\n";
        x += "foreground_color=16777215\n";
        x += "barup_color=65280\n";
        x += "bardown_color=255\n";
        x += "bullcandle_color=65280\n";
        x += "bearcandle_color=255\n";
        x += "chartline_color=65280\n";
        x += "volumes_color=3329330\n";
        x += "grid_color=10061943\n";
        x += "bidline_color=10061943\n";
        x += "askline_color=255\n";
        x += "lastline_color=49152\n";
        x += "stops_color=255\n";
        x += "windows_total=1\n";
        x += "<window>\n";
        x += "height=100.000000\n";
        x += "objects=0\n";
        x += "<indicator>\n";
        x += "name=Main\n";
        x += "path=\n";
        x += "apply=1\n";
        x += "show_data=1\n";
        x += "scale_inherit=0\n";
        x += "scale_line=0\n";
        x += "scale_line_percent=50\n";
        x += "scale_line_value=0.000000\n";
        x += "scale_fix_min=0\n";
        x += "scale_fix_min_val=0.000000\n";
        x += "scale_fix_max=0\n";
        x += "scale_fix_max_val=0.000000\n";
        x += "expertmode=0\n";
        x += "fixed_height=-1\n";
        x += "</indicator>\n";

        return x;

    }

    /**
     *
     *
     * 增加一个均线在MT5图表上
     *
     * @param len 均线的周期
     * @param Mode 1 close, 2 open, 3 high, 4 low
     *
     */
    public void addMv(int len, int Mode) {
        String x = "";
        x += "<indicator>\n";
        x += "name=Moving Average\n";
        x += "path=\n";
        x += "apply=" + Mode + "\n";
        x += "show_data=1\n";
        x += "scale_inherit=0\n";
        x += "scale_line=0\n";
        x += "scale_line_percent=50\n";
        x += "scale_line_value=0.000000\n";
        x += "scale_fix_min=0\n";
        x += "scale_fix_min_val=0.000000\n";
        x += "scale_fix_max=0\n";
        x += "scale_fix_max_val=0.000000\n";
        x += "expertmode=0\n";
        x += "fixed_height=-1\n";
        x += "<graph>\n";
        x += "name=\n";
        x += "draw=129\n";
        x += "style=0\n";
        x += "width=1\n";
        x += "color=255\n";
        x += "</graph>\n";
        x += "period=" + len + "\n";
        x += "method=0\n";
        x += "</indicator>\n";

        pw.println(x);

    }

    private void addFoot() {

        String x = "";
        x += "</window>\n";
        x += "</chart>\n";

        pw.println(x);

    }

    DecimalFormat df;
    DecimalFormat df2;
    PrintWriter pw;
    PrintWriter pw2;
    PrintWriter pw3;
    PrintWriter pw4;
    SimpleDateFormat sdf;

    public TPLMM(CTradeMM ct) throws Exception {
        cfg = new Config("../config/load.cfgx","nomal");
        url = "http://"+cfg.getUrl()+"/";
        
        this.ct = ct;
        File f = new File("result");
        if (f.exists() == false) {
            f.mkdir();
        }
        sdf = new SimpleDateFormat("yyyy/M/d H:mm");
         sdf222 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        df = new DecimalFormat("#0.00000");
        df2 = new DecimalFormat("#0.00");
        pw = new PrintWriter(new FileOutputStream(new File("/Applications/XAMPP/xamppfiles/htdocs/nltrade/fupan/upload/" +  "ALL.tpl")));
        pw2 = new PrintWriter(new FileOutputStream(new File("/Applications/XAMPP/xamppfiles/htdocs/nltrade/fupan/upload/" + "ALL.txt")));
        pw3 = new PrintWriter(new FileOutputStream(new File("/Applications/XAMPP/xamppfiles/htdocs/nltrade/fupan/upload/"  + "ALL_result.csv")));

        pw4 = new PrintWriter(new FileOutputStream(new File("/Applications/XAMPP/xamppfiles/htdocs/nltrade/fupan/upload/"+   "ALL_dt.txt")));
        AliLog.println("正在保存MT5模板、历史记录，请稍等...");
        pw.println(TPLMM.getHead());

    }

    /**
     * 增加一个趋势线
     *
     * @param price1
     * @param price2
     * @param date1
     * @param date2
     */
    public void addTrendLine(double price1, double price2, Date date1, Date date2) {
        String x = "";
        x += "<object>\n";
        x += "type=2\n";
        x += "name=H1 Trendline\n";
        x += "date1=" + ((date1.getTime() + 8 * 60 * 60 * 1000) + "").substring(0, 10) + "\n";
        x += "date2=" + ((date2.getTime() + 8 * 60 * 60 * 1000) + "").substring(0, 10) + "\n";
        x += "value1=" + price1 + "\n";
        x += "value2=" + price2 + "\n";
        x += "</object>\n";
        pw.println(x);

    }

    
    int nowjindu = 0 ;
    int lastjindu = 0 ;
    public void saveToTPL() throws Exception {
         
        String t = "";
        double profits = 0;
        Date from = null;
        Date end = null;
        double deep = 0.0;
        double top = 0;
        int serieslosstimes = 0;
        double serieslosspoint = 0.0;
        int tmpsserieslosstimes = 0;
        //double tmpsserieslosspoint = 0.0;
        
        //String urlremove = url+"nltrade/fupan/delete.php?symbol=" + ct.getSymbol().getName();
        //if (config.getWebUploadString().equals("ON")) {
        //    AliLog.println("清除结果," + AliHTTP.getString(urlremove));
        //}
        //String urlInsertResult = "";

        double result = 0.0;
        int lots = 0;
        List<String> tongji = new ArrayList<String>();
        for (int i = 0; i < ct.OrdersHistoryTotal(); i++) {
            
            t = "";

            // open
            Trades c = ct.getListHistory().get(i);
            tongji.add(sdf222.format(c.getExitDate())+","+df2.format(c.getProfit())+","+c.getComment());
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
            lots += c.getLot();
            if (c.getProfit() > 0) {
                if (tmpsserieslosstimes > serieslosstimes) {
                    serieslosstimes = tmpsserieslosstimes;

                }
                tmpsserieslosstimes = 0;
            } else {

                tmpsserieslosstimes++;

            }

            t += "<object>\n";
            t += "type=" + (c.getDirect() == TradeType.BUY ? "31" : "32") + "\n";
            t += "name=open" + "\n";
            t += "color=" + (c.getDirect() == TradeType.BUY ? "11296515" : "1918177") + "\n";
            // t += "selectable=0\n";
            t += "date1=" + ((c.getEntryTime().getTime() + 8 * 60 * 60 * 1000) + "").substring(0, 10) + "\n";
            t += "value1=" + c.getEntryPrice() + "\n";

            t += "</object>\n";

            // close
            t += "<object>\n";
            t += "type=" + (c.getDirect() == TradeType.BUY ? "32" : "31") + "\n";
            t += "name=" + "close\n";
            t += "color=" + (c.getDirect() == TradeType.BUY ? "1918177" : "11296515") + "\n";
            t += "selectable=0\n";
            t += "date1=" + ((c.getExitDate().getTime() + 8 * 60 * 60 * 1000) + "").substring(0, 10) + "\n";
            t += "value1=" + c.getExitPrice() + "\n";

            t += "</object>\n";

            // line
            t += "<object>\n";
            t += "type=2\n";
            t += "name=line\n";
            t += "descr=haveclose\n";
            t += "color=" + (c.getDirect() == TradeType.BUY ? "11296515" : "1918177") + "\n";
            t += "style=2\n";
            t += "selectable=0\n";
            t += "date1=" + ((c.getEntryTime().getTime() + 8 * 60 * 60 * 1000) + "").substring(0, 10) + "\n";
            t += "date2=" + ((c.getExitDate().getTime() + 8 * 60 * 60 * 1000) + "").substring(0, 10) + "\n";
            t += "value1=" + c.getEntryPrice() + "\n";

            t += "value2=" + c.getExitPrice() + "\n";
            t += "</object>\n";

            // ID
            t += "<object>\n";
            t += "type=109" + "\n";
            t += "name=open" + "\n";
            t += "descr=" + c.getId() + "\n";
            t += "color=16119285\nselectable=0\n";
            t += "date1=" + ((c.getEntryTime().getTime() + 8 * 60 * 60 * 1000) + "").substring(0, 10) + "\n";

            t += "</object>\n";
            result += c.getProfit();
            pw2.print(df.format(result) + (i == ct.OrdersHistoryTotal() - 1 ? "" : ","));
            pw4.print("'" + sdf.format(ct.getListHistory().get(i).getEntryTime()) + "'" + (i == ct.OrdersHistoryTotal() - 1 ? "" : ","));
            //pw3.println(c.getId() + "," + new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(c.getEntryTime()) + "," + df.format(c.getProfit() * c.getSymbol().getPoint()) + "," + c.getComment());
            
            uploadHistory(i+1+"", c.getProfit(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getEntryTime()),c.getComment(),c.getLot()+"");
            
            
            
            
           // urlInsertResult = url+"nltrade/fupan/insert.php?dt=" + new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss").format(c.getEntryTime())
            //       + "&symbol=" + ct.getSymbol().getName() + "&profits=" + c.getProfit() + "&comment=" + c.getComment().replaceAll("\\[", "%5b").replaceAll("\\]", "%5d");

            //nowjindu = (i+1)*100/ ct.OrdersHistoryTotal();
            
            
            
            //if (config.getWebUploadString().equals("ON")) {
                
                //AliHTTP.getString(urlInsertResult);
                
               // if(lastjindu!=nowjindu)
               // AliLog.println( "正在将历史记录上传服务器["+ nowjindu + "%]"  );
           // }
            
            
            
            
            
            
            
            
            
            //lastjindu = nowjindu ;
            pw.println(t);

            pw.flush();
            pw2.flush();
            pw3.flush();
            pw4.flush();

        }

        addFoot();
        AliFile.delete("/Applications/XAMPP/xamppfiles/htdocs/nltrade/fupan/upload/"+"ALL_lishijilu.txt");
        AliFile.append("/Applications/XAMPP/xamppfiles/htdocs/nltrade/fupan/upload/"+"ALL_lishijilu.txt", lishijilu);
        AliFile.append("result/tongji.csv", tongji);
        pw.close();
        pw2.close();
        pw3.close();

        if (cfg.getWebUploadString().equals("ON")) {
            //new File("result/" + ct.getSymbol().getName() + ".tpl")
            //new File("result/" + ct.getSymbol().getName() + ".tpl")
            //AliLog.println("上传模板->" + AliHTTP.uploadFile("result/" +  "ALL.tpl", url+"nltrade/fupan/upload.php"));
            //AliLog.println("上传历史记录->" + AliHTTP.uploadFile("result/" +  "ALL.txt", url+"nltrade/fupan/upload.php"));
            //AliLog.println("上传历史记录_dt->" + AliHTTP.uploadFile("result/" + "ALL_dt.txt", url+"nltrade/fupan/upload.php"));
            //AliLog.println("上传历史记录_detail->" +AliHTTP.uploadFile("result/"+"ALL_lishijilu.txt", url+"nltrade/fupan/upload.php"));
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

        tables.appendColum("Trade Days").appendColum("Total(Lots)").appendColum("PL/Day").appendColum("Lots/Day")
                .appendColum("P/L").appendColum("PL/Lots").appendColum("DrawDown")
                .appendColum("DrawDown/Years").appendColum("SeriesLossTimes");
        tables.appendRow();
        tables.appendColum(days).appendColum(ct.OrdersHistoryTotal() + "(" + lots + ")").appendColum(df2.format((profits / days))).appendColum(df2.format(ct.OrdersHistoryTotal() / 1.0 / (int) days))
                .appendColum(df2.format(profits)).appendColum(df2.format(profits / ct.OrdersHistoryTotal())).appendColum(df2.format(deep))
                .appendColum(df2.format(deep / (profits / days * 260) * 100) + "%").appendColum(serieslosstimes);

        AliLog.println(tables.toString());

    }

    
    
    DecimalFormat df5 = new DecimalFormat("#.00");
    
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
