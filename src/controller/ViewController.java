package controller;

import dto.Config;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Collections;

public class ViewController {

    @FXML
    private ToggleGroup matrixGroup;

    @FXML
    private RadioButton matrix1, matrix2, matrix3, matrix4, matrix5;

    @FXML
    private GridPane matrixMainField;

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
        } else {
            setServerstatus("OFF");
            disableServerEdit(false);
            setToogleServerButton("Server starten");
            disableMatrix(true);
        }
    }


    @FXML
    private void displayChosenMatrix() throws ClassNotFoundException, ParseException {
        RadioButton chk = (RadioButton) matrixGroup.getSelectedToggle();
        int matrixID = Integer.parseInt(chk.getId());
        int binCode = ClientController.getBinCode(getServerConfig(), matrixID);
        markMatrixByBinCode(binCode);
    }

    private void disableMatrix(Boolean status) {
        matrix1.setDisable(status);
        matrix2.setDisable(status);
        matrix3.setDisable(status);
        matrix4.setDisable(status);
        matrix5.setDisable(status);
    }


    private void deselectRadiobutton() {
        if (matrixGroup.getSelectedToggle() != null) {
            matrixGroup.getSelectedToggle().setSelected(false);
        }
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


    private void markMatrixByBinCode(int binCode) {
        for (int row = 4; row >= 0; row--) {
            for (int col = 4; col >=0 ; col--) {
                int helper = (int) Math.pow(2,((row*5)+col));
                Button btn = new Button();//auslagern ... es ist furchbar so ! ist aber spät ... müde ..
                btn.setMinWidth(70);
                btn.setMinHeight(60);
                if (helper<=binCode){
                   btn.setText("X");
                   binCode-=helper;
               } else{
                   btn.setText("-");
               }
                matrixMainField.setConstraints(btn, col, row);
                matrixMainField.getChildren().add(btn);
            }
        }
    }


}
