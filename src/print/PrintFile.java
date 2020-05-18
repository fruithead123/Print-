
package print;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;

public class PrintFile {
    private String name;
    private String num;
    private String alts;
    
    public PrintFile(String name, String num, String alts){
        this.name = name;
        this.num = num;
        this.alts = alts;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getNum(){
        return this.num;
    }
    
    public String getAlts(){
        return this.alts;
    }
    
    public void setNum(String num){
        this.num = num;
    }
    
    public void setAlts(String alts){
        this.alts = alts;
    }
}
