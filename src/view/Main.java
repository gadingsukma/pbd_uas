/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Conn;

/**
 *
 * @author L
 */
public class Main extends javax.swing.JFrame {

    Connection connect = null;
    Statement s = null;
    private int DataRow;
    private PreparedStatement p;
    CallableStatement c = null;
    ResultSet rs = null;

    DefaultTableModel tblTransaksi = new DefaultTableModel(new Object[]{"ID Transaksi", "ID Pelanggan", "ID Jenis", "ID Diskon", "Tanggal Masuk", "Tanggal Keluar", "Jenis", "Berat (kg)", "Total", "Tipe"}, 0);
    DefaultTableModel tblPelanggan = new DefaultTableModel(new Object[]{"ID Pelanggan", "Nama", "Alamat", "Telepon", "Status"}, 0);
    DefaultTableModel tblJenis = new DefaultTableModel(new Object[]{"ID Jenis", "Nama Jenis", "Berat (kg)", "Harga"}, 0);
    DefaultTableModel tblMember = new DefaultTableModel(new Object[]{"ID Jenis Member", "Nama"}, 0);
    DefaultTableModel tblDiskon = new DefaultTableModel(new Object[]{"ID Diskon", "Nama Diskon", "Jumlah Diskon (%)"}, 0);
    DefaultTableModel tblMasalah = new DefaultTableModel(new Object[]{"ID Pelanggan", "ID Masalah", "Nama Barang", "Harga", "Deskripsi"}, 0);

    JDateChooser dateChooser = new JDateChooser();

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();

        this.setLocationRelativeTo(null);

        btnSimpanTransaksi.setBackground(Color.WHITE);
        btnSimpanPelanggan.setBackground(Color.WHITE);
        btnSimpanJenis.setBackground(Color.WHITE);
        btnCetakLaporanTransaksi.setBackground(Color.WHITE);
        btnSimpanMember.setBackground(Color.WHITE);
        btnSimpanDiskon.setBackground(Color.WHITE);
        btnSimpanMasalah.setBackground(Color.WHITE);
        btnCetakLaporanMasalah.setBackground(Color.WHITE);
        btnHapusTableTransaksi.setBackground(Color.RED);
        btnHapusTablePelanggan.setBackground(Color.RED);
        btnHapusTableJenis.setBackground(Color.RED);
        btnHapusTableMember.setBackground(Color.RED);
        btnHapusTableDiskon.setBackground(Color.RED);
        btnHapusTableMasalah.setBackground(Color.RED);

        Conn conn = new Conn();
        conn.setConnections();
        connect = conn.getconnections();

        comboBoxTransaksi();
        comboBoxStatusPelanggan();
//        comboBoxPelanggan();
        tampilTableTransaksi();
//        tampilTablePelanggan();
        tampilTableJenis();
        tampilTableJenisMember();
        tampilTableDiskon();
//        tampilTableMasalah();
        tampilIdJenis();
        tampilIdDiskon();
        tampilIdJenisMember();
        tampilIdPelanggan();
    }

    void comboBoxTransaksi() {
        try {
            String sql = "select NAMA_JENIS from JENIS order by 1";
            s = connect.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                cbJenisTransaksi.addItem(rs.getString("NAMA_JENIS"));
            }
            s.close();

        } catch (SQLException se) {
            System.out.println("Koneksi database gagal " + se);
            System.exit(0);
        }
    }

    void comboBoxStatusPelanggan() {
        try {
            String sql = "select NAMA from JENIS_MEMBER order by 1";
            s = connect.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                cbStatusPelanggan.addItem(rs.getString("NAMA"));
            }
            s.close();

        } catch (SQLException se) {
            System.out.println("Koneksi database gagal " + se);
            System.exit(0);
        }
    }

