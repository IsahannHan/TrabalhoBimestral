package screens;

import java.awt.GridBagConstraints;
import java.lang.reflect.Field;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UtilTela {

	public JPanel generateScreen(Object o){
		JPanel contentPane = new JPanel();
		Class<?> clazz = o.getClass();
		
		Field[] fields = clazz.getDeclaredFields();
		
		for(Field f : fields){
			
			JLabel lblNewLabel = new JLabel(f.getName());
			contentPane.add(lblNewLabel, createConstraints(0, 0));
			
			JTextField textField = new JTextField();
			contentPane.add(textField, createConstraints(0, 1));
			textField.setColumns(10);
		}
		
		return contentPane;
	}
	
	private GridBagConstraints createConstraints(int x, int y){
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridx = x;
		gbc_textField.gridy = y;
		return gbc_textField;
	}
}
