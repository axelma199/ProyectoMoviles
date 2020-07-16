

/*
- * ServicioLogueo.java
 *
 * Created on 8 de junio de 2007, 22:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package CapaAccesoDatos;
 
import CapaLogicaNegocio.Usuario;
import java.sql.CallableStatement;
 import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Observer;
 import oracle.jdbc.internal.OracleTypes;

 
public class ServicioLogin extends Servicio {
    private static final String login = "{?=call login(?,?)}";
    	private static final String insertarUsuario = "{call insertarUsuario (?,?,?)}";
    private static ServicioLogin servicioLogin;
    public String rol;
    	private static final String obtenerRol = "{?=call obtenerPerfilUsuario(?,?)}";
    private boolean esEjecutado; 
    private int instanciaControlador=0;
    	private static final String BUSCARUSUARIO = "{?=call buscarUsuario(?)}";

 
   
    /** Creates a new instance of ServicioLogueo */
    private ServicioLogin() {
        super();
    }

    public String getRol() { 
        return rol;
    }

    public int getInstanciaControlador() {
        return instanciaControlador;
    }

     public void setInstanciaControlador(int instanciaControlador) {
        this.instanciaControlador = instanciaControlador;
    }
    
    	 
       
        private  static void crearInstancia() {
        if (servicioLogin == null) {
            servicioLogin = new ServicioLogin();
         }
    }

 
    public static ServicioLogin getInstancia( ) {
           crearInstancia(); 

        return servicioLogin;
    }
     

     public boolean loginCliente(String user, String password) throws NoDataException, GlobalException, SQLException, ClassNotFoundException{
         
                boolean resp=true;
                
                
               
           
           conectar(); 
    
            
        ResultSet rs = null;
        CallableStatement pstmt=null;  
        int respuesta=0; 
        try {            
                   
             
            pstmt = conexion.prepareCall(login);            
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);            
            pstmt.setString(2,user);     
            pstmt.setString(3,password);            
            pstmt.execute();
            esEjecutado=true; 
            
              
            
            //********************************
            rs=(ResultSet) pstmt.getObject(1);
            
            
            
            while (rs.next()) {
             respuesta = rs.getInt("esta");
            }
             if(respuesta==0){
             resp=false;
             esEjecutado=false;}
             
             
                     
          
               
            
            
             //********************************
        } catch (SQLException e) {
             resp=false;
             esEjecutado=false;
        } finally {
            
            
            try {
                if (rs != null) {
                    rs.close();
                } 
                if (pstmt != null) {
                    pstmt.close();
                }
             } catch (SQLException e) {
                resp=false;
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
            pstmt = null;
            
               
            
		 if(resp){
                     
			pstmt = conexion.prepareCall(obtenerRol);
			pstmt.registerOutParameter(1, Types.VARCHAR);
			pstmt.setString(2, user);
                        pstmt.setString(3, password);
        
                  
          
                        try{
 
			pstmt.execute();
                        esEjecutado=true;
                        
                        }
                         catch(java.sql.SQLException exep){
                               esEjecutado=false;
                         }
                                         
             
                        
			  rol =  (String) pstmt.getObject(1);
 
        
                  }			   
                  
             
        return resp;
        
        
        
    }

    public boolean isEsEjecutado() {
        return esEjecutado;
    }

    public void setEsEjecutado(boolean esEjecutado) {
        this.esEjecutado = esEjecutado;
    }
     
     public Usuario buscarUsuario(String id) throws SQLException, GlobalException, NoDataException{
                     Usuario usuario=null;
		try
		{
			conectar();
		}
		catch (ClassNotFoundException e)
		{
			throw new GlobalException("No se ha localizado el driver");
		}
		catch (SQLException e)
		{
			throw new NoDataException("La base de datos no se encuentra disponible");
		}
		ResultSet rs = null;
		ArrayList coleccion = new ArrayList();
 		CallableStatement pstmt = null;
		try
		{ 
			pstmt = conexion.prepareCall(BUSCARUSUARIO);
			pstmt.registerOutParameter(1, oracle.jdbc.driver.OracleTypes.CURSOR);
			pstmt.setString(2, id); 

			pstmt.execute(); 
			rs = (ResultSet)pstmt.getObject(1);
                        
                                                                                                               
			while (rs.next())
			{       esEjecutado=true;
				usuario= new Usuario();
                                usuario.setCedula(rs.getString("cedula")); 
                                usuario.setClave(rs.getString("clave")); 
                                usuario.setPerfil(rs.getString("perfil")); 
                                usuario.setCorreo(rs.getString("correo")); 
                                
                                usuario.setCedula(rs.getString("cedula")); 
                                usuario.setClave(rs.getString("clave")); 
                                usuario.setPerfil(rs.getString("perfil")); 
                                usuario.setCorreo(rs.getString("correo")); 
                                
                                usuario.setCedula(rs.getString("cedula")); 
                                usuario.setClave(rs.getString("clave")); 
                                usuario.setPerfil(rs.getString("perfil")); 
                                usuario.setCorreo(rs.getString("correo")); 
                                
                               usuario.setCorreo(rs.getString("correo")); 

                              

   			}
		}
		catch (SQLException e)
		{

			esEjecutado=false;
		}
		finally
		{     
                      
                      
                     
			try
			{
				if (rs != null)
				{
					rs.close();
				}
				if (pstmt != null)
				{
					pstmt.close();
				}
				desconectar();
			}
			catch (SQLException e)
			{
				throw new GlobalException("Estatutos invalidos o nulos");
			}
		}
		
                /*if (coleccion == null || coleccion.isEmpty())
		{
			//throw new NoDataException("No hay datos");
		} */
                
		return usuario;
     
     }
    
   
    
}



