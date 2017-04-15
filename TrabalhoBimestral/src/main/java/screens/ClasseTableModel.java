package screens;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dbconnection.Coluna;

public class ClasseTableModel extends AbstractTableModel {

	private List<String> lista;
	private Class<?> clazz;
	int columnNumber = 0;
	List<String> columnNames = new ArrayList<String>();
	int index = 0;
	
	public ClasseTableModel(List<String> lista, Class<?> clazz) {
		this.lista = lista;
		this.clazz = clazz;
		
		for(Field f : clazz.getDeclaredFields()){
			if(f.isAnnotationPresent(Coluna.class)){
				//Salva o nome das colunas e o número de colunas.
				columnNumber++;
				columnNames.add(f.getName().toUpperCase());
			}
		}
	}
	
	public int getColumnCount() {
		return columnNumber;
	}

	public int getRowCount() {
		//Essa divisão é realizada pois a lista vem com todos os registros enfileirados.
		return lista.size() / columnNumber;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames.get(column);
	}	

	public Object getValueAt(int row, int column) {
		//O incremento desse índice viajará por toda a lista, percorrendo todos os membros.
		return lista.get(index++);
	}
}
