/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgp.main;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sgp.config.ConfiguracaoGlobal;
import sgp.models.LerSerial;
import sgp.models.Propriedades;
import sgp.models.Threads;

public class Principal extends Application {

    // Stages
    public static Stage primaryStage;
    public static Stage secondStage;
    public static Stage errorStage;

    // **** Variaveis de uso global
    //INFORMAÇÕES DA SERIAL
    private static String peso_bru = "0";
    private static String peso_liq = "0";
    private static String codEstabilidade = "E";
    private static LerSerial serial;

    //THREADS
    Thread serialThread;
    Thread securityThread;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        new Propriedades();
        //Inicia Stage Principal e as Threads
        this.primaryStage = stage;
        this.errorStage = stage;
        iniciarTelaPrincipal();
        serial = new LerSerial(Propriedades.getPorta(), Propriedades.getEquipamento());
        serialThread = new Thread(lerSerial);
        serialThread.start();
    }

    public static void iniciarTelaPrincipal() { //INICIA TELA PRINCIPAL
        try {
            Parent root = FXMLLoader.load(Principal.class.getResource("/sgp/views/telaprincipal.fxml"));
            primaryStage.setTitle(ConfiguracaoGlobal.getTITULO_INICIAL());
            primaryStage.getIcons().addAll(new Image(Principal.class.getResourceAsStream("/sgp/imgs/" + ConfiguracaoGlobal.getICONE_IMG())));
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(true);
            primaryStage.show();
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent arg0) {
                    System.exit(0);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closePrimaryStage() { //FECHA TELA PRINCIPAL
        primaryStage.close();
    }

    public static void closeErrorStage() { //FECHA TELA DE ERRO
        errorStage.close();
    }

    //CARREGA SCENE NO STAGE SECUNDÁRIO COMO MODAL
    public static void loadScene(Scene scene, String titlePage, boolean resizable) {
        if (secondStage == null) {
            secondStage = new Stage();
            secondStage.initModality(Modality.WINDOW_MODAL);
            secondStage.initOwner(primaryStage);
            secondStage.getIcons().add(new Image(Principal.class.getResourceAsStream("/sgp/imgs/" + ConfiguracaoGlobal.getICONE_IMG())));
            secondStage.setResizable(resizable);
            secondStage.setTitle(titlePage);
            secondStage.setScene(scene);
            secondStage.show();
            secondStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent arg0) {
                    secondStage.close();
                    secondStage = null;
                }
            });
        }
    }

    public static Scene sobreScene() { //SCENE DO MENU SOBRE
        Parent root;
        Scene scene = null;
        try {
            root = FXMLLoader.load(Principal.class.getResource("/sgp/views/sobre.fxml"));
            scene = new Scene(root, 395, 209);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return scene;
    }

    public static Scene relatorioScene() { //SCENE DO MENU RELATÓRIO
        Parent root;
        Scene scene = null;
        try {
            root = FXMLLoader.load(Principal.class.getResource("/sgp/views/relatorio.fxml"));
            scene = new Scene(root, 400, 230);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return scene;
    }

    public static Scene configScene() { //SCENE DO MENU CONFIGURAÇÕES GERAIS
        Parent root;
        Scene scene = null;
        try {
            root = FXMLLoader.load(Principal.class.getResource("/sgp/views/config.fxml"));
            scene = new Scene(root, 382, 359);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return scene;
    }

    public static Scene impressaoScene() { //SCENE DO MENU DE CONFIGURAÇÕES DE IMPRESSAO
        Parent root;
        Scene scene = null;
        try {
            root = FXMLLoader.load(Principal.class.getResource("/sgp/views/impressao.fxml"));
            scene = new Scene(root, 432, 489);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return scene;
    }

    public static Scene pesquisaScene() { //SCENE DO MENU DE PESQUISA DE PLACA
        Parent root;
        Scene scene = null;
        try {
            root = FXMLLoader.load(Principal.class.getResource("/sgp/views/pesquisaplaca.fxml"));
            scene = new Scene(root, 860, 482);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return scene;
    }

    private static Runnable lerSerial = new Runnable() { //INICIA THREAD LEITURA SERIAL
        public void run() {
            Threads th = new Threads();
            th.ReadSerialThread(serial);
        }
    };

    /**
     * Retorna o palco principal
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static String getPeso_bru() {
        return peso_bru;
    }

    public static void setPeso_bru(String peso_bru) {
        Principal.peso_bru = peso_bru;
    }

    public static String getCodEstabilidade() {
        return codEstabilidade;
    }

    public static void setCodEstabilidade(String codEstabilidade) {
        Principal.codEstabilidade = codEstabilidade;
    }

    public static String getPeso_liq() {
        return peso_liq;
    }

    public static void setPeso_liq(String peso_liq) {
        Principal.peso_liq = peso_liq;
    }

}
