package fpoly.tn.helper;

import gui.form.message.DialogThongBao;
import gui.form.message.DialogThongBao.MessageIcon;
import gui.form.message.DialogThongBao.MessageType;
import javax.swing.JFrame;


public class ThongBao {

    /* Thong bao thuong */
    
    // thong bao
    public static boolean taoThongBao(JFrame parent, String message){
        DialogThongBao thongBao = new DialogThongBao(parent, message, MessageType.THONGBAO);
        thongBao.setVisible(true);
        return thongBao.getResuiltValue();
    }
    
    // thong bao, icon
    public static boolean taoThongBao(JFrame frame ,String message,MessageIcon messageIcon){
        DialogThongBao thongBao = new DialogThongBao(frame, message, messageIcon, MessageType.THONGBAO);
        thongBao.setVisible(true);
        return thongBao.getResuiltValue();
    }
    // thong bao + node 
    public static boolean taoThongBao(JFrame frame ,String message, String messageNode){
        DialogThongBao thongBao = new DialogThongBao(frame, message, messageNode, MessageType.THONGBAO);
        thongBao.setVisible(true);
        return thongBao.getResuiltValue();
    }
    // thong bao + node +icon
    public static boolean taoThongBao(JFrame frame ,String message, String messageNode,MessageIcon icon){
        DialogThongBao thongBao = new DialogThongBao(frame, message, messageNode,icon, MessageType.THONGBAO);
        thongBao.setVisible(true);
        return thongBao.getResuiltValue();
    }
    // thong bao + node +thong bao 2
    public static boolean taoThongBao(JFrame frame ,String message, String messageNode, String message2){
        DialogThongBao thongBao = new DialogThongBao(frame, message, messageNode,message2, MessageType.THONGBAO);
        thongBao.setVisible(true);
        return thongBao.getResuiltValue();
    }
    
    // thong bao + node +thong bao 2 + icon
    public static boolean taoThongBao(JFrame frame ,String noiDung, String node,String noiDung2, MessageIcon icon){
        DialogThongBao thongBao = new DialogThongBao(frame, noiDung, node,noiDung2, icon, MessageType.THONGBAO);
        thongBao.setVisible(true);
        return thongBao.getResuiltValue();
    }
    
   /* XAC NHAN  */
    
    // thong bao
    public static boolean taoThongBaoXacNhan(JFrame parent, String message){
        DialogThongBao thongBao = new DialogThongBao(parent, message, MessageType.XACNHAN);
        thongBao.setVisible(true);
        return thongBao.getResuiltValue();
    }
    
    // thong bao, icon
    public static boolean taoThongBaoXacNhan(JFrame frame ,String message,MessageIcon messageIcon){
        DialogThongBao thongBao = new DialogThongBao(frame, message, messageIcon, MessageType.XACNHAN);
        thongBao.setVisible(true);
        return thongBao.getResuiltValue();
    }
    // thong bao + node 
    public static boolean taoThongBaoXacNhan(JFrame frame ,String message, String messageNode){
        DialogThongBao thongBao = new DialogThongBao(frame, message, messageNode, MessageType.XACNHAN);
        thongBao.setVisible(true);
        return thongBao.getResuiltValue();
    }
    
    // thong bao + node + icon
    public static boolean taoThongBaoXacNhan(JFrame frame ,String message, String messageNode , MessageIcon icon){
        DialogThongBao thongBao = new DialogThongBao(frame, message, messageNode,icon, MessageType.XACNHAN);
        thongBao.setVisible(true);
        return thongBao.getResuiltValue();
    }
    
    // thong bao + node +thong bao 2
    public static boolean taoThongBaoXacNhan(JFrame frame ,String message, String messageNode, String message2){
        DialogThongBao thongBao = new DialogThongBao(frame, message, messageNode,message2, MessageType.XACNHAN);
        thongBao.setVisible(true);
        return thongBao.getResuiltValue();
    }
    
    // thong bao + node +thong bao 2 + icon
    public static boolean taoThongBaoXacNhan(JFrame frame ,String noiDung, String node,String noiDung2, MessageIcon icon){
        DialogThongBao thongBao = new DialogThongBao(frame, noiDung, node,noiDung2, icon, MessageType.XACNHAN);
        thongBao.setVisible(true);
        return thongBao.getResuiltValue();
    }
}
