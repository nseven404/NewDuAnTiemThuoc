
package fpoly.tn.helper;

import fpoly.tn.dto.MyComboBox;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;


public class xuLyComboBox {
    
    public static void addData(JComboBox combo,ResultSet rs,String columValueSQL, String columDisplaySQL){
        DefaultComboBoxModel model = layModelCombo(combo);
        
        try {
            while(rs.next()){
                model.addElement(new MyComboBox(rs.getString(columValueSQL), rs.getString(columDisplaySQL)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(xuLyComboBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static DefaultComboBoxModel layModelCombo(JComboBox combo){
        return (DefaultComboBoxModel) combo.getModel();
    }
    
    public static String getSelectedValue(JComboBox combo){
        DefaultComboBoxModel model = layModelCombo(combo);
        MyComboBox selectedData = (MyComboBox) model.getElementAt(combo.getSelectedIndex());
        return selectedData.getValue();
        
    }
    
    public static void setSelectValue(JComboBox combo,String key) {
        DefaultComboBoxModel model = (DefaultComboBoxModel) combo.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            MyComboBox cv = (MyComboBox) model.getElementAt(i);
            if (cv.getValue().equals(key)) {
                model.setSelectedItem(cv);
                break;
            }
        }
    }
    
}
