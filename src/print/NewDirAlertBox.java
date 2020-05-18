
package print;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewDirAlertBox {
    static int res;
    public static int display(String title, String message){
        
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        
        Label l = new Label(message);
        Button confirmBtn = new Button("YES");
        Button denyBtn = new Button("NO");
        
        denyBtn.setOnAction(e -> 
        {
            window.close();
            updateRes(0);
                });
        confirmBtn.setOnAction(e -> 
        {
            window.close();
            updateRes(1);
                });
        
        VBox layout = new VBox();
        HBox btnL = new HBox();
        
        btnL.getChildren().addAll(confirmBtn, denyBtn);
        btnL.setAlignment(Pos.CENTER);
        
        layout.getChildren().addAll(l, btnL);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        
        return res;
    }
    
    public static void updateRes(int i){
        res = i;
    }

}
