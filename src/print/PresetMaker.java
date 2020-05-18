
package print;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class PresetMaker {
    public static void display(){
        
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Preset Maker");
        window.setMinWidth(800);
        
        VBox layout = new VBox(15);
        
        TableView contents = new TableView();
        
        TableColumn<String, PresetFile> c1 = new TableColumn<>("Name");
        c1.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<String, PresetFile> c2 = new TableColumn<>("Num");
        c2.setCellValueFactory(new PropertyValueFactory<>("num"));
        
        TableColumn<String, PresetFile> c3 = new TableColumn<>("Alternates");
        c3.setCellValueFactory(new PropertyValueFactory<>("alts"));
        
        TableColumn<String, PresetFile> c4 = new TableColumn<>("FileTypes");
        c4.setCellValueFactory(new PropertyValueFactory<>("fileTypes"));
        
        contents.getColumns().addAll(c1,c2,c3,c4);
        c1.prefWidthProperty().bind(contents.widthProperty().multiply(0.2));
        c2.prefWidthProperty().bind(contents.widthProperty().multiply(0.14));
        c3.prefWidthProperty().bind(contents.widthProperty().multiply(0.36));
        c4.prefWidthProperty().bind(contents.widthProperty().multiply(0.3));
        
        contents.setOnKeyPressed((final KeyEvent keyEvent) -> {
            final PresetFile r = (PresetFile) contents.getSelectionModel().getSelectedItem();
            if(r != null && keyEvent.getCode().equals(KeyCode.DELETE)){
                contents.getItems().remove(r);
            }
            
            else if(r != null && keyEvent.getCode().equals(KeyCode.LEFT)){
                int index = contents.getSelectionModel().getSelectedIndex();
                if(index > 0){
                    contents.getItems().add(index-1, contents.getItems().remove(index));
                    contents.getSelectionModel().clearAndSelect(index-1);
                }
            }
            
            else if(r != null && keyEvent.getCode().equals(KeyCode.RIGHT)){
                int index = contents.getSelectionModel().getSelectedIndex();
                if(index < contents.getItems().size() - 1){
                    contents.getItems().add(index+1, contents.getItems().remove(index));
                    contents.getSelectionModel().clearAndSelect(index+1);
                }
            }
        });
        
        TextField NameField = new TextField();
        NameField.setPromptText("File Name: ");
        NameField.setPrefWidth(250);
        
        TextField NumField = new TextField();
        NumField.setPromptText("Quantity: ");
        NumField.setPrefWidth(250);
        
        TextField AltField = new TextField();
        AltField.setPromptText("Alternatives if File cannot be found: ");
        AltField.setPrefWidth(400);
        
        TextField FileField = new TextField();
        FileField.setPromptText("File types to search for: ");
        FileField.setPrefWidth(400);
        
        Button AddBtn = new Button("Add");
        Button SaveBtn = new Button("Save");
        
        AddBtn.setOnAction(e -> {
            if(isNum(NumField.getText()) && !"".equals(NameField.getText()) && !"".equals(NumField.getText()) && !"".equals(FileField.getText())){
                contents.getItems().add(new PresetFile(NameField.getText(), Integer.parseInt(NumField.getText()), AltField.getText(), FileField.getText()));
                NameField.setText("");
                NumField.setText("");
                AltField.setText("");
                FileField.setText("");
            }
        });
        
        SaveBtn.setOnAction(e -> {
            String txt = savePreset(contents);
            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fc.getExtensionFilters().add(extFilter);
            File file = fc.showSaveDialog(new Stage());
 
            if (file != null) {
                saveTextToFile(txt, file);
                contents.getItems().clear();
            }
            
                });
        
        
        HBox btn1 = new HBox(10);
        btn1.getChildren().addAll(NameField, AltField);
        btn1.setAlignment(Pos.CENTER);
        
        HBox btn2 = new HBox(10);
        btn2.getChildren().addAll(NumField, FileField);
        btn2.setAlignment(Pos.CENTER);
        
        VBox Fields = new VBox(10);
        Fields.getChildren().addAll(btn1, btn2);
        
        HBox click = new HBox(300);
        click.getChildren().addAll(SaveBtn, AddBtn);
        click.setAlignment(Pos.CENTER);
        
        HBox gap = new HBox();
        
        layout.getChildren().addAll(contents, Fields, click, gap);
        layout.setAlignment(Pos.CENTER);

        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private static boolean isNum(String text) {
        if(text == null){
            return false;
        }
        boolean flag = true;
        for(char c : text.toCharArray()){
            if(!(c >= 48 && c <= 57)){
                flag = false;
            }
        }
        return flag;
    }

    private static String savePreset(TableView contents) {
        String txt = "";
        for(Object x : contents.getItems()){
            PresetFile f = (PresetFile) x;
            String name = f.getName();
            int num = f.getNum();
            txt += (name.trim() + "." + f.formatFiles()[0] + "," + num);
            for(String type : f.formatFiles()){
                txt+= ("," + name.trim() + "." + type.trim());
            }
            for(String alt : f.formatAlts()){
                for(String type : f.formatFiles()){
                    txt += ("," + alt.trim() + "." + type.trim());
                }
            }
            txt+= "\n";
        }
        //System.out.println(txt);
        return txt;
    }

    private static void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.print(content);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
