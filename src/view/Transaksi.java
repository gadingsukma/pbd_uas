/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Conn;

/**
 *
 * @author L
 */
public class Transaksi extends javax.swing.JFrame {

    Connection connect = null;
    Statement s = null;
    private int DataRow;
    private PreparedStatement p;
    DefaultTableModel dm;
    CallableStatement c = null;
    ResultSet rs = null;

    DefaultTableModel tblPelanggan = new DefaultTableModel(new Object[]{"ID Pelanggan", "ID Jenis Member", "Nama", "Alamat", "Telepon", "Status", "Poin"}, 0);
    DefaultTableModel tblTransaksi = new DefaultTableModel(new Object[]{"ID Transaksi", "ID Pelanggan", "ID Jenis", "ID Diskon", "Tanggal Masuk", "Tanggal Keluar", "Jenis", "Berat (kg)", "Total", "Tipe"}, 0);

    /**
     * Creates new form Transaksi
     */
    public Transaksi() {
        initComponents();

        this.setLocationRelativeTo(null);

        btnSimpanTransaksi.setBackground(Color.WHITE);

        Conn conn = new Conn();
        conn.setConnections();
        connect = conn.getconnections();

        comboBoxTransaksi();
//        comboBoxTransaksiNama();
        tampilTableTransaksi();
        tampilTablePelanggan();
        tampilIdtrans();
    }

    void comboBoxTransaksi() {
        try {
            String sql = "select NAMA_JENIS from JENIS order by 1";
            s = connect.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                cbJenisCuciTransaksi.addItem(rs.getString("NAMA_JENIS"));
            }
            s.close();

        } catch (SQLException se) {
            System.out.println("Koneksi database gagal " + se);
            System.exit(0);
        }
    }

