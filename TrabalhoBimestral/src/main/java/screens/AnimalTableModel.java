package screens;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import dbconnection.Animal;

public class AnimalTableModel extends AbstractTableModel {

	private List<Animal> lista;

	public AnimalTableModel(List<Animal> lista) {
		this.lista = lista;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return lista.size();
	}

	@Override
	public String getColumnName(int column) {

		switch (column) {
		case 0:
			return "Id";
		case 1:
			return "Nome";
		}
		
		return super.getColumnName(column);
	}

	@Override
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
		return "coluna inexistente";
	}

}
