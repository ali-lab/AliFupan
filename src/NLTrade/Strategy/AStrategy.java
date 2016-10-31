package NLTrade.Strategy;

import java.text.SimpleDateFormat;
import java.util.List;

import NLTrade.CTrade;
import NLTrade.KLines;
import NLTrade.Model.Frame;
import NLTrade.Model.Symbol;
import NLTrade.Model.Tick;
import NLTrade.util.Config;
import NLTrade.util.ConsoleTable;
import NLTrade.util.Op;
import NLTrade.util.TPL;
import alilibs.AliLog;

import java.util.Date;

public abstract class AStrategy {

    protected Config normalConfig;
    protected boolean isReal;
    protected Symbol symbol;
    protected SimpleDateFormat sdf;
    protected String[] args = new String[]{};
    private Frame frames[];

    protected KLines kl;
    protected CTrade ct;
    private int currentprocess = 0;
    private int lastprocess;
    protected boolean isjpy = false;

    public AStrategy() {

        normalConfig = new Config("../config/load.cfgx", "nomal");
        isReal = normalConfig.getRealTime().equals("ON");
        symbol = new Symbol();
        ct = CTrade.getInstance();

    }

    public void setArgs(Frame[] f, String[] a) {

        AliLog.println("当前复盘框架:" + f[0]);
        for (int i = 1; i < f.length; i++) {
            AliLog.println(f[i] + ",");
        }

        this.frames = f;
        this.args = a;
        AliLog.print("参数:");
        for (int i = 1; i < a.length; i++) {
            AliLog.print(a[i] + ",");
        }
        AliLog.println("");

        String[] tmp = args[0].split("/");

        symbol.setName(tmp[tmp.length - 1].split("\\.")[0]);
        String[] props = normalConfig.getByKey(symbol.getName()).split(",");
        symbol.setPoint(new Double(props[2]));
        symbol.setCommition(new Double(props[1]));
        symbol.setValueper1(new Integer(props[0]));
        ct.setSymbol(symbol);

        AliLog.error("复盘商品:" + symbol.getName());

    }


    /**
     * fasdjkjl fjkkjlfasdkjlfkjasdjkjklfsdakjfjklajksdf  fasd
     * fasdfaskjkfsadjkljakfsdff   fskakfjdkjasf  fasjklfdjkla
     * @throws Exception
     */

    public final void run() throws Exception {

        String ben = System.getProperty("user.dir");
        String os = System.getProperty("os.name");

        AliLog.println("运行目录:" + ben);
        AliLog.println("操作系统:" + os);
        AliLog.println("策略名为:[" + getClass().getSimpleName() + "]");
        CTrade.setNull();

        ConsoleTable tables = new ConsoleTable(4, true);
        Runtime r = Runtime.getRuntime();
        long x = (System.currentTimeMillis());
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        AliLog.println("正在初始化...源文件为：" + args[0]);

//        if (args[0].contains("JPY") || args[0].contains("XAG")) {
//            symbol.setPoint(100);
//        } else if (args[0].contains("XAU")) {
//            symbol.setPoint(10);
//        }

        kl = new KLines(frames, isReal);
        onInit(args[0]);
        try {
            kl.load4Ticks(args[0]);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<Tick> d = kl.getPrices();

        long last = 0l;
        AliLog.println("正进行逻辑处理 ... 将需要更多地时间");

        if (d.get(0).getPrice() > 10) {
            isjpy = true;
        }
        AliLog.println("是否日系->" + isjpy);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < d.size(); i++) {

            currentprocess = (i + 1) * 100 / d.size();

            if (lastprocess != currentprocess && normalConfig.getOptimizeString().equals("OFF")) {

                lastprocess = currentprocess;
                System.gc();

                if (last == 0) {
                    tables.appendRow();

                    tables.appendColum("YEAR").appendColum("JVM FREE MEMORY").appendColum("load time(ms)").appendColum("Process");

                    tables.appendRow();
                    tables.appendColum(sdf.format(d.get(i).getTime())).appendColum((int) r.freeMemory() / 1024 / 1024 + " MB").appendColum("0000").appendColum("0%");
                    if (isReal == false) {
                        AliLog.print(tables.toString());
                    }
                } else {
                    tables.appendRow();
                    tables.appendColum(sdf.format(d.get(i).getTime())).appendColum((int) r.freeMemory() / 1024 / 1024 + " MB").appendColum((System.currentTimeMillis() - last)).appendColum(currentprocess + "%");
                    if (isReal == false) {
                        AliLog.println(tables.toLast());
                    }

                }

                last = System.currentTimeMillis();
            }

            //委托相关
            ct.runWeituo(d.get(i));
            kl.load(d.get(i));
            onCal(d.get(i));
            onTick(d.get(i));

        }

        if (isReal) {

            while (true) {
                Tick t = new Tick();
                t.setTime(new Date());
                t.setVolumn(1);
                t.setPrice(1.0000);
                ct.runWeituo(t);
                kl.load(t);
                onCal(t);
                onTick(t);

                AliLog.println("---");

                Thread.sleep(1000);

            }

        }

        d.clear();
        d = null;
        System.gc();

        //tpl.addMv(10, "1");
        if (normalConfig.getOptimizeString().equals("ON")) {
            Op.saveToOP(ct, args);
            return;
        }

        TPL tpl = new TPL(args, ct);
        addTPL(tpl);

        tpl.saveToTPL();

        AliLog.println("测试完成!  共使用了 " + (System.currentTimeMillis() - x) / 1000 + " 秒");

    }

    public abstract void onInit(String url);

    public abstract void onTick(Tick t);

    public abstract void onCal(Tick t);

    public abstract void addTPL(TPL tpl);

}
