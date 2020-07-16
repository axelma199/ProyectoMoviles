/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaAccesoDatos;

 import java.util.ArrayList;

/**
 *
 * @author Axel
 */
public class ObjetosIterador implements Iterador{
     private final ArrayList <Object> lista;
    private int posicion=0;

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    
    
    
    public ObjetosIterador(ArrayList <Object>  o){
            lista=o;
           posicion=0;}

    public int getPosicion() {
        return posicion;
    }
    
    
     @Override
    public Object siguiente(){
           return lista.get(posicion++);
     }
    
   

    @Override
    public boolean tieneSiguiente() {
         if (posicion >= lista.size()) {
				return false;
			} else {
				return true;
			}
    }


     
}
