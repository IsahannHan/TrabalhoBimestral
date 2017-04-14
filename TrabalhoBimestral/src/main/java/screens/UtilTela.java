package screens;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import dbconnection.Animal;
import dbconnection.Coluna;
import dbconnection.DbConnection;

public class UtilTela {

	private JTable table;
	public JPanel generateScreen(Object o){
		final DbConnection sql = new DbConnection();
		final Animal a = new Animal();
		JPanel contentPane = new JPanel();
		Class<?> clazz = o.getClass();
		
		final List<JTextField> textFields = new ArrayList<JTextField>();
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		int x = 0;
		int y = 1;
		
		for(Field f : clazz.getDeclaredFields()){
			
			JLabel lblNewLabel = new JLabel(f.getAnnotation(Coluna.class).nome().toUpperCase());
			contentPane.add(lblNewLabel, createConstraints(0, x));
			
			JTextField textField = new JTextField();
			textFields.add(textField);
			textField.setName(f.getName());
			contentPane.add(textField, createConstraints(0, y));
			textField.setColumns(30);
			x+=2;
			y+=2;
		}
		x = 0;
		
		JButton create = new JButton("Create");
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				sql.Create(a);
			}
		});
		contentPane.add(create, createConstraints(x, y));
		
		JButton insert = new JButton("Insert");		
		insert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				a.setNome(textFields.get(1).getText());
				a.setIdade(Integer.parseInt(textFields.get(2).getText()));
				BigDecimal peso = new BigDecimal(textFields.get(3).getText());
				a.setPeso(peso);
				try {
					sql.Insert(a);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		contentPane.add(insert, createConstraints(x+=2, y));
		
		JButton delete = new JButton("Delete");		
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				a.setId(Integer.parseInt(textFields.get(0).getText()));
				try {
					sql.Delete(a);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		contentPane.add(delete, createConstraints(x+=2, y));
		
		JButton search = new JButton("Search all");	
		search.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				AnimalTableModel x = new AnimalTableModel(sql.SearchAll(a));
				table.setModel(x);
			}
		});
		contentPane.add(search, createConstraints(x+=2, y));
		
		JButton searchid = new JButton("Search by id");	
		searchid.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				a.setId(Integer.parseInt(textFields.get(0).getText()));
				try {
					sql.SingleSearch(a);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		contentPane.add(searchid, createConstraints(x+=2, y));
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 600;
		gbc_scrollPane.gridwidth = 600;
		gbc_scrollPane.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 10;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		return contentPane;
	}
	
	private GridBagConstraints createConstraints(int x, int y){
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridx = x;
		gbc_textField.gridy = y;
		gbc_textField.insets = new Insets(5, 5, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		return gbc_textField;
	}
}
