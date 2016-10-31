package NLTrade.util;

import alilibs.AliConfig;

import java.util.Map;


/**
 *
 * @Ali
 */
public class Config extends AliConfig{


    Map<String, String> keywords;
    //JSONObject jo = null;

    public Config(String url , String key) {
        super(url,key);
        keywords = getKeyWords();
//        String str = AliHTTP.getString("http://linux.9hlh.com/nltrade/keyvalue/findjson.php?comment="+key+"&code=xienaizhong");
//        AliLog.println(str);
//        try {
//            jo = new JSONObject(str);
//        } catch (JSONException ex) {
//
//        }


    }

    public String getOptimizeString() {
        try {
            return getByKey("optimize");
        } catch (Exception ex) {
        }
        return null;
    }

    public String getAllsymbolString() {
        try {
            return getByKey("allsymbol");
        } catch (Exception ex) {
        }
        return null;

    }

    
    public String getUrl()
    {
        try {
            return getByKey("url");
        } catch (Exception ex) {
        }
        return null;
        
        
        
        
    }
    
    
    public String getWebUploadString() {
        try {
            return getByKey("WebUpload");
        } catch (Exception ex) {
        }
        return null;

    }

    public String getCurrentsymbolString() {
        try {
            return getByKey("currentsymbol");
        } catch (Exception ex) {
        }
        return null;

    }
    public String getCurrentsymbolStringM() {
        try {
            return getByKey("currentsymbolM");
        } catch (Exception ex) {
        }
        return null;

    }

    public String getRealTime() {

        try {
            return getByKey("realtime");
        } catch (Exception ex) {
        }
        return null;

    }

    public boolean getDebug() {

        try {

            return getByKey("debug").equals("ON");
        } catch (Exception ex) {
        }
        return false;

    }

    public String getByKey(String key) {

        try {
            return keywords.get(key)+"";
        } catch (Exception ex) {
        }
        return null;
    }

    public String getClassname() {
        try {
            return getByKey("classname");
        } catch (Exception ex) {
        }
        return null;

    }

    public String getArgs() {
        try {
            return getByKey("args");
        } catch (Exception ex) {
        }
        return null;

    }

}
