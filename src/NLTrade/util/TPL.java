package NLTrade.util;



import NLTrade.CTrade;
import NLTrade.Model.Symbol;
import NLTrade.Model.Trades;
import NLTrade.Model.TradeType;
import alilibs.AliFile;
//import alilibs.AliHTTP;
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

public class TPL {

    /*
     * 
     * <object> type=101 name=fuck descr=Text style=1 angle=0 date1=1419339600
     * value1=0.781700 fontsz=10 fontnm=Arial anchorpos=0 </object>
     * 
     */
    String[] args;
    CTrade ct;
    //String url = "";
    Config cfg ;
List<String> lishijilu = new ArrayList<String>();
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

    public TPL(String[] args, CTrade ct) throws Exception {
        cfg = new Config("../config/load.cfgx","nomal");
        //url = "http://"+cfg.getUrl()+"/";
        this.args = args;
        this.ct = ct;
        File f = new File("../result");
        if (f.exists() == false) {
            f.mkdir();
        }
        sdf = new SimpleDateFormat("yyyy/M/d H:mm");
        df = new DecimalFormat("#0.00000");
        df2 = new DecimalFormat("#0.00");
        pw = new PrintWriter(new FileOutputStream(new File("../result/" + ct.getSymbol().getName() + ".tpl")));
        pw2 = new PrintWriter(new FileOutputStream(new File("../result/" + ct.getSymbol().getName() + ".txt")));
        pw3 = new PrintWriter(new FileOutputStream(new File("../result/" + ct.getSymbol().getName() + "_result.csv")));

        pw4 = new PrintWriter(new FileOutputStream(new File("../result/" + ct.getSymbol().getName() + "_dt.txt")));
        AliLog.println("正在保存MT5模板、历史记录，请稍等...");
        pw.println(TPL.getHead());

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
        for (int i = 0; i < ct.OrdersHistoryTotal(); i++) {

            t = "";

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
        AliFile.delete("../result/"+ct.getSymbol().getName()+"_lishijilu.txt");
        AliFile.append("../result/"+ct.getSymbol().getName()+"_lishijilu.txt", lishijilu);
        
        pw.close();
        pw2.close();
        pw3.close();
        pw4.close();

        List<String> aaa_list = new ArrayList<>();

        List<String> a1= AliFile.getString("../res/aaa-1.data");
        for(int i=0;i<a1.size();i++)
        {
            System.out.println(a1.get(i));
            aaa_list.add(a1.get(i));
        }

        List<String> value= AliFile.getString("../result/" + ct.getSymbol().getName() + "_dt.txt");
        for(int i=0;i<value.size();i++)
        {
            aaa_list.add(value.get(i));
        }

        List<String> a2= AliFile.getString("../res/aaa-2.data");
        for(int i=0;i<a2.size();i++)
        {
            aaa_list.add(a2.get(i));
        }

        List<String> dt= AliFile.getString("../result/" + ct.getSymbol().getName() + ".txt");
        for(int i=0;i<dt.size();i++)
        {
            aaa_list.add(dt.get(i));
        }

        List<String> a3= AliFile.getString("../res/aaa-3.data");
        for(int i=0;i<a3.size();i++)
        {
            aaa_list.add(a3.get(i));
        }

        AliFile.writenew("../result/aaa.html",aaa_list);


        List<String> bbb_list = new ArrayList<>();

        List<String> b1= AliFile.getString("../res/bbb-1.data");
        for(int i=0;i<b1.size();i++)
        {
            bbb_list.add(b1.get(i));
        }

        List<String> history= AliFile.getString("../result/" + ct.getSymbol().getName() + "_lishijilu.txt");
        for(int i=0;i<history.size();i++)
        {
            bbb_list.add(history.get(i));
        }

        List<String> b2= AliFile.getString("../res/bbb-2.data");
        for(int i=0;i<b2.size();i++)
        {
            bbb_list.add(b2.get(i));
        }

        AliFile.writenew("../result/bbb.html",bbb_list);


        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler file://"
                + System.getProperty("user.dir")+"/../result/aaa.html");


        //rundll32 url.dll,FileProtocolHandler http://www.jb51.net

        if (cfg.getWebUploadString().equals("ON")) {
            //new File("result/" + ct.getSymbol().getName() + ".tpl")
            //new File("result/" + ct.getSymbol().getName() + ".tpl")
           // AliLog.println("上传模板->" + AliHTTP.uploadFile("result/" + ct.getSymbol().getName() + ".tpl", url+"nltrade/fupan/upload.php"));
            //AliLog.println("上传历史记录->" + AliHTTP.uploadFile("result/" + ct.getSymbol().getName() + ".txt", url+"nltrade/fupan/upload.php"));
         //   AliLog.println("上传历史记录_dt->" + AliHTTP.uploadFile("result/" + ct.getSymbol().getName() + "_dt.txt", url+"nltrade/fupan/upload.php"));
         //   AliLog.println("上传历史记录_detail->" +AliHTTP.uploadFile("result/"+ct.getSymbol().getName()+"_lishijilu.txt", url+"nltrade/fupan/upload.php"));
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
