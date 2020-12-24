package gui.form.lothuoc;

import com.datepick.DatePick;
import com.shady.event.AutoEvent;
import com.shady.scontrols.SPanel;
import com.shady.scontrols.STextField;
import fpoly.tn.dal.DALDonViTinh;
import fpoly.tn.dal.DALKho;
import fpoly.tn.dal.DALLoThuoc;
import fpoly.tn.dal.DALThuoc;
import fpoly.tn.dto.LoThuoc;
import fpoly.tn.helper.CheckValidation;
import fpoly.tn.helper.MouseEffect;
import fpoly.tn.helper.NgayThang;
import fpoly.tn.helper.SqlHelper;
import fpoly.tn.helper.ThongBao;
import gui.form.message.DialogThongBao;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

public class Frm_LoThuoc extends javax.swing.JDialog {

    static LoThuoc loThuoc = new LoThuoc();

    DatePick ngaySanXuat = new DatePick();
    DatePick ngayHetHan = new DatePick();
    
    int soLuongLo = 0;

    public Frm_LoThuoc(java.awt.Frame parent, String maLo, String maThuoc) {
        super(parent, true);
        initComponents();
        setBackground(new Color(0, 0, 0, 0));

        loThuoc = DALKho.layThongTinLoThuoc(maLo);
        loThuoc.setMaLo(maLo);
        loThuoc.setMaThuoc(maThuoc);
        soLuongLo = loThuoc.getSoLuong();

        initForm();

        loadDataForm();

    }

    private void initForm() {

        MouseEffect.MouseMovingForm(panelTuaDe2, this);

        ngaySanXuat.setButton(btnNgaySX);
        ngayHetHan.setButton(btnNgayHetHan);
    }


