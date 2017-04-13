package dbconnection;

import java.lang.reflect.Field;

public class UtilSql {
	
	public String getCreateSql(Object o){
		StringBuilder sb = new StringBuilder();
		Class<?> clazz = o.getClass();
		if(!o.getClass().isAnnotationPresent(Tabela.class)){
			throw new RuntimeException("Classe incompatível.");
		}
		
		sb.append("CREATE TABLE "+ getTableName(o) +"(");
		int x = 0;
		for(Field f : clazz.getDeclaredFields()){
			f.setAccessible(true);
			if(x > 0){
				sb.append(", ");
			}
			sb.append(f.getName().toLowerCase());
			
			
			x++;
		}
				
				
				
		return sb.toString();
	}

	private String getValues(Object o) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = o.getClass();
		StringBuilder sb = new StringBuilder();
		
		int x = 0;
		for(Field f : clazz.getDeclaredFields()){
			if(x > 0)
				sb.append(", ");
				
			f.setAccessible(true);
			sb.append(f.get(o));
			x++;
		}
		
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
