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
	UtilSql sql = new UtilSql();

	//Funções de conexão
	public DbConnection(){
		Connect();
	}
	
	public boolean Connect(){
		String URL = "jdbc:postgresql://localhost:5432/postgres";
		String USER = "1234";
		String PASS = "1234";
		boolean SUCCESS = false;
		
		try {
			con = DriverManager.getConnection(URL,  USER, PASS);
			SUCCESS = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(SUCCESS){
			System.out.println("----CONECTADO----");
			System.out.println("----BANCO: "+URL+"----\n----USUÁRIO: "+USER+"----\n----SENHA: "+PASS+"----");
			System.out.println("------------------ \n\n");
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
				System.out.println("ERRO DISCONEXÃO");
				e.printStackTrace();
			}
		}
	}
	
	//Funções de manuseio da tabela
	public void Create(Object o){
		Class<?> clazz = o.getClass();
		String s = sql.getCreateSql(clazz);
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(s);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void Insert(Object o) throws IllegalArgumentException, IllegalAccessException{
		Class<?> clazz = o.getClass();
		String s = sql.getInsertSql(clazz);
		PreparedStatement ps;
		
		try{	
			ps = con.prepareStatement(s);
			ps.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
		}
		
	}
	
	public void Delete(Object o) throws IllegalArgumentException, IllegalAccessException{
		Class<?> clazz = o.getClass();
		String s = sql.getDeleteSql(clazz);
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(s);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Object> SearchAll(Object o){
		Class<?> clazz = o.getClass();
		List<Object> lista = new ArrayList<Object>();
		String s = sql.getSelectAllSql(clazz);
		PreparedStatement ps;
		
		try{	
			ps = con.prepareStatement(s);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Animal x = new Animal();
				x.setId(rs.getInt(1));
				x.setNome(rs.getString(2));
				x.setIdade(rs.getInt(3));
				x.setPeso(rs.getBigDecimal(4));
				lista.add(x);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}		
		return lista;
	}
	
	public Object SingleSearch(Object o) throws IllegalArgumentException, IllegalAccessException{
		Class<?> clazz = o.getClass();
		String s = sql.getSelectSql(clazz);
		List<Object> list = new ArrayList<Object>();
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(s);
			ResultSet rs = ps.executeQuery();
			
			//Essa linha é necessária para que os objetos em rs sejam acessíveis.
			rs.next();
			
			while(rs.next()){
				list.add();
				rs.getString(2);
				rs.getInt(3);
				rs.getBigDecimal(4);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public void DropTable(Object o){
		Class<?> clazz = o.getClass();
		String s = sql.getDropSql(clazz);
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(s);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
