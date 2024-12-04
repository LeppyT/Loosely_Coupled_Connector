/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.awt.GridBagConstraints;
import javax.swing.JButton;

/**
 *
 * @author root
 */
public class Filter_Button extends JComponentMapper{
    public Filter_Button(int button_num,int row)
    {
        this.name = "Button_Filtros_button_num";
        JButton button = new JButton();
        button.setText("Filtros");
        this.element = button;
        this.c = new GridBagConstraints();
        this.c.anchor=GridBagConstraints.CENTER;
        this.c.gridx = button_num;
        this.c.weightx = 1;
        this.c.weighty = 1;
        this.c.gridy = row;
    }
}
