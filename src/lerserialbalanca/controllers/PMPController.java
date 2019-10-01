/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lerserialbalanca.controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lerserialbalanca.main.Principal;
import lerserialbalanca.models.LerSerial;
import lerserialbalanca.models.Pecas;
import lerserialbalanca.utils.Format;
import org.controlsfx.control.textfield.TextFields;


/**
 * FXML Controller class
 *
 * @author Desenvolvimento
 */
public class PMPController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private String peso_liquido;
    private boolean estavel;
    private boolean calcularPmp = false;
    private boolean calcularPecas = false;
    
    @FXML
    private Label peso_liq;
    @FXML
    private Label estabilidade;
    @FXML
    private Label qtd_pecas;
    @FXML
    private Label label_gran_peso;
    @FXML
    private Label label_gran_pmp;
    @FXML
    private Label pmp;
    @FXML
    private ComboBox grandeza;
    @FXML
    private TextField qtd_amostras;
    @FXML
    private TextField nome;
    @FXML
    private TextField descricao;
    @FXML
    private Button salvar;
    
    Thread displayThread;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        formatacao();
        eventos();
        //Inicia Thread de atualização no display
        displayThread = new Thread(this::DisplayThread);
        displayThread.start();
        grandeza.setValue("kg");
        carregarComboBox();
    }

    private void formatacao(){
        Format.onlyNumber(qtd_amostras);
        Format.addTextLimiter(qtd_amostras, 4);
    }
    private void eventos(){
         //AO DIGITAR UM CARACTERE NO CAMPO QUANTIDADE DE AMOSTRAS
        qtd_amostras.setOnKeyReleased((event) -> {
            if (qtd_amostras.getText().length() > 0) { //SE O NUMERO DE CARACTERES FOR MAIOR QUE 0
                calcularPmp = true;
            } else {//SE O NUMERO DE CARACTERES FOR ZERO
                //LIMPA TODOS OS CAMPOS
                calcularPmp = false;
                calcularPecas = false;
                qtd_pecas.setText("0");
                pmp.setText("0");
            }
        });

        nome.setOnKeyReleased((event)->{
            Pecas pec = new Pecas();
        });
        
        
        salvar.setOnMouseClicked((event) ->{
            Pecas pec = new Pecas(nome.getText(), descricao.getText(), pmp.getText(), qtd_amostras.getText(), grandeza.getValue().toString());
            pec.salvarPeca();
        });
    }
    
    private void DisplayThread() {
        //THREAD PARA LEITURA DE SERIAL CONTINUA
        while (true) {
            Platform.runLater(() -> {
                //PEGA O PESO LIQUIDO/ESTABILIDADE E ADICIONA AO DISPLAY
                peso_liquido = Principal.getPeso_liq();
                estavel = Principal.isEstavel();
                peso_liq.setText(peso_liquido);
                if (estavel) {
                    estabilidade.setText("Estável");
                    estabilidade.setStyle("-fx-text-fill: green;");
                } else {
                    estabilidade.setText("Oscilando");
                    estabilidade.setStyle("-fx-text-fill: red;");
                }
                
                //SE TIVER QUE CALCULAR O PMP
                if(calcularPmp == true){
                    //FAZ O CALCULO E COLOCA NO DISPLAY
                    String pmp_var = calculoPmp(peso_liquido, qtd_amostras.getText());
                    pmp.setText(pmp_var);
                    calcularPmp = false; //DESATIVA CALCULO DE PMP
                    calcularPecas = true; //ATIVA CALCULO DE PEÇAS
                }
                
                //SE TIVER QUE CALCULAR A QUANTIDADE DE PEÇAS
                if(calcularPecas == true){
                    //FAZ O CALCULO DE PEÇAS E ADICIONA AO DISPLAY
                    String qtd_pecas_var = calculoPecas(peso_liquido, pmp.getText());
                    qtd_pecas.setText(qtd_pecas_var);
                }
                
                //Coloca as grandezas selecionadas no Label
                label_gran_peso.setText(grandeza.getValue().toString());
                label_gran_pmp.setText(grandeza.getValue().toString());

                
            });
            try {
                Thread.sleep(20);
            } catch (InterruptedException iex) {
                System.out.println(iex.getMessage());
            }
        }
    }
    
     //CARREGA COMBOBOX COM DADOS DE PORTAS E EQUIPAMENTOS DISPONIVEIS
    public void carregarComboBox(){
        List<String> grandezas = new ArrayList<String>();
        grandezas.add("mg");
        grandezas.add("g");
        grandezas.add("kg");
        grandeza.setItems(FXCollections.observableArrayList(grandezas));
        
    }
    
    private String calculoPmp(String peso_liq, String qtd_amostras){
        if(!qtd_amostras.equals("0")){
            DecimalFormat df = new DecimalFormat("#.###");
            String teste = String.valueOf(Float.parseFloat(peso_liq)/Integer.parseInt(qtd_amostras)).replaceAll(",", ".");
            System.out.println(Float.parseFloat(peso_liq) + "/" + Integer.parseInt(qtd_amostras) + " = " + teste );
            Number n = Double.parseDouble(teste);
            Double d = n.doubleValue();
            return String.valueOf(df.format(d)).replaceAll(",", ".");
        }
        return "0";
    }
    
    private String calculoPecas(String peso_liq, String pmp){
        if(pmp.equals("0")){
            return "0";
        }
        int pecas = 0;
        Float peso = Float.parseFloat(peso_liq);
        Float pmp_var = Float.parseFloat(pmp);
        Float result = peso/pmp_var;
        pecas = result.intValue();
        return String.valueOf(pecas);
    }
    
}