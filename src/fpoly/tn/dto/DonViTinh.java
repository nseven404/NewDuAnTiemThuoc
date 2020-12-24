package fpoly.tn.dto;

public class DonViTinh {
    String MaDVT;
    String TenDVT;
    String GhiChu;

    public DonViTinh() {
    }

    public DonViTinh(String MaDVT, String TenDVT, String GhiChu) {
        this.MaDVT = MaDVT;
        this.TenDVT = TenDVT;
        this.GhiChu = GhiChu;
    }

    public String getMaDVT() {
        return MaDVT;
    }

    public void setMaDVT(String MaDVT) {
        this.MaDVT = MaDVT;
    }

    public String getTenDVT() {
        return TenDVT;
    }

    public void setTenDVT(String TenDVT) {
        this.TenDVT = TenDVT;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

}
