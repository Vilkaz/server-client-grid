package sample;

import controller.ClientController;
import controller.ServerController;
import dto.Config;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private ToggleGroup matrixGroup;

    @FXML
    private RadioButton matrix1, matrix2, matrix3, matrix4, matrix5;

    @FXML
    private TableView tableView;

    @FXML
    private TextField serverIP_tf, port_tf;

    @FXML
    private Text serverStatus;

    @FXML
    private Button toogleServer_btn;

    @FXML
    protected void toogleServer() {
        if (ServerController.toogleServer(getServerConfig())) {
            setServerstatus("ON");
            disableServerEdit(true);
            setToogleServerButton("Server stoppen");
            disableMatrix(false);
            deselectRadiobutton();
        }
        else {
            setServerstatus("OFF");
            disableServerEdit(false);
            setToogleServerButton("Server starten");
            disableMatrix(true);
        }
    }


    private void disableMatrix(Boolean status) {
        matrix1.setDisable(status);
        matrix2.setDisable(status);
        matrix3.setDisable(status);
        matrix4.setDisable(status);
        matrix5.setDisable(status);
        tableView.setDisable(status);
    }


    private void deselectRadiobutton(){
        if (matrixGroup.getSelectedToggle()!=null){
            matrixGroup.getSelectedToggle().setSelected(false);
        }
    }

    @FXML
    private void getMatrix() throws ClassNotFoundException {
        RadioButton chk = (RadioButton) matrixGroup.getSelectedToggle();
         System.out.println("hole matrix nr:"+chk.getId());
        int matrixID = Integer.parseInt(chk.getId());
        ClientController.makeSocket(getServerConfig(),matrixID );
    }

      private void disableServerEdit(Boolean status) {
        serverIP_tf.setDisable(status);
        port_tf.setDisable(status);
    }


    private void setToogleServerButton(String status) {
        toogleServer_btn.setText(status);
    }



    private void setServerstatus(String status) {
        serverStatus.setText(status);
    }


    private Config getServerConfig() {
        return new Config(
                serverIP_tf.getText(),
                Integer.parseInt(port_tf.getText()));
    }
}
