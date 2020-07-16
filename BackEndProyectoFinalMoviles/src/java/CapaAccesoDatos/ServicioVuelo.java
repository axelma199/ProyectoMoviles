package CapaAccesoDatos;

 import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
 import java.util.ArrayList;
 import CapaLogicaNegocio.*;
 import java.util.Observer;
import oracle.jdbc.driver.OracleTypes;
/**
 * 
 * @author Estudiante
 */
public class ServicioVuelo extends Servicio
{

	private static final String insertarVuelo = "{call insertarVuelo (?,?,?,?,?,?,?,?,?)}";
	private static final String LISTAR = "{?=call listarVuelo()}";
	private static final String buscarVuelo = "{?=call buscarVuelo(?,?,?)}";
	private static final String modificarVuelo = "call modificarVuelo (?,?,?,?,?,?,?,?,?)";
	private static final String eliminarVuelo = "{call eliminarVuelo (?)}";
    private static ServicioVuelo servicioVuelo;

    private boolean esEjecutado;
    private ArrayList lista;
    private Observer observer;
    private int instanciaControlador=0;

      
    
	/** Creates a new instance of ServicioLibro */
	private ServicioVuelo()
	{
	}

    public boolean isEsEjecutado() {
        return esEjecutado;
    }
        
       
        private  static void crearInstancia() {
        if (servicioVuelo == null) {
            servicioVuelo = new ServicioVuelo();
         }
    }

 
    public static ServicioVuelo getInstancia() {
           crearInstancia();

        return servicioVuelo;
    }
 public int getInstanciaControlador() {
        return instanciaControlador;
    }

     public void setInstanciaControlador(int instanciaControlador) {
        this.instanciaControlador = instanciaControlador;
    }
	 
	public void insertarVuelo(Vuelo a) throws GlobalException, NoDataException
	{
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
			 
			pstmt = conexion.prepareCall(insertarVuelo);
			pstmt.setString(1, a.getNombre());
			pstmt.setString(2,a.getCódigo());
			pstmt.setInt(3, a.getTeléfono());
			pstmt.setString(4, a.getCorreo()); 
 			pstmt.setString(5, a.getDirección()); 
	        pstmt.setString(1, a.getNombre());
			pstmt.setString(2,a.getCódigo());
			pstmt.setInt(3, a.getTeléfono());
			pstmt.setString(4, a.getCorreo()); 
 
		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
			boolean resultado = pstmt.execute();
                        esEjecutado=true;
			 

		}
		catch (SQLException e)
		{ esEjecutado=false;
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
	}

	public void modificarVuelo(Vuelo a) throws GlobalException, NoDataException
	{esEjecutado=false;
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
		PreparedStatement pstmt = null;
		try
		{
                     //Se busca para ver si existe en tabla
                     String sqlSelectQuery = 
                           "SELECT * FROM VUELO WHERE CODIGO = ?";
 
                     PreparedStatement preparedStatement = null;
                    ResultSet resultSet = null;
        preparedStatement = conexion.prepareStatement(sqlSelectQuery);
 
             preparedStatement.setString(1, a.getCódigo()); // PLAYER_ID
 
             resultSet = preparedStatement.executeQuery();
             esEjecutado=true;
                           if (resultSet.next()){
 			pstmt = conexion.prepareStatement(modificarVuelo);
			
			pstmt.setString(1, a.getNombre());
			pstmt.setString(2,a.getCódigo());
			pstmt.setInt(3, a.getTeléfono());
			pstmt.setString(4, a.getCorreo()); 
 			pstmt.setString(5, a.getDirección()); 
	        pstmt.setString(1, a.getNombre());
			pstmt.setString(2,a.getCódigo());
			pstmt.setInt(3, a.getTeléfono());
			pstmt.setString(4, a.getCorreo()); 
 

 
			 
			int resultado = pstmt.executeUpdate();

			 
			 
		}}
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
	}

