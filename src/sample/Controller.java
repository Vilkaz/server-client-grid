package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private TextField serverIP;

    @FXML
    private Text serverStatus;


    @FXML
    protected void toogleServer() {
        System.out.println("The button was clicked!"+serverStatus.getText());
    }
}
