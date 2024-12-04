/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author root
 */
public class Label_Insert extends JComponentMapper{
    public Label_Insert(String text,int row)
    {
        this.name = "lbl_"+text;
        JLabel label = new JLabel();
        label.setText(text);
        this.element = label;
        this.c = new GridBagConstraints();
        this.c.anchor=GridBagConstraints.WEST;
        this.c.gridx = 0;
        this.c.weightx = 1;
        this.c.weighty = 1;
        this.c.gridy = row;
        this.c.ipadx = 10;
    }
}
