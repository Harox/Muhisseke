package com.firedevz.sistemadegestaofinanceira.modelo;


import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Usuario {

    public static String PAPER_NAME = "usuarios";
    public static String LOGGED_USER = "logged_user";

    int id;
    String nome,telefone,email,endereco,senha;

    public Usuario() {

    }

    public static boolean login(String telefone, String password){
        List<Usuario> usuarios = Paper.book().read(PAPER_NAME, new ArrayList<Usuario>());
        for (Usuario usuario: usuarios) {
            if(usuario.telefone.equals(telefone) && usuario.senha.equals(password)){
                Paper.book().write(LOGGED_USER, usuario);
                return true;
            }
        }
        return false;
    }

    public static boolean exists(String telefone){
        List<Usuario> usuarios = Paper.book().read(PAPER_NAME, new ArrayList<Usuario>());
        for (Usuario usuario: usuarios) {
            if(usuario.telefone.equals(telefone)){
                return true;
            }
        }
        return false;
    }

    public static boolean register(Usuario usuario){
        usuario.id = java.lang.System.identityHashCode(usuario);
        List<Usuario> usuarios = Paper.book().read(PAPER_NAME, new ArrayList<Usuario>());
        usuarios.add(usuario);
        Paper.book().write(PAPER_NAME, usuarios);
        return true;
    }

    public Usuario(int _id, String _Telefone,String _Nome, String _Email, String _Endereco,String _Senha){
        this.id = _id;
        this.telefone=_Telefone;
        this.nome = _Nome;
        this.telefone = _Email;
        this.endereco = _Endereco;
        this.email = _Senha;
    }

    public Usuario(String _Nome, String _Email, String _Endereco, String _Senha){
        this.nome = _Nome;
        this.email = _Email;
        this.endereco = _Endereco;
        this.senha = _Senha;
    }

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public String getTelefone() {

        return telefone;
    }

    public void setTelefone(String telefone) {

        this.telefone = telefone;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {

        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
