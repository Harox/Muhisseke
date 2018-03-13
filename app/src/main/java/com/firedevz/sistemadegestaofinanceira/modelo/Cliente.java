package com.firedevz.sistemadegestaofinanceira.modelo;


import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Cliente {

    public static String PAPER_NAME = "clientes";

    int Id;
    String nome;
    String telefone;
    String email;
    String  morada;
    int nuitCliente;
    float divida;

    public static List<Cliente> list(){
        List<Cliente> clientes = Paper.book().read(PAPER_NAME, new ArrayList<Cliente>());
        return clientes;
    }

    public static Cliente getById(int id){
        List<Cliente> clientes = Paper.book().read(PAPER_NAME, new ArrayList<Cliente>());
        for (Cliente cliente : clientes){
            if(cliente.Id==id){
                return cliente;
            }
        }
        return null;
    }

    public static boolean register(Cliente cliente){
        cliente.Id = java.lang.System.identityHashCode(cliente);
        List<Cliente> clientes = Paper.book().read(PAPER_NAME, new ArrayList<Cliente>());
        clientes.add(cliente);
        Paper.book().write(PAPER_NAME, clientes);
        return true;
    }

    public Cliente(String nome, String telefone, String email, String morada, int nuitCliente, float divida) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.morada = morada;
        this.nuitCliente = nuitCliente;
        this.divida = divida;
    }


    public Cliente(String nome, String telefone, String email, String morada, int nuitCliente) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.morada = morada;
        this.nuitCliente = nuitCliente;
    }

    public Cliente(int id, String nome, String telefone, String email, String morada, float divida) {
        Id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.morada = morada;
        this.divida = divida;
    }

    public Cliente(String nome, String telefone, String email, String morada, float divida) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.morada = morada;
        this.divida = divida;
    }

    public Cliente(String nome, String telefone, String email, String morada) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.morada = morada;
    }

    public Cliente() {

    }

    public Cliente(String nome) {
        this.nome = nome;
    }



    public String getNome() {
        return nome;
    }

    public int getNuitCliente() {
        return nuitCliente;
    }

    public void setNuitCliente(int nuitCliente) {
        this.nuitCliente = nuitCliente;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String contacto) {
        this.telefone = contacto;
    }

    public float getDivida() {
        return divida;
    }

    public void setDivida(float divida) {
        this.divida = divida;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "Id=" + Id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", morada='" + morada + '\'' +
                ", divida=" + divida +
                '}';
    }
}
