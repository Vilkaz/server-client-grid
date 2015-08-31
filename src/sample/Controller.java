package sample;

import com.sun.corba.se.spi.activation.Server;
import controller.ServerController;
import dto.ServerConfigDTO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private TextField serverIP_tf;

    @FXML
    private Text serverStatus;

    @FXML
    private TextField port_tf;


    @FXML
    protected void toogleServer() {
        ServerController.toogleServer(getServerConfig());
    }


    private ServerConfigDTO getServerConfig(){
        return new ServerConfigDTO(
            serverIP_tf.getText(),
           Integer.parseInt(port_tf.getText()));
    }
}
