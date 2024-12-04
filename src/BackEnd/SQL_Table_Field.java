/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

/**
 *
 * @author Aluno
 */
public class SQL_Table_Field {
    private String Field_Name;
    private String Datatype;
    private boolean Not_Null;
    private boolean Auto_Increment;
    
    public SQL_Table_Field(String Field_Name, String Datatype, boolean Not_Null, boolean Auto_Increment)
    {
        this.Field_Name = Field_Name;
        this.Datatype = Datatype;
        this.Not_Null = Not_Null;
        this.Auto_Increment = Auto_Increment;
    }
    
    
    public String Get_Field_Name()
    {
        return Field_Name;
    }
    
    public String Get_Datatype()
    {
        return Datatype;
    }
    
    public String Get_Not_Null()
    {
        return Not_Null? "NOT NULL" : "";
    }
    
    public String Get_Auto_Increment()
    {
        return Auto_Increment? "AUTO_INCREMENT" : "";
    }
}
