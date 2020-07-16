/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaAccesoDatos;

import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author Axel
 */
public class Pruebas {
            public static void main(String[] args) throws NoDataException, SQLException, GlobalException, ClassNotFoundException, ParseException /*throws NoDataException, GlobalException, ClassNotFoundException, SQLException*/ {

        //Crear un objeto de la clase View
   
       ServicioAeropuerto servicio= ServicioAeropuerto.getInstancia();
        
      System.out.println("servicio:"+servicio.buscarAeropuerto("mia", "miami"));

        System.out.println("servicioss"+servicio.isEsEjecutado());   
      
}
}
