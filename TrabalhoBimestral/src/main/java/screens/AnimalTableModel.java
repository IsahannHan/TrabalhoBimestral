package screens;

import java.lang.reflect.Field;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dbconnection.Animal;

public class AnimalTableModel extends AbstractTableModel {

	private List<Animal> lista;

	public AnimalTableModel(List<Animal> lista) {
		this.lista = lista;
	}
	
	public int getColumnCount() {
		return 4;
	}

	public int getRowCount() {
		return lista.size();
	}

	@Override
	public String getColumnName(int column) {
		Animal a = new Animal();
		Class<?> clazz = a.getClass();
		Field[] fields = clazz.getDeclaredFields();
		
		switch (column) {
		case 0:
			return fields[column].getName();
		case 1:
			return fields[column].getName();
		case 2:
			return fields[column].getName();
		case 3:
			return fields[column].getName();
		}
		
		return super.getColumnName(column);
	}	

	public Object getValueAt(int row, int column) {
		Animal a = lista.get(row);
		switch (column) {
		case 0:
			return a.getId();
		case 1:
			return a.getNome();
		case 2:
			return a.getIdade();
		case 3:
			return a.getPeso();
		}
		return "Coluna inexistente";
	}
}
