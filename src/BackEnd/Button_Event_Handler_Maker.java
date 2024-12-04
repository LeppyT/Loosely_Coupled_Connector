/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import FrontEnd.Entrada;
import FrontEnd.Row_Filter;
import FrontEnd.Tabela;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author root
 */
public class Button_Event_Handler_Maker {
    Action_List action;
    JButton button;
    String table_name;
    JTable current_grid;
    JResponsiveComponent caller;
    Conexao c;
    SQL_Table_Manager mngr;
    String[] selected_row;
    ArrayList<Tuple<String,String>> pk_names = new ArrayList<>();
    public Button_Event_Handler_Maker(Action_List action,
            JButton button,
            String table_name,
            JTable current_grid,
            JResponsiveComponent caller,
            Conexao c,
            String[] selected_row)
    {
        try
        {
            if(action!= Action_List.confirm_table_change)
            {
                throw new Exception("Ação invalida para construtor.");
            }
            else
            {
                this.selected_row = selected_row;
                this.action = action;
                this.button = button;
                this.table_name = table_name;
                this.current_grid = current_grid;
                this.caller = caller;
                this.c = c;
                this.mngr = new SQL_Table_Manager(table_name, c);
                pk_names = mngr.getPks();
                button.addActionListener(e ->{
                    Button_Confirm(e);
                });
            }
        }
        catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    System.exit(0);
                }
    }
    
    public Button_Event_Handler_Maker(Action_List action,
            JButton button,
            String table_name,
            JTable current_grid,
            JResponsiveComponent caller,
            Conexao c)
        {
            try
            {
                if(action != Action_List.open_filter 
                        ||action!= Action_List.edit_selected_form
                        ||action!= Action_List.delete_from_table
                        ||action!=Action_List.add_to_table_form)
                {
                    throw new Exception("Ação invalida para construtor.");
                }
                boolean delete = action == Action_List.delete_from_table;
                boolean edit  = action == Action_List.edit_selected_form;
                boolean open = action == Action_List.open_filter;
                this.selected_row = selected_row;
                this.action = action;
                this.button = button;
                this.table_name = table_name;
                this.current_grid = current_grid;
                this.caller = caller;
                this.c = c;
                this.mngr = new SQL_Table_Manager(table_name, c);
                pk_names = mngr.getPks();
                if(delete)
                {
                    button.addActionListener(e ->{
                        Button_Delete(e);
                    });
                }
                else if(edit)
                {
                    button.addActionListener(e ->{
                        Button_Edit(e);
                    });
                }
                else if(open)
                {
                    button.addActionListener(e ->{
                        Button_Open_Filter(e);
                    });
                }
                else
                {
                    button.addActionListener(e ->{
                        Button_Add(e);
                    });
                }
            }
            catch (Exception ex) 
            {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        System.exit(0);
            }
        }

    public Button_Event_Handler_Maker(Action_List action,
            JButton button,
            String table_name,
            JTable current_grid,
            Tabela caller,
            Conexao c)
    {
        try
        {
            
            if(action != Action_List.confirm_filter)
            {
                throw new Exception("Ação invalida para construtor.");
            }
            else
            {
                this.action = action;
                this.button = button;
                this.table_name = table_name;
                this.current_grid = current_grid;
                this.caller = caller;
                this.c = c;
                this.mngr = new SQL_Table_Manager(table_name, c);
                    button.addActionListener(e ->{
                    try {
                        Button_Confirm_Filter(e);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        System.exit(0);
                    }
                    });
            }
        }
        catch(Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        System.exit(0);
        }
    }
    
    private void Button_Delete(java.awt.event.ActionEvent evt)
    {
        if (IsLinhaSelecionada()) 
            {
                int e = JOptionPane.showConfirmDialog(null,
                    "Deseja realmente excluir o registro selecionado?",
                    "Confirmar exclusão",
                    JOptionPane.YES_NO_OPTION);
                String pk_nome = pk_names.iterator().next().x;
                if (e == JOptionPane.YES_OPTION) 
                {
                    String cod = current_grid.getValueAt(current_grid.getSelectedRow(), 0).toString();
                    this.c.SQLExecute("DELETE FROM "+ table_name +" WHERE " + pk_nome + " = " + cod);
                    JOptionPane.showMessageDialog(null, "Registro excluído com sucesso");
                    caller.setDados();
                }
            }
    }
    
    private void Button_Confirm(java.awt.event.ActionEvent evt)
    {
        ArrayList<SQL_Table_Entry> entries = caller.get_Entries();
        boolean sucess = false;
        if(selected_row[0].equals("0"))
        {
            try
            {
                sucess = mngr.Insert_Row(entries);
            }
            catch(SQLException e)
            {
                JOptionPane.showMessageDialog(null, "Erro no DML MySQL");            
                JOptionPane.showMessageDialog(null, e.getMessage());
                JOptionPane.showMessageDialog(null, "A aplicação será finalizada...");
                caller.dispose();
            }
            catch(Exception e)
            {         
                JOptionPane.showMessageDialog(null, "Erro:" + e.getMessage());
            }
            if(sucess)
            {
                JOptionPane.showMessageDialog(null, table_name+" cadastrado com sucesso.");
                caller.dispose();
            }
        }
        else
        {
            try
            {
                String first_pk = selected_row[0];
                sucess = mngr.Update_Row(entries, first_pk);

            }
            catch(SQLException e)
            {
                JOptionPane.showMessageDialog(null, "Erro no DML MySQL");             
                JOptionPane.showMessageDialog(null, e.getMessage());
                JOptionPane.showMessageDialog(null, "A aplicação será finalizada...");
                caller.dispose();
            }
            catch(Exception e)
            {         
                JOptionPane.showMessageDialog(null, "Erro:" + e.getCause().toString() + " " + e.getMessage());
            }
            if(sucess)
            {
                JOptionPane.showMessageDialog(null, table_name + " editado com sucesso.");
                caller.dispose();
            }
        }
    }
    
    private void Button_Add(java.awt.event.ActionEvent evt)
    {
        String[] str = new String[pk_names.size()];
        for(int i = 0; i<pk_names.size(); i++)
        {
            str[i] = pk_names.get(i).x;
        }
        new Entrada(str, "paciente", c).setVisible(true);
        caller.setDados();
    }
    
    private void Button_Open_Filter(java.awt.event.ActionEvent evt)
    {
        new Row_Filter((Tabela) caller).setVisible(true);
    }
    
    private void Button_Edit(java.awt.event.ActionEvent evt)
    {
        if (IsLinhaSelecionada()) {
                String m = current_grid.getValueAt(current_grid.getSelectedRow(), 0).toString();
                String [] str_edit = new String[1];
                str_edit[0] = m;
                new Entrada(str_edit, table_name, c).setVisible(true);
                caller.setDados();
            }
    }
    
    private void Button_Confirm_Filter(java.awt.event.ActionEvent evt) throws Exception
    {
        DefaultTableModel d = (DefaultTableModel) current_grid.getModel();
        int i = 0;
        if(caller instanceof Tabela)
        {
            Tabela tbl = (Tabela) caller;   
            while(i<d.getColumnCount())
            {
                tbl.filter_table[i] = (Boolean) d.getValueAt(0, i);
                i++;
            }
            String boolstr = new String();
            for(Boolean b : tbl.filter_table)
            {
                int res = (b) ? 1:0;
                boolstr = boolstr + res;
            }
            try
            {
                tbl.p.setProperty(tbl.table_name+"Filters",boolstr);
                tbl.p.store(new FileWriter("Filters.properties"),boolstr);
            }
            catch(Exception e)
            {
                throw e;
            }
        }
    }
    
    private boolean IsLinhaSelecionada() {
        if (current_grid.getRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "O cadastro não possui registros");
            return false;
        } 
        else if (current_grid.getSelectedRowCount() < 1) {
            JOptionPane.showMessageDialog(null, "Selecionar um aluno");
            return false;
        }
        else
            return true;
    }
    
    
}
