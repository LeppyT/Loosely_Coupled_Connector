/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.awt.GridBagConstraints;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author root
 */
public class Textfield_Insert extends JComponentMapper{

    public Textfield_Insert(String text,int row, String Datatype) throws Exception
    {
        JComponent txt = null;
        this.name = "txt_"+text;
        if(text.equals("DataNascimento"))
        {
            try
            {
                txt = new JFormattedTextField(new MaskFormatter("####-##-##"));
            }
            catch(Exception e)
            {
                throw e;
            }
        }
        else if(text.equals("CPF"))
        {
            try
            {
                txt = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
            } 
            catch(Exception e)
            {
                throw e;
            }
        }
        else if(text.equals("Observacoes"))
        {
            txt = new JTextArea();
        }
        else
        {
            txt = new JTextField();
        }
        this.element = txt;
        this.c = new GridBagConstraints();
        this.c.anchor=GridBagConstraints.WEST;
        this.c.fill=GridBagConstraints.HORIZONTAL;
        this.c.gridx = 0;
        this.c.weightx = 3;
        this.c.weighty = 2;
        this.c.gridy = row;
        if(txt instanceof JTextArea)
        {
            this.c.gridheight = 8;
            this.c.fill = GridBagConstraints.BOTH;
        }
        this.c.ipadx = 10;
    }
}