//    void comboBoxPelanggan(){
//        try {
//            String sql="select STATUS from PELANGGAN order by NAMA";
//            s=connect.createStatement();
//            rs=s.executeQuery(sql);
//            while(rs.next()){
//                cbJenisTransaksi.addItem(rs.getString("STATUS"));
//            }
// 
//        } catch (SQLException se) {
//            System.out.println("Koneksi database gagal " + se);
//            System.exit(0);
//        }
//    }
    void filterAngka(KeyEvent a) {
        if (Character.isAlphabetic(a.getKeyChar())) {
            a.consume();
            JOptionPane.showMessageDialog(null, "Hanya boleh memasukan angka");
        }
    }

    void tampilIdJenis() {
        try {
            String sql = "select max(id_jenis) as id from jenis";
            p = connect.prepareStatement(sql);
            ResultSet st = p.executeQuery();
            st.next();
            int dataid = st.getInt("id") + 1;
            tf_idjenis.setText(String.valueOf(dataid));
        } catch (Exception e) {
        }
    }

    void tampilIdJenisMember() {
        try {
            String sql = "select max(id_jenis_member) as id from jenis_member";
            p = connect.prepareStatement(sql);
            ResultSet st = p.executeQuery();
            st.next();
            int dataid = st.getInt("id") + 1;
            tf_jns_member.setText(String.valueOf(dataid));
        } catch (Exception e) {
        }
    }

    void tampilIdDiskon() {
        try {
            String sql = "select max(id_diskon) as id from diskon";
            p = connect.prepareStatement(sql);
            ResultSet st = p.executeQuery();
            st.next();
            int dataid = st.getInt("id") + 1;
            tf_id_diskon.setText(String.valueOf(dataid));
        } catch (Exception e) {
        }
    }
    
    void tampilIdPelanggan(){
         try {
            String sql = "select max(id_pelanggan) as id from pelanggan";
            p = connect.prepareStatement(sql);
            ResultSet st = p.executeQuery();
            st.next();
            int dataid = st.getInt("id") + 1;
           tf_id_pelanggan.setText(String.valueOf(dataid));
        } catch (Exception e) {
        }
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
            tableTransaksi.setModel(tblTransaksi);
        } catch (SQLException se) {
            System.out.println(se);
        }
    }

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

    void tampilTableJenis() {
        try {
            String sql = "select * from JENIS order by ID_JENIS";
            p = connect.prepareStatement(sql);
            rs = p.executeQuery();
            tblJenis.setRowCount(0);
            String idJenis, namaJenis, berat, harga;
            while (rs.next()) {
                idJenis = rs.getString("ID_JENIS");
                namaJenis = rs.getString("NAMA_JENIS");
                berat = rs.getString("BERAT");
                harga = rs.getString("HARGA");
                tblJenis.addRow(new Object[]{idJenis, namaJenis, berat, harga});
            }
            tableJenis.setModel(tblJenis);
            p.close();
            tfNamaJenis.setText(null);
            tfHargaJenis.setText(null);
            spinnerBeratJenis.setValue(0);
        } catch (SQLException se) {
            System.out.println(se);
        }
    }

    void tampilTableJenisMember() {
        try {
            String sql = "select * from JENIS_MEMBER order by NAMA";
            p = connect.prepareStatement(sql);
            rs = p.executeQuery();

            tblMember.setRowCount(0);

            String idJenisMember, nama;

            while (rs.next()) {
                idJenisMember = rs.getString("ID_JENIS_MEMBER");
                nama = rs.getString("NAMA");

                tblMember.addRow(new Object[]{idJenisMember, nama});
            }
            p.close();
            tableMember.setModel(tblMember);
            tfNamaJenisMember.setText(null);
        } catch (SQLException se) {
            System.out.println(se);
        }
    }

    void tampilTableDiskon() {
        try {
            String sql = "select * from DISKON order by ID_DISKON";
            p = connect.prepareStatement(sql);
            rs = p.executeQuery();

            tblDiskon.setRowCount(0);

            String idDiskon, namaDiskon, jumlahDiskon;

            while (rs.next()) {
                idDiskon = rs.getString("ID_DISKON");
                namaDiskon = rs.getString("NAMA_DISKON");
                jumlahDiskon = rs.getString("JUMLAH_DISKON");

                tblDiskon.addRow(new Object[]{idDiskon, namaDiskon, jumlahDiskon});
            }
            tableDiskon.setModel(tblDiskon);
            tfNamaDiskon.setText(null);
            spinnerJumlahDiskon.setValue(0);
        } catch (SQLException se) {
            System.out.println(se);
        }
    }

    void tampilTableMasalah() {
        try {
            String sql = "select * from MASALAH order by NAMA_BARANG";
            p = connect.prepareStatement(sql);
            rs = p.executeQuery();

            tblJenis.setRowCount(0);

            String idMasalah, idPelanggan, namaBarang, harga, deskripsi;

            while (rs.next()) {
                idMasalah = rs.getString("ID_MASALAH");
                idPelanggan = rs.getString("ID_PELANGGAN");
                namaBarang = rs.getString("NAMA_BARANG");
                harga = rs.getString("HARGA");
                deskripsi = rs.getString("DESKRIPSI");

                tblJenis.addRow(new Object[]{idMasalah, idPelanggan, namaBarang, harga, deskripsi});
            }

            tableMasalah.setModel(tblMasalah);
        } catch (SQLException se) {
            System.out.println(se);
        }
    }

    void hapusTableTransaksi() {
        int selected = tableTransaksi.getSelectedRow();
        String row = tableTransaksi.getModel().getValueAt(selected, 0).toString();

        String sql = "delete from TRANSAKSI where ID_TRANSAKSI=" + row;
        try {
            p = connect.prepareStatement(sql);
            p.execute();
            JOptionPane.showMessageDialog(null, "Data Terhapus");
        } catch (SQLException se) {
            System.out.println(se);;
        }
    }

    void hapusTablePelanggan() {
        int selected = tablePelanggan.getSelectedRow();
        String row = tablePelanggan.getModel().getValueAt(selected, 0).toString();

        String sql = "delete from PELANGGAN where ID_PELANGGAN=" + row;
        try {
            p = connect.prepareStatement(sql);
            p.execute();
            JOptionPane.showMessageDialog(null, "Data Terhapus");
        } catch (SQLException se) {
            System.out.println(se);;
        }
    }

    void hapusTableJenis() {
        int selected = tableJenis.getSelectedRow();
        String row = tableJenis.getModel().getValueAt(selected, 0).toString();

        String sql = "delete from JENIS where ID_JENIS=" + row;
        try {
            p = connect.prepareStatement(sql);
            p.execute();
            JOptionPane.showMessageDialog(null, "Data Terhapus");
        } catch (SQLException se) {
            System.out.println(se);;
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

        tabTransaksi = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfNamaTransaksi = new javax.swing.JTextField();
        cbJenisTransaksi = new javax.swing.JComboBox();
        spinnerBeratTransaksi = new javax.swing.JSpinner();
        btnSimpanTransaksi = new javax.swing.JButton();
        btnHapusFieldsTransaksi = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTransaksi = new javax.swing.JTable();
        btnHapusTableTransaksi = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        cbTipeTransaksi = new javax.swing.JComboBox();
        jPanel10 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tfNamaPelanggan = new javax.swing.JTextField();
        tfAlamatPelanggan = new javax.swing.JTextField();
        tfTeleponPelanggan = new javax.swing.JTextField();
        cbStatusPelanggan = new javax.swing.JComboBox();
        btnHapusFieldsPelanggan = new javax.swing.JButton();
        btnSimpanPelanggan = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePelanggan = new javax.swing.JTable();
        btnHapusTablePelanggan = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        tf_id_pelanggan = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tfNamaJenis = new javax.swing.JTextField();
        spinnerBeratJenis = new javax.swing.JSpinner();
        tfHargaJenis = new javax.swing.JTextField();
        btnHapusFieldsJenis = new javax.swing.JButton();
        btnSimpanJenis = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableJenis = new javax.swing.JTable();
        btnHapusTableJenis = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        tf_idjenis = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        tfNamaJenisMember = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableMember = new javax.swing.JTable();
        btnHapusTableMember = new javax.swing.JButton();
        btnSimpanMember = new javax.swing.JButton();
        btnHapusFieldMember = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        tf_jns_member = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        tfNamaDiskon = new javax.swing.JTextField();
        btnSimpanDiskon = new javax.swing.JButton();
        btnHapusFieldsDiskon = new javax.swing.JButton();
        spinnerJumlahDiskon = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableDiskon = new javax.swing.JTable();
        btnHapusTableDiskon = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        tf_id_diskon = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        tfNamaBarangMasalah = new javax.swing.JTextField();
        tfHargaMasalah = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        taDeskripsiMasalah = new javax.swing.JTextArea();
        btnSimpanMasalah = new javax.swing.JButton();
        btnHapusFieldsMasalah = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableMasalah = new javax.swing.JTable();
        btnHapusTableMasalah = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        DateChooserTanggalCetakAwalLaporanTransaksi = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        DateChooserTanggalCetakAkhirLaporanTransaksi = new com.toedter.calendar.JDateChooser();
        btnCetakLaporanTransaksi = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        tfHargaAwalLaporanMasalah = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        tfHargaAkhirLaporanMasalah = new javax.swing.JTextField();
        btnCetakLaporanMasalah = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabTransaksi.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabTransaksiStateChanged(evt);
            }
        });
        tabTransaksi.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tabTransaksiPropertyChange(evt);
            }
        });

        jLabel2.setText("Nama");

        jLabel3.setText("Jenis");

        jLabel4.setText("Berat");

        cbJenisTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbJenisTransaksiActionPerformed(evt);
            }
        });

        spinnerBeratTransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                spinnerBeratTransaksiKeyTyped(evt);
            }
        });

        btnSimpanTransaksi.setText("Simpan");
        btnSimpanTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanTransaksiActionPerformed(evt);
            }
        });

        btnHapusFieldsTransaksi.setText("Hapus");
        btnHapusFieldsTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusFieldsTransaksiActionPerformed(evt);
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

        btnHapusTableTransaksi.setText("Hapus");
        btnHapusTableTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusTableTransaksiActionPerformed(evt);
            }
        });

        jLabel12.setText("Tipe");

        jLabel26.setText("Kg");

        cbTipeTransaksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Reguler", "Kilat" }));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel12))
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfNamaTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbTipeTransaksi, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(spinnerBeratTransaksi, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbJenisTransaksi, javax.swing.GroupLayout.Alignment.LEADING, 0, 98, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel26)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnHapusFieldsTransaksi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSimpanTransaksi)
                        .addGap(54, 54, 54)))
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusTableTransaksi, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfNamaTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbJenisTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(spinnerBeratTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cbTipeTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpanTransaksi)
                    .addComponent(btnHapusFieldsTransaksi))
                .addContainerGap(130, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnHapusTableTransaksi)
                .addContainerGap())
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabTransaksi.addTab("Transaksi", jPanel1);

        jLabel1.setText("Nama");

        jLabel6.setText("Alamat");

        jLabel7.setText("Telepon");

        jLabel8.setText("Status");

        tfTeleponPelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfTeleponPelangganKeyTyped(evt);
            }
        });

        btnHapusFieldsPelanggan.setText("Hapus");
        btnHapusFieldsPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusFieldsPelangganActionPerformed(evt);
            }
        });

        btnSimpanPelanggan.setText("Simpan");
        btnSimpanPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanPelangganActionPerformed(evt);
            }
        });

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
        jScrollPane2.setViewportView(tablePelanggan);

        btnHapusTablePelanggan.setText("Hapus");
        btnHapusTablePelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusTablePelangganActionPerformed(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        tf_id_pelanggan.setEditable(false);
        tf_id_pelanggan.setBackground(new java.awt.Color(204, 204, 204));

        jLabel30.setText("ID Pelanggan");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel30))
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbStatusPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(btnHapusFieldsPelanggan)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSimpanPelanggan))
                        .addComponent(tfAlamatPelanggan, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tfTeleponPelanggan, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tfNamaPelanggan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tf_id_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusTablePelanggan, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnHapusTablePelanggan)
                .addContainerGap())
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_id_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tfAlamatPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tfTeleponPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbStatusPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapusFieldsPelanggan)
                    .addComponent(btnSimpanPelanggan))
                .addGap(62, 62, 62))
        );

        tabTransaksi.addTab("Pelanggan", jPanel2);

        jLabel9.setText("Nama Jenis");

        jLabel10.setText("Berat");

        jLabel11.setText("Harga");

        tfNamaJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNamaJenisActionPerformed(evt);
            }
        });

        spinnerBeratJenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                spinnerBeratJenisKeyTyped(evt);
            }
        });

        tfHargaJenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfHargaJenisKeyTyped(evt);
            }
        });

        btnHapusFieldsJenis.setText("Hapus");
        btnHapusFieldsJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusFieldsJenisActionPerformed(evt);
            }
        });

        btnSimpanJenis.setText("Simpan");
        btnSimpanJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanJenisActionPerformed(evt);
            }
        });

        tableJenis.setModel(new javax.swing.table.DefaultTableModel(
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
        tableJenis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableJenisMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableJenis);

        btnHapusTableJenis.setText("Hapus");
        btnHapusTableJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusTableJenisActionPerformed(evt);
            }
        });

        jLabel25.setText("Kg");

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        tf_idjenis.setEditable(false);
        tf_idjenis.setBackground(new java.awt.Color(204, 204, 204));

        jLabel27.setText("ID JENIS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel27))
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(btnHapusFieldsJenis)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSimpanJenis))
                        .addComponent(tfNamaJenis)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(spinnerBeratJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel25))
                        .addComponent(tfHargaJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tf_idjenis, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusTableJenis, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnHapusTableJenis)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_idjenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(tfNamaJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(spinnerBeratJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(tfHargaJenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapusFieldsJenis)
                    .addComponent(btnSimpanJenis))
                .addGap(0, 128, Short.MAX_VALUE))
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabTransaksi.addTab("Jenis", jPanel3);

        jLabel5.setText("Nama Jenis Member");

        tableMember.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tableMember);

        btnHapusTableMember.setText("Hapus");

        btnSimpanMember.setText("Simpan");
        btnSimpanMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanMemberActionPerformed(evt);
            }
        });

        btnHapusFieldMember.setText("Hapus");
        btnHapusFieldMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusFieldMemberActionPerformed(evt);
            }
        });

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel29.setText("ID Jenis Member");

        tf_jns_member.setEditable(false);
        tf_jns_member.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(18, 18, 18)
                            .addComponent(tfNamaJenisMember, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(btnHapusFieldMember)
                            .addGap(76, 76, 76)
                            .addComponent(btnSimpanMember)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tf_jns_member, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusTableMember, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnHapusTableMember)
                .addContainerGap())
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(tf_jns_member, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tfNamaJenisMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpanMember)
                    .addComponent(btnHapusFieldMember))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabTransaksi.addTab("Member", jPanel8);

        jLabel15.setText("Nama Diskon");

        jLabel16.setText("Jumlah Diskon");

        btnSimpanDiskon.setText("Simpan");
        btnSimpanDiskon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanDiskonActionPerformed(evt);
            }
        });

        btnHapusFieldsDiskon.setText("Hapus");
        btnHapusFieldsDiskon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusFieldsDiskonActionPerformed(evt);
            }
        });

        jLabel17.setText("%");

        tableDiskon.setModel(new javax.swing.table.DefaultTableModel(
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
        tableDiskon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableDiskonMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableDiskon);

        btnHapusTableDiskon.setText("Hapus");
        btnHapusTableDiskon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusTableDiskonActionPerformed(evt);
            }
        });

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel28.setText("ID Diskon");

        tf_id_diskon.setEditable(false);
        tf_id_diskon.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(btnHapusFieldsDiskon)
                        .addGap(75, 75, 75)
                        .addComponent(btnSimpanDiskon))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel28))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(spinnerJumlahDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17))
                            .addComponent(tfNamaDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf_id_diskon, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusTableDiskon, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnHapusTableDiskon)
                .addContainerGap())
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(tf_id_diskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(tfNamaDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(spinnerJumlahDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(41, 41, 41)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpanDiskon)
                    .addComponent(btnHapusFieldsDiskon))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabTransaksi.addTab("Diskon", jPanel5);

        jLabel18.setText("Nama Barang");

        jLabel19.setText("Harga");

        jLabel20.setText("Deskripsi");

        taDeskripsiMasalah.setColumns(20);
        taDeskripsiMasalah.setRows(5);
        jScrollPane6.setViewportView(taDeskripsiMasalah);

        btnSimpanMasalah.setText("Simpan");

        btnHapusFieldsMasalah.setText("Hapus");
        btnHapusFieldsMasalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusFieldsMasalahActionPerformed(evt);
            }
        });

        tableMasalah.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(tableMasalah);

        btnHapusTableMasalah.setText("Hapus");

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfNamaBarangMasalah)
                            .addComponent(tfHargaMasalah)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnHapusFieldsMasalah)
                        .addGap(71, 71, 71)
                        .addComponent(btnSimpanMasalah)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusTableMasalah, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(tfNamaBarangMasalah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(tfHargaMasalah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel20))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSimpanMasalah)
                            .addComponent(btnHapusFieldsMasalah))
                        .addGap(0, 118, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapusTableMasalah)))
                .addContainerGap())
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabTransaksi.addTab("Masalah", jPanel9);

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jLabel13.setText("Tentukan tanggal");

        jLabel14.setText("Sampai");

        btnCetakLaporanTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/exportResize.png"))); // NOI18N
        btnCetakLaporanTransaksi.setText("Cetak");
        btnCetakLaporanTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakLaporanTransaksiActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel23.setText("Export Laporan Transaksi");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(DateChooserTanggalCetakAwalLaporanTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DateChooserTanggalCetakAkhirLaporanTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel23)
                    .addComponent(btnCetakLaporanTransaksi))
                .addContainerGap(580, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DateChooserTanggalCetakAwalLaporanTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DateChooserTanggalCetakAkhirLaporanTransaksi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addComponent(btnCetakLaporanTransaksi)
                .addContainerGap(237, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Transaksi", jPanel6);

        jLabel21.setText("Tentukan Harga");

        jLabel22.setText("Sampai");

        btnCetakLaporanMasalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/exportResize.png"))); // NOI18N
        btnCetakLaporanMasalah.setText("Cetak");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel24.setText("Export Laporan Masalah");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(tfHargaAwalLaporanMasalah, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfHargaAkhirLaporanMasalah, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel24)
                    .addComponent(btnCetakLaporanMasalah))
                .addContainerGap(720, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfHargaAwalLaporanMasalah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(tfHargaAkhirLaporanMasalah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCetakLaporanMasalah)
                .addContainerGap(237, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Masalah", jPanel7);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tabTransaksi.addTab("Laporan", jPanel4);

        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/log-outResize.png"))); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout)
                .addGap(0, 14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tabTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnLogout)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbJenisTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbJenisTransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbJenisTransaksiActionPerformed

    private void btnHapusFieldsTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusFieldsTransaksiActionPerformed
        // TODO add your handling code here:
        tfNamaTransaksi.setText(null);
        spinnerBeratTransaksi.setValue(0);
    }//GEN-LAST:event_btnHapusFieldsTransaksiActionPerformed

    private void btnHapusFieldsPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusFieldsPelangganActionPerformed
        // TODO add your handling code here:
        tfNamaPelanggan.setText(null);
        tfAlamatPelanggan.setText(null);
        tfTeleponPelanggan.setText(null);
    }//GEN-LAST:event_btnHapusFieldsPelangganActionPerformed

    private void btnHapusFieldsJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusFieldsJenisActionPerformed
        // TODO add your handling code here:

        String id_jns = tf_idjenis.getText();
        String sql;
        int result = JOptionPane.showConfirmDialog(null, "Apa anda yakin menghapus data dengan id " + id_jns + " ini ?", null, JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                sql = "{call HAPUS_JENIS(?,?)}";
                c = connect.prepareCall(sql);
                c.setString(1, id_jns);
                c.registerOutParameter(2, Types.INTEGER);
                c.executeUpdate();
                if (c.getInt(2) == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf data tidak ditemukan");
                } else {
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                }
                tampilTableJenis();
                tampilIdJenis();
            } catch (Exception e) {
            }
        }

    }//GEN-LAST:event_btnHapusFieldsJenisActionPerformed

    private void spinnerBeratJenisKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spinnerBeratJenisKeyTyped
        // TODO add your handling code here:
        filterAngka(evt);
    }//GEN-LAST:event_spinnerBeratJenisKeyTyped

    private void tfHargaJenisKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfHargaJenisKeyTyped
        // TODO add your handling code here:
        filterAngka(evt);
    }//GEN-LAST:event_tfHargaJenisKeyTyped

    private void tfTeleponPelangganKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfTeleponPelangganKeyTyped
        // TODO add your handling code here:
        filterAngka(evt);
    }//GEN-LAST:event_tfTeleponPelangganKeyTyped

    private void spinnerBeratTransaksiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spinnerBeratTransaksiKeyTyped
        // TODO add your handling code here:
        filterAngka(evt);
    }//GEN-LAST:event_spinnerBeratTransaksiKeyTyped

    private void btnHapusTableTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusTableTransaksiActionPerformed
        // TODO add your handling code here:
        hapusTableTransaksi();
        tampilTableTransaksi();
    }//GEN-LAST:event_btnHapusTableTransaksiActionPerformed

    private void btnHapusTablePelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusTablePelangganActionPerformed
        // TODO add your handling code here:
        hapusTablePelanggan();
        tampilTablePelanggan();
    }//GEN-LAST:event_btnHapusTablePelangganActionPerformed

    private void btnHapusTableJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusTableJenisActionPerformed
        // TODO add your handling code here:
        hapusTableJenis();
        tampilTableJenis();
    }//GEN-LAST:event_btnHapusTableJenisActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        new Login().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void tableTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTransaksiMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tableTransaksiMouseClicked

    private void btnHapusFieldMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusFieldMemberActionPerformed
        // TODO add your handling code here:
        String id_jns = tf_jns_member.getText();
        String sql;
        int result = JOptionPane.showConfirmDialog(null, "Apa anda yakin menghapus data dengan id " + id_jns + " ini ?", null, JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                sql = "{call HAPUS_JENIS_MEMBER(?,?)}";
                c = connect.prepareCall(sql);
                c.setString(1, id_jns);
                c.registerOutParameter(2, Types.INTEGER);
                c.executeUpdate();
                if (c.getInt(2) == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf data tidak ditemukan");
                } else {
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                }
                tampilTableJenisMember();
                tampilIdJenisMember();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_btnHapusFieldMemberActionPerformed

    private void btnHapusFieldsDiskonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusFieldsDiskonActionPerformed
        // TODO add your handling code here:
        String id_diskon = tf_id_diskon.getText();
        String sql;
        int result = JOptionPane.showConfirmDialog(null, "Apa anda yakin menghapus data dengan id " + id_diskon + " ini ?", null, JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                sql = "{call HAPUS_DISKON(?,?)}";
                c = connect.prepareCall(sql);
                c.setString(1, id_diskon);
                c.registerOutParameter(2, Types.INTEGER);
                c.executeUpdate();
                if (c.getInt(2) == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf data tidak ditemukan");
                } else {
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                }
                tampilTableDiskon();
                tampilIdDiskon();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_btnHapusFieldsDiskonActionPerformed

    private void btnHapusFieldsMasalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusFieldsMasalahActionPerformed
        // TODO add your handling code here:
        tfNamaBarangMasalah.setText(null);
        tfHargaMasalah.setText(null);
        taDeskripsiMasalah.setText(null);
    }//GEN-LAST:event_btnHapusFieldsMasalahActionPerformed

    private void btnSimpanTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanTransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanTransaksiActionPerformed

    private void btnCetakLaporanTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakLaporanTransaksiActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnCetakLaporanTransaksiActionPerformed

    private void tfNamaJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNamaJenisActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_tfNamaJenisActionPerformed

    private void btnSimpanJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanJenisActionPerformed
        // TODO add your handling code here:
        String nm_jenis = tfNamaJenis.getText();
        String id_jns = tf_idjenis.getText();
        Float brt_jenis = Float.parseFloat(spinnerBeratJenis.getValue().toString());
        Integer hrg = Integer.parseInt(tfHargaJenis.getText());
        String sql;
        try {
            sql = "{call DML_JENIS(?,?,?,?,?)}";
            c = connect.prepareCall(sql);
            c.setString(1, id_jns);
            c.setString(2, nm_jenis);
            c.setFloat(3, brt_jenis);
            c.setInt(4, hrg);
            c.registerOutParameter(5, Types.INTEGER);
            c.executeUpdate();
            if (c.getInt(5) == 0) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            } else {
                JOptionPane.showMessageDialog(null, "Data Berhasil di update");
            }
            tampilTableJenis();
            tampilIdJenis();
        } catch (Exception e) {
        }

    }//GEN-LAST:event_btnSimpanJenisActionPerformed

    private void tabTransaksiStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabTransaksiStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_tabTransaksiStateChanged

    private void tabTransaksiPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tabTransaksiPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_tabTransaksiPropertyChange

    private void tableJenisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableJenisMouseClicked
        // TODO add your handling code here:
        DataRow = tableJenis.getSelectedRow();
        tf_idjenis.setText(tableJenis.getValueAt(DataRow, 0).toString());
        tfNamaJenis.setText(tableJenis.getValueAt(DataRow, 1).toString());
        spinnerBeratJenis.setValue(Integer.valueOf(tableJenis.getValueAt(DataRow, 2).toString()));
        tfHargaJenis.setText(tableJenis.getValueAt(DataRow, 3).toString());
    }//GEN-LAST:event_tableJenisMouseClicked

    private void btnSimpanDiskonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanDiskonActionPerformed
        // TODO add your handling code here:
        String nm_diskon = tfNamaDiskon.getText();
        int id_diskon = Integer.valueOf(tf_id_diskon.getText());
        int jml_diskon = Integer.valueOf(spinnerJumlahDiskon.getValue().toString());

        String sql;
        try {
            sql = "{call DML_DISKON(?,?,?,?)}";
            c = connect.prepareCall(sql);
            c.setInt(1, id_diskon);
            c.setString(2, nm_diskon);
            c.setInt(3, jml_diskon);
            c.registerOutParameter(4, Types.INTEGER);
            c.executeUpdate();
            if (c.getInt(4) == 0) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            } else {
                JOptionPane.showMessageDialog(null, "Data Berhasil di update");
            }
            tampilTableDiskon();
            tampilIdDiskon();
        } catch (Exception e) {
        }

    }//GEN-LAST:event_btnSimpanDiskonActionPerformed

    private void btnHapusTableDiskonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusTableDiskonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusTableDiskonActionPerformed

    private void tableDiskonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDiskonMouseClicked
        // TODO add your handling code here:
        DataRow = tableDiskon.getSelectedRow();
        tf_id_diskon.setText(tableDiskon.getValueAt(DataRow, 0).toString());
        tfNamaDiskon.setText(tableDiskon.getValueAt(DataRow, 1).toString());
        spinnerJumlahDiskon.setValue(Integer.valueOf(tableDiskon.getValueAt(DataRow, 2).toString()));
    }//GEN-LAST:event_tableDiskonMouseClicked

    private void btnSimpanMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanMemberActionPerformed
        // TODO add your handling code here:
        String nm_jns_member = tfNamaJenisMember.getText();
        int id_diskon_member = Integer.valueOf(tf_jns_member.getText());
        String sql;
        try {
            sql = "{call DML_JNS_MEMBER(?,?,?)}";
            c = connect.prepareCall(sql);
            c.setInt(1, id_diskon_member);
            c.setString(2, nm_jns_member);
            c.registerOutParameter(3, Types.INTEGER);
            c.executeUpdate();
            if (c.getInt(3) == 0) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            } else {
                JOptionPane.showMessageDialog(null, "Data Berhasil di update");
            }
            tampilTableJenisMember();
            tampilIdJenisMember();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnSimpanMemberActionPerformed

    private void btnSimpanPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanPelangganActionPerformed
        // TODO add your handling code here:
        String nm_pelanggan = tfNamaPelanggan.getText();
        String id_pelanggan = tf_id_pelanggan.getText();
        String no_hp = tfTeleponPelanggan.getText();
        String alamat = tfAlamatPelanggan.getText();
        String status = cbStatusPelanggan.getSelectedItem().toString();
        String sql;
        try {
            sql = "{call DML_PELANGGAN(?,?,?,?,?,?)}";
            c = connect.prepareCall(sql);
            c.setString(1, id_pelanggan);
            c.setString(2, nm_pelanggan);
            c.setString(3, alamat);
            c.setString(4, no_hp);
            c.setString(5, status);
            c.registerOutParameter(6, Types.INTEGER);
            c.executeUpdate();
            if (c.getInt(6) == 0) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            } else {
                JOptionPane.showMessageDialog(null, "Data Berhasil di update");
            }
            tampilTablePelanggan();
            tampilIdPelanggan();
        } catch (Exception e) {
        }

    }//GEN-LAST:event_btnSimpanPelangganActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateChooserTanggalCetakAkhirLaporanTransaksi;
    private com.toedter.calendar.JDateChooser DateChooserTanggalCetakAwalLaporanTransaksi;
    private javax.swing.JButton btnCetakLaporanMasalah;
    private javax.swing.JButton btnCetakLaporanTransaksi;
    private javax.swing.JButton btnHapusFieldMember;
    private javax.swing.JButton btnHapusFieldsDiskon;
    private javax.swing.JButton btnHapusFieldsJenis;
    private javax.swing.JButton btnHapusFieldsMasalah;
    private javax.swing.JButton btnHapusFieldsPelanggan;
    private javax.swing.JButton btnHapusFieldsTransaksi;
    private javax.swing.JButton btnHapusTableDiskon;
    private javax.swing.JButton btnHapusTableJenis;
    private javax.swing.JButton btnHapusTableMasalah;
    private javax.swing.JButton btnHapusTableMember;
    private javax.swing.JButton btnHapusTablePelanggan;
    private javax.swing.JButton btnHapusTableTransaksi;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnSimpanDiskon;
    private javax.swing.JButton btnSimpanJenis;
    private javax.swing.JButton btnSimpanMasalah;
    private javax.swing.JButton btnSimpanMember;
    private javax.swing.JButton btnSimpanPelanggan;
    private javax.swing.JButton btnSimpanTransaksi;
    private javax.swing.JComboBox cbJenisTransaksi;
    private javax.swing.JComboBox cbStatusPelanggan;
    private javax.swing.JComboBox cbTipeTransaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JSpinner spinnerBeratJenis;
    private javax.swing.JSpinner spinnerBeratTransaksi;
    private javax.swing.JSpinner spinnerJumlahDiskon;
    private javax.swing.JTextArea taDeskripsiMasalah;
    private javax.swing.JTabbedPane tabTransaksi;
    private javax.swing.JTable tableDiskon;
    private javax.swing.JTable tableJenis;
    private javax.swing.JTable tableMasalah;
    private javax.swing.JTable tableMember;
    private javax.swing.JTable tablePelanggan;
    private javax.swing.JTable tableTransaksi;
    private javax.swing.JTextField tfAlamatPelanggan;
    private javax.swing.JTextField tfHargaAkhirLaporanMasalah;
    private javax.swing.JTextField tfHargaAwalLaporanMasalah;
    private javax.swing.JTextField tfHargaJenis;
    private javax.swing.JTextField tfHargaMasalah;
    private javax.swing.JTextField tfNamaBarangMasalah;
    private javax.swing.JTextField tfNamaDiskon;
    private javax.swing.JTextField tfNamaJenis;
    private javax.swing.JTextField tfNamaJenisMember;
    private javax.swing.JTextField tfNamaPelanggan;
    private javax.swing.JTextField tfNamaTransaksi;
    private javax.swing.JTextField tfTeleponPelanggan;
    private javax.swing.JTextField tf_id_diskon;
    private javax.swing.JTextField tf_id_pelanggan;
    private javax.swing.JTextField tf_idjenis;
    private javax.swing.JTextField tf_jns_member;
    // End of variables declaration//GEN-END:variables
}