//    void comboBoxTransaksiNama() {
//        try {
//            String sql = "select NAMA from PELANGGAN order by 1";
//            s = connect.createStatement();
//            rs = s.executeQuery(sql);
//            while (rs.next()) {
//                cbNamaTransaksi.addItem(rs.getString("NAMA"));
//            }
//
//        } catch (SQLException se) {
//            System.out.println("Koneksi database gagal " + se);
//            System.exit(0);
//        }
//    }
    void tampilTablePelanggan() {
        try {
            String sql = "select * from PELANGGAN order by NAMA";
            p = connect.prepareStatement(sql);
            rs = p.executeQuery();

            tblPelanggan.setRowCount(0);

            String idPelanggan, idJenisMember, nama, alamat, telepon, status, poin;

            while (rs.next()) {
                idPelanggan = rs.getString("ID_PELANGGAN");
                idJenisMember = rs.getString("ID_JENIS_MEMBER");
                nama = rs.getString("NAMA");
                alamat = rs.getString("ALAMAT");
                telepon = rs.getString("TELEPON");
                status = rs.getString("STATUS");
                poin = rs.getString("POIN");

                tblPelanggan.addRow(new Object[]{idPelanggan, idJenisMember, nama, alamat, telepon, status, poin});
            }
            p.close();
            tablePelanggan.setModel(tblPelanggan);
        } catch (SQLException se) {
            System.out.println(se);
        }
    }

    void filterAngka(KeyEvent a) {
        if (Character.isAlphabetic(a.getKeyChar())) {
            a.consume();
            JOptionPane.showMessageDialog(null, "Hanya boleh memasukan angka");
        }
    }

    void tampilIdtrans() {
        String rnd = "TR" + String.valueOf((new Date()).getTime());
        tf_idtrans.setText(rnd);
    }

    void tampilTableTransaksi() {
        try {
            String sql = "select * from TRANSAKSI order by ID_TRANSAKSI";
            p = connect.prepareStatement(sql);
            rs = p.executeQuery();

            tblTransaksi.setRowCount(0);

            String idTransaksi, idPelanggan, idJenis, idDiskon, tglMasuk, tglKeluar, jenis, berat, total, tipe;

            while (rs.next()) {
                idTransaksi = rs.getString("ID_TRANSAKSI");
                idPelanggan = rs.getString("ID_PELANGGAN");
                idJenis = rs.getString("ID_JENIS");
                idDiskon = rs.getString("ID_DISKON");
                tglMasuk = rs.getString("TANGGAL_MASUK");
                tglKeluar = rs.getString("TANGGAL_KELUAR");
                jenis = rs.getString("JENIS");
                berat = rs.getString("BERAT");
                total = rs.getString("TOTAL");
                tipe = rs.getString("TIPE");

                tblTransaksi.addRow(new Object[]{idTransaksi, idPelanggan, idJenis, idDiskon, tglMasuk, tglKeluar, jenis, berat, total, tipe});
            }
            p.close();
            tf_id_pel.setText(null);
            tf_diskon.setText(null);
            tf_jns_member.setText(null);
            tf_ttl_bayar.setText(null);
            tf_ttl_harga.setText(null);
            tf_uang.setText(null);
            tf_kembalian.setText(null);
            cbJenisCuciTransaksi.setSelectedIndex(0);
            spinnerBeratTransaksi.setValue(0);
            cb_diskon.removeAllItems();
            cbTipeTransaksi.setSelectedIndex(0);
            dcTanggalTransaksiMasuk.setDate(null);
            dcTanggalTransaksiKeluar.setDate(null);
            tableTransaksi.setModel(tblTransaksi);
        } catch (SQLException se) {
            System.out.println(se);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbJenisCuciTransaksi = new javax.swing.JComboBox();
        spinnerBeratTransaksi = new javax.swing.JSpinner();
        btnSimpanTransaksi = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTransaksi = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        cbTipeTransaksi = new javax.swing.JComboBox();
        jLabel35 = new javax.swing.JLabel();
        tf_idtrans = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePelanggan = new javax.swing.JTable();
        tf_cari_user = new javax.swing.JTextField();
        tf_search_trans = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tf_id_pel = new javax.swing.JTextField();
        cb_diskon = new javax.swing.JComboBox<String>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tf_jns_member = new javax.swing.JTextField();
        dcTanggalTransaksiKeluar = new com.toedter.calendar.JDateChooser();
        labeltgltransaksi = new javax.swing.JLabel();
        dcTanggalTransaksiMasuk = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnMenuTransaksi = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        tf_ttl_harga = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        tf_diskon = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tf_ttl_bayar = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        tf_uang = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        tf_kembalian = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("ID Pelanggan");

        jLabel3.setText("Jenis Cuci");

        jLabel4.setText("Berat");

        cbJenisCuciTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbJenisCuciTransaksiActionPerformed(evt);
            }
        });

        spinnerBeratTransaksi.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerBeratTransaksiStateChanged(evt);
            }
        });
        spinnerBeratTransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                spinnerBeratTransaksiKeyTyped(evt);
            }
        });

        btnSimpanTransaksi.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnSimpanTransaksi.setText("Buat Transaksi");
        btnSimpanTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanTransaksiActionPerformed(evt);
            }
        });

        tableTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTransaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableTransaksi);

        jLabel12.setText("Tipe");

        jLabel26.setText("Kg");

        cbTipeTransaksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Reguler", "Kilat" }));
        cbTipeTransaksi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTipeTransaksiItemStateChanged(evt);
            }
        });

        jLabel35.setText("ID Transaksi");

        tf_idtrans.setEditable(false);
        tf_idtrans.setBackground(new java.awt.Color(204, 204, 204));

        tablePelanggan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablePelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePelangganMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablePelanggan);

        tf_cari_user.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_cari_userKeyPressed(evt);
            }
        });

        tf_search_trans.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_search_transKeyPressed(evt);
            }
        });

        jLabel1.setText("Cari Data");

        jLabel5.setText("Cari Data");

        jLabel6.setText("Data Pelanggan");

        tf_id_pel.setEditable(false);
        tf_id_pel.setBackground(new java.awt.Color(204, 204, 204));

        cb_diskon.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_diskonItemStateChanged(evt);
            }
        });
        cb_diskon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_diskonActionPerformed(evt);
            }
        });

        jLabel7.setText("Diskon");

        jLabel8.setText("Data Transaksi");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel9.setText("Menu Transaksi");

        jLabel10.setText("Jenis Member");

        tf_jns_member.setEditable(false);
        tf_jns_member.setBackground(new java.awt.Color(204, 204, 204));

        dcTanggalTransaksiKeluar.setDateFormatString("dd/MM/yyyy");

        labeltgltransaksi.setText("Tanggal");

        dcTanggalTransaksiMasuk.setDateFormatString("dd/MM/yyyy");

        jLabel11.setText("Masuk");

        jLabel13.setText("Keluar");

        btnMenuTransaksi.setText("Menu");
        btnMenuTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuTransaksiActionPerformed(evt);
            }
        });

        jLabel14.setText("Total Harga");

        tf_ttl_harga.setEditable(false);
        tf_ttl_harga.setBackground(new java.awt.Color(204, 204, 204));

        jLabel15.setText("Diskon");

        tf_diskon.setEditable(false);
        tf_diskon.setBackground(new java.awt.Color(204, 204, 204));

        jLabel16.setText("Total Bayar");

        tf_ttl_bayar.setEditable(false);
        tf_ttl_bayar.setBackground(new java.awt.Color(204, 204, 204));

        jLabel18.setText("Uang");

        tf_uang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_uangKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_uangKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_uangKeyTyped(evt);
            }
        });

        jLabel19.setText("Kembalian");

        tf_kembalian.setEditable(false);
        tf_kembalian.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel35))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tf_id_pel)
                                            .addComponent(tf_idtrans, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel7))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(spinnerBeratTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel26))
                                                .addComponent(cbJenisCuciTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(cbTipeTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cb_diskon, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jLabel11)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(dcTanggalTransaksiMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jLabel13)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(dcTanggalTransaksiKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tf_jns_member)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel19))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tf_ttl_harga, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                    .addComponent(tf_diskon)
                                    .addComponent(tf_ttl_bayar)
                                    .addComponent(tf_uang)
                                    .addComponent(tf_kembalian, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(labeltgltransaksi))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(118, 118, 118)
                                .addComponent(btnSimpanTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(165, 165, 165)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_search_trans, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_cari_user, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addComponent(btnMenuTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(tf_idtrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(tf_ttl_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tf_id_pel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(tf_diskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tf_jns_member, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(tf_ttl_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbJenisCuciTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(tf_uang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinnerBeratTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel26)
                    .addComponent(jLabel19)
                    .addComponent(tf_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cb_diskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cbTipeTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(dcTanggalTransaksiMasuk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labeltgltransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dcTanggalTransaksiKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnSimpanTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tf_search_trans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnMenuTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_cari_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 74, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbJenisCuciTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbJenisCuciTransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbJenisCuciTransaksiActionPerformed

    private void spinnerBeratTransaksiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spinnerBeratTransaksiKeyTyped
        // TODO add your handling code here:
        filterAngka(evt);

    }//GEN-LAST:event_spinnerBeratTransaksiKeyTyped

    private void btnSimpanTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanTransaksiActionPerformed
        // TODO add your handling code here:
        String id_tran, jns_member, jns_cuci, diskon, tp, tgl_msk, tgl_klr;
        int id_pel, brt, ttl_byr, ttl_diskon;

        if (cbJenisCuciTransaksi.getSelectedItem().toString().equalsIgnoreCase("") && spinnerBeratTransaksi.getValue().toString().equalsIgnoreCase("0") && spinnerBeratTransaksi.getValue().toString().equalsIgnoreCase("") && cb_diskon.getSelectedItem().toString().equalsIgnoreCase("") && cbTipeTransaksi.getSelectedItem().toString().equalsIgnoreCase("") && dcTanggalTransaksiMasuk.getDate().toString().equalsIgnoreCase("") && dcTanggalTransaksiKeluar.getDate().toString().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Fields tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else if (cbJenisCuciTransaksi.getSelectedItem().toString().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Jenis cuci tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else if (spinnerBeratTransaksi.getValue().toString().equalsIgnoreCase("") || spinnerBeratTransaksi.getValue().toString().equalsIgnoreCase("0")) {
            JOptionPane.showMessageDialog(null, "Berat tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else if (cb_diskon.getSelectedItem().toString().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Diskon tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else if (cbTipeTransaksi.getSelectedItem().toString().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Tipe tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else if (dcTanggalTransaksiMasuk.getDate().toString().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Tanggal masuk tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else if (dcTanggalTransaksiKeluar.getDate().toString().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Tanggal keluar boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                //input
                id_tran = tf_idtrans.getText();
                id_pel = Integer.valueOf(tf_id_pel.getText());
                jns_member = tf_jns_member.getText();
                diskon = cb_diskon.getSelectedItem().toString();
                brt = Integer.valueOf(spinnerBeratTransaksi.getValue().toString());
                DateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");
                Date tgl1 = dcTanggalTransaksiMasuk.getDate();
                Date tgl2 = dcTanggalTransaksiKeluar.getDate();
                tgl_msk = dateFormat.format(tgl1);
                tgl_klr = dateFormat.format(tgl2);
                jns_cuci = cbJenisCuciTransaksi.getSelectedItem().toString();
                tp = cbTipeTransaksi.getSelectedItem().toString();
                ttl_diskon = Integer.parseInt(tf_diskon.getText());
                ttl_byr = Integer.parseInt(tf_ttl_bayar.getText());
                System.out.println(ttl_diskon);
                //procedure
                String sql = "call ADD_TRANSAKSI(?,?,?,?,?,?,?,?,?,?,?)";
                c = connect.prepareCall(sql);
                c.setString(1, id_tran);
                c.setInt(2, id_pel);
                c.setString(3, jns_cuci);
                c.setString(4, diskon);
                c.setString(5, tgl_msk);
                c.setString(6, tgl_klr);
                c.setString(7, jns_cuci);
                c.setInt(8, brt);
                c.setString(9, tp);
                c.setInt(10, ttl_diskon);
                c.setInt(11, ttl_byr);
                c.executeUpdate();
                JOptionPane.showMessageDialog(null, "Transaksi Berhasil Dibuat");

            } catch (SQLException e) {
                System.out.println(e);
            }

            tampilIdtrans();
            tampilTableTransaksi();
            tampilTablePelanggan();
        }
    }//GEN-LAST:event_btnSimpanTransaksiActionPerformed

    private void tableTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTransaksiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableTransaksiMouseClicked

    private void btnMenuTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuTransaksiActionPerformed
        // TODO add your handling code here:
        new Main().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnMenuTransaksiActionPerformed

    private void tablePelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePelangganMouseClicked
        // TODO add your handling code here:
        DataRow = tablePelanggan.getSelectedRow();
        tf_id_pel.setText(tablePelanggan.getValueAt(DataRow, 0).toString());
        tf_jns_member.setText(tablePelanggan.getValueAt(DataRow, 5).toString());
    }//GEN-LAST:event_tablePelangganMouseClicked

    private void cb_diskonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_diskonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_diskonActionPerformed

    private void tf_cari_userKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_cari_userKeyPressed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tablePelanggan.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        tablePelanggan.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(tf_cari_user.getText().trim().toUpperCase()));
    }//GEN-LAST:event_tf_cari_userKeyPressed

    private void tf_search_transKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_search_transKeyPressed
        // TODO add your handling code here
        DefaultTableModel model = (DefaultTableModel) tableTransaksi.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        tableTransaksi.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(tf_search_trans.getText().trim().toUpperCase()));
    }//GEN-LAST:event_tf_search_transKeyPressed

    private void spinnerBeratTransaksiStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerBeratTransaksiStateChanged
        // TODO add your handling code here:
        try {

//            connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "alvian2", "1234");
            //procedure
            String sql = "call CARI_MEMBER(?,?)";
            c = connect.prepareCall(sql);
            c.setString(1, tf_jns_member.getText());
            c.registerOutParameter(2, Types.INTEGER);
            c.execute();
            int id_jns_member = c.getInt(2);
            int brt = Integer.valueOf(spinnerBeratTransaksi.getValue().toString());

            //select statement
            sql = "SELECT * FROM DISKON WHERE ID_JENIS_MEMBER=? AND BERAT=? AND NAMA_DISKON !='TIDAK ADA'";
            p = connect.prepareStatement(sql);
            p.setInt(1, id_jns_member);
            p.setInt(2, brt);
            rs = p.executeQuery();
            cb_diskon.removeAllItems();
            cb_diskon.addItem("Tidak Ada");
            while (rs.next()) {
                cb_diskon.addItem(rs.getString("NAMA_DISKON"));
            }

            //set total harga field
            String getJenisCuci = cbJenisCuciTransaksi.getSelectedItem().toString();
            sql = "SELECT * FROM JENIS WHERE UPPER(NAMA_JENIS)=UPPER((?))";
            p = connect.prepareStatement(sql);
            p.setString(1, getJenisCuci);
            rs = p.executeQuery();
            rs.next();
            int ttl_harga = rs.getInt("HARGA") * brt;
            tf_ttl_harga.setText(String.valueOf(ttl_harga));

            p.close();

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, se);
        }
    }//GEN-LAST:event_spinnerBeratTransaksiStateChanged

    private void cb_diskonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_diskonItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cb_diskonItemStateChanged

    private void cbTipeTransaksiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTipeTransaksiItemStateChanged
        // TODO add your handling code here:
        String sql;
        String getDiskon = "";
        try {
            if (cb_diskon.getSelectedItem() != null) {
                //set total harga field
                getDiskon = cb_diskon.getSelectedItem().toString();
                int ttl_harga = Integer.parseInt(tf_ttl_harga.getText());
                if (!getDiskon.equals(null) && !getDiskon.equals("") && !getDiskon.equalsIgnoreCase("Tidak Ada")) {
                    sql = "SELECT * FROM DISKON WHERE NAMA_DISKON=(?)";
                    p = connect.prepareStatement(sql);
                    p.setString(1, getDiskon);
                    rs = p.executeQuery();
                    rs.next();
                    double diskon = ttl_harga * (rs.getDouble("JUMLAH_DISKON") / 100);
                    tf_diskon.setText(String.valueOf((int) diskon));
                    double ttl_bayar = ttl_harga - diskon;
                    tf_ttl_bayar.setText(String.valueOf((int) ttl_bayar));
                } else if (!getDiskon.equals(null) && !getDiskon.equals("") && getDiskon.equalsIgnoreCase("Tidak Ada")) {
                    tf_diskon.setText(String.valueOf(0));
                    tf_ttl_bayar.setText(String.valueOf(ttl_harga));
                }

            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_cbTipeTransaksiItemStateChanged

    private void tf_uangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_uangKeyPressed
        // TODO add your handling code here


    }//GEN-LAST:event_tf_uangKeyPressed

    private void tf_uangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_uangKeyTyped


    }//GEN-LAST:event_tf_uangKeyTyped

    private void tf_uangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_uangKeyReleased
        // TODO add your handling code here:
        String input = "";
        input = tf_uang.getText();
        if (!input.toString().equalsIgnoreCase("")) {
            int uang = Integer.parseInt(tf_uang.getText());
            int ttl_bayar = Integer.parseInt(tf_ttl_bayar.getText());
            if (uang >= ttl_bayar) {
                int res = uang - ttl_bayar;
                tf_kembalian.setText(String.valueOf(res));
            } else {
                tf_kembalian.setText(null);
            }
        }
    }//GEN-LAST:event_tf_uangKeyReleased

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMenuTransaksi;
    private javax.swing.JButton btnSimpanTransaksi;
    private javax.swing.JComboBox cbJenisCuciTransaksi;
    private javax.swing.JComboBox cbTipeTransaksi;
    private javax.swing.JComboBox<String> cb_diskon;
    private com.toedter.calendar.JDateChooser dcTanggalTransaksiKeluar;
    private com.toedter.calendar.JDateChooser dcTanggalTransaksiMasuk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labeltgltransaksi;
    private javax.swing.JSpinner spinnerBeratTransaksi;
    private javax.swing.JTable tablePelanggan;
    private javax.swing.JTable tableTransaksi;
    private javax.swing.JTextField tf_cari_user;
    private javax.swing.JTextField tf_diskon;
    private javax.swing.JTextField tf_id_pel;
    private javax.swing.JTextField tf_idtrans;
    private javax.swing.JTextField tf_jns_member;
    private javax.swing.JTextField tf_kembalian;
    private javax.swing.JTextField tf_search_trans;
    private javax.swing.JTextField tf_ttl_bayar;
    private javax.swing.JTextField tf_ttl_harga;
    private javax.swing.JTextField tf_uang;
    // End of variables declaration//GEN-END:variables
}
