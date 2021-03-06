package dbconnection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbconnection.sql.UtilSql;

public class DbConnection {

	private Connection con;
	//Objeto que ser� utilizado para obter as SQLs necess�rias.
	UtilSql sql = new UtilSql();

	//Fun��es de conex�o
	public DbConnection(){
		Connect();
	}
	
	public boolean Connect(){
		String URL = "jdbc:postgresql://localhost:5432/postgres";
		String USER = "postgres";
		String PASS = "admin";
		boolean SUCCESS = false;
		
		try {
			con = DriverManager.getConnection(URL,  USER, PASS);
			SUCCESS = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(SUCCESS){
			System.out.println("----CONECTADO----");
			System.out.println("----BANCO: "+URL+"----\n----USU�RIO: "+USER+"----\n----SENHA: "+PASS+"----");
		}else{
			return SUCCESS;
		}
		return SUCCESS;
	}

	public void Disconnect(){
		if(con != null){
			try {
				con.close();
				System.out.println("DESCONECTANDO...");				
			} catch (SQLException e) {
				System.out.println("ERRO DISCONEX�O");
				e.printStackTrace();
			}
		}
	}
	
	//Fun��es de manuseio da tabela
	public void Create(Object o){
		String s = sql.getCreateSql(o);
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(s);
			ps.executeUpdate();
			System.out.println("\n-----COMANDO EXECUTADO----- \n "+s+"\n---------------------------");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void Insert(Object o, List<?> lista) throws IllegalArgumentException, IllegalAccessException{
		String s = sql.getInsertSql(o, lista);
		PreparedStatement ps;
		
		try{	
			ps = con.prepareStatement(s);
			ps.executeUpdate();
			System.out.println("\n-----COMANDO EXECUTADO----- \n "+s+"\n---------------------------");
		} catch (SQLException e){
			e.printStackTrace();
		}
		
	}
	
	public void Delete(Object o, int id) throws IllegalArgumentException, IllegalAccessException{
		String s = sql.getDeleteSql(o, id);
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(s);
			ps.executeUpdate();
			System.out.println("\n-----COMANDO EXECUTADO----- \n "+s+"\n---------------------------");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> SearchAll(Object o){
		int column = 0;
		for(Field f : ((Class<?>) o).getDeclaredFields()){
			if(f.isAnnotationPresent(Coluna.class)){
				column++;
			}
		}
		
		List<String> lista = new ArrayList<String>(column);
		String s = sql.getSelectAllSql(o);
		PreparedStatement ps;
		ResultSet rs;
		try{	
			ps = con.prepareStatement(s);
			rs = ps.executeQuery();
			
			while(rs.next()){
				for(int j = 1; j <= column; j++){
					lista.add(rs.getString(j));
				}			    
			}
			System.out.println("\n-----COMANDO EXECUTADO----- \n "+s+"\n---------------------------");
		} catch (SQLException e){
			e.printStackTrace();
		}		
		return lista;
	}
	
	public List<String> SearchById(Object o, int id) throws IllegalArgumentException, IllegalAccessException{
		int column = 0;
		for(Field f : ((Class<?>) o).getDeclaredFields()){
			if(f.isAnnotationPresent(Coluna.class)){
				column++;
			}
		}
		
		String s = sql.getSelectSql(o, id);
		List<String> result = new ArrayList<String>();
		PreparedStatement ps;
		ResultSet rs;
		
		try {
			ps = con.prepareStatement(s);
			rs = ps.executeQuery();
			
			while(rs.next()){
				for(int j = 1; j <= column; j++){
					//Salva os campos numa lista do tamanho do n�mero de colunas.
					result.add(rs.getString(j));
				}
			}
			System.out.println("\n-----COMANDO EXECUTADO----- \n "+s+"\n---------------------------");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void DropTable(Object o){
		String s = sql.getDropSql(o);
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(s);
			ps.executeUpdate();
			System.out.println("\n-----COMANDO EXECUTADO----- \n "+s+"\n---------------------------");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
