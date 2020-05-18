/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package print;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class Preset {
    int size = 0;
    ArrayList<String> names = new ArrayList();
    ArrayList<Integer> nums = new ArrayList();
    ArrayList<String[]> alts = new ArrayList();
    
    public Preset(File f) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
        String st;
        
        while((st = br.readLine()) != null){
            String[] parts = st.split(",");
            names.add(parts[0]);
            nums.add(Integer.parseInt(parts[1]));
            alts.add(Arrays.copyOfRange(parts, 2, parts.length));
            size++;
        }
    }
    
    public ArrayList<String> getNames(){
        return this.names;
    }
    public ArrayList<Integer> getNums(){
        return this.nums;
    }
    public ArrayList<String[]> getAlts(){
        return this.alts;
    }
    
}
