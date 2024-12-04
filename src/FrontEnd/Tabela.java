/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package FrontEnd;

import BackEnd.Action_List;
import BackEnd.Button_Event_Handler_Maker;
import java.awt.*;
import javax.swing.*;
import BackEnd.Conexao;
import BackEnd.Filter_Button;
import BackEnd.JComponentMapper;
import BackEnd.JResponsiveComponent;
import BackEnd.SQL_Table_Entry;
import BackEnd.SQL_Table_Field;
import BackEnd.SQL_Table_Manager;
import BackEnd.ScrollAdder;
import BackEnd.Tuple;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.lang.Boolean;
import java.util.Properties;
/**
 *
 * @author Henrique
 */
public class Tabela extends JResponsiveComponent {
    int current_row = 0;
    int current_column = 0;
    public JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
    public JTable Table = new javax.swing.JTable();
    ArrayList<JComponentMapper> elements = new ArrayList<>();
    ArrayList<Tuple<String,String>> pk_names = new ArrayList<>();
    private Conexao c;
    SQL_Table_Manager mngr;
    public String table_name;
    public ArrayList<SQL_Table_Field> fields = new ArrayList<>();
    public Boolean[] filter_table;
    public Properties p = new Properties();
    public Tabela(String table_name) {
        initComponents(table_name);
        elements.add(cruds.Button_Cadastrar);
        elements.add(cruds.Button_Editar);
        elements.add(cruds.Button_Excluir);
        Filter_Button filter = new Filter_Button(current_column, current_row);
        elements.add(filter);
        current_row++;
        
        
        try
        {
            p.load(new FileReader("Filters.properties"));
            filter_table = p.getProperty(table_name+"Filters").chars().mapToObj((c) -> (char)c == '1').toArray(Boolean[]::new);;
        }
        catch(Exception e)
        {
            filter_table = new Boolean[fields.size()];
            for(Boolean b : filter_table)
            {
                b= false;
            }
            String str = new String();
            for(Boolean b : filter_table)
            {
                int res = (b) ? 1:0;
                str = str + res;
            }
            p.setProperty(table_name+"Filters", str);
            try
            {
            p.store(new FileWriter("Filters.properties"), str);
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(null, "Erro");            
                JOptionPane.showMessageDialog(null, e.getMessage());
                JOptionPane.showMessageDialog(null, "A aplicação será finalizada...");
                dispose();
            }
        }
        JButton button = null;
        if(cruds.Button_Cadastrar.element instanceof JButton)
        {
            button = (JButton) cruds.Button_Cadastrar.element;
            Button_Event_Handler_Maker handler_1 = new Button_Event_Handler_Maker(Action_List.add_to_table_form, 
                    button,
                    table_name,
                    Table,
                    (JResponsiveComponent) this,
                    c);
        }
	if(cruds.Button_Editar.element instanceof JButton)
        {
            button = (JButton) cruds.Button_Editar.element;
            Button_Event_Handler_Maker handler_2 = new Button_Event_Handler_Maker(Action_List.edit_selected_form, 
                    button,
                    table_name,
                    Table,
                    (JResponsiveComponent) this,
                    c);
        }
	if(cruds.Button_Excluir.element instanceof JButton)
        {
            button = (JButton) cruds.Button_Excluir.element;
            Button_Event_Handler_Maker handler_3 = new Button_Event_Handler_Maker(Action_List.delete_from_table, 
                    button,
                    table_name,
                    Table,
                    (JResponsiveComponent) this,
                    c);
        }
        if(filter.element instanceof JButton)
        {
            button = (JButton) filter.element;
            Button_Event_Handler_Maker handler_4 = new Button_Event_Handler_Maker(Action_List.open_filter, 
                    button,
                    table_name,
                    Table,
                    this,
                    c);
        }
        ArrayList<String> model = new ArrayList<>();
        int i=0;
        for(SQL_Table_Field field : fields)
        {
            if(filter_table[i])
            {
                model.add(field.Get_Field_Name());
            }
            i++;
        }
        
        boolean no[]  = new boolean[i];
        for(int j=0;j<i;j++)
        {
            no[j] = false;
        }
        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            model.toArray()
        ) {
            boolean[] canEdit = no;

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Table);
        ScrollAdder adder = new ScrollAdder(table_name, jScrollPane1, Table, current_row);
        current_row++;
        elements.add(adder);
        GridBagLayout layout = new GridBagLayout();
        this.setSize(800, 600);
        this.setLayout(layout);
        this.initGrid();
        
        this.setDados();
    }

    private void initGrid()
    {
        for(JComponentMapper element : elements)
        {
            this.add(element.element, element.c);
        }
    }
    
    
    public void setDados() {
        DefaultTableModel d = (DefaultTableModel) Table.getModel();
        
        //Deletar linhas
        while(d.getRowCount() > 0) {
            d.removeRow(0);
        }          
        
        
        
        try {
            this.c.setResultSet("SELECT * FROM paciente");
            if (this.c.getResultSet().first()) {
                do {
                    Object[] Linha = {
                        this.c.getResultSet().getString("CodPaciente"),
                        this.c.getResultSet().getString("Nome"),
                        this.c.getResultSet().getString("Cpf"),
                        this.c.getResultSet().getString("Altura"),
                        this.c.getResultSet().getString("Peso"),
                    };
                    d.addRow(Linha);
                } while(this.c.getResultSet().next());
            }
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    
    private void initComponents(String table_name) {
        this.table_name = table_name;
        this.c = new Conexao();
        this.mngr = new SQL_Table_Manager(table_name, c);
        pk_names = mngr.getPks();
        fields = mngr.getFields();
	cruds = new Crud_Elements(current_column, current_row);
        current_column+=3;
    }

    

        

    /**
     * @param args the command line arguments
     */


    Crud_Elements cruds;

    @Override
    public ArrayList<SQL_Table_Entry> get_Entries() {
        return null;
    }
}
