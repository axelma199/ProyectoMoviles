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
public class ListaObjetos {
    private ArrayList<Object> lista;
    private int posicion;
    
    
    public ListaObjetos(){
            lista=new ArrayList();
            posicion=0;
        }
    
    public void agregar(Object o){
           lista.add(o); 
    }    
    public ObjetosIterador iterador(){
           return new ObjetosIterador(lista);}
    
    public Object  mostrar(int pos){
                 for(Object  num : lista){
                      
                        System.out.println(num.toString());}
        
            return lista.get(pos);}
     
}
