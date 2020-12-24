
package fpoly.tn.dto;

public class DonViDongGoi {
    String MaDVDG;
    String MaDVT1;
    String MaDVT2;
    String GhiChu;

    public DonViDongGoi() {
    }

    public DonViDongGoi(String MaDVDG, String MaDVT1, String MaDVT2, String GhiChu) {
        this.MaDVDG = MaDVDG;
        this.MaDVT1 = MaDVT1;
        this.MaDVT2 = MaDVT2;
        this.GhiChu = GhiChu;
    }

    public String getMaDVDG() {
        return MaDVDG;
    }

    public void setMaDVDG(String MaDVDG) {
        this.MaDVDG = MaDVDG;
    }

    public String getMaDVT1() {
        return MaDVT1;
    }

    public void setMaDVT1(String MaDVT1) {
        this.MaDVT1 = MaDVT1;
    }

    public String getMaDVT2() {
        return MaDVT2;
    }

    public void setMaDVT2(String MaDVT2) {
        this.MaDVT2 = MaDVT2;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }
    
    
}
