package com.example.proyectofinalmoviles.logicaNegocio;

public class Vuelo {

    private String codigo;
    private String origen;
    private String  destino;
    private String horaPartida;
    private String horaLlegada;
    private int numeroPasajeros;
    private int númeroEscalas;
    private boolean comida;
    private int numeroDeAsientosLibres;
    private int duración;
    private Avion avión;
    private String puertaEmbarque;

    public Vuelo(String codigo, String origen, String destino, String horaPartida, String horaLlegada, int numeroPasajeros, int númeroEscalas, boolean comida, int numeroDeAsientosLibres, int duración, Avion avión, String puertaEmbarque) {
        this.codigo = codigo;
        this.origen = origen;
        this.destino = destino;
        this.horaPartida = horaPartida;
        this.horaLlegada = horaLlegada;
        this.numeroPasajeros = numeroPasajeros;
        this.númeroEscalas = númeroEscalas;
        this.comida = comida;
        this.numeroDeAsientosLibres = numeroDeAsientosLibres;
        this.duración = duración;
        this.avión = avión;
        this.puertaEmbarque = puertaEmbarque;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getHoraPartida() {
        return horaPartida;
    }

    public void setHoraPartida(String horaPartida) {
        this.horaPartida = horaPartida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public int getNumeroPasajeros() {
        return numeroPasajeros;
    }

    public void setNumeroPasajeros(int numeroPasajeros) {
        this.numeroPasajeros = numeroPasajeros;
    }

    public int getNúmeroEscalas() {
        return númeroEscalas;
    }

    public void setNúmeroEscalas(int númeroEscalas) {
        this.númeroEscalas = númeroEscalas;
    }

    public boolean isComida() {
        return comida;
    }

    public void setComida(boolean comida) {
        this.comida = comida;
    }

    public int getNumeroDeAsientosLibres() {
        return numeroDeAsientosLibres;
    }

    public void setNumeroDeAsientosLibres(int numeroDeAsientosLibres) {
        this.numeroDeAsientosLibres = numeroDeAsientosLibres;
    }

    public int getDuración() {
        return duración;
    }

    public void setDuración(int duración) {
        this.duración = duración;
    }

    public Avion getAvión() {
        return avión;
    }

    public void setAvión(Avion avión) {
        this.avión = avión;
    }

    public String getPuertaEmbarque() {
        return puertaEmbarque;
    }

    public void setPuertaEmbarque(String puertaEmbarque) {
        this.puertaEmbarque = puertaEmbarque;
    }

    @Override
    public String toString() {
        return
                "  codigo= " + codigo +
                "  origen=  " + origen +
                "  destino=  " + destino +
                "  horaPartida=  " + horaPartida +
                "  horaLlegada=  " + horaLlegada +
                "  numeroPasajeros= " + numeroPasajeros +
                "  númeroEscalas= " + númeroEscalas +
                "  comida=  " + comida +
                "  numeroDeAsientosLibres=  " + numeroDeAsientosLibres +
                "  duración=  " + duración +
                "  avión=  " + avión +
                "  puertaEmbarque=  " + puertaEmbarque  ;
    }
}
