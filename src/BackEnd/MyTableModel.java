/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author root
 */
public class MyTableModel extends DefaultTableModel {

    public MyTableModel(String[] model) {
      super(model, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {

      return Boolean.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      return true;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
            Vector rowData = (Vector)getDataVector().get(row);
            rowData.set(column, (boolean)aValue);
            fireTableCellUpdated(row, column);
    }
}


