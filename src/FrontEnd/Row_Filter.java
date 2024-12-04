/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FrontEnd;

import BackEnd.Action_List;
import BackEnd.ButtonMapper;
import BackEnd.Button_Event_Handler_Maker;
import BackEnd.JComponentMapper;
import BackEnd.JResponsiveComponent;
import BackEnd.SQL_Table_Field;
import BackEnd.ScrollAdder;
import java.awt.GridBagLayout;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import BackEnd.MyTableModel;
import BackEnd.SQL_Table_Entry;
import java.util.ArrayList;
import javax.swing.JButton;

/**
 *
 * @author root
 */
public class Row_Filter extends JResponsiveComponent{
    int current_row = 0;
    int current_column = 0;
    Tabela tabela;
    JCheckBox[] checkboxes;
    ArrayList<JComponentMapper> elements = new ArrayList<>();
    public Row_Filter(Tabela tabela)
    {
        this.tabela = tabela;
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        this.setSize(800, 600);
        String[] model = new String[tabela.fields.size()];
        int i=0;
        for(SQL_Table_Field field : tabela.fields)
        {
            model[i] = field.Get_Field_Name();
            i++;
        }
        JTable FilterTable = new JTable();
        MyTableModel checkboxmodel = new MyTableModel(model);
        FilterTable.setModel(checkboxmodel);
        checkboxmodel.addRow(tabela.filter_table);
        JButton confirmar = new JButton();
        Button_Event_Handler_Maker handler = new Button_Event_Handler_Maker(Action_List.confirm_filter,confirmar,"",FilterTable,tabela,null,null);
        ButtonMapper confirmar_mapped = new ButtonMapper("Confirmar",confirmar,current_column, current_row);
        elements.add(confirmar_mapped);
        current_row++;
        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.setViewportView(FilterTable);
        ScrollAdder adder = new ScrollAdder("FilterTable", jScrollPane1, FilterTable, current_row);
        elements.add(adder);
        this.setSize(800, 600);
        initGrid();
        FilterTable.addMouseListener(new java.awt.event.MouseAdapter()
        {
                public void mouseClicked(java.awt.event.MouseEvent e)
                {

                    int row=FilterTable.rowAtPoint(e.getPoint());

                    int col= FilterTable.columnAtPoint(e.getPoint());
                    boolean current = (boolean) FilterTable.getValueAt(row, col);
                    FilterTable.setValueAt(!current,row, col);
                }
        }
        );
        
        
    }
    
    private void initGrid()
    {
        for(JComponentMapper element : elements)
        {
            this.add(element.element, element.c);
        }
    }

    @Override
    public void setDados() {
    }

    @Override
    public ArrayList<SQL_Table_Entry> get_Entries() {
        return null;
    }
}


