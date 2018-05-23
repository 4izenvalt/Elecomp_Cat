/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public final class Pembelian_Hutang_RincianBarang extends javax.swing.JFrame {

    private int potongan = 0;
    private int totalHutang = 0;
    String[] noFaktur;
    int[] hrgItem;
    String fakturBP = null;
    String fakturBB = null;
    int jum;
    int keuangan1 = 0;
    int keuangan2 = 0;

    public Pembelian_Hutang_RincianBarang() {
        initComponents();
        this.setVisible(true);
        //  fakturBP();
        fakturBB();
    }

    public Pembelian_Hutang_RincianBarang(int totalHutang, int potongan, String[] noFaktur, int[] hrgItem, int jum) {
        initComponents();
        this.potongan = potongan;
        this.totalHutang = totalHutang;
        this.noFaktur = noFaktur;
        this.hrgItem = hrgItem;
        this.jum = jum;

        txt_potongan.setText("" + this.potongan);
        txt_blmBayar.setText("" + this.totalHutang);

        date();
        fakturBP();
        bank();
    }

    public void bayar() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        // System.out.println(sdf.format(cal_tglBG.getDate()));
        try {
            String sqlA = null, sqlB = null;
            for (int i = 0; i < jum; i++) {
                if (com_jenisBG.getSelectedIndex() == 0) {
                    sqlA = "update pembelian SET tgl_bg = '"
                            + sdf.format(cal.getTime())
                            + "', no_seri_bg = " + txt_noSeriBG.getText()
                            + ", faktur_tempo_bg = '" + sdf.format(cal_tglBG.getDate())
                            + "' WHERE no_faktur_pembelian = '" + noFaktur[i] + "'";

                    sqlB = "INSERT INTO transaksi_master values ("
                            + null + ",'" + fakturBP + "','" + txt_noSeriBG.getText() + "','" + noFaktur[i] + "','" + sdf.format(cal.getTime()) + "'"
                            + ",'0'," + hrgItem[i] + ",'0','0','" + txt_ket.getText() + "','" + keuangan1 + "'"
                            + ",'0','0','0000-00-00 00:00:00','0000-00-00')";
                } else {
                    sqlA = "update pembelian SET biaya_pembayaran = 0 WHERE no_faktur_pembelian = '" + noFaktur[i] + "'";

                    sqlB = "INSERT INTO transaksi_master values ("
                            + null + ",'" + fakturBP + "','','" + noFaktur[i] + "','" + sdf.format(cal.getTime()) + "'"
                            + ",'0'," + hrgItem[i] + ",'0','0','" + txt_ket.getText() + "','" + keuangan1 + "'"
                            + ",'0','0','0000-00-00 00:00:00','0000-00-00')";
                }
//                System.out.println("SQL A = " + sqlA);
//                System.out.println("SQL B = " + sqlB);
                Connection conn = (Connection) Koneksi.configDB();
                Statement stat = conn.createStatement();
                stat.executeUpdate(sqlA);
                stat.executeUpdate(sqlB);
            }

            System.out.println("Jum = " + jum);
            JOptionPane.showMessageDialog(this, "Sukses");
            dispose();
            Pembelian_Hutang a = new Pembelian_Hutang();
            a.loadTable();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String dotConverter(String b) {
        b = b.replace("-", "");
        return b;
    }

    public void autoSum() {
        int bayar = 0;
        int bayar2 = 0;
        if (txt_bayar.getText().equals("")) {
            bayar = 0;
        } else {
            bayar = Integer.parseInt(txt_bayar.getText());
        }
        if (txt_bayar2.getText().equals("")) {
            bayar2 = 0;
        } else {
            bayar2 = Integer.parseInt(txt_bayar2.getText());
        }
        int kembali = bayar + bayar2 + (totalHutang + potongan);
        txt_kembali.setText("" + kembali);
    }

    public void date() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal_tglBG.setDate(cal.getTime());
    }

    public void bank() {
        int keuangan1 = 1;
        int keuangan2 = 1;
        switch (com_keuangan_1.getSelectedIndex()) {
            case 0:
                keuangan1 = 1;
                break;
            case 1:
                keuangan1 = 2;
                break;
            case 2:
                keuangan1 = 3;
                break;
            case 3:
                keuangan1 = 4;
                break;
            case 4:
                keuangan1 = 5;
                break;
            default:
                break;
        }
        switch (com_keuangan_2.getSelectedIndex()) {
            case 0:
                keuangan2 = 1;
                break;
            case 1:
                keuangan2 = 2;
                break;
            case 2:
                keuangan2 = 3;
                break;
            case 3:
                keuangan2 = 4;
                break;
            case 4:
                keuangan2 = 5;
                break;
            default:
                break;
        }
        this.keuangan1 = keuangan1;
        this.keuangan2 = keuangan2;
    }

    public void fakturBP() {
        try {
            String sql = "select faktur_bp, kode_transaksi_master from transaksi_master "
                    + "WHERE faktur_bp LIKE 'BP%' ORDER BY kode_transaksi_master desc LIMIT 1";
            Connection conn = (Connection) Koneksi.configDB();
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                if (res.first() == false) {
                    System.out.println("BP18-1");

                } else {
                    res.last();
                    String auto_num = res.getString(1);
                    String huruf = String.valueOf(auto_num.substring(0, 5));
                    int angka = Integer.valueOf(auto_num.substring(5)) + 1;
                    this.fakturBP = huruf + angka;
                    System.out.println(this.fakturBP);
                }
            }
            res.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void fakturBB() {
        System.out.println("jalan");
        try {
            String sql = "select faktur_bg, kode_pembelian from pembelian "
                    + "WHERE faktur_bg LIKE 'BB%' ORDER BY kode_pembelian desc LIMIT 1";
            Connection conn = (Connection) Koneksi.configDB();
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                System.out.println("==========");
                String a = res.getString("faktur_bg");
                System.out.println("Data =");
                if (a == null) {
//                    this.fakturBB = "BB18-0001";
//                    System.out.println(this.fakturBB);
                    System.out.println("work");

                } else {
                    res.last();
                    String auto_num = res.getString("faktur_bg");
                    String huruf = String.valueOf(auto_num.substring(0, 5));
                    int angka = Integer.valueOf(auto_num.substring(5)) + 1;
                    this.fakturBB = huruf + angka;
                    System.out.println(this.fakturBB);
                }
            }
            res.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel11 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel36 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        com_jenisBG = new javax.swing.JComboBox<>();
        txt_kembali = new javax.swing.JTextField();
        btn_close = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        btn_bayar = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        txt_noSeriBG = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        com_keuangan_2 = new javax.swing.JComboBox<>();
        txt_bayar2 = new javax.swing.JTextField();
        txt_bayar = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        com_keuangan_1 = new javax.swing.JComboBox<>();
        jLabel38 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txt_blmBayar = new javax.swing.JTextField();
        txt_ket = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        cal_tglBG = new com.toedter.calendar.JDateChooser();
        txt_potongan = new javax.swing.JTextField();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(new java.awt.Dimension(437, 582));

        jPanel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setText("Cara Bayar");

        jLabel36.setText("Tanggal BG");

        jLabel35.setText("Jenis BG");

        com_jenisBG.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BG", "No BG" }));
        com_jenisBG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                com_jenisBGActionPerformed(evt);
            }
        });

        txt_kembali.setEnabled(false);
        txt_kembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_kembaliMouseClicked(evt);
            }
        });

        btn_close.setBackground(new java.awt.Color(153, 153, 153));
        btn_close.setText("Close");
        btn_close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_closeMouseClicked(evt);
            }
        });

        jLabel44.setText("Kembalian");

        btn_bayar.setBackground(new java.awt.Color(85, 222, 93));
        btn_bayar.setText("Bayar");
        btn_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bayarActionPerformed(evt);
            }
        });

        jLabel34.setText("No Seri BG");

        jLabel37.setText("Potongan");

        jLabel41.setText("Bayar 2");

        jLabel40.setText("Keuangan 2");

        com_keuangan_2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BCA | 1", "MANDIRI | 2", "BRI | 3", "CAT73 | 4", "TOKO CAT73 | 5" }));
        com_keuangan_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                com_keuangan_2ActionPerformed(evt);
            }
        });

        txt_bayar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bayar2KeyReleased(evt);
            }
        });

        txt_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bayarKeyReleased(evt);
            }
        });

        jLabel39.setText("Bayar");

        com_keuangan_1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BCA | 1", "MANDIRI | 2", "BRI | 3", "CAT73 | 4", "TOKO CAT73 | 5" }));
        com_keuangan_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                com_keuangan_1ActionPerformed(evt);
            }
        });

        jLabel38.setText("Keuangan");

        jLabel31.setText("Belum Bayar");

        txt_blmBayar.setEditable(false);
        txt_blmBayar.setEnabled(false);
        txt_blmBayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_blmBayarMouseClicked(evt);
            }
        });

        jLabel42.setText("Keterangan");

        cal_tglBG.setDateFormatString(" yyyy - M - d");

        txt_potongan.setBackground(new java.awt.Color(240, 240, 240));
        txt_potongan.setEnabled(false);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel30))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel42)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel40)
                                    .addComponent(jLabel41)
                                    .addComponent(jLabel39))
                                .addGap(103, 103, 103)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(com_jenisBG, 0, 210, Short.MAX_VALUE)
                                    .addComponent(txt_blmBayar, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                    .addComponent(txt_bayar, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                    .addComponent(txt_ket, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                    .addComponent(txt_bayar2, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                    .addComponent(com_keuangan_1, 0, 210, Short.MAX_VALUE)
                                    .addComponent(txt_noSeriBG, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                    .addComponent(com_keuangan_2, 0, 210, Short.MAX_VALUE)
                                    .addComponent(cal_tglBG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_potongan, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addGap(114, 114, 114)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(btn_bayar)
                                        .addGap(41, 41, 41)
                                        .addComponent(btn_close))
                                    .addComponent(txt_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel30)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_blmBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(com_jenisBG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_noSeriBG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel36))
                    .addComponent(cal_tglBG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(txt_potongan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(com_keuangan_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addGap(12, 12, 12)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(com_keuangan_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_bayar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_ket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addGap(26, 26, 26)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_bayar)
                    .addComponent(btn_close))
                .addContainerGap(96, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(453, 584));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_blmBayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_blmBayarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_blmBayarMouseClicked

    private void btn_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bayarActionPerformed
        bayar();
    }//GEN-LAST:event_btn_bayarActionPerformed

    private void btn_closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_closeMouseClicked
        dispose();
    }//GEN-LAST:event_btn_closeMouseClicked

    private void txt_kembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_kembaliMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kembaliMouseClicked

    private void txt_bayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyReleased
        autoSum();
    }//GEN-LAST:event_txt_bayarKeyReleased

    private void txt_bayar2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayar2KeyReleased
        autoSum();
    }//GEN-LAST:event_txt_bayar2KeyReleased

    private void com_jenisBGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_com_jenisBGActionPerformed
        if (com_jenisBG.getSelectedItem() == "No BG") {
            txt_noSeriBG.setEnabled(false);
            txt_noSeriBG.setText("");
            cal_tglBG.setEnabled(false);
        } else {
            txt_noSeriBG.setEnabled(true);
            cal_tglBG.setEnabled(true);
        }
    }//GEN-LAST:event_com_jenisBGActionPerformed

    private void com_keuangan_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_com_keuangan_1ActionPerformed
        bank();
    }//GEN-LAST:event_com_keuangan_1ActionPerformed

    private void com_keuangan_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_com_keuangan_2ActionPerformed
        bank();
    }//GEN-LAST:event_com_keuangan_2ActionPerformed

    /* @param args the command line arguments
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
            java.util.logging.Logger.getLogger(Pembelian_Hutang_RincianBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Hutang_RincianBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Hutang_RincianBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembelian_Hutang_RincianBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pembelian_Hutang_RincianBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_bayar;
    private javax.swing.JButton btn_close;
    private com.toedter.calendar.JDateChooser cal_tglBG;
    private javax.swing.JComboBox<String> com_jenisBG;
    private javax.swing.JComboBox<String> com_keuangan_1;
    private javax.swing.JComboBox<String> com_keuangan_2;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txt_bayar;
    private javax.swing.JTextField txt_bayar2;
    private javax.swing.JTextField txt_blmBayar;
    private javax.swing.JTextField txt_kembali;
    private javax.swing.JTextField txt_ket;
    private javax.swing.JTextField txt_noSeriBG;
    private javax.swing.JTextField txt_potongan;
    // End of variables declaration//GEN-END:variables
}
