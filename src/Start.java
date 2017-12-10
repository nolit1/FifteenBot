import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.*;

public class Start extends Application {

    //стартовое окошко
    public void start(Stage startStage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        startStage.setTitle("Пятнашки с ботом");

        Text text = new Text("ПРАВИЛА ИГРЫ: \n" +
        "Чтобы активировать бота, нажмите \"R\" \n" + "Нажимайте \"ENTER\", чтобы ходы бота отображались на экране\n" +
        "Удерживайте \"ENTER\", чтобы ускорить процесс сборки" + "\n" + "\n Нажмите \"Начать\", когда будете готовы");
        //TextField textField = new TextField();
        Button startButton = new Button();
        startButton.setText("Начать");
        startButton.setOnAction(event -> {
            startStage.close();
            Fifteen.Launch();
        });



        gridPane.add(text, 0, 0);
        //gridPane.add(textField,0,1);
        gridPane.add(startButton, 0, 2);
        startStage.setScene(new Scene(gridPane));
        startStage.show();

    }
}
