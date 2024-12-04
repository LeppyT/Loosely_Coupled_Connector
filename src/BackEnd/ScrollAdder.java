/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.REMAINDER;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

/**
 *
 * @author root
 */
public class ScrollAdder extends JComponentMapper{
    JComponent inside;
    public ScrollAdder(String name, JScrollPane element, JComponent inside,int row)
    {
        this.name = name;
        this.element = element;
        this.inside = inside;
        this.c = new GridBagConstraints();
        this.c.fill = GridBagConstraints.HORIZONTAL;
        this.c.weightx = 3;
        this.c.weighty = 1;
        this.c.gridx = 0;
        this.c.gridy = row;
        this.c.gridwidth = REMAINDER;
    }
}