    private void loadDataForm() {

        boolean loMoi = CheckValidation.checkStringTrong(loThuoc.getMaLo());
        boolean maThuocMoi = CheckValidation.checkStringTrong(loThuoc.getMaThuoc());

        if (maThuocMoi) {

            txtTenThuoc.sets_textType(STextField.textTypeEnum.AUTOCOMPLETE);

            txtTenThuoc.addFiledColum("Mã", "ma", 100);
            txtTenThuoc.addFiledColum("Tên Thuốc", "ten", 300);

            txtTenThuoc.sets_DisplayColum("ten");
            txtTenThuoc.sets_FieldColum("ma");

            try {
                txtTenThuoc.loadData(DALKho.layDanhSachThuocLoThuoc());
            } catch (SQLException ex) {
                Logger.getLogger(Frm_LoThuoc.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                SqlHelper.closeConnection();
            }
            
            txtTenThuoc.addValueChangedListener((AutoEvent ae) -> {
                loadSoLuongThuoc(ae.getValue());
                
            });
            
        }else{
            loadSoLuongThuoc(loThuoc.getMaThuoc());
            txtTenThuoc.setTextValue(DALThuoc.layTenThuoc(loThuoc.getMaThuoc()));
            txtTenThuoc.setEnabled(false);
        }

        if (!loMoi) {
            ngaySanXuat.setSelectDate(NgayThang.doiDinhDangNgay(loThuoc.getNgaySanXuat(), "yyyy-MM-dd", "dd-MM-yyyy"));
            ngayHetHan.setSelectDate(NgayThang.doiDinhDangNgay(loThuoc.getHanSuDung(), "yyyy-MM-dd", "dd-MM-yyyy"));
            txtGhiChu.setText(loThuoc.getGhiChu());
            txtMaLo.setTextValue(loThuoc.getMaLo());
            txtMaLo.setEnabled(false);
        }
        
    }
    
    private void loadComponentPanelSoLuong(String maDVT, String tenDVT, int soLuongChua){
        
        Font font = new Font("Tahoma",1,12);
        
        SPanel panelSL = new SPanel();
        panelSL.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelSL.setPreferredSize(new Dimension(200, 40));
        panelSL.sets_drawborder(true);
        panelSL.sets_borderColor(new Color(204,204,204));
        panelSL.sets_cornerRadius(15);
        panelSL.setBackground(Color.white);
        
        JLabel lbTenDVT = new JLabel(tenDVT);
        lbTenDVT.setFont(font);
        lbTenDVT.setForeground(new Color(102,102,102));
        lbTenDVT.setBorder(new MatteBorder(0, 0, 0, 2, new Color(204,204,204)));
        lbTenDVT.setPreferredSize(new Dimension(70, 40));
        lbTenDVT.setHorizontalAlignment(SwingConstants.CENTER);
        
        STextField textSLDVT = new STextField();
        textSLDVT.setFont(font);
        textSLDVT.sets_drawborder(false);
        textSLDVT.sets_textType(STextField.textTypeEnum.NUMBER_FORMAT);
        textSLDVT.setPreferredSize(new Dimension(130, 30));
        textSLDVT.setName(String.valueOf(soLuongChua));
        textSLDVT.setTextValue("0");
        
        // lay so luong theo don vi tinh
        if(soLuongLo > 0 ){
            int soLuong = soLuongLo/soLuongChua;
            textSLDVT.setTextValue(String.valueOf(soLuong));
            // tinh so luong le con lai
            soLuongLo %= soLuongChua;
        }
        
        textSLDVT.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                
                if(CheckValidation.checkStringTrong(textSLDVT.getTextValue())){
                    textSLDVT.setTextValue("0");
                }
            }
            
        });
        
        panelSL.add(lbTenDVT);
        panelSL.add(textSLDVT);
        
        panelSoLuong.add(panelSL);
        
    }
    
    private void loadSoLuongThuoc(String maThuoc){
        ResultSet rs = DALDonViTinh.layDanhSanhDVTThuoc(maThuoc,1);
        panelSoLuong.removeAll();
        try {
            while(rs.next()){
                String maDVT = rs.getString("ma");
                String tenDVT = rs.getString("ten");
                int soLuongChua = rs.getInt("soluong");
                loadComponentPanelSoLuong(maDVT,tenDVT,soLuongChua);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Frm_LoThuoc.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            SqlHelper.closeConnection();
        }
        this.revalidate();
    }

    private void clickLuu() {

        if (!checkValidate()) {
            return;
        }
        
        loThuoc.setGhiChu(txtGhiChu.getText());
        loThuoc.setHanSuDung(NgayThang.doiDinhDangNgay(ngayHetHan.getSelectedDate(), "dd-MM-yyyy", "yyyy-MM-dd"));
        loThuoc.setNgaySanXuat(NgayThang.doiDinhDangNgay(ngaySanXuat.getSelectedDate(), "dd-MM-yyyy", "yyyy-MM-dd"));
        loThuoc.setSoLuong(tinhTongSoLuong());

        boolean thanhCong;
        
        if(txtTenThuoc.isEnabled()){
            loThuoc.setMaThuoc(txtTenThuoc.getFieldValue());
        }
        
        if (txtMaLo.isEnabled()) {
            loThuoc.setMaLo(txtMaLo.getTextValue());
           thanhCong = themMoi();
        } else {
            thanhCong =  capNhat();
        }
        
        if(thanhCong){
           ThongBao.taoThongBao(null, "Lưu thành công", DialogThongBao.MessageIcon.CHECK);
       }else{
           ThongBao.taoThongBao(null, "Lỗi khi lưu", DialogThongBao.MessageIcon.WARNING);
       }

    }

    private boolean themMoi() {
        return DALLoThuoc.themMoMoi(loThuoc);
    }

    private boolean capNhat() {
        return DALLoThuoc.suaLoThuoc(loThuoc);
    }

    private boolean checkValidate() {
        
        boolean themMoiLo = CheckValidation.checkStringTrong(loThuoc.getMaLo());
        boolean chuaCoMaThuoc = CheckValidation.checkStringTrong(loThuoc.getMaThuoc());
        
        if(themMoiLo){
            
            String maLoNhap = txtMaLo.getTextValue();
            // mã lô trống
            if(CheckValidation.checkStringTrong(maLoNhap)){
                ThongBao.taoThongBao(null, "Chưa có mã lô thuốc", DialogThongBao.MessageIcon.WARNING);
               return false;
            }
            if(DALLoThuoc.kiemTraMaLoTonTai(maLoNhap)){
                ThongBao.taoThongBao(null, "Mã",maLoNhap,"đã tồn tại. Nhập mã mới", DialogThongBao.MessageIcon.WARNING);
               return false;
            }
            
        }
        
        if(chuaCoMaThuoc){
            // mã thuốc trống
            if(CheckValidation.checkStringTrong(txtTenThuoc.getFieldValue())){
                ThongBao.taoThongBao(null, "Chưa chọn thuốc", DialogThongBao.MessageIcon.WARNING);
               return false;
            }
        }
        
        if(NgayThang.layKhoangCachNgay(ngaySanXuat.getSelectedDate(), ngayHetHan.getSelectedDate(), "dd-MM-yyyy") < 0){
            ThongBao.taoThongBao(null, "Ngày hết hạn phải lớn hơn ngày sản xuất", DialogThongBao.MessageIcon.WARNING);
            return false;
        }
        
        
        return true;
    }
    
    private int tinhTongSoLuong(){
        
        int tong = 0;
        
        for(Component itemSL : panelSoLuong.getComponents()){
            SPanel panelItemSL = (SPanel) itemSL;
            for(Component comp : panelItemSL.getComponents()){
                if(comp instanceof STextField){
                    STextField txtSL = (STextField) comp;
                    int soLuong = Integer.parseInt(txtSL.getTextValue());
                    int soLuongChua = Integer.parseInt(txtSL.getName());
                    tong += soLuong * soLuongChua;
                }
            }
        }
        
        return tong;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sPanel1 = new com.shady.scontrols.SPanel();
        panelTuaDe2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        sButton3 = new com.shady.scontrols.SButton();
        sButton4 = new com.shady.scontrols.SButton();
        jPanel1 = new javax.swing.JPanel();
        lbMaLo = new javax.swing.JLabel();
        txtMaLo = new com.shady.scontrols.STextField();
        lbTenThuoc = new javax.swing.JLabel();
        txtTenThuoc = new com.shady.scontrols.STextField();
        jLabel7 = new javax.swing.JLabel();
        btnNgaySX = new com.shady.scontrols.SButton();
        btnNgayHetHan = new com.shady.scontrols.SButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        lbMaLo1 = new javax.swing.JLabel();
        panelSoLuong = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        sPanel1.setBackground(new java.awt.Color(255, 255, 255));
        sPanel1.sets_dropShadow(true);
        sPanel1.sets_shadowOpacity(0.3F);
        sPanel1.sets_shadowSize(13);

        panelTuaDe2.setOpaque(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/icon/lothuoc.png"))); // NOI18N
        jLabel3.setText("LÔ THUỐC");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("X");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.setPreferredSize(new java.awt.Dimension(30, 30));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelTuaDe2Layout = new javax.swing.GroupLayout(panelTuaDe2);
        panelTuaDe2.setLayout(panelTuaDe2Layout);
        panelTuaDe2Layout.setHorizontalGroup(
            panelTuaDe2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTuaDe2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelTuaDe2Layout.setVerticalGroup(
            panelTuaDe2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTuaDe2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(panelTuaDe2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTuaDe2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        sButton3.setBackground(new java.awt.Color(0, 204, 0));
        sButton3.setText("Lưu");
        sButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        sButton3.sets_ForeColor(new java.awt.Color(255, 255, 255));
        sButton3.sets_HoverForeColor(new java.awt.Color(255, 255, 255));
        sButton3.sets_Radius(15);
        sButton3.sets_drawBorder(false);
        sButton3.sets_hoverBackground(new java.awt.Color(1, 196, 1));
        sButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sButton3ActionPerformed(evt);
            }
        });

        sButton4.setBackground(new java.awt.Color(255, 153, 153));
        sButton4.setText("Thoát");
        sButton4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        sButton4.sets_ForeColor(new java.awt.Color(255, 255, 255));
        sButton4.sets_HoverForeColor(new java.awt.Color(255, 255, 255));
        sButton4.sets_Radius(15);
        sButton4.sets_drawBorder(false);
        sButton4.sets_hoverBackground(new java.awt.Color(243, 152, 152));
        sButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sButton4ActionPerformed(evt);
            }
        });

        jPanel1.setOpaque(false);

        lbMaLo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbMaLo.setForeground(new java.awt.Color(102, 102, 102));
        lbMaLo.setText("Mã");

        txtMaLo.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        txtMaLo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMaLo.sets_maxTextLength(6);
        txtMaLo.sets_placeholder("Mã...");
        txtMaLo.sets_radius(15);
        txtMaLo.sets_textType(com.shady.scontrols.STextField.textTypeEnum.ID);

        lbTenThuoc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbTenThuoc.setForeground(new java.awt.Color(102, 102, 102));
        lbTenThuoc.setText("Thuốc");

        txtTenThuoc.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        txtTenThuoc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTenThuoc.sets_placeholder("Tên thuốc ....");
        txtTenThuoc.sets_radius(15);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Ngày Sản Xuất");

        btnNgaySX.setBackground(new java.awt.Color(255, 255, 255));
        btnNgaySX.setText("00-00-00000");
        btnNgaySX.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNgaySX.sets_Radius(15);
        btnNgaySX.sets_hoverBackground(new java.awt.Color(255, 255, 255));

        btnNgayHetHan.setBackground(new java.awt.Color(255, 255, 255));
        btnNgayHetHan.setText("00-00-00000");
        btnNgayHetHan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNgayHetHan.sets_Radius(15);
        btnNgayHetHan.sets_hoverBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Ngày Hết Hạn");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("Ghi Chú");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11)
                    .addComponent(lbMaLo)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNgaySX, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNgayHetHan, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addComponent(lbTenThuoc)
                    .addComponent(txtTenThuoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(txtMaLo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbMaLo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaLo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbTenThuoc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNgaySX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNgayHetHan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setOpaque(false);

        lbMaLo1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbMaLo1.setForeground(new java.awt.Color(102, 102, 102));
        lbMaLo1.setText("Số lượng");

        panelSoLuong.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lbMaLo1)
                        .addGap(149, 149, 149)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbMaLo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout sPanel1Layout = new javax.swing.GroupLayout(sPanel1);
        sPanel1.setLayout(sPanel1Layout);
        sPanel1Layout.setHorizontalGroup(
            sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTuaDe2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(sPanel1Layout.createSequentialGroup()
                .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(sPanel1Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(sButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sPanel1Layout.setVerticalGroup(
            sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sPanel1Layout.createSequentialGroup()
                .addComponent(panelTuaDe2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(sPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void sButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sButton3ActionPerformed
        clickLuu();
    }//GEN-LAST:event_sButton3ActionPerformed

    private void sButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sButton4ActionPerformed
        this.dispose();
    }//GEN-LAST:event_sButton4ActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frm_LoThuoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm_LoThuoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm_LoThuoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm_LoThuoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {

            Frm_LoThuoc dialog = new Frm_LoThuoc(new javax.swing.JFrame(), "", "");
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
    private com.shady.scontrols.SButton btnNgayHetHan;
    private com.shady.scontrols.SButton btnNgaySX;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbMaLo;
    private javax.swing.JLabel lbMaLo1;
    private javax.swing.JLabel lbTenThuoc;
    private javax.swing.JPanel panelSoLuong;
    private javax.swing.JPanel panelTuaDe2;
    private com.shady.scontrols.SButton sButton3;
    private com.shady.scontrols.SButton sButton4;
    private com.shady.scontrols.SPanel sPanel1;
    private javax.swing.JTextArea txtGhiChu;
    private com.shady.scontrols.STextField txtMaLo;
    private com.shady.scontrols.STextField txtTenThuoc;
    // End of variables declaration//GEN-END:variables
}
