/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lerserialbalanca.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;
import lerserialbalanca.models.ManipuladorEtiqueta;
import lerserialbalanca.models.Registro;
import lerserialbalanca.utils.BrowserLaunch;

/**
 * FXML Controller class
 *
 * @author Desenvolvimento
 */
public class RelatorioController implements Initializable {

    @FXML
    private Button buscar;
    @FXML
    private DatePicker data1;
    @FXML
    private DatePicker data2;
    @FXML
    private Label msg;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        eventosElementos();
    }    

    private void eventosElementos() {
        buscar.setOnMouseClicked((event)->{
            try {
                if(data1.getValue() == null || data2.getValue() == null){
                    msg.setStyle("-fx-text-fill: red;");
                    msg.setText("ERRO : Selecione as datas.");
                } else {
                    Registro reg = new Registro();
                    List<Registro> registros = reg.listaDeRegistros(data1.getValue().toString(), data2.getValue().toString());
                    ManipuladorEtiqueta.fazerRelatorioHtml(registros,data1.getValue(),data2.getValue());
                    BrowserLaunch.openURL(ManipuladorEtiqueta.getPath_html_report());
                }
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RelatorioController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(RelatorioController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(RelatorioController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RelatorioController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(RelatorioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
}
