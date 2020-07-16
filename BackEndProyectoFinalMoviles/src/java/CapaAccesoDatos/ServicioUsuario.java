package CapaAccesoDatos;
 
import java.sql.SQLException;
import java.sql.CallableStatement; 
 import CapaLogicaNegocio.Usuario;
import java.util.Date;
import java.util.Observer; 
 
public class ServicioUsuario extends Servicio
{

    private static final String insertarUsuario = "{call insertarUsuario(?,?,?,?,?,?,?,?,?,?,?,?,?)}";
 
    private static ServicioUsuario servicioUsuario;
    private boolean esEjecutado;
    private Observer observer;
    private int instanciaControlador ;

    public boolean isEsEjecutado() {
        return esEjecutado;
    }

    
     public int getInstanciaControlador() {
        return instanciaControlador;
    }

     public void setInstanciaControlador(int instanciaControlador) {
        this.instanciaControlador = instanciaControlador;
    }
     
     
	/** Creates a new instance of ServicioLibro */
	private ServicioUsuario()
	           {   }
        
         	 
       
        private  static void crearInstancia() {
        if (servicioUsuario == null) {
            servicioUsuario = new ServicioUsuario();
         }
    }

 
    public static ServicioUsuario getInstancia( ) {
           crearInstancia();

        return servicioUsuario;
    }


	 public boolean insertarUsuario(Usuario u) throws GlobalException, NoDataException
	{                     
       
            
            String estado;
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
		CallableStatement pstmt = null;

		try
		{
 			
                           
                      
             pstmt = conexion.prepareCall(insertarUsuario);
           pstmt.setString(1, u.getNombre());
			pstmt.setString(2, u.getPrimerApellido());
			pstmt.setString(3,u.getSegundoApellido());
			pstmt.setString(4, u.getCedula());
			
			pstmt.setInt(5, u.getTeléfono());
			pstmt.setString(6, u.getDirección());
			pstmt.setString(7,u.getNacionalidad());
                        
         
			pstmt.setDate(8, (java.sql.Date) u.getFechaNacimiento());
			
			pstmt.setString(9, u.getCorreo());
			pstmt.setString(10, u.getClave());
			pstmt.setString(11,u.getEstadoCivil());
			pstmt.setInt(12, u.getEdad()); 
                         
			pstmt.setString(13, u.getPerfil());
 
                        
 			boolean resultado = pstmt.execute(); 
  
                        esEjecutado=true;
			 
 
		}
		catch (SQLException e)
		{
                     
                       
                    esEjecutado=false;
			 
		}
		finally
		{
                    
     
        
                     
			try
			{
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
                 
	return true;}

   
    
		 
}