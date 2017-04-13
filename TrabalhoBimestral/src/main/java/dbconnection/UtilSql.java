package dbconnection;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class UtilSql {
	
	public static void main(String[] args) {
		new UtilSql();
	}
	
	public UtilSql(){
		Animal o = new Animal();
		
		o.setIdade(10);
		o.setNome("sla�kdfj");
		o.setPeso(new BigDecimal(5.3));
		System.out.println(getCreateSql(o));
//		System.out.println(getInsertSql(o));
	}
	
	public String getCreateSql(Object o){
		StringBuilder sb = new StringBuilder();
		Class<?> clazz = o.getClass();
		
		//Cria o nome da tabela com base na anota��o.
		String nomeTabela;
		if(clazz.isAnnotationPresent(Tabela.class)){
			Tabela anotacaoTabela = clazz.getAnnotation(Tabela.class);
			nomeTabela = anotacaoTabela.value().toLowerCase();
		}else{
			nomeTabela = clazz.getSimpleName().toLowerCase();
		}
			
		sb.append("CREATE TABLE "+ nomeTabela +"( ");
		
		//Array que pega todos os atributos da classe, os quais ser�o criados como colunas.
		Field[] atributos = clazz.getDeclaredFields();
		
		for(int i = 0; i < atributos.length; i++){
			if(i > 0){
				sb.append(", ");
			}
				
			Field field = atributos[i];
			
			String nomeColuna;
			String tipoColuna = "";
			StringBuilder restricaoColuna = new StringBuilder();
			
			//Define as restri��es da coluna tais como pk ou not null.
			if(field.isAnnotationPresent(Coluna.class)){
				Coluna anotacaoColuna = field.getAnnotation(Coluna.class);
				
				if(anotacaoColuna.nome().isEmpty()){
					nomeColuna = field.getName().toLowerCase();
				}else{
					nomeColuna = anotacaoColuna.nome();
				}
				
				if(anotacaoColuna.pk()){
					restricaoColuna.append("PRIMARY KEY ");
				}
				if(!anotacaoColuna.restricao().isEmpty()){
					restricaoColuna.append(anotacaoColuna.restricao());
				}
			}else{
				nomeColuna = field.getName().toLowerCase();
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

	private String getInsert(Object o){
		Class<?> clazz = o.getClass();
		StringBuilder sb = new StringBuilder();
		
		
		
//		int x = 0;
//		for(Field f : clazz.getDeclaredFields()){
//			if(x > 0)
//				sb.append(", ");
//				
//			f.setAccessible(true);
//			sb.append(f.get(o));
//			x++;
//		}
		
		return sb.toString();
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
		return o.getClass().getSimpleName().toLowerCase();
	}
}
