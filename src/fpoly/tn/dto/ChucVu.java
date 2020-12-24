/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpoly.tn.dto;

/**
 *
 * @author DINH QUOC HAI
 */
public class ChucVu {
    String MaCV, TenCV, GhiChu;

    public ChucVu(String MaCV, String TenCV, String GhiChu) {
        this.MaCV = MaCV;
        this.TenCV = TenCV;
        this.GhiChu = GhiChu;
    }

    public ChucVu() {
    }

    public String getMaCV() {
        return MaCV;
    }

    public void setMaCV(String MaCV) {
        this.MaCV = MaCV;
    }

    public String getTenCV() {
        return TenCV;
    }

    public void setTenCV(String TenCV) {
        this.TenCV = TenCV;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }
    
    
}
