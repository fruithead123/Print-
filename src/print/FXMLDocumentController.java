/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package print;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.table.DefaultTableModel;


public class FXMLDocumentController implements Initializable {
    
    @FXML TextField DirField;
    @FXML TableView LTable;
    @FXML TableColumn fileColL;
    
    @FXML TableView RTable;
    @FXML TableColumn fileColR;
    @FXML TableColumn fileNumR;
    @FXML TableColumn fileAltR;
    
    boolean firstChoice = true;
    String curDir;
    
    
    @FXML
    public void findDir(ActionEvent event){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setInitialDirectory(new File("C:\\"));
        File selectedDirectory;
        
        try{
         selectedDirectory = chooser.showDialog(new Stage());
         DirField.setText(selectedDirectory.getAbsolutePath());
        }
        catch(Exception e){
            
        }

        //System.out.println(selectedDirectory.getAbsolutePath());
        
    }
    
    @FXML
    public void getDir(ActionEvent event){
        if(! firstChoice){   
            int res = NewDirAlertBox.display("Confirm Action", "Are you sure you want to change directories? This will erase all unsaved work.");
            if(res == 1){
                RTable.getItems().clear();
                showFiles(DirField.getText());
            }
        }
        else{
            showFiles(DirField.getText());
            curDir = DirField.getText();
        }
        firstChoice = false;
    }
    
    public void showFiles(String dir){
        LTable.getItems().clear();
        ArrayList<File> files = fetchFiles(dir);
        for(File F : files){
            //System.out.println(F);
            LTable.getItems().add(new ContentFile(F.getName()));
        }
    }
    
    public ArrayList<File> fetchFiles(String dir){
        File folder = new File(dir);
        File[] contents = folder.listFiles();
        ArrayList<File> files = new ArrayList<>();
        
        for(File content : contents) {
            if (content.isFile()) {
                files.add(content);
            }
        }
        return files; 
    }
    
    public void updateR(String name, int num, String alts){
        PrintFile f = new PrintFile(name, Integer.toString(num), alts);
        RTable.getItems().add(f);
    }
    
    public void changeNum(CellEditEvent e){
        PrintFile f = (PrintFile) RTable.getSelectionModel().getSelectedItem();
        f.setNum(e.getNewValue().toString());
    }
    
    public void changeAlt(CellEditEvent e){
        PrintFile f = (PrintFile) RTable.getSelectionModel().getSelectedItem();
        f.setAlts(e.getNewValue().toString());
    }
    
    public void makeHTML(){
        try{
            
            PrintWriter writer = new PrintWriter(curDir + "/temp.html", "UTF-8");
            writer.write("<html><style type=\"text/css\">\n" +
            "            .photo{\n" +
            "                    display:block;\n" +
            "                    padding: 7px;\n" +
            "                    width: 650px; \n" +
            "            }\n" +
            "            .img{\n" +
            "                    image-orientation: from-image;\n" +
            "            }\n" +
            "            \n" +
"                          </style>");
            
            writer.write("<body>\n");
            
            for(Object o : RTable.getItems()){
                PrintFile f = (PrintFile) o;
                String fname = f.getName();
                int fnum = Integer.parseInt(f.getNum());
                System.out.println(fname + f.getNum());
                for(int i=0; i<fnum; i++){
                    writer.write(String.format("<img id=\"main\"><img class=\"photo\" src=\"%s\" /></img>\n", fname));
                }
                
            }
            writer.write("</body></html>\n");
            writer.close();
            
            ActionBox.display("HTML Created", String.format("HTML created at %s.", curDir));
        }
        catch(FileNotFoundException | UnsupportedEncodingException e){
            System.out.println("1");
        }
    }
    
    public void makePDF(){
        
    }
    
    public void sendToPrinter(){
        
    }
    
    public void moveRow(){
        
    }
    
    public void applyPreset(){
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("C:\\"));
        try{
         File file = chooser.showOpenDialog(new Stage());
         Preset contents = new Preset(file);
         RTable.getItems().clear();
         for(int i=0; i<contents.size; i++){
             File tmp = new File(curDir + "/" + contents.getNames().get(i));
             if(tmp.exists()){
                 updateR(contents.getNames().get(i), contents.getNums().get(i), "");
             }
             else{
                 boolean resolved = false;
                 for(String a: contents.getAlts().get(i)){
                     File tmp2 = new File(curDir + "/" + a);
                     if(tmp2.exists()){
                         updateR(a, contents.getNums().get(i), "Replacing " + contents.getNames().get(i));
                         resolved = true;
                         break;
                     }
                 }
                 if(! resolved){
                     //open window and get user to pick another one
                     TableView<ContentFile> clone = new TableView();
                     TableColumn c = new TableColumn("Name");
                     c.setCellValueFactory(new PropertyValueFactory<>("name"));

                     clone.getColumns().add(c);
                     clone.getItems().addAll(LTable.getItems());
                     
                     String replacement = AltFileBox.display(String.format("Choose a file to replace %s", contents.getNames().get(i)), "Could Not Find File In Directory", clone);
                     if("".equals(replacement)){
                     }
                     else{
                         updateR(replacement, contents.getNums().get(i), "Replacing " + contents.getNames().get(i));
                     }
                     
                 }
             }
         }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void makePreset(){
        PresetMaker.display();
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileColL.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        fileColR.setCellValueFactory(new PropertyValueFactory<>("name"));
        fileNumR.setCellValueFactory(new PropertyValueFactory<>("num"));
        fileAltR.setCellValueFactory(new PropertyValueFactory<>("alts"));
        
        LTable.setRowFactory( tv -> {
        TableRow<ContentFile> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                ContentFile rowData = row.getItem();
                //System.out.println(rowData.getName());
                updateR(rowData.getName(), 1, "");
            }
        });
        return row ;
        });
        
        RTable.setOnKeyPressed((final KeyEvent keyEvent) -> {
            final PrintFile r = (PrintFile) RTable.getSelectionModel().getSelectedItem();
            if(r != null && keyEvent.getCode().equals(KeyCode.DELETE)){
                RTable.getItems().remove(r);
            }
            
            else if(r != null && keyEvent.getCode().equals(KeyCode.LEFT)){
                int index = RTable.getSelectionModel().getSelectedIndex();
                if(index > 0){
                    RTable.getItems().add(index-1, RTable.getItems().remove(index));
                    RTable.getSelectionModel().clearAndSelect(index-1);
                }
            }
            
            else if(r != null && keyEvent.getCode().equals(KeyCode.RIGHT)){
                int index = RTable.getSelectionModel().getSelectedIndex();
                if(index < RTable.getItems().size() - 1){
                    RTable.getItems().add(index+1, RTable.getItems().remove(index));
                    RTable.getSelectionModel().clearAndSelect(index+1);
                }
            }
            /*
            else if(r != null && keyEvent.getCode().equals(KeyCode.LEFT)){
                int index = RTable.getSelectionModel().getSelectedIndex();
                if(index > 0){
                    RTable.getSelectionModel().clearAndSelect(index-1);
                }
            }
            
            else if(r != null && keyEvent.getCode().equals(KeyCode.RIGHT)){
                int index = RTable.getSelectionModel().getSelectedIndex();
                if(index < RTable.getItems().size() - 1){
                    RTable.getSelectionModel().clearAndSelect(index+1);
                }
            }
            */
        });
        
        RTable.setEditable(true);
        fileNumR.setCellFactory(TextFieldTableCell.forTableColumn());
        fileAltR.setCellFactory(TextFieldTableCell.forTableColumn());
    }    
    
}
