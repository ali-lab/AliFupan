package NLTrade.Strategy.king;

import NLTrade.Model.Tick;
import NLTrade.Strategy.AStrategy;
import NLTrade.util.Config;
import NLTrade.util.TPL;

/**
 * Created by 闲道人阿力 on 2016/7/20.
 */
public class King2 extends AStrategy {


    int mainFrame;
    final static String otherFrame = "60";
    Config cfg;

    public King2(){
        super();
        cfg = new Config("../config/load.cfgx","king");

        mainFrame = new Integer(cfg.getByKey("frame"));
    }

    @Override
    public void onInit(String url) {

    }

    @Override
    public void onTick(Tick t) {

    }

    @Override
    public void onCal(Tick t) {

    }

    @Override
    public void addTPL(TPL tpl) {

    }
}
