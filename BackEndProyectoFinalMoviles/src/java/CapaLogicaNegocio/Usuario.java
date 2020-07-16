package CapaLogicaNegocio;

import java.util.Date;

public class Usuario {

    private String correo;
    private String clave;
    private String cedula;
    private String perfil;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private int teléfono;
    private String dirección;
    private String nacionalidad;
    private int edad;
    private Date fechaNacimiento;
    private String estadoCivil;

    public Usuario(String correo, String clave, String cedula, String perfil, String nombre, String primerApellido, String segundoApellido, int teléfono, String dirección, String nacionalidad, int edad, Date fechaNacimiento, String estadoCivil) {
        this.correo = correo;
        this.clave = clave;
        this.cedula = cedula;
        this.perfil = perfil;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.teléfono = teléfono;
        this.dirección = dirección;
        this.nacionalidad = nacionalidad;
        this.edad = edad;
        this.fechaNacimiento = fechaNacimiento;
        this.estadoCivil = estadoCivil;
    }

    public Usuario() {
         
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public int getTeléfono() {
        return teléfono;
    }

    public void setTeléfono(int teléfono) {
        this.teléfono = teléfono;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "correo='" + correo + '\'' +
                ", clave='" + clave + '\'' +
                ", cedula='" + cedula + '\'' +
                ", perfil='" + perfil + '\'' +
                ", nombre='" + nombre + '\'' +
                ", primerApellido='" + primerApellido + '\'' +
                ", segundoApellido='" + segundoApellido + '\'' +
                ", teléfono=" + teléfono +
                ", dirección='" + dirección + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", edad=" + edad +
                ", fechaNacimiento=" + fechaNacimiento +
                ", estadoCivil='" + estadoCivil + '\'' +
                '}';
    }
}