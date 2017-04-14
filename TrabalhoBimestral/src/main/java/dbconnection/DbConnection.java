package dbconnection;

import java.io.IOException;
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
	public void Create(Animal a){
		String s = sql.getCreateSql(a);
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(s);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void Insert(Animal a) throws IllegalArgumentException, IllegalAccessException{
		String s = sql.getInsertSql(a);
		PreparedStatement ps;
		
		try{	
			ps = con.prepareStatement(s);
			ps.setString(1, a.getNome());
			ps.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
		}
		
	}
	
	public void Delete(Animal a) throws IllegalArgumentException, IllegalAccessException{
		String s = sql.getDeleteSql(a);
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(s);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Animal> SearchAll(Animal a){
		List<Animal> lista = new ArrayList<Animal>();
		String s = sql.getSelectAllSql(a);
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
	
	public Animal SingleSearch(Animal a) throws IllegalArgumentException, IllegalAccessException{
		String s = sql.getSelectSql(a);
		PreparedStatement ps;
		
		Animal c = new Animal();
		try {
			ps = con.prepareStatement(s);
			ResultSet rs = ps.executeQuery();
			
			//Essa linha é necessária para que os objetos em rs sejam acessíveis.
			rs.next();
			
			c.setId(rs.getInt(1));
			c.setNome(rs.getString(2));
			c.setIdade(rs.getInt(3));
			c.setPeso(rs.getBigDecimal(4));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public void DropTable(Animal a){
		String s = sql.getDropSql(a);
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(s);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
