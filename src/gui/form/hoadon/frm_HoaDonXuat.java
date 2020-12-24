/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.form.hoadon;

import com.shady.event.AutoEvent;
import com.shady.scontrols.SButton;
import fpoly.tn.bll.BLLDonHang;
import fpoly.tn.dal.DALDonHangXuat;
import fpoly.tn.dal.DALKhachHang;
import fpoly.tn.dal.DALNhanVien;
import fpoly.tn.dto.ChiTietDonHangXuat;
import fpoly.tn.dto.DonHangXuat;
import fpoly.tn.dto.KhachHang;
import gui.form.message.DialogThongBao.MessageIcon;
import fpoly.tn.helper.CheckValidation;
import fpoly.tn.helper.MouseEffect;
import fpoly.tn.helper.NgayThang;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.ThongBao;
import fpoly.tn.helper.XuLyInput;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

public class frm_HoaDonXuat extends javax.swing.JDialog {

    //Thông tin nhân viên
    String maNVDangNhap;

    /// Đơn hàng
    private DonHangXuat donHang;

    HashMap<Integer, ChiTietDonHangXuat> listChiTietDonHang;
    
    final String tienTo = "HD";

    // Popup KH
    JPopupMenu popupKH;
    private SButton bntSuaKhachHang;
    private JSeparator jSeparator1;
    private JPanel khungThongTinCoBanKH;
    private JLabel lbThongTinCoBanKH;
    private JLabel txtEmailKH;
    private JLabel txtDiaChiKH;
    private JLabel txtSDTKH;
    private JLabel txtTenKhachHang;


    public frm_HoaDonXuat(java.awt.Frame parent, String maNV, String MaDonHang) {
        super(parent, true);
        initComponents();
        this.maNVDangNhap = maNV;

        setFormUI();

        initForm();

    //    loadThongTinForm(MaDonHang);
    }

    private void setFormUI() {
        // form background
        setBackground(new Color(0, 0, 0, 0));

        // mouseevent
        addMouseEvent();

        // Tạp popup View Khách Hàng
        settingPopupKH();

    }

    private void settingPopupKH() {

        popupKH = new JPopupMenu();

        khungThongTinCoBanKH = new JPanel();

        txtSDTKH = new JLabel();
        txtDiaChiKH = new JLabel();
        txtEmailKH = new JLabel();
        bntSuaKhachHang = new SButton();
        lbThongTinCoBanKH = new JLabel();
        jSeparator1 = new JSeparator();
        txtTenKhachHang = new JLabel();

        khungThongTinCoBanKH.setBackground(new java.awt.Color(255, 255, 255));

        txtSDTKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSDTKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconPhoneUser.png"))); // NOI18N
        txtSDTKH.setText("016549874875");
        txtSDTKH.setIconTextGap(10);

        txtDiaChiKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDiaChiKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconMap.png"))); // NOI18N
        txtDiaChiKH.setText("159 Chợ đạt lý thôn 5 xã hòa thuận tp Buôn ma thuột");
        txtDiaChiKH.setIconTextGap(10);

        txtEmailKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtEmailKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/mail.png"))); // NOI18N
        txtEmailKH.setText("khachhang.gmail.com");
        txtEmailKH.setIconTextGap(10);

        bntSuaKhachHang.setBackground(new java.awt.Color(255, 255, 255));
        bntSuaKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/edit.png"))); // NOI18N
        bntSuaKhachHang.setText("Sửa");
        bntSuaKhachHang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bntSuaKhachHang.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntSuaKhachHang.sets_Radius(20);
        bntSuaKhachHang.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntSuaKhachHang.sets_drawBorder(false);

        lbThongTinCoBanKH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbThongTinCoBanKH.setForeground(new java.awt.Color(102, 102, 102));
        lbThongTinCoBanKH.setText("Thông tin cơ bản");

        txtTenKhachHang.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtTenKhachHang.setText("Nguyễn Văn An");

        javax.swing.GroupLayout khungThongTinCoBanKHLayout = new javax.swing.GroupLayout(khungThongTinCoBanKH);
        khungThongTinCoBanKH.setLayout(khungThongTinCoBanKHLayout);
        khungThongTinCoBanKHLayout.setHorizontalGroup(khungThongTinCoBanKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(khungThongTinCoBanKHLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(khungThongTinCoBanKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(khungThongTinCoBanKHLayout.createSequentialGroup()
                                        .addComponent(lbThongTinCoBanKH)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bntSuaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap())
                                .addGroup(khungThongTinCoBanKHLayout.createSequentialGroup()
                                        .addGroup(khungThongTinCoBanKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtSDTKH)
                                                .addComponent(txtEmailKH)
                                                .addComponent(txtDiaChiKH)
                                                .addComponent(txtTenKhachHang))
                                        .addGap(0, 71, Short.MAX_VALUE))))
        );
        khungThongTinCoBanKHLayout.setVerticalGroup(khungThongTinCoBanKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(khungThongTinCoBanKHLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(khungThongTinCoBanKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(bntSuaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(lbThongTinCoBanKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenKhachHang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSDTKH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEmailKH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDiaChiKH)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bntSuaKhachHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popupKH.setVisible(false);
            }

        });
        // add popup
        popupKH.add(khungThongTinCoBanKH);

    }

