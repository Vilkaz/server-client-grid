package sample;

import controller.ServerController;
import dto.ServerConfigDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private TextField serverIP_tf, port_tf;

    @FXML
    private Text serverStatus;

    @FXML
    private Button toogleServer_btn,connectWithServer_btn;

    @FXML
    protected void toogleServer() {
      if (ServerController.toogleServer(getServerConfig())){
          enableConectionToServer();
          disableServerEdit(true);
          setToogleServerButton("Server stoppen");
      } else {
          disableConnectionToServer();
          disableServerEdit(false);
          setToogleServerButton("Server starten");
      }
    }

    private  void disableConnectionToServer(){
        setServerstatus("ON");
        disableConnectionButton(true);
    }


    private void enableConectionToServer(){
        setServerstatus("ON");
        disableConnectionButton(false);
    }

    private void disableServerEdit(Boolean status){
        serverIP_tf.setDisable(status);
        port_tf.setDisable(status);
    }



    private void setToogleServerButton(String status){
        toogleServer_btn.setText(status);
    }

    private void disableConnectionButton(Boolean status){
        connectWithServer_btn.setDisable(status);
    }

    private void setServerstatus(String status){
        serverStatus.setText(status);
    }




    private ServerConfigDTO getServerConfig(){
        return new ServerConfigDTO(
            serverIP_tf.getText(),
           Integer.parseInt(port_tf.getText()));
    }
}
