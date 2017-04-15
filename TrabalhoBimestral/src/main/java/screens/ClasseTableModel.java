package screens;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dbconnection.Coluna;

public class ClasseTableModel extends AbstractTableModel {

	private List<String> lista;
	private Class<?> clazz;
	

	public ClasseTableModel(List<String> lista, Class<?> clazz) {
		this.lista = lista;
		this.clazz = clazz;
	}
	
	public int getColumnCount() {
		int column = 0;
		for(Field f : clazz.getDeclaredFields()){
			if(f.isAnnotationPresent(Coluna.class)){
				column++;
			}
		}
		return column;
	}

	public int getRowCount() {
		return lista.size();
	}

	@Override
	public String getColumnName(int column) {
		
		List<String> columnNames = new ArrayList<String>();
		for(Field f : clazz.getDeclaredFields()){
			columnNames.add(f.getName().toUpperCase());
		}
		return columnNames.get(column);
	}	

	public Object getValueAt(int row, int column) {
		lista.get(row);
		for(Field f : clazz.getDeclaredFields()){
			return lista.get(column);
		}
		return "Coluna inexistente";
	}
}
