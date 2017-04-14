package dbconnection.sql;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import dbconnection.Animal;
import dbconnection.Coluna;
import dbconnection.Pessoa;
import dbconnection.Tabela;

public class UtilSql {
	
	//Métodos CRUD
	public String getCreateSql(Object o){
		StringBuilder sb = new StringBuilder();
		Class<?> clazz = o.getClass();
		
		String nomeTabela = getTableName(o);
		sb.append("CREATE TABLE "+ nomeTabela +"( ");
		
		//Array que pega todos os atributos da classe, os quais serão criados como colunas.
		Field[] atributos = clazz.getDeclaredFields();
		
		for(int i = 0; i < atributos.length; i++){
			if(i > 0){
				sb.append(", ");
			}
				
			Field field = atributos[i];
			
			Coluna anotacaoColuna = field.getAnnotation(Coluna.class);
			
			String nomeColuna = getColumnName(field, anotacaoColuna);
			String tipoColuna = "";
			StringBuilder restricaoColuna = new StringBuilder();
			
			
			//Define as restrições da coluna.
			if(anotacaoColuna.pk()){
				restricaoColuna.append("PRIMARY KEY ");
			}
			if(!anotacaoColuna.restricao().isEmpty()){
				restricaoColuna.append(anotacaoColuna.restricao());
			}
			
			//Define o tipo da coluna.
			Class<?> tipoParametro = field.getType();
			if(tipoParametro.equals(String.class)){
				tipoColuna = "VARCHAR(255)";
			} else if (tipoParametro.equals(int.class)){
				if(!restricaoColuna.toString().contains("PRIMARY KEY")){
					tipoColuna = "INTEGER";
				}
			} else if (tipoParametro.equals(BigDecimal.class)){
				tipoColuna = "NUMERIC(5, 2)";
			} else {
				tipoColuna = "TIPO_DESCONHECIDO";
			}
			
			sb.append("\n\t").append(nomeColuna +" "+ tipoColuna +" "+ restricaoColuna);
		}
		sb.append("\n);");
				
		return sb.toString();
	}

	public String getInsertSql(Object o){
		Class<?> clazz = o.getClass();
		//sbCn = String Builder Column Names // sbCv = String Builder Column Values
		StringBuilder sb = new StringBuilder();
		StringBuilder sbCn = new StringBuilder();
		StringBuilder sbCv = new StringBuilder();
		
		sbCn.append("INSERT INTO "+ getTableName(o)+"(");
		int x = 0;
		for(Field f : clazz.getDeclaredFields()){
			Coluna anotacaoColuna = f.getAnnotation(Coluna.class);
			//Fará a vírgula somente depois que passar do primary key (id_animal).
			if(x > 1){
				sbCn.append(", ");
				sbCv.append(", ");
			}
			f.setAccessible(true);
			
			if(anotacaoColuna.pk() == false){
				try {
					sbCv.append(f.get(o));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sbCn.append(getColumnName(f, anotacaoColuna));
			}			
			x++;
		}
		sbCn.append(")\nVALUES (");		
		sbCv.append(");");
		
		sb.append(sbCn.append(sbCv));
		
		return sb.toString();
	}
	
	public String getDeleteSql(Object o){
		StringBuilder sb = new StringBuilder();
		Class<?> clazz = o.getClass();
		
		sb.append("DELETE FROM "+ getTableName(o) +" WHERE ");
		for(Field f : clazz.getDeclaredFields()){
			f.setAccessible(true);
			Coluna anotacaoColuna = f.getAnnotation(Coluna.class);
			
			if(anotacaoColuna.pk() == true){
				sb.append(getColumnName(f, anotacaoColuna)+ " = ");
				try {
					sb.append(f.get(o) +";");
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return sb.toString();
	}
	
	public String getDropSql(Object o){
		StringBuilder sb = new StringBuilder();
		
		sb.append("DROP TABLE "+ getTableName(o) +";");
		return sb.toString();
	}
	
	public String getSelectAllSql(Object o){
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT * FROM " + getTableName(o) + ";");		
		return sb.toString();
	}
	
	public String getSelectSql(Object o){
		Class<?> clazz = o.getClass();
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT * FROM " + getTableName(o) + " WHERE ");
		for(Field f : clazz.getDeclaredFields()){
			f.setAccessible(true);
			Coluna anotacaoColuna = f.getAnnotation(Coluna.class);
			
			if(anotacaoColuna.pk() == true){
				sb.append(getColumnName(f, anotacaoColuna)+ " = ");
				try {
					sb.append(f.get(o) +";");
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	//Métodos auxiliares
	private String getTableName(Object o) {
		Class<?> clazz = o.getClass();
		
		//Cria o nome da tabela com base na anotação.
		String nomeTabela;
		if(clazz.isAnnotationPresent(Tabela.class)){
			Tabela anotacaoTabela = clazz.getAnnotation(Tabela.class);
			nomeTabela = anotacaoTabela.value().toLowerCase();
		}else{
			nomeTabela = clazz.getSimpleName().toLowerCase();
		}
		
		return nomeTabela;
	}
	
	private String getColumnName(Field field, Coluna anotacaoColuna) {
		String nomeColuna;
		//Define o nome da coluna.
		if(field.isAnnotationPresent(Coluna.class)){
			if(anotacaoColuna.nome().isEmpty()){
				nomeColuna = field.getName().toLowerCase();
			}else{
				nomeColuna = anotacaoColuna.nome();
			}
		}else{
			nomeColuna = field.getName().toLowerCase();
		}		
		return nomeColuna;
		
	}
}
