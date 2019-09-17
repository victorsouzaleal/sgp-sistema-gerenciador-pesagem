/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lerserialbalanca.models;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lerserialbalanca.persistence.Acoes;

/**
 *
 * @author Desenvolvimento
 */
public class ManipuladorEtiqueta {
    
    private static String path_txt = "C:\\Users\\Desenvolvimento\\Documents\\Java\\etiqueta.txt";
    //private static String path_txt = "./etiqueta.txt";
    private static String path_html = "C:\\Users\\Desenvolvimento\\Documents\\Java\\PRINT.HTML";
    //private static String path_html = "./PRINT.HTML";


    public static void fazerEtiquetaHtml(String placa) throws IOException, ClassNotFoundException, SQLException, ParseException {
        Acoes acao = new Acoes();
        Registro reg = acao.getUltimoRegistro(placa);
        BufferedReader buffRead = new BufferedReader(new InputStreamReader(new FileInputStream(path_txt), StandardCharsets.ISO_8859_1));
        String linha = "";
        OutputStreamWriter buffWrite = new OutputStreamWriter(new FileOutputStream(path_html), StandardCharsets.UTF_8);
        buffWrite.write("");
        buffWrite.append("<!DOCTYPE html><html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'></head><body><pre style='font-size:20'>");
        while (true) {
            if (linha != null) {
                String[] variaveis = new String[]{"$ID", "$PRODUTO", "$FORNECEDOR", "$MOTORISTA", "$PLACA", "$DT_ENTRADA",
                    "$HH_ENTRADA", "$PS_ENTRADA", "$DT_SAIDA", "$HH_SAIDA", "$PS_SAIDA", "$PS_LIQ"};
                for (String str : variaveis) {
                    if (linha.contains(str)) {
                        linha = colocarValores(linha, str, reg);
                    }
                }
                buffWrite.append(linha + "<br>");
            } else {
                break;
            }
            linha = buffRead.readLine();
        }
        buffWrite.append("</pre><script>print()</script></body></html>");
        buffRead.close();
        buffWrite.close();
    }
    
    public static void recriarEtiqueta(int id) throws IOException, ClassNotFoundException, SQLException, ParseException{
        Acoes acao = new Acoes();
        Registro reg = acao.pegarRegistro(id);
        BufferedReader buffRead = new BufferedReader(new InputStreamReader(new FileInputStream(path_txt), StandardCharsets.ISO_8859_1));
        // buffRead = new BufferedReader(new FileReader(path_txt));
        String linha = "";
        OutputStreamWriter buffWrite = new OutputStreamWriter(new FileOutputStream(path_html), StandardCharsets.UTF_8);
        //BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path_html,false));
        buffWrite.write("");
        buffWrite.append("<!DOCTYPE html><html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'></head><body><pre style='font-size:20'>");
        while (true) {
            if (linha != null) {
                String[] variaveis = new String[]{"$ID", "$PRODUTO", "$FORNECEDOR", "$MOTORISTA", "$PLACA", "$DT_ENTRADA",
                    "$HH_ENTRADA", "$PS_ENTRADA", "$DT_SAIDA", "$HH_SAIDA", "$PS_SAIDA", "$PS_LIQ"};
                for (String str : variaveis) {
                    if (linha.contains(str)) {
                        linha = colocarValores(linha, str, reg);
                    }
                }
                buffWrite.append(linha + "<br>");
            } else {
                break;
            }
            linha = buffRead.readLine();
        }
        buffWrite.append("</pre><script>print()</script></body></html>");
        buffRead.close();
        buffWrite.close();
    }

    public static String colocarValores(String linha, String var, Registro reg) throws ParseException {
        SimpleDateFormat dateFormatSql = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatView = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        switch (var) {
            case "$ID":
                linha = linha.replace(var, Integer.toString(reg.getId()));
                break;
            case "$PRODUTO":
                linha = linha.replace(var, reg.getProduto());
                break;
            case "$FORNECEDOR":
                linha = linha.replace(var, reg.getFornecedor());
                break;
            case "$MOTORISTA":
                linha = linha.replace(var, reg.getNome());
                break;
            case "$PLACA":
                linha = linha.replace(var, reg.getPlaca());
                break;
            case "$DT_ENTRADA":
                data = dateFormatSql.parse(reg.getDt_entrada());
                String data_entrada = dateFormatView.format(data);
                linha = linha.replace(var, data_entrada);
                break;
            case "$HH_ENTRADA":
                linha = linha.replace(var, reg.getH_entrada());
                break;
            case "$PS_ENTRADA":
                linha = linha.replace(var, reg.getPs_entrada()+"KG");
                break;
            case "$DT_SAIDA":
                if(reg.getDt_saida() != null){
                    data = dateFormatSql.parse(reg.getDt_entrada());
                    String data_saida = dateFormatView.format(data);
                    linha = linha.replace(var, data_saida);
                } else {
                    linha = linha.replace(var, "---");
                }
                break;
            case "$HH_SAIDA":
                linha = linha.replace(var, (reg.getH_saida() == null)? "---" : reg.getH_saida());
                break;
            case "$PS_SAIDA":
                linha = linha.replace(var, (reg.getPs_saida() == null)? "---" : reg.getPs_saida()+"KG");
                break;
            case "$PS_LIQ":
                linha = linha.replace(var, (reg.getPs_liquido() == null)? "---" : reg.getPs_liquido()+"KG");
                break;
        }
        return linha;
    }
}

    