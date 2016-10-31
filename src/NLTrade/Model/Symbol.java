package NLTrade.Model;


public class Symbol {

    private String name;
    private double commition;
    private double point;
    private double valueper1;
    
    
    
    public  Symbol (String name)
    {
        this.name = name ;
        
        
    }
    
    public Symbol()
    {
        
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCommition() {
        return commition;
    }

    public void setCommition(double spread) {
        this.commition = spread;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    /**
     * @return the valueper1
     */
    public double getValueper1() {
        return valueper1;
    }

    /**
     * @param valueper1 the valueper1 to set
     */
    public void setValueper1(double valueper1) {
        this.valueper1 = valueper1;
    }


}
