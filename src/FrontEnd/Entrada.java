/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package FrontEnd;

import BackEnd.Action_List;
import BackEnd.ButtonMapper;
import BackEnd.Button_Event_Handler_Maker;
import javax.swing.*;
import BackEnd.Conexao;
import BackEnd.JComponentMapper;
import BackEnd.JResponsiveComponent;
import BackEnd.Label_Insert;
import BackEnd.SQL_Table_Entry;
import BackEnd.SQL_Table_Field;
import BackEnd.SQL_Table_Manager;
import BackEnd.Textfield_Insert;
import BackEnd.Tuple;
import BackEnd.get_Entries;
import BackEnd.setDados;
import java.awt.GridBagLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Iterator;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Henrique
 */
public class Entrada extends JResponsiveComponent {

    private Conexao c;
    private String selected_row[];
    ArrayList<JComponentMapper> elements = new ArrayList<>();
    ArrayList<Tuple<String,String>> pk_names = new ArrayList<>();
    SQL_Table_Manager mngr;
    String table_name;
    
    public Entrada(String selected_row[], String table_name, Conexao c) {
        this.setModal(true);
        this.c = c;
        this.selected_row = selected_row;
        initComponents(table_name);
        ArrayList<SQL_Table_Field> fields = mngr.getFields();
        pk_names = mngr.getPks();
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        int row = 0;
        for(SQL_Table_Field field : fields)
        {
            if(!field.Get_Auto_Increment().equals("") && selected_row.equals("0"))
                continue;
            Label_Insert lbl = new Label_Insert(field.Get_Field_Name(),row);
            elements.add(lbl);
            row++;
            try
            {
                Textfield_Insert txt = new Textfield_Insert(field.Get_Field_Name(),row,field.Get_Datatype());
                elements.add(txt);
                row++;
                if(txt.element instanceof JTextArea)
                {
                    row+=7;
                }
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, "Erro na criação de máscara");            
                JOptionPane.showMessageDialog(null, e.getMessage());
                JOptionPane.showMessageDialog(null, "A aplicação será finalizada...");
                System.exit(0); //Finalizar a aplicação
            }
        }
        
        ButtonMapper confirmar = new ButtonMapper("Confirmar", jButton1, 0, row);
        elements.add(confirmar);
        this.setSize(800, 1000);
        initGrid();
        
        
        //Cadastrar
        if (selected_row.equals("0")) {
            this.setTitle("Cadastrar");
        }
        //Editar
        else {
            this.setTitle("Editar");
            
            //Carregar campos nos TextFields
            try 
            {
                StringBuilder pks = new StringBuilder();
                pks.append("SELECT * FROM ")
                        .append(table_name)
                        .append(" WHERE ");
                Iterator<Tuple<String,String>>pkterator = pk_names.iterator();
                boolean first = true;
                for(String primary_row : selected_row)
                {
                    if(first)
                        first=false;
                    else
                        pks.append(" AND ");
                    Tuple<String,String> pk = pkterator.next();
                    pks.append(pk.x)
                        .append("=")
                        .append(primary_row);
                }
                pks.append(";");
                this.c.setResultSet(pks.toString());
                
                if (this.c.getResultSet().first()) 
                {
                    ResultSet rs = this.c.getResultSet();
                    Iterator<SQL_Table_Field> fielderator = fields.iterator();
                    
                    for(JComponentMapper element : elements)
                    {
                          if(element.element instanceof JLabel)
                          {
                              continue;
                          }
                          if(element.element instanceof JTextComponent)
                          {
                              JTextComponent current = (JTextComponent) element.element;
                              SQL_Table_Field field = fielderator.next();
                              current.setText(rs.getString(field.Get_Field_Name()));
                          }
                    }
                }
            }
            catch(SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro no DML MySQL"); 
                JOptionPane.showMessageDialog(null , e.getMessage());
                dispose();
            }
            
        }
    }
    private void initGrid()
    {
        for(JComponentMapper element : elements)
        {
            this.add(element.element, element.c);
        }
    }

    private void initComponents(String table_name) {
        this.table_name = table_name;
        this.mngr = new SQL_Table_Manager(table_name, c);
	jButton1 = new JButton();
        Button_Event_Handler_Maker handler = new Button_Event_Handler_Maker(Action_List.confirm_table_change,
                jButton1,
                table_name,
                null,
                (JResponsiveComponent) this,
                this.c,
                this.selected_row
        );
	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	
    }

    public ArrayList<SQL_Table_Entry> get_Entries()
    {
        ArrayList<SQL_Table_Entry> entries = new ArrayList<>();
        Iterator<SQL_Table_Field> fields = mngr.getFields().iterator();
        for(JComponentMapper element : elements)
        {
            if(element.element instanceof JLabel)
                continue;
            else
            {
                if(element.element instanceof JTextComponent)
                {
                    SQL_Table_Field field = fields.next();
                    if(!field.Get_Auto_Increment().equals("") && selected_row.equals("0"))
                        field = fields.next();

                    JTextComponent textcomp = (JTextComponent) element.element;
                    entries.add(new SQL_Table_Entry(textcomp.getText(),field.Get_Datatype()));
                }
            } 
        }
        return entries;
    }
    


    /**
     * @param args the command line arguments
     */


    private JButton jButton1;

    @Override
    public void setDados() {
        
    }
}
