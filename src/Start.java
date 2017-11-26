import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static java.awt.Event.UP;

public class Start extends Application {
    private String sMaxSize;

    //стартовое окошко
    public void start(Stage startStage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        Text text = new Text("Enter max size of board:");
        TextField textField = new TextField();
        Button startButton = new Button();
        startButton.setText("Start");
        startButton.setOnAction(event -> {
            startStage.close();
            Fifteen.Launch();
        });



        gridPane.add(text, 0, 0);
        gridPane.add(textField,0,1);
        gridPane.add(startButton, 0, 2);
        startStage.setScene(new Scene(gridPane));
        startStage.show();

    }

//    public void getMax() {
//        int maxSize = Integer.parseInt(sMaxSize);
//        Fifteen.Launch();
//    }

    static void Launch() {
        launch();
    }
}