	public void eliminarVuelo(String id) throws GlobalException, NoDataException
	{      esEjecutado=false;

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
		PreparedStatement pstmt = null;
		try
		{
                      String sqlSelectQuery = 
                           "SELECT * FROM vuelo WHERE codigo = ?";
 
                     PreparedStatement preparedStatement = null;
                    ResultSet resultSet = null;
        preparedStatement = conexion.prepareStatement(sqlSelectQuery);
 
             preparedStatement.setString(1, id); // PLAYER_ID
 
             resultSet = preparedStatement.executeQuery();
                           if (resultSet.next()){ 
			pstmt = conexion.prepareStatement(eliminarVuelo);
			pstmt.setString(1, id);
 
			int resultado = pstmt.executeUpdate();
                         esEjecutado=true;

                          }}
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
	}
	public Vuelo buscarVuelo(String id, String origenV,String destinoV) throws GlobalException, NoDataException
	{                        esEjecutado=false;
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
		Vuelo p = null;
		CallableStatement pstmt = null;
		try
		{
			pstmt = conexion.prepareCall(buscarVuelo);
			pstmt.registerOutParameter(1, OracleTypes.CURSOR);
			pstmt.setString(2, id);
                       pstmt.setString(3, origenV);
             pstmt.setString(3, destinoV);
			pstmt.execute();
			rs = (ResultSet)pstmt.getObject(1);
                                                                                                                
			while (rs.next())
			{                         esEjecutado=true;

				 p = new Vuelo();
                                p.setCódigo(rs.getString("codigo")); 
                                p.setNombre(rs.getString("nombre")); 
                                p.setTeléfono(rs.getInt("telefono")); 
                                p.setCorreo(rs.getString("correo")); 
                                p.setDirección(rs.getString("direccion")); 
								  p.setCódigo(rs.getString("codigo")); 
                                p.setNombre(rs.getString("nombre")); 
                                p.setTeléfono(rs.getInt("telefono")); 
                                p.setCorreo(rs.getString("correo")); 
				coleccion.add(p);
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
		if (coleccion == null || coleccion.size() == 0)
		{
			//throw new NoDataException("No hay datos");
		}
		return p;
	}

        
            public ArrayList listarVuelo() throws GlobalException, NoDataException
	{      esEjecutado=false;
		try
		{
			conectar();
		}
		catch (ClassNotFoundException ex)
		{
			throw new GlobalException("No se ha localizado el Driver");
		}

		catch (SQLException e)
		{
			throw new NoDataException("La base de datos no se encuentra disponible");
		}

		ResultSet rs = null;
                         ListaObjetos listaO=new ListaObjetos();

		ArrayList coleccion = new ArrayList();
		Vuelo a = null;
		CallableStatement pstmt = null;
		try
		{
			pstmt = conexion.prepareCall(LISTAR);
			pstmt.registerOutParameter(1, OracleTypes.CURSOR);
			pstmt.execute();
			rs = (ResultSet)pstmt.getObject(1);
                         
                                         
 			while (rs.next())
			{
                               esEjecutado=true;

				 a = new Vuelo();
                                a.setCódigo(rs.getString("codigo")); 
                                a.setNombre(rs.getString("nombre")); 
                                a.setTeléfono(rs.getInt("telefono")); 
                                a.setCorreo(rs.getString("correo")); 
                                a.setDirección(rs.getString("direccion")); 
                            a.setCódigo(rs.getString("codigo")); 
                                a.setNombre(rs.getString("nombre")); 
                                a.setTeléfono(rs.getInt("telefono")); 
                                a.setCorreo(rs.getString("correo")); 
                                   coleccion.add(a);
 
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
		/*if (coleccion == null || coleccion.size() == 0)
		{
			//throw new NoDataException("No hay datos");
		}   */          
                
                 this.lista=coleccion;
 		return coleccion;
 	} 

          

}