/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NLTrade.util;

import NLTrade.Model.TickM;
import alilibs.AliFile;
import alilibs.AliLog;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ali
 */
public class MultiSymbol {
    public static List<TickM> getTickMs(String url,int count) throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<String> strings = AliFile.getString(url);
        List<TickM> result = new ArrayList<TickM>();
        for (int i = 0; i < strings.size(); i++) {
            TickM t = new TickM();
            String [] strs = strings.get(i).split(",");
            Date [] d = new Date[count];
            double [] price = new double[count];
            int  [] vol = new int[count] ;
            for(int j=0;j<price.length;j++)
            {
                d[j]= sdf.parse(strs[0]+" "+strs[1]) ;
                price[j] = Double.parseDouble(strs[j+2]);
                vol[j] = 100 ;
            }
            
            
            
            
            t.setPrice(price);
            t.setTime(d);
            t.setVolumn(vol);
            
            
            result.add(t);
        }
        return result ;
    }
    
    public static void main(String[] args) throws Exception {
        List<TickM> l =getTickMs("/Users/ali/NetBeansProjects/NLTrade/data/AUD|NZD|EUR.txt", 3);
        AliLog.println(l.size());
        for(int i=0;i<10;i++)
        {
            AliLog.println(l.get(i).getPrice()[0]+" "+l.get(i).getPrice()[1]+" "+l.get(i).getPrice()[2]);
        }
        
        
        
        
    }
    
    
    
}
