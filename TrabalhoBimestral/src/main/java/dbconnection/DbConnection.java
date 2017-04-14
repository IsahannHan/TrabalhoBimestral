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
			System.out.println("----BANCO: "+URL+"----\n----USUÁRIO: "+USER+"----\n----SENHA: "+PASS+"----");
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Object> SearchAll(Object o){
		List<Object> lista = new ArrayList<Object>();
		String s = sql.getSelectAllSql(o);
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
		String s = sql.getSelectSql(o);
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
