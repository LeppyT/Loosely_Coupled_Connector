/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FrontEnd;

import BackEnd.Crud;
import BackEnd.Crud_Button;

/**
 *
 * @author root
 */
public class Crud_Elements {
    Crud_Button Button_Cadastrar;
    Crud_Button Button_Editar;
    Crud_Button Button_Excluir;
    public Crud_Elements(int row, int column)
    {
        this.Button_Cadastrar = new Crud_Button(Crud.Cadastrar, column, row);
        column++;
        this.Button_Editar = new Crud_Button(Crud.Editar, column, row);
        column++;
        this.Button_Excluir = new Crud_Button(Crud.Excluir, column, row);
        
    }
}
