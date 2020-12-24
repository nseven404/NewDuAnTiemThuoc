
package fpoly.tn.dto;


public class LoThuoc {
    String MaLo;
    String MaThuoc;
    String MaNCC;
    String ngaySanXuat;
    String hanSuDung;
    int SoLuong = 0;
    String ghiChu;

    public LoThuoc() {
    }

    public LoThuoc(String MaLo, String MaThuoc, String MaNCC, String ngaySanXuat, String hanSuDung, int SoLuong, String ghiChu) {
        this.MaLo = MaLo;
        this.MaThuoc = MaThuoc;
        this.MaNCC = MaNCC;
        this.ngaySanXuat = ngaySanXuat;
        this.hanSuDung = hanSuDung;
        this.SoLuong = SoLuong;
        this.ghiChu = ghiChu;
    }

    public String getMaLo() {
        return MaLo;
    }

    public void setMaLo(String MaLo) {
        this.MaLo = MaLo;
    }

    public String getMaThuoc() {
        return MaThuoc;
    }

    public void setMaThuoc(String MaThuoc) {
        this.MaThuoc = MaThuoc;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String MaNCC) {
        this.MaNCC = MaNCC;
    }

    public String getNgaySanXuat() {
        return ngaySanXuat;
    }

    public void setNgaySanXuat(String ngaySanXuat) {
        this.ngaySanXuat = ngaySanXuat;
    }

    public String getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(String hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

   
    
    
}
