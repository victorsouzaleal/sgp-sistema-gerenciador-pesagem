/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lerserialbalanca.models;

import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lerserialbalanca.persistence.Acoes;

/**
 *
 * @author Desenvolvimento
 */
public class Registro {
    private int id;
    private String placa;
    private String nome;
    private String produto;
    private String fornecedor;
    private String ps_entrada;
    private String ps_saida;
    private String ps_liquido;
    private String dt_entrada;
    private String h_entrada;
    private String dt_saida;
    private String h_saida;
    
    public Registro(){
    
    }
    
    public Registro(int id,String placa, String nome, String produto, String fornecedor, String dt_entrada, String h_entrada, String ps_entrada, String dt_saida, String h_saida, String ps_saida, String ps_liquido){
        setId(id);
        setPlaca(placa);
        setNome(nome);
        setProduto(produto);
        setFornecedor(fornecedor);
        setDt_entrada(dt_entrada);
        setH_entrada(h_entrada);
        setPs_entrada(ps_entrada);
        setDt_saida(dt_saida);
        setH_saida(h_saida);
        setPs_saida(ps_saida);
        setPs_liquido(ps_liquido);
    }
    
    public boolean registrarEntrada(String placa, String ps_entrada) throws ClassNotFoundException, SQLException{
        Acoes acao = new Acoes();
        Motorista mot = acao.procurarPlaca(placa);
        SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fmtTime = new SimpleDateFormat("HH:mm:ss");
        Date data = new Date();
        this.setPlaca(placa);
        this.setNome(mot.getNome());
        this.setProduto(mot.getProduto());
        this.setFornecedor(mot.getFornecedor());
        this.setDt_entrada(fmtDate.format(data));
        this.setH_entrada(fmtTime.format(data));
        this.setPs_entrada(ps_entrada);
        return acao.entradaRegistro(this);
    }
    
    public boolean registrarSaida(String placa, String ps_entrada, String ps_saida) throws ClassNotFoundException, SQLException{
        Acoes acao = new Acoes();
        SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fmtTime = new SimpleDateFormat("HH:mm:ss");
        Date data = new Date();
        Registro reg = new Registro();
        reg.setPlaca(placa);
        reg.setDt_saida(fmtDate.format(data));
        reg.setH_saida(fmtTime.format(data));
        reg.setPs_saida(ps_saida);
        reg.setPs_liquido(String.valueOf(Integer.parseInt(ps_saida) - Integer.parseInt(ps_entrada)));
        return acao.saidaRegistro(reg);
    }
    
    public Registro ultimoRegistro(String placa) throws ClassNotFoundException, SQLException{
        Acoes acao = new Acoes();
        return acao.getUltimoRegistro(placa);
    }
    
    public ObservableList<Registro> listaDeRegistros() throws ClassNotFoundException, SQLException, ParseException {
        Acoes acao = new Acoes();
        return FXCollections.observableList(acao.listarRegistros());
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    
    public String getPs_entrada() {
        return ps_entrada;
    }

    public void setPs_entrada(String ps_entrada) {
        this.ps_entrada = ps_entrada;
    }

    public String getPs_saida() {
        return ps_saida;
    }

    public void setPs_saida(String ps_saida) {
        this.ps_saida = ps_saida;
    }

    public String getPs_liquido() {
        return ps_liquido;
    }

    public void setPs_liquido(String ps_liquido) {
        this.ps_liquido = ps_liquido;
    }

    public String getDt_entrada() {
        return dt_entrada;
    }

    public void setDt_entrada(String dt_entrada) {
        this.dt_entrada = dt_entrada;
    }

    public String getH_entrada() {
        return h_entrada;
    }

    public void setH_entrada(String h_entrada) {
        this.h_entrada = h_entrada;
    }

    public String getDt_saida() {
        return dt_saida;
    }

    public void setDt_saida(String dt_saida) {
        this.dt_saida = dt_saida;
    }

    public String getH_saida() {
        return h_saida;
    }

    public void setH_saida(String h_saida) {
        this.h_saida = h_saida;
    }
    
    
}