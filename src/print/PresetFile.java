package print;

import java.util.Arrays;

public class PresetFile {
    private String name;
    private int num;
    private String[] alts;
    private String[] fileTypes;
    
    public PresetFile(String name, int num, String alts, String fileTypes){
        this.name = name;
        this.num = num;
        this.alts = alts.split(",");
        this.fileTypes = fileTypes.split(",");
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public String getAlts() {
        return Arrays.toString(alts);
    }

    public String getFileTypes() {
        return Arrays.toString(fileTypes);
    }
    
    public String[] formatAlts(){
        return this.alts;
    }
    
    public String[] formatFiles(){
        return this.fileTypes;
    }
    
}
