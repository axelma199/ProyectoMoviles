/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import CapaAccesoDatos.ServicioAeropuerto;
import CapaAccesoDatos.ServicioLogin;
import CapaAccesoDatos.ServicioUsuario;
import CapaLogicaNegocio.Aeropuerto;
import CapaLogicaNegocio.Usuario;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Axel
 */
public class ProyectoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
 protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            try
        {       
            //System.out.println("-----servlet--------------");
            // UserBean user = new UserBean();
                    //response.getOutputStream().println("Hurray !! Thihhhhs Servlet Works");
            
            
                String accion= request.getParameter("accion");
               // accion="listarA";
         if(accion.equals("ingresar")){    
 
            String email=request.getParameter("email"); 
            String clave=request.getParameter("password"); 
            ServicioLogin s=ServicioLogin.getInstancia();
 
             boolean login=s.loginCliente(email, clave);
 
         if(login){ 
 
                 out.println(s.getRol());
            
         }
         else
         {         out.println("Error , correo o clave incorrecta");
                }
         }
       
            
             
           if(accion.equals("registroU")){  
             
              
            String nombre=request.getParameter("nomb");
            String ape1=request.getParameter("ape1");
            String ape2=request.getParameter("ape2");
            String clave=request.getParameter("clave");
            String cedula=request.getParameter("cedula");
            String correo=request.getParameter("correo");
            String direccion=request.getParameter("dir");
            String nacionalidad=request.getParameter("nacion");
            String estadoCivil=request.getParameter("estadoC");
            String fechaN=request.getParameter("fechaN");
            
            String perfil=request.getParameter("perfil");
              
            String edad= (request.getParameter("edad"));
            String telefono= (request.getParameter("tel"));
            
 
 
           Usuario u=new Usuario();

            
            if(fechaN!=null){
             String fechaNac=fechaN;
                            DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                            java.util.Date date=format.parse(fechaN); 
                           java.sql.Date inputDate = new java.sql.Date(date.getTime()); 
                        u.setFechaNacimiento(inputDate);}
            
             
             u.setCedula(cedula);
            u.setClave(clave);
            u.setNombre(nombre);
            u.setPrimerApellido(ape1);

            u.setSegundoApellido(ape2);
 
             if(telefono!=null){
            u.setTeléfono(Integer.parseInt(telefono));}
            u.setCorreo(correo);
            u.setDirección(direccion);
 
            if(edad!=null){
            u.setEdad(Integer.parseInt(edad));}
 
            
            u.setPerfil(perfil);
            u.setNacionalidad(nacionalidad);
            u.setEstadoCivil(estadoCivil);
            
 
            
           ServicioUsuario servicio= ServicioUsuario.getInstancia();
             
           if(u.getNombre()!=null){
                   servicio.insertarUsuario(u);
                    
                    if(servicio.isEsEjecutado()){
                        out.print("Usuario agregado correctamente");}
                    
                    else{
                    out.print("Error , usuario no fue agregado");}  }
          }
          
          
           if(accion.equals("insertarA")){
          {  
             
            String nombre=request.getParameter("nomb");
            String codigo=request.getParameter("codigo");
            String direccion=request.getParameter("dir");
            String correo=request.getParameter("correo");

            String telefono= (request.getParameter("tel"));
 
 
           Aeropuerto a=new Aeropuerto();
 
                      
            a.setCódigo(codigo);
            a.setNombre(nombre);
            a.setCorreo(correo);
            a.setDirección(direccion);
            
              
  
             if(telefono!=null){
                 a.setTeléfono(Integer.parseInt(telefono));}
             
            
           ServicioAeropuerto servicio= ServicioAeropuerto.getInstancia();
             
           if(a.getNombre()!=null){
                servicio.insertarAeropuerto(a);
                    
                    if(servicio.isEsEjecutado()){
                        out.print("Aeropuerto agregado correctamente");}
                    
                    else{
                    out.print("Error , aeropuerto no fue agregado");}  }
          }
          
          
          }
          
           if(accion.equals("modificarA")){
          {  
             
            String nombre=request.getParameter("nomb");
            String codigo=request.getParameter("codigo");
            String direccion=request.getParameter("dir");
            String correo=request.getParameter("correo");

            String telefono= (request.getParameter("tel"));
 
 
           Aeropuerto a=new Aeropuerto();
 
                      
            a.setCódigo(codigo);
            a.setNombre(nombre);
            a.setCorreo(correo);
            a.setDirección(direccion);
            
              
  
             if(telefono!=null){
                 a.setTeléfono(Integer.parseInt(telefono));}
             
            
           ServicioAeropuerto servicio= ServicioAeropuerto.getInstancia();
             
           if(a.getNombre()!=null){
                servicio.modificarAeropuerto(a);
                    
                    if(servicio.isEsEjecutado()){
                        out.print("Aeropuerto modificado correctamente");}
                    
                    else{
                    out.print("Error , aeropuerto no fue modificado");}  }
          }
          
          
          }
            
           
           
             if(accion.equals("eliminarA")){
          {  
             
            String codigoE=request.getParameter("codigoE"); 

    
           ServicioAeropuerto servicio= ServicioAeropuerto.getInstancia();
            
                servicio.eliminarAeropuerto(codigoE);
                    
                    if(servicio.isEsEjecutado()){
                        out.print("Aeropuerto eliminado correctamente");}
                    
                    else{
                    out.print("Error , aeropuerto no fue eliminado");}  
          }
          
          
          }
             
           
             
 
     if (accion.equals("buscarA")) { 
               
           ServicioAeropuerto servicio=ServicioAeropuerto.getInstancia();
              String codigoB = request.getParameter("codigoB");
              String nombreB =  request.getParameter("nombreB");
           
         Aeropuerto a= servicio.buscarAeropuerto(codigoB,nombreB ); 
               // out.println("+"+codigoB++"no");

         if(a!=null){
              Gson gson = new Gson();        
              
              String js=gson.toJson(a);
               out.println(js);
                 

                                    }
           else{
           out.println("NOT"); }
                                   
    
 
} 
     
     
       if (accion.equals("listarA")) {
           
            ServicioAeropuerto servicio= ServicioAeropuerto.getInstancia();
            ArrayList<Aeropuerto> lista=   servicio.listarAeropuerto();
                       Gson gson = new Gson();        
              
                       String js=gson.toJson(lista);
                       out.println(js);
  
    
               }
 
 ////////////////////////////////////////////////CRUD DE VUELOS //////////////////////////////////////////////////////////////////////////////////////////////            
			 
			  if(accion.equals("insertarV")){
          {  
             
              String codigo=request.getParameter("codigo");
            String origen=request.getParameter("origen");
            String destino=request.getParameter("destino");
            String comida=request.getParameter("comida");
            String horaSalida=request.getParameter("horaSalida");
            String horaLlegada=request.getParameter("horaLlegada");
            String escalas=request.getParameter("escalas");
            String numPasajeros=request.getParameter("numPasajeros");
            String avion= (request.getParameter("avion"));
 
 
           Vuelo a=new Vuelo();
 
                      
            a.setCódigo(codigo);
            a.setNombre(nombre);
            a.setCorreo(correo);
            a.setDirección(direccion);
                a.setCódigo(codigo);
            a.setNombre(nombre);
            a.setCorreo(correo);
            a.setDirección(direccion);
             a.setDirección(direccion);

              
  
             if(telefono!=null){
                 a.setTeléfono(Integer.parseInt(telefono));}
             
            
           ServicioVuelo servicio= ServicioVuelo.getInstancia();
             
           if(a.getNombre()!=null){
                servicio.insertarVuelo(a);
                    
                    if(servicio.isEsEjecutado()){
                        out.print("Vuelo agregado correctamente");}
                    
                    else{
                    out.print("Error , vuelo no fue agregado");}  }
          }
          
          
          }
          
           if(accion.equals("modificarV")){
          {  
             
            String codigo=request.getParameter("codigo");
            String origen=request.getParameter("origen");
            String destino=request.getParameter("destino");
            String comida=request.getParameter("comida");
            String horaSalida=request.getParameter("horaSalida");
            String horaLlegada=request.getParameter("horaLlegada");
            String escalas=request.getParameter("escalas");
            String numPasajeros=request.getParameter("numPasajeros");
            String avion= (request.getParameter("avion"));
 
 
           Vuelo a=new Vuelo();
 
                      
            a.setCódigo(codigo);
            a.setNombre(nombre);
            a.setCorreo(correo);
            a.setDirección(direccion);
                a.setCódigo(codigo);
            a.setNombre(nombre);
            a.setCorreo(correo);
            a.setDirección(direccion);
             a.setDirección(direccion);

   
             
            
           ServicioVuelo servicio= ServicioVuelo.getInstancia();
             
           if(a.getNombre()!=null){
                servicio.modificarVuelo(a);
                    
                    if(servicio.isEsEjecutado()){
                        out.print("Vuelo modificado correctamente");}
                    
                    else{
                    out.print("Error , vuelo no fue modificado");}  }
          }
          
          
          }
            
           
           
             if(accion.equals("eliminarV")){
          {  
             
            String codigoE=request.getParameter("codigoE"); 

    
           ServicioVuelo servicio= ServicioVuelo.getInstancia();
            
                servicio.eliminarVuelo(codigoE);
                    
                    if(servicio.isEsEjecutado()){
                        out.print("Vuelo eliminado correctamente");}
                    
                    else{
                    out.print("Error , vuelo no fue eliminado");}  
          }
          
          
          }
             
           
             
 
     if (accion.equals("buscarV")) { 
               
           ServicioVuelo servicio=ServicioVuelo.getInstancia();
              String codigoB = request.getParameter("codigoB");
              String origenB =  request.getParameter("origenB");
              String destinoB =  request.getParameter("destinoB");

         Vuelo a= servicio.buscarVuelo(codigoB,origenB,destinoB ); 
               // out.println("+"+codigoB++"no");

         if(a!=null){
              Gson gson = new Gson();        
              
              String js=gson.toJson(a);
               out.println(js);
                 

                                    }
           else{
           out.println("NOT"); }
                                   
    
 
} 
     
     
       if (accion.equals("listarV")) {
           
            ServicioVuelo servicio= ServicioVuelo.getInstancia();
            ArrayList<Vuelo> lista=   servicio.listarAeropuerto();
                       Gson gson = new Gson();        
              
                       String js=gson.toJson(lista);
                       out.println(js);
  
    
               }
			    
                                   
           // System.out.println("uname ins ervlet==="+uname);

            //System.out.println("password in servlet==="+password);
            
            //out.println("Bienvenidos al servlet");*/
          
        }               
        catch (Throwable theException)      
        {
            System.out.println(theException); 
        }
         }
    }

            
             

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
