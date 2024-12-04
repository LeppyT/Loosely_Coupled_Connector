/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;
import java.awt.Insets;
import javax.swing.JButton;

/**
 *
 * @author root
 */



public class Crud_Button extends JComponentMapper{
    
    public Crud_Button(Crud button_type, int button_num,int row)
    {
        this.name = "Button_"+button_type.toString()+"_"+button_num;
        JButton button = new JButton();
        button.setText(button_type.toString());
        this.element = button;
        this.c = new GridBagConstraints();
        this.c.anchor=GridBagConstraints.CENTER;
        this.c.gridx = button_num;
        this.c.weightx = 1;
        this.c.weighty = 1;
        this.c.gridy = row;
    }
    
}
