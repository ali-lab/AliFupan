package NLTrade;



import NLTrade.Strategy.AStrategy;

import NLTrade.util.Config;
import alilibs.AliFile;
import alilibs.AliLog;
import alilibs.AliSort;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author a
 */
public class Main {

    public static void main(String[] args) throws Exception {


        File directory = new File("");//设定为当前文件夹
        try{
            System.out.println(directory.getCanonicalPath());//获取标准的路径
            System.out.println(directory.getAbsolutePath());//获取绝对路径
        }catch(Exception e){}

        System.out.print("|"+System.getProperty("user.dir")+"|");

        Config cfg = new Config("../config/load.cfgx","nomal");
        String curr = (cfg.getCurrentsymbolString());

        String op = (cfg.getOptimizeString());

        String all = (cfg.getAllsymbolString());

        if (args.length == 1 && args[0].equals("-h")) {

            AliLog.println("使用说明:");
            AliLog.println("目录下有一个config/op.txt文件.是用来配置优化参数的,每个优化参数放一排,优化属性用逗号分开\n例如1,100,10意思是:从1开始,最大100,每次递增10");
            AliLog.println("目录下有一个config/load.cfg文件.是用来配置整个运行环境的常量");
            AliLog.println("复盘结果在result目录里,log文件在log文件夹下");
            AliLog.println("更多地使用方法,请参看http://www.9hlh.com 相关文档");
            return;

        }

        
        if (cfg.getDebug()) {
            
            //args = new String[]{cfg.getCurrentsymbolString()};
            
            //new King(args,null).run();
            run(cfg.getClassname(),cfg.getArgs(),cfg.getCurrentsymbolString());
            return;
        }
        
        
        //从SH文件进入
        if (args.length > 0) {
            //new King(args,null).run();
            
            String  args_ = "";
            for(int i=1;i<args.length;i++)
            {
                args_ += args[i]+" ";
            }
            
            
            
            run(cfg.getClassname(),args_,args[0]);
            return;
        }

        if (all.equals("ON")) {

            String x = System.getProperty("user.dir")+"/../data";
            File f = new File(x);

            File[] l = f.listFiles();
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < l.length; i++) {

                if (l[i].getName().endsWith(".min")) {
                    list.add("java -Xms3000m -Xmx3000m -jar NLTrade.jar " + l[i].getAbsolutePath() + "");
                }

            }
            //AliFile.delete("run.bat");
            AliFile.delete("../run.sh");
            //AliFile.append("run.bat", list);
            AliFile.append("../run.sh", list);
            AliLog.println("在目录下产生sh文件成功,请运行进行优化复盘");

        } else if (op.equals("ON")) {
            op(curr);
        } else {

            List<String> list = new ArrayList<String>();
            
            
            
            
            list.add("java -Xms3000m -Xmx3000m -jar NLTrade.jar " + curr + " "+cfg.getArgs().replaceAll(",", " "));

            //AliFile.delete("run.bat");
            AliFile.delete("run.sh");
            //AliFile.append("run.bat", list);
            AliFile.append("run.sh", list);
            AliLog.println("在目录下产生bat/sh文件成功,请运行进行优化复盘");

            AliLog.println("请查看load.cfg配置文件");
            AliLog.println("");

        }

    }

    /**
     * 优化
     */
    private static void op(String url) throws IOException {

        List<String> ops = AliFile.getString("../config/op.txt");
        if (ops == null) {
            AliLog.println("没有op.txt文件");
            return;
        }

        List<Object[]> args = new ArrayList<Object[]>();
        for (int i = 0; i < ops.size(); i++) {
            String s[] = ops.get(i).split(",");
            List<Double> d = new ArrayList<Double>();
            Double start = new Double(s[0]);
            Double end = new Double(s[1]);
            Double step = new Double(s[2]);
            for (double tmp = start; tmp < end; tmp += step) {
                d.add(tmp);

            }
            d.add(end);
            args.add(d.toArray());

        }

        List<String> argList = AliSort.paileizuhe(args);

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < argList.size(); i++) {
            list.add("java -Xms3000m -Xmx3000m -jar NLTrade.jar " + url + " " + argList.get(i) + "");
        }

        AliFile.delete("runOP.bat");
        AliFile.delete("runOP.sh");
        AliFile.append("runOP.bat", list);
        AliFile.append("runOP.sh", list);
        AliLog.println("在目录下生成sh文件成功,请运行进行优化复盘");

    }
    static boolean run(String className,String args,String filename)
    {
        
        try {
            
            String [] args_ = args.split(" ") ;
            String [] s = new String[args_.length+1];
            s[0] = filename ;
            for(int i=0;i<args_.length;i++)
            {
                s[i+1] = args_[i];
            }
            
            Class xmlmenu = Class.forName(className);
            
            AStrategy a = (AStrategy)xmlmenu.newInstance();
            a.setArgs(null,s );
            
            
            
            
            
            a.run();
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
            AliLog.println(""+ex.getStackTrace().toString());
            return false ;
        }
        
        
        
        
        
        
        return true ;
        
    }
}
