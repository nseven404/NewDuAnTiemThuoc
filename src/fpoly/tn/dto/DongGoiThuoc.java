
package fpoly.tn.dto;

public class DongGoiThuoc {
    
    String id;
    String maThuoc;
    String maDongGoi;
    String GhiChu;
    int soLuong;

    public DongGoiThuoc() {
    }

    public DongGoiThuoc(String id, String maThuoc, String maDongGoi, String GhiChu, int soLuong) {
        this.id = id;
        this.maThuoc = maThuoc;
        this.maDongGoi = maDongGoi;
        this.GhiChu = GhiChu;
        this.soLuong = soLuong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getMaDongGoi() {
        return maDongGoi;
    }

    public void setMaDongGoi(String maDongGoi) {
        this.maDongGoi = maDongGoi;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    
    

    
}
