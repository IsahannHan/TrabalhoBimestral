package screens;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import dbconnection.Coluna;

public class UtilTela {

	public JPanel generateScreen(Object o){
		JPanel contentPane = new JPanel();
		Class<?> clazz = o.getClass();
		
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
			contentPane.add(textField, createConstraints(0, y));
			textField.setColumns(10);
			x+=2;
			y+=2;
		}
		x-=2;
		
		JButton create = new JButton("Create");		
		contentPane.add(create, createConstraints(x, y));
		
		JButton insert = new JButton("Insert");		
		contentPane.add(insert, createConstraints(x+=2, y));
		
		JButton delete = new JButton("Delete");		
		contentPane.add(delete, createConstraints(x+=2, y));
		
		JButton search = new JButton("Search");		
		contentPane.add(search, createConstraints(x+=2, y));
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 500;
		gbc_scrollPane.gridwidth = 600;
		gbc_scrollPane.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 10;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		return contentPane;
	}
	
	private GridBagConstraints createConstraints(int x, int y){
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridx = x;
		gbc_textField.gridy = y;
		gbc_textField.insets = new Insets(5, 5, 5, 5);
		return gbc_textField;
	}
}
