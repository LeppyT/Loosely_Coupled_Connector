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
public class ButtonMapper extends JComponentMapper{
    public ButtonMapper(String text, JButton button, int button_num,int row)
    {
        button.setText(text);
        this.name = "Button_"+text+"_"+button_num;
        this.element = button;
        this.c = new GridBagConstraints();
        this.c.anchor=GridBagConstraints.CENTER;
        this.c.gridx = button_num;
        this.c.weightx = 1;
        this.c.weighty = 1;
        this.c.gridy = row;
    }
}
