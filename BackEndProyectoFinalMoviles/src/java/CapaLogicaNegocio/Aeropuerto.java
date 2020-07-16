package CapaLogicaNegocio;

public class Aeropuerto {
    private String código ;
    private String nombre;
    private int teléfono ;
    private String correo ;
    private String dirección;

    public Aeropuerto(){}
    
    public Aeropuerto(String código, String nombre, int teléfono, String correo, String dirección) {
        this.código = código;
        this.nombre = nombre;
        this.teléfono = teléfono;
        this.correo = correo;
        this.dirección = dirección;
    }

    public String getCódigo() {
        return código;
    }

    public void setCódigo(String código) {
        this.código = código;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return
                "código= " + código  +
                "  nombre= " + nombre +
                "  teléfono= " + teléfono +
                "  correo= " + correo +
                "  dirección= " + dirección ;
    }
}
