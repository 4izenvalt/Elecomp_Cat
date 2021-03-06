/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Class.Koneksi;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public final class Penjualan_LaporanPenjualan extends javax.swing.JFrame {

    /**
     * Creates new form Penjualan_laporanpenjualan
     */
    public Penjualan_LaporanPenjualan() {
        initComponents();
        this.setVisible(true);
        kalender();
        dateChooser();
        loadTable();

    }

    public void dateChooser() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd : hh-mm-ss");
        Calendar cal = Calendar.getInstance();
        tglAwal.setDate(cal.getTime());
        tglAkhir.setDate(cal.getTime());

        System.out.println(sdf.format(tglAwal.getDate()));

        // txt_tglAwal.setText(tglAwal.setDate(cal.getTime().));     
    }

    public void kalender() {
        Thread p = new Thread() {
            @Override
            public void run() {
                for (;;) {
                    GregorianCalendar cal = new GregorianCalendar();
                    int hari = cal.get(Calendar.DAY_OF_MONTH);
                    int bulan = cal.get(Calendar.MONTH);
                    int tahun = cal.get(Calendar.YEAR);
                    int jam = cal.get(Calendar.HOUR);
                    int menit = cal.get(Calendar.MINUTE);
                    // int detik = cal.get(Calendar.SECOND);
                    lblWaktu.setText(hari + " - " + (bulan + 1) + " - " + tahun + " | " + jam + ":" + menit);

                }
            }
        };
        p.start();
    }

    public void loadTable() {
        removeRow();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        DefaultTableModel model = (DefaultTableModel) tblPenjualanLaporan.getModel();
        int i = 1;
        try {
            String sql = "select * from penjualan where tgl_penjualan BETWEEN '"
                    + sdf.format(tglAwal.getDate())
                    + " 00:00:01' and '"
                    + sdf.format(tglAkhir.getDate())
                    + " 23:59:00' AND no_faktur_penjualan like '"
                    + "%"
                    + txtCari.getText()
                    + "%'";
//            System.out.println("SQL " + sql);
            Connection conn = (Connection) Koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()) {
                model.addRow(new Object[]{
                    i++,
                    res.getString("no_faktur_penjualan"),
                    res.getString("tgl_penjualan"),
                    res.getString("kode_customer"),
                    "kota",
                    res.getString("kode_salesman"),
                    "kota",
                    "pemasukan",
                    "pengeluaran"
                //                    res.getString("discon_rp"),
                //                    res.getString("discon_2persen"),
                //                    res.getString("discon_2rp")
                });
            }
            res.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Eror = " + e);
        }
    }

    void removeRow() {
        DefaultTableModel model = (DefaultTableModel) tblPenjualanLaporan.getModel();
        int row = tblPenjualanLaporan.getRowCount();
        for (int i = 0; i < row; i++) {
            model.removeRow(0);
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

        jPanel23 = new javax.swing.JPanel();
        jLabel88 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblPenjualanLaporan = new javax.swing.JTable();
        jButton22 = new javax.swing.JButton();
        jLabel106 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        txtCari = new javax.swing.JTextField();
        jLabel181 = new javax.swing.JLabel();
        tglAwal = new com.toedter.calendar.JDateChooser();
        tglAkhir = new com.toedter.calendar.JDateChooser();
        jPanel24 = new javax.swing.JPanel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        lblWaktu = new javax.swing.JLabel();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray));

        jLabel88.setText("Tanggal");

        tblPenjualanLaporan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "No. Faktur", "Tanggal", "Customer", "Kota", "Salesman", "Kota", "Pemasukan", "Pengeluaran"
            }
        ));
        jScrollPane10.setViewportView(tblPenjualanLaporan);
        if (tblPenjualanLaporan.getColumnModel().getColumnCount() > 0) {
            tblPenjualanLaporan.getColumnModel().getColumn(0).setResizable(false);
            tblPenjualanLaporan.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblPenjualanLaporan.getColumnModel().getColumn(1).setResizable(false);
            tblPenjualanLaporan.getColumnModel().getColumn(3).setResizable(false);
            tblPenjualanLaporan.getColumnModel().getColumn(4).setResizable(false);
            tblPenjualanLaporan.getColumnModel().getColumn(5).setResizable(false);
            tblPenjualanLaporan.getColumnModel().getColumn(6).setResizable(false);
            tblPenjualanLaporan.getColumnModel().getColumn(7).setResizable(false);
            tblPenjualanLaporan.getColumnModel().getColumn(8).setResizable(false);
        }

        jButton22.setBackground(new java.awt.Color(71, 166, 227));
        jButton22.setText("Tampilkan");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jLabel106.setText("s.d.");

        jSeparator15.setForeground(new java.awt.Color(204, 204, 204));

        txtCari.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cari", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        jLabel181.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/005-refresh-3.png"))); // NOI18N
        jLabel181.setText("Refresh");

        tglAwal.setDateFormatString(" yyyy - M - d");

        tglAkhir.setDateFormatString(" yyyy - M - d");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator15)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel181)
                .addGap(27, 27, 27))
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel88)
                        .addGap(18, 18, 18)
                        .addComponent(tglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel106)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(jButton22))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 979, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel88)
                        .addComponent(jLabel106)
                        .addComponent(jButton22))
                    .addComponent(tglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tglAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel181))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray, null));
        jPanel24.setForeground(new java.awt.Color(51, 51, 51));

        jLabel103.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel103.setText("Laporan Penjualan");

        jLabel104.setText("Tanggal ");

        lblWaktu.setText("00-00-00");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel103)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel104)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWaktu)
                .addGap(119, 119, 119))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel104)
                            .addComponent(lblWaktu))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel103, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1041, 493));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        loadTable();
    }//GEN-LAST:event_jButton22ActionPerformed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        loadTable();
    }//GEN-LAST:event_txtCariKeyReleased

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
            java.util.logging.Logger.getLogger(Penjualan_LaporanPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan_LaporanPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan_LaporanPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan_LaporanPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Penjualan_LaporanPenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton22;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JLabel lblWaktu;
    private javax.swing.JTable tblPenjualanLaporan;
    private com.toedter.calendar.JDateChooser tglAkhir;
    private com.toedter.calendar.JDateChooser tglAwal;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}
