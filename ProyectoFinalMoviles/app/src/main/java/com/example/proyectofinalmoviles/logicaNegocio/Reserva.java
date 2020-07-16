package com.example.proyectofinalmoviles.logicaNegocio;

import java.util.Date;

public class Reserva {

   private String  nombre;
    private String   apellidos;
    private String   cédula;
    private int    teléfono;
    private String   correo;
    private String   dirección;
    private String   nacionalidad;
    private String   fechaNacimiento;

    public Reserva(String nombre, String apellidos, String cédula, int teléfono, String correo, String dirección, String nacionalidad, String fechaNacimiento) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.cédula = cédula;
        this.teléfono = teléfono;
        this.correo = correo;
        this.dirección = dirección;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCédula() {
        return cédula;
    }

    public void setCédula(String cédula) {
        this.cédula = cédula;
    }

    public int getTeléfono() {
        return teléfono;
    }

    public void setTeléfono(int teléfono) {
        this.teléfono = teléfono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDirección() {
        return dirección;
    }

    public void setDirección(String dirección) {
        this.dirección = dirección;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", cédula='" + cédula + '\'' +
                ", teléfono=" + teléfono +
                ", correo='" + correo + '\'' +
                ", dirección='" + dirección + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                '}';
    }
}
