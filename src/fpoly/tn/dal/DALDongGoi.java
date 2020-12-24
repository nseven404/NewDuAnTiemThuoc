/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpoly.tn.dal;

import fpoly.tn.helper.Gobal;
import fpoly.tn.helper.SqlHelper;

/**
 *
 * @author NAM
 */
public class DALDongGoi {
    public static boolean xoaDongGoi(String id) {
        String cauLenh = String.format("DELETE %s WHERE id = ?", Gobal.SQL.tenBang.donggoi);
        return SqlHelper.executeUpdate(cauLenh, id);
    }
}
