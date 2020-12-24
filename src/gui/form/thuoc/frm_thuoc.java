package gui.form.thuoc;

import gui.form.lothuoc.Frm_LoThuoc;
import com.shady.tableweb.TableWebItem;
import com.shady.tableweb.TableWebSubItem;
import fpoly.tn.dal.DALDonViDongDoi;
import fpoly.tn.dal.DALDongGoi;
import fpoly.tn.dal.DALKho;
import fpoly.tn.dal.DALLoThuoc;
import fpoly.tn.dal.DALThuoc;
import fpoly.tn.helper.CheckValidation;
import fpoly.tn.dto.MyComboBox;
import fpoly.tn.dto.Thuoc;
import gui.form.message.DialogThongBao;
import fpoly.tn.helper.MouseEffect;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.ThongBao;
import fpoly.tn.helper.XuLyInput;
import fpoly.tn.helper.xuLyComboBox;
import fpoly.tn.helper.xuLyHinhAnh;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class frm_thuoc extends javax.swing.JDialog {

    Thuoc thuoc;

    public frm_thuoc(java.awt.Frame parent, String maSp) {
        super(parent, true);

        initComponents();

        setFormUI();

        init();

        loadDataControls();

        loadDataForm(maSp);
    }

    ///// HAM XU LY
    private void init() {
        initTableDongGoi();
        initTableLoThuoc();

        // validate tam
        txtGiaBan.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (CheckValidation.checkStringTrong(txtGiaBan.getTextValue())) {
                    txtGiaBan.setTextValue("0");
                }
            }

        });

        txtGiaNhap.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (CheckValidation.checkStringTrong(txtGiaNhap.getTextValue())) {
                    txtGiaNhap.setTextValue("0");
                }
            }

        });

    }

    private void initTableDongGoi() {
        tableDongGoi.addColum("ĐVT1", 100);
        tableDongGoi.addColum("ĐVT2", 100);
        tableDongGoi.addColum("Chuyển đổi", 100);
        tableDongGoi.addColum("Thao tác", 0);
    }

    private void initTableLoThuoc() {

        tableLoThuoc.addColum("Mã", 80);
        tableLoThuoc.addColum("Hạn Sử Dụng", 100);
        tableLoThuoc.addColum("SL", 100);
        tableLoThuoc.addColum("Thao tác", 0);
    }

    private void loadDataControls() {
        loadDataComboDVT();
        loadDataComboKho();
        loadDataLoaiThuoc();
    }

    private void loadDataForm(String maSp) {

        thuoc = new Thuoc();
        thuoc.setMa(maSp);

        boolean themMoi = CheckValidation.checkStringTrong(maSp);

        if (themMoi) {
            lbTuaDe.setText("THÊM THUỐC");
            cleanControls();

        } else {

            thuoc = DALThuoc.layThongTinThuoc(maSp);

            txtMa.setTextValue(thuoc.getMa().trim());
            txtTen.setTextValue(thuoc.getTen());
            xuLyComboBox.setSelectValue(comboDVT, thuoc.getDvtcb());
            xuLyComboBox.setSelectValue(comboKho, thuoc.getMaKho());
            xuLyComboBox.setSelectValue(comboLoai, thuoc.getMaloai());
            txtGiaBan.setTextValue(String.valueOf(thuoc.getGiaban()));
            txtGiaNhap.setTextValue(String.valueOf(thuoc.getGianhap()));
            checkbSuDung.setSelected(thuoc.isSuDung());
            txtGhiChu.setText(thuoc.getGhiChu());
            txtNSX.setTextValue(thuoc.getNhaSx());
            lbTuaDe.setText(thuoc.getTen());

            loadDataDonViDongGoi(thuoc.getMa());
            loadDataLoThuoc(thuoc.getMa());

        }

        txtMa.setEditable(themMoi);

        bntXoa.setEnabled(!themMoi);

    }

    private void loadDataLoThuoc(String maThuoc) {

        ResultSet rs = DALThuoc.loadLoThuoc(maThuoc);
        tableLoThuoc.removeAllItem();

        try {
            while (rs.next()) {
                String MaLo;
                int SoLuong, HSD;

                MaLo = rs.getString("ma");
                HSD = rs.getInt("HSD");
                SoLuong = rs.getInt("SoLuong");

                loadItemTableLoThuoc(MaLo, HSD, SoLuong);
            }
        } catch (SQLException ex) {
            Logger.getLogger(frm_thuoc.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
    }

    private void loadItemTableLoThuoc(String MaLo, int HSD, int SoLuong) {

        TableWebItem item = new TableWebItem();
        item.setId(MaLo);

        JPanel panelThaoTac = new JPanel();

        FlowLayout fLayout = new FlowLayout();
        fLayout.setAlignment(FlowLayout.CENTER);
        fLayout.setVgap((tableLoThuoc.getItemHeight() - 20) / 2);
        fLayout.setHgap(5);

        panelThaoTac.setLayout(fLayout);
        panelThaoTac.setOpaque(false);

        JLabel lbMaLo = new JLabel(MaLo);
        JLabel lbHanSuDung = new JLabel();
        JLabel lbSoLuong = new JLabel();

        lbMaLo.setHorizontalAlignment(SwingConstants.CENTER);
        lbHanSuDung.setHorizontalAlignment(SwingConstants.CENTER);
        lbSoLuong.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel edit = new JLabel();
        JLabel remove = new JLabel();

        edit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        remove.setCursor(new Cursor(Cursor.HAND_CURSOR));
        remove.setBorder(new EmptyBorder(0, 15, 0, 0));

        edit.setIcon(xuLyHinhAnh.LayAnhTuSource(edit, "/fpoly/icon/edit.png"));
        remove.setIcon(xuLyHinhAnh.LayAnhTuSource(remove, "/fpoly/icon/remove.png"));

        edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                moFormLoThuoc(MaLo, thuoc.getMa());
            }
        });

        remove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean xacNhan = ThongBao.taoThongBaoXacNhan(null, "Bạn có muốn xóa lô thuốc này", DialogThongBao.MessageIcon.QUESTION);
                if (xacNhan) {
                    if (DALLoThuoc.xoaLoThuoc(MaLo)) {
                        ThongBao.taoThongBao(null, "Xóa thành công", DialogThongBao.MessageIcon.CHECK);
                    } else {
                        ThongBao.taoThongBao(null, "Có lỗi khi xóa lô thuốc", DialogThongBao.MessageIcon.WARNING);
                    }
                    loadDataLoThuoc(thuoc.getMa());
                }
            }
        });

        Font font = new Font("Tahoma", 1, 12);
        lbMaLo.setFont(font);
        lbHanSuDung.setFont(font);
        lbSoLuong.setFont(font);

        if (HSD > 30) {
            lbHanSuDung.setText(HSD / 30 + " Tháng");
        } else if (HSD < 30 && HSD > 0) {
            lbHanSuDung.setText(HSD + " Ngày");
        } else {
            lbHanSuDung.setForeground(Color.red);
            lbHanSuDung.setText("Hết Hạn");
        }

        lbSoLuong.setText(XuLyInput.formatThemChamVaoSo(SoLuong));

        panelThaoTac.add(edit);
        panelThaoTac.add(remove);

        item.add(lbMaLo);
        item.add(lbHanSuDung);
        item.add(lbSoLuong);
        item.add(panelThaoTac);

        tableLoThuoc.addItem(item);

    }

    private void loadDataDonViDongGoi(String maThuoc) {

        ResultSet rs = DALThuoc.loadDanhSachDongGoiThuoc(maThuoc);
        tableDongGoi.removeAllItem();

        try {
            while (rs.next()) {
                String id = rs.getString("id");
                String DVT1 = rs.getString("dvt1");
                String DVT2 = rs.getString("dvt2");
                int soluong = rs.getInt("soluong");
                loadItemTableDongGoi(id, DVT1, DVT2, soluong);
            }
        } catch (SQLException ex) {
            Logger.getLogger(frm_thuoc.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
    }

    private void loadItemTableDongGoi(String id, String DVT1, String DVT2, int soluong) {

        TableWebItem item = new TableWebItem();
        item.setId(id);

        Font font = new Font("Tahoma", 1, 12);

        JLabel lbDvt1 = new JLabel(DVT1);
        JLabel lbDvt2 = new JLabel(DVT2);
        JLabel lbSoLuong = new JLabel(String.valueOf(soluong));
        JPanel panelThaoTac = new JPanel();

        lbDvt1.setHorizontalAlignment(SwingConstants.CENTER);
        lbDvt2.setHorizontalAlignment(SwingConstants.CENTER);
        lbSoLuong.setHorizontalAlignment(SwingConstants.CENTER);

        FlowLayout fLayout = new FlowLayout();
        fLayout.setAlignment(FlowLayout.CENTER);
        fLayout.setVgap((tableDongGoi.getItemHeight() - 20) / 2);
        fLayout.setHgap(5);

        panelThaoTac.setLayout(fLayout);
        panelThaoTac.setOpaque(false);

        lbDvt1.setFont(font);
        lbDvt2.setFont(font);
        lbSoLuong.setFont(new Font("Tahoma", 1, 13));
        lbSoLuong.setForeground(new Color(204, 0, 51));

        JLabel edit = new JLabel();
        JLabel remove = new JLabel();

        edit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        remove.setCursor(new Cursor(Cursor.HAND_CURSOR));
        remove.setBorder(new EmptyBorder(0, 10, 0, 0));

        edit.setIcon(xuLyHinhAnh.LayAnhTuSource(tableDongGoi, "/fpoly/icon/edit.png"));
        remove.setIcon(xuLyHinhAnh.LayAnhTuSource(tableDongGoi, "/fpoly/icon/remove.png"));

        edit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                moFormDongGoi(id);

            }

        });

        remove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean xacNhan = ThongBao.taoThongBaoXacNhan(null, "Bạn có muốn xóa đóng gói này", DialogThongBao.MessageIcon.QUESTION);
                if (xacNhan) {
                    if (DALDongGoi.xoaDongGoi(id)) {
                        ThongBao.taoThongBao(null, "Xóa thành công", DialogThongBao.MessageIcon.CHECK);
                    } else {
                        ThongBao.taoThongBao(null, "Có lỗi khi xóa đóng gói này", DialogThongBao.MessageIcon.WARNING);
                    }
                    loadDataDonViDongGoi(thuoc.getMa());
                }
            }

        });
        panelThaoTac.add(edit);
        panelThaoTac.add(remove);

        item.add(lbDvt1);
        item.add(lbDvt2);
        item.add(lbSoLuong);
        item.add(panelThaoTac);

        tableDongGoi.addItem(item);
    }

    private void moFormDongGoi(String idDongGoi) {

        if (CheckValidation.checkStringTrong(thuoc.getMa())) {
            ThongBao.taoThongBao(null, "Vui lòng lưu thông tin thuốc trước khi thêm Đóng Gói");
            return;
        }

        Frm_ThemDongGoiThuoc frm = new Frm_ThemDongGoiThuoc(null, idDongGoi, thuoc.getMa());
        frm.setVisible(true);

        loadDataDonViDongGoi(thuoc.getMa());
    }

    private void moFormLoThuoc(String maLo, String maThuoc) {

        if (CheckValidation.checkStringTrong(thuoc.getMa())) {
            ThongBao.taoThongBao(null, "Vui lòng lưu thông tin thuốc trước khi thêm Lô Thuốc");
            return;
        }

        Frm_LoThuoc form = new Frm_LoThuoc(null, maLo, maThuoc);
        form.setVisible(true);

        loadDataLoThuoc(thuoc.getMa());
    }

    private void loadDataComboDVT() {

        DefaultComboBoxModel modelCombo = (DefaultComboBoxModel) comboDVT.getModel();
        ResultSet rs;
        try {
            rs = DALKho.loadDanhSachDonViTinh();

            if (rs != null) {
                while (rs.next()) {
                    modelCombo.addElement(new MyComboBox(rs.getString("ma"), rs.getString("ten")));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(frm_thuoc.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SqlHelper.closeConnection();
        }
    }

    private void loadDataComboKho() {

        DefaultComboBoxModel modelCombo = (DefaultComboBoxModel) comboKho.getModel();
        ResultSet rs;

        rs = DALKho.loadDanhSachKho();
        try {
            while (rs.next()) {
                try {
                    modelCombo.addElement(new MyComboBox(rs.getString("ma"), rs.getString("ten")));
                } catch (SQLException ex) {
                    Logger.getLogger(frm_thuoc.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(frm_thuoc.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SqlHelper.closeConnection();
        }

    }

    private void loadDataLoaiThuoc() {

        DefaultComboBoxModel modelCombo = (DefaultComboBoxModel) comboLoai.getModel();
        ResultSet rs;

        rs = DALKho.loadDanhSachLoai();
        if (rs != null) {
            try {
                while (rs.next()) {
                    modelCombo.addElement(new MyComboBox(rs.getString("ma"), rs.getString("ten")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(frm_thuoc.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                SqlHelper.closeConnection();
            }
        }

    }

    private boolean checkValidate(boolean themMoi) {
        boolean hopLe = true;

        String message = "", message2 = "", message3;

        message3 = "không được để trống";

        String maSp = txtMa.getTextValue();
        String ten = txtTen.getTextValue();
        String giaban = txtGiaBan.getTextValue();
        String gianhap = txtGiaNhap.getTextValue();

        if (CheckValidation.checkStringTrong(maSp)) {
            message2 = "Mã sản phẩm";
            hopLe = false;
        } else if (CheckValidation.checkStringTrong(ten)) {
            message2 = "Tên";
            hopLe = false;
        } else if (CheckValidation.checkStringTrong(giaban)) {
            message2 = "Giá bán";
            hopLe = false;
        } else if (CheckValidation.checkStringTrong(gianhap)) {
            message2 = "Giá nhập";
            hopLe = false;
        } else if (themMoi && DALKho.kiemTraMaThuocTonTai(maSp)) {
            message = "Mã";
            message2 = maSp;
            message3 = "đã tồn tại! Điền mã mới";
            hopLe = false;
        }

        if (!hopLe) {
            ThongBao.taoThongBao(null, message, message2, message3, DialogThongBao.MessageIcon.WARNING);
        }

        return hopLe;
    }

    private void setFormUI() {
        setBackground(new Color(0, 0, 0, 0));
        MouseEffect.MouseMovingForm(panelTuaDe, this);

        // scroll
        txtGhiChu.putClientProperty("styleId", "hovering");

    }

    private void clickLuu() {

        boolean themMoi = CheckValidation.checkStringTrong(thuoc.getMa());

        if (!checkValidate(themMoi)) {
            return;
        }

        luuThongTinThuoc(themMoi);

    }

    private void luuThongTinThuoc(boolean themMoi) {

        layThongTinControls(themMoi);

        if (themMoi) {

            if (DALThuoc.themThuocMoi(thuoc)) {
                ThongBao.taoThongBao(null, "Thêm thành công", DialogThongBao.MessageIcon.CHECK);
                lbTuaDe.setText(thuoc.getTen());
            } else {
                ThongBao.taoThongBao(null, "Lỗi khi thêm sản phẩm", DialogThongBao.MessageIcon.CHECK);
                thuoc.setMa("");
            }

        } else {
            DALThuoc.suaThuoc(thuoc);
            ThongBao.taoThongBao(null, "Lưu thông tin thành công", DialogThongBao.MessageIcon.CHECK);
        }
    }

    private void cleanControls() {

        thuoc = new Thuoc();

        txtMa.setTextValue("");
        txtTen.setTextValue("");
        comboDVT.setSelectedIndex(0);
        comboKho.setSelectedIndex(0);
        txtGiaBan.setTextValue("");
        txtGiaNhap.setTextValue("");
        checkbSuDung.setSelected(true);
        txtGhiChu.setText("");
        txtNSX.setTextValue("");
    }

    private void clickThoat() {
        if (!ThongBao.taoThongBaoXacNhan(null, "Bạn muốn thoát", DialogThongBao.MessageIcon.QUESTION)) {
            return;
        }
        this.dispose();
    }

    private void layThongTinControls(boolean ThemMoi) {

        if (ThemMoi) {
            thuoc.setMa(txtMa.getTextValue());
        }

        thuoc.setTen(txtTen.getTextValue());
        thuoc.setDvtcb(xuLyComboBox.getSelectedValue(comboDVT));
        thuoc.setMaKho(xuLyComboBox.getSelectedValue(comboKho));
        thuoc.setMaloai(xuLyComboBox.getSelectedValue(comboLoai));

        thuoc.setGiaban(Integer.parseInt(txtGiaBan.getTextValue()));
        thuoc.setGianhap(Integer.parseInt(txtGiaNhap.getTextValue()));
        thuoc.setSuDung(checkbSuDung.isSelected());
        thuoc.setGhiChu(txtGhiChu.getText());
        thuoc.setNhaSx(txtNSX.getTextValue());
    }

    private void clickXoa() {

        if (!ThongBao.taoThongBaoXacNhan(null, "Bạn muốn xóa", thuoc.getTen(), DialogThongBao.MessageIcon.QUESTION)) {
            return;
        }

        if (DALThuoc.kiemTraSanPhamCoTrongHoaDon(thuoc.getMa())) {
            ThongBao.taoThongBao(null, "Không thể xóa", thuoc.getTen() + ".", "Sản phẩm này có trong hóa đơn", DialogThongBao.MessageIcon.WARNING);
            return;
        }

//        if (DALThuoc.xoaSanPham(thuoc.getMa())) {
//            ThongBao.taoThongBao(null, "Xóa thành công", DialogThongBao.MessageIcon.CHECK);
//            dispose();
//        } else {
//            ThongBao.taoThongBao(null, "Lỗi khi xóa sản phẩm", DialogThongBao.MessageIcon.WARNING);
//        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sPanel1 = new com.shady.scontrols.SPanel();
        panelTuaDe = new javax.swing.JPanel();
        lbTuaDe = new javax.swing.JLabel();
        btThoat2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMa = new com.shady.scontrols.STextField();
        jLabel2 = new javax.swing.JLabel();
        txtTen = new com.shady.scontrols.STextField();
        jLabel4 = new javax.swing.JLabel();
        txtGiaNhap = new com.shady.scontrols.STextField();
        txtGiaBan = new com.shady.scontrols.STextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        comboKho = new javax.swing.JComboBox<>();
        checkbSuDung = new javax.swing.JCheckBox();
        comboDVT = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        comboLoai = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtNSX = new com.shady.scontrols.STextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        scrollGhiChu = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        bntLuu = new com.shady.scontrols.SButton();
        bntMoi = new com.shady.scontrols.SButton();
        bntXoa = new com.shady.scontrols.SButton();
        bntThoat = new com.shady.scontrols.SButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnThemDongGoi = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        tableLoThuoc = new com.shady.scontrols.STableWeb();
        tableDongGoi = new com.shady.scontrols.STableWeb();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        sPanel1.setBackground(new java.awt.Color(255, 255, 255));
        sPanel1.sets_cornerRadius(0);
        sPanel1.sets_dropShadow(true);
        sPanel1.sets_shadowOpacity(0.2F);
        sPanel1.sets_shadowSize(10);

        panelTuaDe.setBackground(new java.awt.Color(82, 130, 216));

        lbTuaDe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbTuaDe.setForeground(new java.awt.Color(255, 255, 255));
        lbTuaDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icons_add_shopping_cart_30.png"))); // NOI18N
        lbTuaDe.setText("THÊM THUỐC MỚI");

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

        jPanel2.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Mã");

        txtMa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtMa.sets_iconLeft(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/iconKey.png"))); // NOI18N
        txtMa.sets_maxTextLength(6);
        txtMa.sets_placeholder("Mã");
        txtMa.sets_radius(10);
        txtMa.sets_textType(com.shady.scontrols.STextField.textTypeEnum.ID);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Tên thuốc");

        txtTen.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTen.sets_placeholder("Tên Thuốc...");
        txtTen.sets_radius(10);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Giá nhập");

        txtGiaNhap.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGiaNhap.sets_maxTextLength(10);
        txtGiaNhap.sets_placeholder("Giá nhập");
        txtGiaNhap.sets_radius(10);
        txtGiaNhap.sets_textType(com.shady.scontrols.STextField.textTypeEnum.NUMBER_FORMAT);
        txtGiaNhap.setTextValue("0");

        txtGiaBan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGiaBan.sets_maxTextLength(10);
        txtGiaBan.sets_placeholder("Giá bán");
        txtGiaBan.sets_radius(10);
        txtGiaBan.sets_textType(com.shady.scontrols.STextField.textTypeEnum.NUMBER_FORMAT);
        txtGiaBan.setTextValue("0");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Giá bán");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Kho");

        comboKho.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        checkbSuDung.setBackground(new java.awt.Color(255, 255, 255));
        checkbSuDung.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        checkbSuDung.setSelected(true);
        checkbSuDung.setText("Sử Dụng");

        comboDVT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Đơn vị tính cơ bản");

        comboLoai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboLoaiActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Loại thuốc");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("THÔNG TIN CƠ BẢN");

        txtNSX.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtNSX.sets_placeholder("Nhà sản xuát...");
        txtNSX.sets_radius(10);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("NSX");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Ghi chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtGhiChu.setRows(5);
        scrollGhiChu.setViewportView(txtGhiChu);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(comboKho, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(comboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(comboDVT, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scrollGhiChu, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(checkbSuDung)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(txtNSX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(22, 22, 22))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(checkbSuDung)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNSX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboKho, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboDVT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(21, 21, 21)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 5));

        bntLuu.setBackground(new java.awt.Color(248, 246, 246));
        bntLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_save.png"))); // NOI18N
        bntLuu.setText("Lưu");
        bntLuu.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntLuu.sets_Radius(10);
        bntLuu.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntLuuActionPerformed(evt);
            }
        });
        jPanel1.add(bntLuu);

        bntMoi.setBackground(new java.awt.Color(248, 246, 246));
        bntMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_new_file_20.png"))); // NOI18N
        bntMoi.setText("Làm mới");
        bntMoi.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntMoi.sets_Radius(10);
        bntMoi.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntMoiActionPerformed(evt);
            }
        });
        jPanel1.add(bntMoi);

        bntXoa.setBackground(new java.awt.Color(248, 246, 246));
        bntXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_delete.png"))); // NOI18N
        bntXoa.setText("Xóa");
        bntXoa.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntXoa.sets_Radius(10);
        bntXoa.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntXoaActionPerformed(evt);
            }
        });
        jPanel1.add(bntXoa);

        bntThoat.setBackground(new java.awt.Color(255, 204, 204));
        bntThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/tn/icon/icon_exit_20.png"))); // NOI18N
        bntThoat.setText("Thoát");
        bntThoat.sets_Cursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntThoat.sets_Radius(10);
        bntThoat.sets_borderColor(new java.awt.Color(153, 153, 153));
        bntThoat.sets_hoverBackground(new java.awt.Color(240, 240, 240));
        bntThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntThoatActionPerformed(evt);
            }
        });
        jPanel1.add(bntThoat);

        jPanel3.setOpaque(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("ĐƠN VỊ ĐÓNG GÓI");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("LÔ THUỐC");

        btnThemDongGoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/add2.png"))); // NOI18N
        btnThemDongGoi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThemDongGoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemDongGoiMouseClicked(evt);
            }
        });

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/add2.png"))); // NOI18N
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        tableLoThuoc.setHeaderBackground(new java.awt.Color(82, 130, 216));
        tableLoThuoc.setHeaderForeground(new java.awt.Color(255, 255, 255));
        tableLoThuoc.setHeaderRound(10);
        tableLoThuoc.setListViewBackground(new java.awt.Color(255, 255, 255));
        tableLoThuoc.setPreferredSize(new java.awt.Dimension(538, 30));

        tableDongGoi.setHeaderBackground(new java.awt.Color(82, 130, 216));
        tableDongGoi.setHeaderForeground(new java.awt.Color(255, 255, 255));
        tableDongGoi.setHeaderRound(10);
        tableDongGoi.setListViewBackground(new java.awt.Color(255, 255, 255));
        tableDongGoi.setPreferredSize(new java.awt.Dimension(538, 30));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableLoThuoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tableDongGoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnThemDongGoi)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThemDongGoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableDongGoi, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableLoThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout sPanel1Layout = new javax.swing.GroupLayout(sPanel1);
        sPanel1.setLayout(sPanel1Layout);
        sPanel1Layout.setHorizontalGroup(
            sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTuaDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sPanel1Layout.createSequentialGroup()
                .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(sPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        sPanel1Layout.setVerticalGroup(
            sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sPanel1Layout.createSequentialGroup()
                .addComponent(panelTuaDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(sPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1045, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btThoat2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btThoat2MouseClicked
        clickThoat();
    }//GEN-LAST:event_btThoat2MouseClicked

    private void bntLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntLuuActionPerformed
        clickLuu();
    }//GEN-LAST:event_bntLuuActionPerformed

    private void bntMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntMoiActionPerformed
        loadDataForm("");
    }//GEN-LAST:event_bntMoiActionPerformed

    private void bntThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntThoatActionPerformed
        clickThoat();
    }//GEN-LAST:event_bntThoatActionPerformed

    private void bntXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntXoaActionPerformed
        clickXoa();
    }//GEN-LAST:event_bntXoaActionPerformed

    private void comboLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboLoaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboLoaiActionPerformed

    private void btnThemDongGoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemDongGoiMouseClicked
        moFormDongGoi("");
    }//GEN-LAST:event_btnThemDongGoiMouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        moFormLoThuoc("", thuoc.getMa());
    }//GEN-LAST:event_jLabel14MouseClicked

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
            java.util.logging.Logger.getLogger(frm_thuoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            frm_thuoc dialog = new frm_thuoc(new javax.swing.JFrame(), "");
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.shady.scontrols.SButton bntLuu;
    private com.shady.scontrols.SButton bntMoi;
    private com.shady.scontrols.SButton bntThoat;
    private com.shady.scontrols.SButton bntXoa;
    private javax.swing.JLabel btThoat2;
    private javax.swing.JLabel btnThemDongGoi;
    private javax.swing.JCheckBox checkbSuDung;
    private javax.swing.JComboBox<String> comboDVT;
    private javax.swing.JComboBox<String> comboKho;
    private javax.swing.JComboBox<String> comboLoai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbTuaDe;
    private javax.swing.JPanel panelTuaDe;
    private com.shady.scontrols.SPanel sPanel1;
    private javax.swing.JScrollPane scrollGhiChu;
    private com.shady.scontrols.STableWeb tableDongGoi;
    private com.shady.scontrols.STableWeb tableLoThuoc;
    private javax.swing.JTextArea txtGhiChu;
    private com.shady.scontrols.STextField txtGiaBan;
    private com.shady.scontrols.STextField txtGiaNhap;
    private com.shady.scontrols.STextField txtMa;
    private com.shady.scontrols.STextField txtNSX;
    private com.shady.scontrols.STextField txtTen;
    // End of variables declaration//GEN-END:variables
}
