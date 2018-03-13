package com.firedevz.sistemadegestaofinanceira.modelo;

public class Movimentos {

    public String PAPER_NAME = "movimentos";

    int idMovimento;
    String contaMovimento;
    float valorMovimento;
    String dataMovimento;
    String tipoMovimento;

    public Movimentos() {
    }

    public Movimentos(int idMovimento, String contaMovimento, float valorMovimento, String dataMovimento, String tipoMovimento) {
        this.idMovimento = idMovimento;
        this.contaMovimento = contaMovimento;
        this.valorMovimento = valorMovimento;
        this.dataMovimento = dataMovimento;
        this.tipoMovimento = tipoMovimento;
    }

    public Movimentos(String contaMovimento, float valorMovimento, String dataMovimento, String tipoMovimento) {
        this.contaMovimento = contaMovimento;
        this.valorMovimento = valorMovimento;
        this.dataMovimento = dataMovimento;
        this.tipoMovimento = tipoMovimento;
    }

    public int getIdMovimento() {
        return idMovimento;
    }

    public void setIdMovimento(int idMovimento) {
        this.idMovimento = idMovimento;
    }

    public String getContaMovimento() {
        return contaMovimento;
    }

    public void setContaMovimento(String contaMovimento) {
        this.contaMovimento = contaMovimento;
    }

    public float getValorMovimento() {
        return valorMovimento;
    }

    public void setValorMovimento(float valorMovimento) {
        this.valorMovimento = valorMovimento;
    }

    public String getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(String dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public String getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(String tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    @Override
    public String toString() {
        return "Movimentos{" +
                "idMovimento=" + idMovimento +
                ", contaMovimento='" + contaMovimento + '\'' +
                ", valorMovimento=" + valorMovimento +
                ", dataMovimento='" + dataMovimento + '\'' +
                ", tipoMovimento='" + tipoMovimento + '\'' +
                '}';
    }
}
