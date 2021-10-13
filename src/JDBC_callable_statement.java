import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC_callable_statement {

  public static void muestraErrorSQL(SQLException e) {
    System.err.println("SQL ERROR mensaje: " + e.getMessage());
    System.err.println("SQL Estado: " + e.getSQLState());
    System.err.println("SQL código específico: " + e.getErrorCode());
  }

  public static void main(String[] args) {

		String basedatos = "exemples_accessDades";
		String host = "localhost";
		String port = "3306";
		String parAdic = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String urlConnection = "jdbc:mysql://" + host + ":" + port + "/" + basedatos + parAdic;
		String user = "xavier";
		String pwd = "1234";

    try (
            Connection c = DriverManager.getConnection(urlConnection, user, pwd);
            CallableStatement s = c.prepareCall("{call listado_parcial_clientes(?,?)}")) {

        s.setString(1, "56789012B");
        s.setInt(2, 0);
        s.registerOutParameter(2, java.sql.Types.INTEGER);
        
        s.execute();

        ResultSet rs = s.getResultSet();
        int inout_long = s.getInt(2);
        
        System.out.println("=> inout_long: " + inout_long);
        int nCli=1;
        
        while(rs.next()) {
        	System.out.println("["+(nCli++)+"]");
        	System.out.println("DNI:"+rs.getString("DNI"));
        	System.out.println("Apellidos:"+rs.getString("apellidos"));
        }
        

      } catch (SQLException e) {
        muestraErrorSQL(e);
    } catch (Exception e) {
      System.err.println("ERROR de conexión");
      e.printStackTrace(System.err);
    }
  }

}