    private void showPopupKH(Component comp) {
        popupKH.show(comp, 0, comp.getHeight());
    }

    private void addMouseEvent() {

        MouseEffect.MouseMovingForm(panelTuaDe, this);
        MouseEffect.addMouseEffect(btThoat2, Color.red, new Color(102, 102, 102), Color.white, Color.white);
    }

    private void initForm() {

        initTableChiTiet();
     //   settingAutoKhachHang();

    }

    private void loadThongTinForm(String maHD) {

        donHang = new DonHangXuat();

        listChiTietDonHang = new HashMap<>();

        // lấy mã hóa đơn
        donHang.setMa(maHD);

        boolean donHangMoi = CheckValidation.checkStringTrong(donHang.getMa());

        if (donHangMoi) {

            // đặt mã nhân viên đơn hàng
            donHang.setMa_nhanvien(maNVDangNhap);

            // hiển thị tên nhân viên đơn hàng
            loadThongTinNhanVien(donHang.getMa_nhanvien());

            txtMaHoaDon.setText(BLLDonHang.layMaDonHangMoi(tienTo));

            String ngayThang = NgayThang.layNgayThangNamFormat("dd-MM-yyyy") + " / " + NgayThang.layNgayThangNamFormat("HH:mm:ss");
            txtNgayThang.setText(ngayThang);

            comboKhachHang.setFieldValue("");

        } else { // đơn hàng cũ

            donHang = BLLDonHang.layThongTinDonHang(donHang.getMa());
            loadThongTinKhachHang(donHang.getMa_khachhang());
            loadThongTinNhanVien(donHang.getMa_nhanvien());

            txtMaHoaDon.setText(donHang.getMa());

            String ngayThang = NgayThang.layNgayThangNamFormat(donHang.getNgaythang(), "dd-MM-yyyy") + " / " + NgayThang.layNgayThangNamFormat(donHang.getNgaythang(), "HH:mm:ss");
            txtNgayThang.setText(ngayThang);

            comboKhachHang.setFieldValue(donHang.getMa_khachhang());
            comboTrangThaiDonHang.setSelectedIndex(donHang.getTrangthai());
            txtGhiChu.setText(donHang.getGhichu());
            txtTienDaTra.setTextValue(String.valueOf(donHang.getTiendatra()));

            loadChiTietDonHang();
        }

        txtTienHang.setText(XuLyInput.formatThemChamVaoSo(donHang.getTongtien()) + " Đ");
        txtTongTien.setText(txtTienHang.getText());
        txtTienConLai.setTextValue(String.valueOf(donHang.getTongtien() - donHang.getTiendatra()));

    }

    private void loadThongTinNhanVien(String maNV) {
        txtTenNhanVien.setText(DALNhanVien.layTenNhanVien(maNV));
    }

