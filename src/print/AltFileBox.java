
package print;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AltFileBox {
    static String res = "";
    
    public static String display(String msg, String title, TableView table){
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(400);
        
        Label l = new Label(msg);
        l.setFont(new Font("Ariel", 24));
        
        
        TextField newFile = new TextField();
        newFile.setPromptText("Replacement File");
        newFile.setStyle("-fx-alignment: center");
        newFile.setEditable(false);
        
        Button skipBtn = new Button("Skip this file");
        skipBtn.setOnAction(e -> 
        {
            update("");
            window.close();
                });
        
        Button confirmBtn = new Button("Replace this File");
        confirmBtn.setOnAction(e -> 
        {
            update(newFile.getText());
            window.close();
                });
        
        table.setRowFactory(tv -> {
        TableRow row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && (! row.isEmpty()) ) {

                ContentFile clickedRow = (ContentFile) row.getItem();
                newFile.setText(clickedRow.getName());
            }
        });
        return row ;
    });
        
        BorderPane layout = new BorderPane();
        
        VBox bot = new VBox(10);
        HBox bot2 = new HBox(10);
        bot2.getChildren().addAll(skipBtn, confirmBtn);
        bot.getChildren().addAll(newFile, bot2);
        BorderPane.setAlignment(l,Pos.CENTER);
        bot2.setAlignment(Pos.CENTER);

        
        
        layout.setTop(l);
        layout.setLeft(null);
        layout.setRight(null);
        layout.setCenter(table);
        layout.setBottom(bot);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return res;
    }
    
    public static void update(String s){
        res = s;
    }
    
    
}
