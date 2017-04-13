package dbconnection;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class UtilSql {
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		new UtilSql();
	}
	
	public UtilSql() throws IllegalArgumentException, IllegalAccessException{
		Animal o = new Animal();
		
		o.setIdade(10);
		o.setNome("slaçkdfj");
		o.setPeso(new BigDecimal(5.3));
		System.out.println(getCreateSql(o));
		System.out.println(getInsertSql(o));
	}
	
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

	private String getInsertSql(Object o) throws IllegalArgumentException, IllegalAccessException{
		Class<?> clazz = o.getClass();
		//sbCn = String Builder Column Names // sbCv = String Builder Column Values
		StringBuilder sbCn = new StringBuilder();
		StringBuilder sbCv = new StringBuilder();
		
		sbCn.append("INSERT INTO "+ getTableName(o)+"(");
		int x = 0;
		for(Field f : clazz.getDeclaredFields()){
			Coluna anotacaoColuna = f.getAnnotation(Coluna.class);
			if(x > 0){
				sbCn.append(", ");
				sbCv.append(", ");
			}
			f.setAccessible(true);
			sbCn.append(getColumnName(f, anotacaoColuna));
			if(!sbCn.equals("id_animal")){
				sbCv.append(f.get(o));
			}			
			x++;
		}
		sbCn.append(") VALUES (");
		
//		for(Field f : clazz.getDeclaredFields()){
//			if(x > 0)
//				sbCn.append(", ");
//				
//			f.setAccessible(true);
//			
//			x++;
//		}
		sbCv.append(");");
		
		sbCn.append(sbCv);
		
		
		return sbCn.toString();
	}

	private String getAttributes(Object o) {
		Class<?> clazz = o.getClass();
		StringBuilder sb = new StringBuilder();
		
		int x = 0;
		for(Field f : clazz.getDeclaredFields()){
			if(x > 0)
				sb.append(", ");
			sb.append(f.getName());
			x++;
		}
		
		return sb.toString();
	}

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
}