    private void loadChiTietDonHang() {

        listChiTietDonHang = new HashMap<>();
        ResultSet rs = DALDonHangXuat.layChiTietDonHang(donHang.getMa());
        try {
            while (rs.next()) {
                ChiTietDonHangXuat chitiet = new ChiTietDonHangXuat();

                chitiet.setId(rs.getInt("id"));
                chitiet.setGhichu(rs.getString("ghichu"));
                chitiet.setGiaban(rs.getInt("giaban"));
                chitiet.setMa_donhang(rs.getString("ma_donhang"));
                chitiet.setGianhap(rs.getInt("gianhap"));
                chitiet.setMa_sanPham(rs.getString("ma_sanpham"));
                chitiet.setThanhtien(rs.getInt("thanhtien"));
                chitiet.setMa_donvitinh(rs.getString("ma_donvitinh"));
                chitiet.setSoluong(rs.getFloat("soluong"));

                listChiTietDonHang.put(chitiet.getId(), chitiet);
            }
        } catch (SQLException ex) {
            Logger.getLogger(frm_HoaDonXuat.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SqlHelper.closeConnection();
        }

        if (listChiTietDonHang.size() > 0) {
          
        }
    }

    

    private void initTableChiTiet() {

        tableChiTietDonHang.addColum("", 50);
        tableChiTietDonHang.addColum("STT", 50);
        tableChiTietDonHang.addColum("Tên thuốc", 0);
        tableChiTietDonHang.addColum("ĐVT", 80);
        tableChiTietDonHang.addColum("Số Lương", 100);
        tableChiTietDonHang.addColum("Giá bán", 100);
        tableChiTietDonHang.addColum("Thành Tiền", 150);
        tableChiTietDonHang.addColum("Thao Tác", 150);
        

    }

    private void settingAutoKhachHang() {

        comboKhachHang.addFiledColum("Mã", "ma", 100);
        comboKhachHang.addFiledColum("Tên", "ten", 150);
        comboKhachHang.addFiledColum("Địa Chỉ", "diachi", 200);
        comboKhachHang.addFiledColum("SĐT", "sodienthoai", 100);

        try {
            comboKhachHang.loadData(DALDonHangXuat.layDanhSachKhachHang());
        } catch (SQLException ex) {
            Logger.getLogger(frm_HoaDonXuat.class.getName()).log(Level.SEVERE, null, ex);
        }

        comboKhachHang.addValueChangedListener((AutoEvent event) -> {
            loadThongTinKhachHang(event.getValue());
        });

    }


    private void loadThongTinKhachHang(String maKH) {

        KhachHang kh = DALKhachHang.laythongtinKhachHang(maKH);
//
//        txtSDTKH.setText(kh.getSodienthoai());
//        txtDiaChiKH.setText(kh.getDiachi());
//        txtEmailKH.setText(kh.getEmail());
//        txtTenKhachHang.setText(kh.getTen());

    }

    // XỬ LÝ XỰ KIÊN TABLE

   

    

    /// XỬ LÝ BUTTON FORM EVENT
    private void clickDongForm() {
        if (ThongBao.taoThongBaoXacNhan(null, "Bạn muốn thoát?", MessageIcon.QUESTION)) {
            dispose();
        }
    }



    private void clickXoa() {

        if (!kiemtraPhanQuyen(this.maNVDangNhap)) {
            ThongBao.taoThongBao(null, "Bạn không thể xóa đơn hàng của nhân viên khác!", MessageIcon.WARNING);
            return;
        }

        if (!ThongBao.taoThongBaoXacNhan(null, "Bạn muốn xóa đơn hàng?", MessageIcon.QUESTION)) {
            return;
        }

        xoaDonHang();
        this.dispose();
    }

    private void xoaDonHang() {

        DALDonHangXuat.xoaDonHang(donHang.getMa());

        // xóa chi tiết đơn hàng
        for (ChiTietDonHangXuat ct : listChiTietDonHang.values()) {
            DALDonHangXuat.xoaChiTietDonHang(ct);
        }

    }

    private boolean kiemtraPhanQuyen(String maNV) {

        return maNV.equals(donHang.getMa_nhanvien());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sPanel3 = new com.shady.scontrols.SPanel();
        khungTable = new com.shady.scontrols.SPanel();
        bntXoaDong = new com.shady.scontrols.SButton();
        btnXoaTatCa = new com.shady.scontrols.SButton();
        tableChiTietDonHang = new com.shady.scontrols.STableWeb();
        sTextField1 = new com.shady.scontrols.STextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        sTextField2 = new com.shady.scontrols.STextField();
        sButton1 = new com.shady.scontrols.SButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sTextField3 = new com.shady.scontrols.STextField();
        jLabel4 = new javax.swing.JLabel();
        panelTuaDe = new javax.swing.JPanel();
        lbTuaDe = new javax.swing.JLabel();
        btThoat2 = new javax.swing.JLabel();
        lbThongTinThanhToan = new javax.swing.JPanel();
        txtChietKhau = new com.shady.scontrols.STextField();
        lbChietKhau = new javax.swing.JLabel();
        lbTongTien = new javax.swing.JLabel();
        lbTienHang = new javax.swing.JLabel();
        txtTienHang = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        txtTienConLai = new com.shady.scontrols.STextField();
        lbTienConLai = new javax.swing.JLabel();
        lbTienDaTra = new javax.swing.JLabel();
        txtTienDaTra = new com.shady.scontrols.STextField();
        lbTrangThaiThanhToan = new javax.swing.JLabel();
        txtGhiChu = new com.shady.scontrols.STextField();
        lbGhiChu = new javax.swing.JLabel();
        comboTrangThaiDonHang = new javax.swing.JComboBox<>();
        khungThongTinDonHangCoDinh = new javax.swing.JPanel();
        lbNhanVien = new javax.swing.JLabel();
        txtTenNhanVien = new javax.swing.JLabel();
        lbNgayThang = new javax.swing.JLabel();
        txtNgayThang = new javax.swing.JLabel();
        lbMaHD = new javax.swing.JLabel();
        txtMaHoaDon = new javax.swing.JLabel();
        bntSuaMaHD = new com.shady.scontrols.SButton();
        lbKhachHang = new javax.swing.JLabel();
        comboKhachHang = new com.shady.scontrols.STextField();
        bntViewKhachHang = new com.shady.scontrols.SButton();
        bntThemKhachHang = new com.shady.scontrols.SButton();
        khungThaoTac = new javax.swing.JPanel();
        bntThoat = new com.shady.scontrols.SButton();
        bntInHoaDon = new com.shady.scontrols.SButton();
        bntLuu1 = new com.shady.scontrols.SButton();
        bntXoa = new com.shady.scontrols.SButton();
        bntLuu = new com.shady.scontrols.SButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        sPanel3.setBackground(new java.awt.Color(255, 255, 255));
        sPanel3.sets_borderColor(new java.awt.Color(204, 204, 204));
        sPanel3.sets_dropShadow(true);
        sPanel3.sets_shadowOpacity(0.3F);
        sPanel3.sets_shadowSize(8);

        khungTable.setBackground(new java.awt.Color(255, 255, 255));
        khungTable.sets_cornerRadius(0);
        khungTable.sets_dropShadow(false);

        bntXoaDong.setBackground(new java.awt.Color(255, 255, 255));
        bntXoaDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_delete.png"))); // NOI18N
        bntXoaDong.setText("Xóa dòng");
        bntXoaDong.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        bntXoaDong.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntXoaDong.sets_Radius(10);
        bntXoaDong.sets_borderColor(new java.awt.Color(204, 204, 204));
        bntXoaDong.sets_drawBorder(false);
        bntXoaDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntXoaDongActionPerformed(evt);
            }
        });

        btnXoaTatCa.setBackground(new java.awt.Color(255, 255, 255));
        btnXoaTatCa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_delete.png"))); // NOI18N
        btnXoaTatCa.setText("Xóa tất cả");
        btnXoaTatCa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnXoaTatCa.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoaTatCa.sets_Radius(10);
        btnXoaTatCa.sets_borderColor(new java.awt.Color(204, 204, 204));
        btnXoaTatCa.sets_drawBorder(false);
        btnXoaTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTatCaActionPerformed(evt);
            }
        });

        tableChiTietDonHang.setHeaderForeground(new java.awt.Color(255, 255, 255));
        tableChiTietDonHang.setHeaderHeight(35);
        tableChiTietDonHang.setHeaderRound(10);
        tableChiTietDonHang.setListViewBackground(new java.awt.Color(255, 255, 255));

        sTextField1.sets_placeholder("Tên thuốc...");
        sTextField1.sets_radius(15);
        sTextField1.sets_textType(com.shady.scontrols.STextField.textTypeEnum.AUTOCOMPLETE);

        jComboBox1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboBox1.setPreferredSize(new java.awt.Dimension(100, 30));

        sTextField2.setText("0");
        sTextField2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sTextField2.setPreferredSize(new java.awt.Dimension(100, 30));
        sTextField2.sets_placeholder("Số lượng...");
        sTextField2.sets_radius(15);
        sTextField2.sets_textType(com.shady.scontrols.STextField.textTypeEnum.NUMBER_FORMAT);

        sButton1.setBackground(new java.awt.Color(0, 153, 51));
        sButton1.setText("Thêm");
        sButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sButton1.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sButton1.sets_ForeColor(new java.awt.Color(255, 255, 255));
        sButton1.sets_HoverForeColor(new java.awt.Color(255, 255, 255));
        sButton1.sets_Radius(15);
        sButton1.sets_drawBorder(false);
        sButton1.sets_hoverBackground(new java.awt.Color(0, 153, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Thuốc");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("ĐVT");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Số lượng");

        sTextField3.setText("0");
        sTextField3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sTextField3.setPreferredSize(new java.awt.Dimension(100, 30));
        sTextField3.sets_placeholder("Số lượng...");
        sTextField3.sets_radius(15);
        sTextField3.sets_textType(com.shady.scontrols.STextField.textTypeEnum.NUMBER_FORMAT);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Giá bán");

        javax.swing.GroupLayout khungTableLayout = new javax.swing.GroupLayout(khungTable);
        khungTable.setLayout(khungTableLayout);
        khungTableLayout.setHorizontalGroup(
            khungTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableChiTietDonHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(khungTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(khungTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(sTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(khungTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(khungTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(khungTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(khungTableLayout.createSequentialGroup()
                        .addComponent(sTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(khungTableLayout.createSequentialGroup()
                .addComponent(bntXoaDong, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoaTatCa, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        khungTableLayout.setVerticalGroup(
            khungTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(khungTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(khungTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(khungTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(khungTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaTatCa, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntXoaDong, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableChiTietDonHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelTuaDe.setBackground(new java.awt.Color(102, 102, 102));

        lbTuaDe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbTuaDe.setForeground(new java.awt.Color(255, 255, 255));
        lbTuaDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icons_add_shopping_cart_30.png"))); // NOI18N
        lbTuaDe.setText("ĐƠN HÀNG MỚI");

        btThoat2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btThoat2.setForeground(new java.awt.Color(255, 255, 255));
        btThoat2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btThoat2.setText("X");
        btThoat2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btThoat2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btThoat2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelTuaDeLayout = new javax.swing.GroupLayout(panelTuaDe);
        panelTuaDe.setLayout(panelTuaDeLayout);
        panelTuaDeLayout.setHorizontalGroup(
            panelTuaDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTuaDeLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lbTuaDe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btThoat2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelTuaDeLayout.setVerticalGroup(
            panelTuaDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTuaDeLayout.createSequentialGroup()
                .addComponent(lbTuaDe, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(btThoat2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        lbThongTinThanhToan.setBackground(new java.awt.Color(255, 255, 255));
        lbThongTinThanhToan.setOpaque(false);

        txtChietKhau.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtChietKhau.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtChietKhau.sets_borderColor(new java.awt.Color(153, 153, 153));
        txtChietKhau.sets_maxTextLength(3);
        txtChietKhau.sets_radius(10);
        txtChietKhau.sets_textType(com.shady.scontrols.STextField.textTypeEnum.NUMBER_FORMAT);
        txtChietKhau.setTextValue("0");

        lbChietKhau.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbChietKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconCoin.png"))); // NOI18N
        lbChietKhau.setText("Chiết khấu / Khuyến mãi:");

        lbTongTien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbTongTien.setForeground(new java.awt.Color(255, 0, 0));
        lbTongTien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTongTien.setText("TỔNG TIỀN :");

        lbTienHang.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbTienHang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTienHang.setText("TIỀN HÀNG :");

        txtTienHang.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTienHang.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTienHang.setText("0 Đ");

        txtTongTien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTongTien.setForeground(new java.awt.Color(255, 0, 0));
        txtTongTien.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTongTien.setText("0 Đ");

        txtTienConLai.setEditable(false);
        txtTienConLai.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTienConLai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTienConLai.sets_borderColor(new java.awt.Color(153, 153, 153));
        txtTienConLai.sets_maxTextLength(10);
        txtTienConLai.sets_radius(10);
        txtTienConLai.sets_textType(com.shady.scontrols.STextField.textTypeEnum.NUMBER_FORMAT);
        txtTienConLai.setTextValue("0");

        lbTienConLai.setBackground(new java.awt.Color(255, 255, 255));
        lbTienConLai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbTienConLai.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTienConLai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconCoin.png"))); // NOI18N
        lbTienConLai.setText("Tiền còn lại");

        lbTienDaTra.setBackground(new java.awt.Color(255, 255, 255));
        lbTienDaTra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbTienDaTra.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTienDaTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconCoin.png"))); // NOI18N
        lbTienDaTra.setText("Tiền đã trả");

        txtTienDaTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTienDaTra.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTienDaTra.sets_borderColor(new java.awt.Color(153, 153, 153));
        txtTienDaTra.sets_maxTextLength(10);
        txtTienDaTra.sets_radius(10);
        txtTienDaTra.sets_textType(com.shady.scontrols.STextField.textTypeEnum.NUMBER_FORMAT);
        txtTienDaTra.setTextValue("0");

        lbTrangThaiThanhToan.setBackground(new java.awt.Color(255, 255, 255));
        lbTrangThaiThanhToan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbTrangThaiThanhToan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTrangThaiThanhToan.setText("Trạng thái thanh toán");

        txtGhiChu.sets_borderColor(new java.awt.Color(153, 153, 153));
        txtGhiChu.sets_iconLeft(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_bubble_20.png"))); // NOI18N
        txtGhiChu.sets_placeholder("Thêm ghi chú");
        txtGhiChu.sets_radius(10);

        lbGhiChu.setBackground(new java.awt.Color(255, 255, 255));
        lbGhiChu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbGhiChu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbGhiChu.setText("Ghi chú");

        comboTrangThaiDonHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout lbThongTinThanhToanLayout = new javax.swing.GroupLayout(lbThongTinThanhToan);
        lbThongTinThanhToan.setLayout(lbThongTinThanhToanLayout);
        lbThongTinThanhToanLayout.setHorizontalGroup(
            lbThongTinThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbThongTinThanhToanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(lbThongTinThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtChietKhau, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(txtGhiChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(lbThongTinThanhToanLayout.createSequentialGroup()
                        .addGroup(lbThongTinThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbTongTien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbTienHang, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(lbThongTinThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTienHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(txtTienDaTra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTienConLai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(lbThongTinThanhToanLayout.createSequentialGroup()
                        .addGroup(lbThongTinThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTienConLai, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTienDaTra)
                            .addComponent(lbTrangThaiThanhToan)
                            .addComponent(lbChietKhau)
                            .addComponent(lbGhiChu))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(comboTrangThaiDonHang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        lbThongTinThanhToanLayout.setVerticalGroup(
            lbThongTinThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lbThongTinThanhToanLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbGhiChu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGhiChu, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(lbTrangThaiThanhToan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboTrangThaiDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTienDaTra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTienDaTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbTienConLai)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTienConLai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbChietKhau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtChietKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(lbThongTinThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTienHang)
                    .addComponent(txtTienHang))
                .addGap(9, 9, 9)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lbThongTinThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTongTien))
                .addGap(17, 17, 17))
        );

        khungThongTinDonHangCoDinh.setBackground(new java.awt.Color(255, 255, 255));

        lbNhanVien.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_user_color_20.png"))); // NOI18N
        lbNhanVien.setText("Nhân viên:");
        lbNhanVien.setIconTextGap(8);

        txtTenNhanVien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtTenNhanVien.setText("Tên Nhân Viên");

        lbNgayThang.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbNgayThang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconLich.png"))); // NOI18N
        lbNgayThang.setText("Ngày Tháng :");
        lbNgayThang.setIconTextGap(8);

        txtNgayThang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNgayThang.setText("00-00-0000");

        lbMaHD.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbMaHD.setText("Mã HD:");
        lbMaHD.setIconTextGap(8);

        txtMaHoaDon.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMaHoaDon.setText("HD00001");

        bntSuaMaHD.setBackground(new java.awt.Color(255, 255, 255));
        bntSuaMaHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/edit.png"))); // NOI18N
        bntSuaMaHD.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntSuaMaHD.sets_Radius(15);
        bntSuaMaHD.sets_drawBorder(false);
        bntSuaMaHD.sets_hoverBackground(new java.awt.Color(255, 255, 255));

        lbKhachHang.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/user20.png"))); // NOI18N
        lbKhachHang.setText("Khách hàng:");

        comboKhachHang.setBackground(new java.awt.Color(204, 204, 204));
        comboKhachHang.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboKhachHang.sets_DisplayColum("ten");
        comboKhachHang.sets_FieldColum("ma");
        comboKhachHang.sets_borderColor(new java.awt.Color(153, 153, 153));
        comboKhachHang.sets_placeholder("Chọn Khách Hàng");
        comboKhachHang.sets_radius(10);
        comboKhachHang.sets_textType(com.shady.scontrols.STextField.textTypeEnum.AUTOCOMPLETE);

        bntViewKhachHang.setBackground(new java.awt.Color(255, 255, 255));
        bntViewKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_viewfile_20.png"))); // NOI18N
        bntViewKhachHang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bntViewKhachHang.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntViewKhachHang.sets_Radius(20);
        bntViewKhachHang.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntViewKhachHang.sets_drawBorder(false);
        bntViewKhachHang.sets_hoverBackground(new java.awt.Color(255, 255, 255));
        bntViewKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntViewKhachHangActionPerformed(evt);
            }
        });

        bntThemKhachHang.setBackground(new java.awt.Color(255, 255, 255));
        bntThemKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_add.png"))); // NOI18N
        bntThemKhachHang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bntThemKhachHang.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntThemKhachHang.sets_Radius(20);
        bntThemKhachHang.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntThemKhachHang.sets_drawBorder(false);
        bntThemKhachHang.sets_hoverBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout khungThongTinDonHangCoDinhLayout = new javax.swing.GroupLayout(khungThongTinDonHangCoDinh);
        khungThongTinDonHangCoDinh.setLayout(khungThongTinDonHangCoDinhLayout);
        khungThongTinDonHangCoDinhLayout.setHorizontalGroup(
            khungThongTinDonHangCoDinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, khungThongTinDonHangCoDinhLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lbKhachHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bntViewKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bntThemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbNhanVien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenNhanVien)
                .addGap(39, 39, 39)
                .addComponent(lbNgayThang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNgayThang)
                .addGap(27, 27, 27)
                .addComponent(lbMaHD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaHoaDon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bntSuaMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        khungThongTinDonHangCoDinhLayout.setVerticalGroup(
            khungThongTinDonHangCoDinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(khungThongTinDonHangCoDinhLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(khungThongTinDonHangCoDinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(khungThongTinDonHangCoDinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMaHoaDon)
                        .addComponent(lbMaHD)
                        .addComponent(lbNhanVien)
                        .addComponent(txtTenNhanVien)
                        .addComponent(lbNgayThang)
                        .addComponent(txtNgayThang)
                        .addComponent(lbKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(khungThongTinDonHangCoDinhLayout.createSequentialGroup()
                        .addGroup(khungThongTinDonHangCoDinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(khungThongTinDonHangCoDinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(bntViewKhachHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bntThemKhachHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(bntSuaMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        khungThaoTac.setOpaque(false);

        bntThoat.setBackground(new java.awt.Color(255, 255, 255));
        bntThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_exit_20.png"))); // NOI18N
        bntThoat.setText("Thoát");
        bntThoat.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntThoat.sets_Radius(10);
        bntThoat.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntThoat.sets_hoverBackground(null);
        bntThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntThoatActionPerformed(evt);
            }
        });

        bntInHoaDon.setBackground(new java.awt.Color(255, 255, 255));
        bntInHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_print_20.png"))); // NOI18N
        bntInHoaDon.setText("In ");
        bntInHoaDon.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntInHoaDon.sets_Radius(10);
        bntInHoaDon.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntInHoaDon.sets_hoverBackground(null);

        bntLuu1.setBackground(new java.awt.Color(255, 255, 255));
        bntLuu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_new_file_20.png"))); // NOI18N
        bntLuu1.setText("Thêm mới");
        bntLuu1.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntLuu1.sets_Radius(10);
        bntLuu1.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntLuu1.sets_hoverBackground(null);
        bntLuu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntLuu1ActionPerformed(evt);
            }
        });

        bntXoa.setBackground(new java.awt.Color(255, 255, 255));
        bntXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_delete.png"))); // NOI18N
        bntXoa.setText("Xóa");
        bntXoa.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntXoa.sets_Radius(10);
        bntXoa.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntXoa.sets_hoverBackground(null);
        bntXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntXoaActionPerformed(evt);
            }
        });

        bntLuu.setBackground(new java.awt.Color(255, 255, 255));
        bntLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_save.png"))); // NOI18N
        bntLuu.setText("Lưu");
        bntLuu.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntLuu.sets_Radius(10);
        bntLuu.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntLuu.sets_hoverBackground(null);
        bntLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntLuuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout khungThaoTacLayout = new javax.swing.GroupLayout(khungThaoTac);
        khungThaoTac.setLayout(khungThaoTacLayout);
        khungThaoTacLayout.setHorizontalGroup(
            khungThaoTacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(khungThaoTacLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bntLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bntLuu1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bntXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bntInHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bntThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        khungThaoTacLayout.setVerticalGroup(
            khungThaoTacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(khungThaoTacLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(khungThaoTacLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntThoat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntInHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntLuu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntLuu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout sPanel3Layout = new javax.swing.GroupLayout(sPanel3);
        sPanel3.setLayout(sPanel3Layout);
        sPanel3Layout.setHorizontalGroup(
            sPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTuaDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sPanel3Layout.createSequentialGroup()
                .addGroup(sPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(khungThongTinDonHangCoDinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(sPanel3Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(khungTable, javax.swing.GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbThongTinThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(khungThaoTac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sPanel3Layout.setVerticalGroup(
            sPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sPanel3Layout.createSequentialGroup()
                .addComponent(panelTuaDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(khungThongTinDonHangCoDinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(sPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(khungTable, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                    .addComponent(lbThongTinThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(khungThaoTac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1213, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btThoat2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btThoat2MouseClicked
        clickDongForm();
    }//GEN-LAST:event_btThoat2MouseClicked

    private void bntThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntThoatActionPerformed
        clickDongForm();
    }//GEN-LAST:event_bntThoatActionPerformed

    private void bntXoaDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntXoaDongActionPerformed
       
    }//GEN-LAST:event_bntXoaDongActionPerformed

    private void btnXoaTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTatCaActionPerformed
       
    }//GEN-LAST:event_btnXoaTatCaActionPerformed

    private void bntLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntLuuActionPerformed
       
    }//GEN-LAST:event_bntLuuActionPerformed

    private void bntXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntXoaActionPerformed
        clickXoa();
    }//GEN-LAST:event_bntXoaActionPerformed

    private void bntLuu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntLuu1ActionPerformed
        
    }//GEN-LAST:event_bntLuu1ActionPerformed

    private void bntViewKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntViewKhachHangActionPerformed
        showPopupKH(bntViewKhachHang);
    }//GEN-LAST:event_bntViewKhachHangActionPerformed

    private void comboTrangThaiDonHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTrangThaiDonHangActionPerformed
        
    }//GEN-LAST:event_comboTrangThaiDonHangActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_HoaDonXuat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new frm_HoaDonXuat(null, "NV001", "").setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.shady.scontrols.SButton bntInHoaDon;
    private com.shady.scontrols.SButton bntLuu;
    private com.shady.scontrols.SButton bntLuu1;
    private com.shady.scontrols.SButton bntSuaMaHD;
    private com.shady.scontrols.SButton bntThemKhachHang;
    private com.shady.scontrols.SButton bntThoat;
    private com.shady.scontrols.SButton bntViewKhachHang;
    private com.shady.scontrols.SButton bntXoa;
    private com.shady.scontrols.SButton bntXoaDong;
    private javax.swing.JLabel btThoat2;
    private com.shady.scontrols.SButton btnXoaTatCa;
    private com.shady.scontrols.STextField comboKhachHang;
    private javax.swing.JComboBox<String> comboTrangThaiDonHang;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator2;
    private com.shady.scontrols.SPanel khungTable;
    private javax.swing.JPanel khungThaoTac;
    private javax.swing.JPanel khungThongTinDonHangCoDinh;
    private javax.swing.JLabel lbChietKhau;
    private javax.swing.JLabel lbGhiChu;
    private javax.swing.JLabel lbKhachHang;
    private javax.swing.JLabel lbMaHD;
    private javax.swing.JLabel lbNgayThang;
    private javax.swing.JLabel lbNhanVien;
    private javax.swing.JPanel lbThongTinThanhToan;
    private javax.swing.JLabel lbTienConLai;
    private javax.swing.JLabel lbTienDaTra;
    private javax.swing.JLabel lbTienHang;
    private javax.swing.JLabel lbTongTien;
    private javax.swing.JLabel lbTrangThaiThanhToan;
    private javax.swing.JLabel lbTuaDe;
    private javax.swing.JPanel panelTuaDe;
    private com.shady.scontrols.SButton sButton1;
    private com.shady.scontrols.SPanel sPanel3;
    private com.shady.scontrols.STextField sTextField1;
    private com.shady.scontrols.STextField sTextField2;
    private com.shady.scontrols.STextField sTextField3;
    private com.shady.scontrols.STableWeb tableChiTietDonHang;
    private com.shady.scontrols.STextField txtChietKhau;
    private com.shady.scontrols.STextField txtGhiChu;
    private javax.swing.JLabel txtMaHoaDon;
    private javax.swing.JLabel txtNgayThang;
    private javax.swing.JLabel txtTenNhanVien;
    private com.shady.scontrols.STextField txtTienConLai;
    private com.shady.scontrols.STextField txtTienDaTra;
    private javax.swing.JLabel txtTienHang;
    private javax.swing.JLabel txtTongTien;
    // End of variables declaration//GEN-END:variables
}
