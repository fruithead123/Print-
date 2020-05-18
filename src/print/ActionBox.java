
package print;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static print.NewDirAlertBox.res;
import static print.NewDirAlertBox.updateRes;

public class ActionBox {
    public static void display(String title, String message){
        
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        
        Label l = new Label(message);
        Button confirmBtn = new Button("OK");
        confirmBtn.setOnAction(e -> 
        {
            window.close();
                });
        
        VBox layout = new VBox();
        HBox btnL = new HBox();
        
        btnL.getChildren().addAll(confirmBtn);
        btnL.setAlignment(Pos.CENTER);
        
        layout.getChildren().addAll(l, btnL);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
