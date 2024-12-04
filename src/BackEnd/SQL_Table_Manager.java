/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;


/**
 *
 * @author Aluno
 */
public class SQL_Table_Manager {
    private final ArrayList<SQL_Table_Field> Fields = new ArrayList();
    private ArrayList<Tuple<String,String>> pk_names = new ArrayList<>();
    private String Table_Name;
    private Conexao c;
    public SQL_Table_Manager(String Table_Name, Conexao c)
    {
        this.Table_Name = Table_Name;
        this.c = c;
        DatabaseMetaData mdata = c.getMetaData();
        try{
            ResultSet pks = mdata.getPrimaryKeys(null, null, Table_Name);
            while(pks.next())
            {
                String buf1 = pks.getString("COLUMN_NAME");
                String buf2 = pks.getString("PK_NAME");
                pk_names.add(new Tuple(buf1, buf2));
            }
            ResultSet columns = mdata.getColumns(null,null,"paciente",null);
            while(columns.next())
            {
                Add_Field(columns.getString("COLUMN_NAME"),
                        JDBCType.valueOf(Integer.parseInt(columns.getString("DATA_TYPE"))).getName(),
                        columns.getString("IS_NULLABLE").equals("NO"),
                        columns.getString("IS_AUTOINCREMENT").equals("YES"));
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Erro na conexão MySQL");            
            JOptionPane.showMessageDialog(null, e.getMessage());
            JOptionPane.showMessageDialog(null, "A aplicação será finalizada...");
            System.exit(0); //Finalizar a aplicação
        }
    }
    
    public ArrayList<SQL_Table_Field> getFields()
    {
        return Fields;
    }
    
    public ArrayList<Tuple<String,String>> getPks()
    {
        return pk_names;
    }
    
    public void Add_Field(String Field_Name, String Datatype, boolean Not_Null, boolean Auto_Increment) throws Exception
    {
        try{
        if(Field_Name==null || Datatype==null)
        {
            Exception e = new Exception("Valores invalidos");
            throw e;
        }
        if(!Datatype.equals("INTEGER") && Auto_Increment)
        {
            Exception e = new Exception("Auto Increment so pode ser usado em Integer");
            throw e;
        }
        SQL_Table_Field field = new SQL_Table_Field(Field_Name, Datatype, Not_Null, Auto_Increment);
        Fields.add(field);
        }
        catch(Exception e)
        {
            throw e;
        }
    }
    
    public boolean Remove_Field(String Field_Name)
    {
        for(SQL_Table_Field field : Fields)
        {
            if(field.Get_Field_Name().equals(Field_Name))
            {
                return Fields.remove(field);
            }
        }
        return false;
    }
    
    public void Set_Table_Name(String name)
    {
        this.Table_Name = name;
    }
    
    public String Get_Create_Text()
    {
        StringBuilder Create_Text = new StringBuilder();
        Create_Text.append("CREATE TABLE ").append(this.Table_Name).append(" (\n");
        for(SQL_Table_Field field : Fields)
        {
            Create_Text
                    .append(field.Get_Field_Name())
                    .append(" ");
            String type = field.Get_Datatype();
            if(type.equals("String"))
            {
                Create_Text
                        .append("VARCHAR(255)")
                        .append(" ");
            }
            else
            {
                Create_Text
                        .append(field.Get_Datatype())
                        .append(" ");
            }
            Create_Text
                        .append(field.Get_Not_Null())
                        .append(" ");
            Create_Text
                        .append(field.Get_Auto_Increment())
                        .append(" ");
            Create_Text
                        .append(",\n");
        }
        Create_Text
                        .append(");");
        return Create_Text.toString();
    }
    
    public boolean Insert_Row(ArrayList<SQL_Table_Entry> row) throws Exception
    {
        boolean sucess = false;
        StringBuilder insert_text = new StringBuilder();
        Iterator<SQL_Table_Entry> iterow = row.iterator();
        insert_text.append("INSERT INTO ")
                .append(this.Table_Name)
                .append(" (");
        boolean first = true;
        for(SQL_Table_Field field : Fields)
        {
            if(field.Get_Auto_Increment().equals("AUTO_INCREMENT"))
                    continue;
            if(first)
                first = false;
            else
                insert_text.append(",");
            insert_text.append(field.Get_Field_Name());
        }
        insert_text.append(") ")
                .append("VALUES (");
        ArrayList<String> strTypes = new ArrayList<>();
        strTypes.add(JDBCType.VARCHAR.toString());
        strTypes.add(JDBCType.DATE.toString());
        strTypes.add(JDBCType.CHAR.toString());
        strTypes.add(JDBCType.LONGVARCHAR.toString());
        strTypes.add(JDBCType.TIME.toString());
        try{
            first = true;
            for(SQL_Table_Field field : Fields)
            {
                if(field.Get_Auto_Increment().equals("AUTO_INCREMENT"))
                    continue;
                SQL_Table_Entry entry = iterow.next();
                if(entry == null)
                {
                    throw new Exception("Erro no processamento de entradas");
                }
                if(first)
                {
                    first = false;
                }
                else
                {
                    insert_text.append(", ");
                }
                
                
                if(!entry.datatype.equals(field.Get_Datatype()) || (entry.value == null || entry.value.equals("")) && field.Get_Not_Null().equals("NOT NULL"))
                {
                    Exception e = new Exception("Dado em" + field.Get_Field_Name()+ " invalido ou nulo");
                    throw e;
                }
                if(strTypes.contains(entry.datatype))
                {
                    insert_text.append("'")
                        .append(entry.value)
                        .append("'");
                }
                else
                {
                    insert_text.append(entry.value);
                }
            }
            insert_text.append(");");
            c.SQLExecute(insert_text.toString());
            sucess = true;
        }
        catch(Exception e)
        {
            throw e;
        }
        return sucess;
    }
    
    public boolean Update_Row(ArrayList<SQL_Table_Entry> row, String CodPaciente) throws Exception
    {
        boolean sucess = false;
        StringBuilder insert_text = new StringBuilder();
        Iterator<SQL_Table_Entry> iterow = row.iterator();
        insert_text.append("UPDATE ")
                .append(this.Table_Name)
                .append(" \n")
                .append("SET ");
        ArrayList<String> strTypes = new ArrayList<>();
        strTypes.add(JDBCType.VARCHAR.toString());
        strTypes.add(JDBCType.DATE.toString());
        strTypes.add(JDBCType.CHAR.toString());
        strTypes.add(JDBCType.LONGVARCHAR.toString());
        strTypes.add(JDBCType.TIME.toString());
        try{
            boolean first = true;
            for(SQL_Table_Field field : Fields)
            {
                SQL_Table_Entry entry = iterow.next();
                if(entry == null)
                {
                    throw new Exception("Erro no processamento de entradas");
                }
                if(!first)
                {
                    insert_text.append(", ");
                }
                else
                {
                    first = false;
                }
                
                if(!entry.datatype.equals(field.Get_Datatype()) || (entry.value == null || entry.value.equals("")) && field.Get_Not_Null().equals("NOT NULL"))
                {
                    Exception e = new Exception("Dado em" + field.Get_Field_Name()+ " invalido ou nulo");
                    throw e;
                }
                
                insert_text.append(field.Get_Field_Name())
                        .append(" = ");
                if(strTypes.contains(entry.datatype))
                {
                    insert_text.append("'")
                        .append(entry.value)
                        .append("'");
                }
                else
                {
                    if(entry.datatype.equals(JDBCType.FLOAT.toString()))
                    {
                        try
                        {
                            Float.valueOf(entry.value);
                        }
                        catch(NumberFormatException e)
                        {
                            JOptionPane.showMessageDialog(null, "Valor em" + field.Get_Field_Name() + "inválido"); 
                            throw e;
                        }
                    }
                    insert_text.append(entry.value);
                }
            }
            insert_text.append(" WHERE CodPaciente = ")
                    .append(CodPaciente)
                    .append(";");
            c.SQLExecute(insert_text.toString());
            sucess = true;
        }
        catch(Exception e)
        {
            throw e;
        }
        return sucess;
    }
}
