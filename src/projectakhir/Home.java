package projectakhir;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author ahlul
 */
public class Home extends javax.swing.JFrame {
    Connection con;
    Statement stm;
    ResultSet rs;
    PreparedStatement pst;
    
    private String[] args;
    private String idUp;
    private String idstok;
    private int cur;
    
    private String randomID(String[] args){
       Random r;
       String ID;
       int Angka;
               
       r = new Random();
       
       do{
          Angka = r.nextInt(100000); 
       }while ( Angka < 9999); 
       
       
       char Alpabet1 = (char) (r.nextInt(26)+'A');
       char Alpabet2 = (char) (r.nextInt(26)+'A');
       ID = String.valueOf(Alpabet1) + String.valueOf(Alpabet2) + "-" + String.valueOf(Angka);
      
       return ID;
    }
    
    
    public void clear(){
        namaProduk.setText("");
        stokAwal.setText("");
        satuan.setSelectedIndex(0);
        hargaJual.setText("");
        hargaBeli.setText("");
        IDProduk.setText("");
    }
    
    public void loadData(){
        DefaultTableModel model = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column)
        {
        return false;//This causes all cells to be not editable
        }
        };
        
        
        model.addColumn("ID");
        model.addColumn("NAME");
        model.addColumn("UNIT");
        model.addColumn("FIRST STOCK");
        model.addColumn("PURCHASE PRICE");
        model.addColumn("SELLING PRICE");
        
        try {
            String sql = "SELECT * FROM produk";
            
            java.sql.Statement stat = con.createStatement();
            ResultSet r = stat.executeQuery(sql);
            while (r.next()) {                
                Object[] o = new Object[6];
                o [0] = r.getString("id");
                o [1] = r.getString("nama_produk");
                o [2] = r.getString("satuan");
                o [3] = r.getString("stok_awal");
                o [4] = "Rp " + r.getString("harga_beli");
                o [5] = "Rp " + r.getString("harga_jual");
                model.addRow(o);
                tabelProduk.setModel(model);
            }
            System.out.println("kes");
        } catch (Exception e) {
                System.out.println("terjadi kesalahan");
                
        }
        
    }
    
    
    public void cari(){
        DefaultTableModel model = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column)
        {
        return false;//This causes all cells to be not editable
        }
        };
        
        
        model.addColumn("ID");
        model.addColumn("NAME");
        model.addColumn("UNIT");
        model.addColumn("FIRST STOCK");
        model.addColumn("PURCHASE PRICE");
        model.addColumn("SELLING PRICE");
        
        try {
            String sql = "Select * from produk where id like '%" + cariProduk.getText() + "%'" +"or nama_produk like '%" + cariProduk.getText() + "%'";
            java.sql.Statement stat = con.createStatement();
            ResultSet r = stat.executeQuery(sql);
            while (r.next()) {                
                Object[] o = new Object[6];
                o [0] = r.getString("id");
                o [1] = r.getString("nama_produk");
                o [2] = r.getString("satuan");
                o [3] = r.getString("stok_awal");
                o [4] = r.getString("harga_beli");
                o [5] = r.getString("harga_jual");
                model.addRow(o);
                tabelProduk.setModel(model);
            }
            System.out.println("kes");
        } catch (Exception e) {
            System.out.println("Cari Data Error");
        }finally{
        }
    }
    
    public void hapusProduk(){
        String nama = (String)namaProduk.getText();
        int konfirmasi = JOptionPane.showConfirmDialog(null,"Sure? You want to delete?", "Warning",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE); 
        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM produk WHERE id = '" + this.idUp + "'";
                pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Data Terhapus");
                String info = "Delete product data " + nama + " (" + idUp+ ")";
                String ket = "DELETE";
                history(info , ket);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal");
            }finally{
            clear();
            load();
        }
        }
    }
   
    public void loadDataStok(){
        DefaultTableModel model = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column)
        {
        return false;//This causes all cells to be not editable
        }
        };
        
        
        model.addColumn("ID");
        model.addColumn("NAME");
        model.addColumn("FIRST STOCK");
        model.addColumn("IN");
        model.addColumn("OUT");
        model.addColumn("CURRENT");
        
        try {
            String sql = "SELECT * FROM produk";
            
            java.sql.Statement stat = con.createStatement();
            ResultSet r = stat.executeQuery(sql);
            
            while (r.next()) {                
                Object[] o = new Object[6];
                int current = r.getInt("stok_awal") + r.getInt("masuk") - r.getInt("keluar");
                o [0] = r.getString("id");
                o [1] = r.getString("nama_produk");
                o [2] = r.getString("stok_awal");
                o [3] = r.getString("masuk");
                o [4] = r.getString("keluar");
                o [5] = current;
                
                model.addRow(o);
                tabelStok.setModel(model);
            }
            System.out.println("kes");
        } catch (Exception e) {
                System.out.println("terjadi kesalahan");
                
        }
        
    }
    
    public void loadDatalive(){
        DefaultTableModel model = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column)
        {
        return false;//This causes all cells to be not editable
        }
        };
        
        
        model.addColumn("ID");
        model.addColumn("NAME");
        model.addColumn("UNIT");
        model.addColumn("PURCHASE PRICE");
        model.addColumn("SELLING PRICE");
        model.addColumn("FIRST STOCK");
        model.addColumn("IN");
        model.addColumn("OUT");
        model.addColumn("CURRENT");
        
        try {
            String sql = "SELECT * FROM produk";
            
            java.sql.Statement stat = con.createStatement();
            ResultSet r = stat.executeQuery(sql);
            
            while (r.next()) {                
                Object[] o = new Object[9];
                int current = r.getInt("stok_awal") + r.getInt("masuk") - r.getInt("keluar");
                o [0] = r.getString("id");
                o [1] = r.getString("nama_produk");
                o [2] = r.getString("satuan");
                o [3] = r.getString("harga_beli");
                o [4] = r.getString("harga_jual");
                o [5] = r.getString("stok_awal");
                o [6] = r.getString("masuk");
                o [7] = r.getString("keluar");
                o [8] = current;
                
                model.addRow(o);
                tabelLive.setModel(model);
            }
            System.out.println("kes");
        } catch (Exception e) {
                System.out.println("terjadi kesalahan");
        }
        
    }
    
    public void cariLive(){
        DefaultTableModel model = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column)
        {
        return false;//This causes all cells to be not editable
        }
        };
        
        model.addColumn("ID");
        model.addColumn("NAME");
        model.addColumn("UNIT");
        model.addColumn("PURCHASE PRICE");
        model.addColumn("SELLING PRICE");
        model.addColumn("FIRST STOCK");
        model.addColumn("IN");
        model.addColumn("OUT");
        model.addColumn("CURRENT");
        
        try {
            String sql = "Select * from produk where id like '%" + cariLive.getText() + "%'" +"or nama_produk like '%" + cariLive.getText() + "%'";
            java.sql.Statement stat = con.createStatement();
            ResultSet r = stat.executeQuery(sql);
            while (r.next()) {                
                Object[] o = new Object[9];
                int current = r.getInt("stok_awal") + r.getInt("masuk") - r.getInt("keluar");
                o [0] = r.getString("id");
                o [1] = r.getString("nama_produk");
                o [2] = r.getString("satuan");
                o [3] = r.getString("harga_beli");
                o [4] = r.getString("harga_jual");
                o [5] = r.getString("stok_awal");
                o [6] = r.getString("masuk");
                o [7] = r.getString("keluar");
                o [8] = current;
                model.addRow(o);
                tabelLive.setModel(model);
            }
            System.out.println("kes");
        } catch (Exception e) {
            System.out.println("Cari Data Error");
        }finally{
        }
    }
    
    public void loadhistory(){
        DefaultTableModel model = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column)
        {
        return false;//This causes all cells to be not editable
        }
        };
        
        model.addColumn("TIME");
        model.addColumn("INFORMATION");
        model.addColumn("OTHERS");
        
        
        try {
            String sql = "Select * from history ORDER BY waktu DESC";
            java.sql.Statement stat = con.createStatement();
            ResultSet r = stat.executeQuery(sql);
            while (r.next()) {                
                Object[] o = new Object[3];
                o [0] = r.getString("waktu");
                o [1] = r.getString("info");
                o [2] = r.getString("ket");
                model.addRow(o);
                tabelHistory.setModel(model);
            }
            System.out.println("kes");
        } catch (Exception e) {
            System.out.println("Cari Data Error");
        }finally{
        }
    }
    
    
    public void history(String info, String ket){
        try {
            String sql = "INSERT INTO history (info, ket) VALUES (?, ?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, info);
            pst.setString(2, ket);
            pst.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("gagal history lagi");
        }finally{
            clear();
            load();
        }
    }
    
    public void load(){
        loadData();
        loadDataStok();
        loadDatalive();
        loadhistory();
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // Interface Barang
    public interface Barang {
        void tambahStok(int jumlah);
        void kurangiStok(int jumlah);
        int getStok();
        public void tampilkanInfo();
    }

    // Kelas Abstrak BarangGudang
    public abstract class BarangGudang implements Barang {
        private int stok;

        public BarangGudang(int stok) {
            this.stok = stok;
        }

        public void tambahStok(int jumlah) {
            
            try {
                String sql = "UPDATE produk SET masuk = masuk + " + jumlah + " WHERE id = '" + idstok + "'";
                pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Add Stock Successfully");
            } catch (Exception e) {
                System.out.println("Terjadi Kesalahan");
            }
        }

        public void kurangiStok(int jumlah) {
            if (jumlah <= stok) {
                try {
                    String sql = "UPDATE produk SET keluar = keluar + " + jumlah + " WHERE id = '" + idstok + "'";
                    pst = con.prepareStatement(sql);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Reduce Stock Successfully");
                } catch (Exception e) {
                    System.out.println("Terjadi Kesalahan");
                }    
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient Stock.");
            }
        }

        public int getStok() {
            return stok;
        }

        public abstract void tampilkanInfo();
        
        // Metode untuk menambah stok barang
        public void tambahStokBarang(int jumlah) {
            tambahStok(jumlah);
            
            try {
                String sql = "Select * from produk WHERE id = '" + idstok + "'";
                stm = con.createStatement();
                rs = stm.executeQuery(sql);
                if (rs.next()) {
                    String n = rs.getString("nama_produk");
                    String s = rs.getString("satuan");

                    String info = "Updating inventory for product " + n + " id " + idstok + ", " + jumlah + " " + s + " " + n + " added to stock";
                    String ket = "ADD";
                    history(info , ket);
            
                }    
            } catch (Exception e) {
                System.out.println("error");
            }
            
            
            System.out.println("Stok berhasil ditambahkan.");
        }
        
        public void kurangiStokBarang(int jumlah) {
            kurangiStok(jumlah);
            try {
                String sql = "Select * from produk WHERE id = '" + idstok + "'";
                stm = con.createStatement();
                rs = stm.executeQuery(sql);
                if (rs.next()) {
                    String n = rs.getString("nama_produk");
                    String s = rs.getString("satuan");

                    String info = "Updating inventory for product " + n + " id " + idstok + ", " + jumlah + " " + s + " " + n + " take to stock";
                    String ket = "OUT";
                    history(info , ket);
            
                }    
            } catch (Exception e) {
                System.out.println("error");
            }
            
            System.out.println("Stok berhasil dikurangi.");
        }
    }

    // Kelas BarangMasuk
    public class BarangMasuk extends BarangGudang {
        private String IDmasuk;

        public BarangMasuk(int stok, String IDmasuk) {
            super(stok);
            this.IDmasuk = IDmasuk;
        }

        public void tampilkanInfo() {
            loadDataStok();            
        }
    }

    // Kelas BarangKeluar
    public class BarangKeluar extends BarangGudang {
        private String IDkeluar;

        public BarangKeluar(int stok, String IDkeluar) {
            super(stok);
            this.IDkeluar = IDkeluar;
        }

        public void tampilkanInfo() {
            loadDataStok();
        }
    }

   
























    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        
        Konektor DB = new Konektor();
        DB.Konektor(); 
        con = DB.koneksi;
        stm = DB.statement;
        
        setExtendedState(JFrame.MAXIMIZED_HORIZ);
        setVisible(true);
        setResizable(false);
        load();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel33 = new javax.swing.JLabel();
        body = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnhome = new javax.swing.JButton();
        btnupdatestock = new javax.swing.JButton();
        btnnewproduk = new javax.swing.JButton();
        btnhistory = new javax.swing.JButton();
        btnlivestok = new javax.swing.JButton();
        btnlogout = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        updatestok = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelStok = new javax.swing.JTable();
        btnmasuk = new javax.swing.JButton();
        btnkeluar = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        newproduk = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnkonfirmnewproduk = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelProduk = new javax.swing.JTable();
        btndelete = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        cariProduk = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        namaProduk = new javax.swing.JTextField();
        hargaBeli = new javax.swing.JTextField();
        hargaJual = new javax.swing.JTextField();
        stokAwal = new javax.swing.JTextField();
        satuan = new javax.swing.JComboBox<>();
        btnEdit = new javax.swing.JButton();
        IDProduk = new javax.swing.JLabel();
        btndelete2 = new javax.swing.JButton();
        live = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelLive = new javax.swing.JTable();
        cariLive = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        history = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelHistory = new javax.swing.JTable();
        home = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();

        jLabel33.setText("jLabel33");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        body.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentHidden(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("WAREHOUSE");
        jLabel1.setAlignmentY(0.0F);

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("MANAGEMENT");

        btnhome.setBackground(new java.awt.Color(255, 255, 255));
        btnhome.setForeground(new java.awt.Color(51, 51, 51));
        btnhome.setText("Home");
        btnhome.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnhome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnhome.setIconTextGap(10);
        btnhome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhomeActionPerformed(evt);
            }
        });

        btnupdatestock.setBackground(new java.awt.Color(255, 255, 255));
        btnupdatestock.setForeground(new java.awt.Color(51, 51, 51));
        btnupdatestock.setText("Update Stock");
        btnupdatestock.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnupdatestock.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnupdatestock.setIconTextGap(10);
        btnupdatestock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdatestockActionPerformed(evt);
            }
        });

        btnnewproduk.setBackground(new java.awt.Color(255, 255, 255));
        btnnewproduk.setForeground(new java.awt.Color(51, 51, 51));
        btnnewproduk.setText("New Product");
        btnnewproduk.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnnewproduk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnnewproduk.setIconTextGap(10);
        btnnewproduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewprodukActionPerformed(evt);
            }
        });

        btnhistory.setBackground(new java.awt.Color(255, 255, 255));
        btnhistory.setForeground(new java.awt.Color(51, 51, 51));
        btnhistory.setText("History");
        btnhistory.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnhistory.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnhistory.setIconTextGap(10);
        btnhistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhistoryActionPerformed(evt);
            }
        });

        btnlivestok.setBackground(new java.awt.Color(255, 255, 255));
        btnlivestok.setForeground(new java.awt.Color(51, 51, 51));
        btnlivestok.setText("Live Stock");
        btnlivestok.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnlivestok.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnlivestok.setIconTextGap(10);
        btnlivestok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlivestokActionPerformed(evt);
            }
        });

        btnlogout.setBackground(new java.awt.Color(255, 255, 255));
        btnlogout.setForeground(new java.awt.Color(51, 51, 51));
        btnlogout.setText("Logout");
        btnlogout.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnlogout.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnlogout.setIconTextGap(10);
        btnlogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(btnhome, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnupdatestock, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnnewproduk, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnhistory, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnlivestok, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnlogout, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnhome, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnupdatestock, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnnewproduk, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnlivestok, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(btnhistory, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnlogout, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
        );

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setLayout(new java.awt.CardLayout());

        updatestok.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Sitka Text", 1, 36)); // NOI18N
        jLabel4.setText("UPDATE STOCK");

        tabelStok.setBackground(new java.awt.Color(204, 204, 204));
        tabelStok.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabelStok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelStokMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelStok);

        btnmasuk.setBackground(new java.awt.Color(255, 255, 255));
        btnmasuk.setForeground(new java.awt.Color(51, 51, 51));
        btnmasuk.setText("Incoming");
        btnmasuk.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnmasuk.setIconTextGap(20);
        btnmasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmasukActionPerformed(evt);
            }
        });

        btnkeluar.setBackground(new java.awt.Color(255, 255, 255));
        btnkeluar.setForeground(new java.awt.Color(51, 51, 51));
        btnkeluar.setText("Out");
        btnkeluar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnkeluar.setIconTextGap(20);
        btnkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkeluarActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Sitka Text", 1, 24)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Select Product");

        jLabel16.setFont(new java.awt.Font("Sitka Text", 1, 24)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Table Product");

        jLabel17.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel17.setText("Name");

        jLabel18.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel18.setText("Current Stock");

        jLabel20.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel20.setText("ID");

        jLabel19.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel19.setText("Selling Price");

        jLabel21.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel21.setText("Purchase Price");

        jLabel22.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel22.setText(":");

        jLabel23.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel23.setText(":");

        jLabel24.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel24.setText(":");

        jLabel25.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel25.setText(":");

        jLabel26.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel26.setText(":");

        jLabel27.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel27.setText("-");

        jLabel28.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel28.setText("-");

        jLabel29.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel29.setText("-");

        jLabel30.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel30.setText("-");

        jLabel31.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel31.setText("-");

        javax.swing.GroupLayout updatestokLayout = new javax.swing.GroupLayout(updatestok);
        updatestok.setLayout(updatestokLayout);
        updatestokLayout.setHorizontalGroup(
            updatestokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatestokLayout.createSequentialGroup()
                .addGroup(updatestokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(updatestokLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(updatestokLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(updatestokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE))
                        .addGap(38, 38, 38)
                        .addGroup(updatestokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15)
                            .addGroup(updatestokLayout.createSequentialGroup()
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(updatestokLayout.createSequentialGroup()
                                .addComponent(btnmasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(updatestokLayout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(updatestokLayout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel26)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(updatestokLayout.createSequentialGroup()
                                .addGroup(updatestokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(updatestokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(updatestokLayout.createSequentialGroup()
                                        .addComponent(jLabel25)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(updatestokLayout.createSequentialGroup()
                                        .addComponent(jLabel24)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        updatestokLayout.setVerticalGroup(
            updatestokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatestokLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(updatestokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(updatestokLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(updatestokLayout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(jLabel15)
                        .addGap(41, 41, 41)
                        .addGroup(updatestokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jLabel22)
                            .addComponent(jLabel27))
                        .addGap(18, 18, 18)
                        .addGroup(updatestokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel23)
                            .addComponent(jLabel28))
                        .addGap(18, 18, 18)
                        .addGroup(updatestokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jLabel25)
                            .addComponent(jLabel29))
                        .addGap(18, 18, 18)
                        .addGroup(updatestokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jLabel24)
                            .addComponent(jLabel30))
                        .addGap(18, 18, 18)
                        .addGroup(updatestokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jLabel26)
                            .addComponent(jLabel31))
                        .addGap(47, 47, 47)
                        .addGroup(updatestokLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnmasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnkeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        mainPanel.add(updatestok, "card3");

        newproduk.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Sitka Text", 1, 36)); // NOI18N
        jLabel6.setText("NEW PRODUCT");

        jLabel5.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel5.setText("Name");

        jLabel9.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel9.setText("Unit");

        jLabel10.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel10.setText("Purchase Price");

        jLabel11.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel11.setText("Selling Price");

        btnkonfirmnewproduk.setBackground(new java.awt.Color(0, 102, 0));
        btnkonfirmnewproduk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnkonfirmnewproduk.setForeground(new java.awt.Color(255, 255, 255));
        btnkonfirmnewproduk.setText("CONFIRM");
        btnkonfirmnewproduk.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnkonfirmnewproduk.setIconTextGap(20);
        btnkonfirmnewproduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkonfirmnewprodukActionPerformed(evt);
            }
        });

        tabelProduk.setBackground(new java.awt.Color(255, 255, 255));
        tabelProduk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabelProduk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelProdukMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabelProduk);

        btndelete.setBackground(new java.awt.Color(204, 0, 51));
        btndelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btndelete.setForeground(new java.awt.Color(255, 255, 255));
        btndelete.setText("DELETE");
        btndelete.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btndelete.setIconTextGap(20);
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Sitka Text", 1, 24)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Table Product");

        cariProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariProdukActionPerformed(evt);
            }
        });

        jLabel13.setText("Find Product");

        jLabel14.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        jLabel14.setText("First Stock");

        namaProduk.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N

        hargaBeli.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N

        hargaJual.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N

        stokAwal.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N

        satuan.setFont(new java.awt.Font("Sitka Subheading", 0, 18)); // NOI18N
        satuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pcs", "Box", "Sack", "Roll", "Kg" }));

        btnEdit.setBackground(new java.awt.Color(255, 255, 51));
        btnEdit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(51, 51, 51));
        btnEdit.setText("EDIT");
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEdit.setIconTextGap(20);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        IDProduk.setFont(new java.awt.Font("Sitka Subheading", 1, 18)); // NOI18N
        IDProduk.setText("  ");

        btndelete2.setBackground(new java.awt.Color(255, 255, 255));
        btndelete2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btndelete2.setForeground(new java.awt.Color(51, 51, 51));
        btndelete2.setText("CLEAR");
        btndelete2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btndelete2.setIconTextGap(20);
        btndelete2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndelete2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout newprodukLayout = new javax.swing.GroupLayout(newproduk);
        newproduk.setLayout(newprodukLayout);
        newprodukLayout.setHorizontalGroup(
            newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newprodukLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newprodukLayout.createSequentialGroup()
                        .addGroup(newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(newprodukLayout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newprodukLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newprodukLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cariProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 813, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22))))
            .addGroup(newprodukLayout.createSequentialGroup()
                .addGroup(newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newprodukLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(namaProduk)
                                .addComponent(satuan, 0, 187, Short.MAX_VALUE))
                            .addComponent(stokAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(newprodukLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(hargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(newprodukLayout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(hargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(IDProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(newprodukLayout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addGroup(newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(newprodukLayout.createSequentialGroup()
                                .addComponent(btndelete2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnkonfirmnewproduk, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        newprodukLayout.setVerticalGroup(
            newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newprodukLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addGap(8, 8, 8)
                .addGroup(newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cariProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10)
                    .addComponent(namaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(hargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(satuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(stokAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(IDProduk)))
                .addGap(49, 49, 49)
                .addComponent(btnkonfirmnewproduk, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(newprodukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70))
        );

        mainPanel.add(newproduk, "card5");

        live.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Sitka Text", 1, 36)); // NOI18N
        jLabel7.setText("STOCK LIVE REPORT");

        tabelLive.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tabelLive);

        cariLive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariLiveActionPerformed(evt);
            }
        });

        jLabel32.setText("Find Product");

        javax.swing.GroupLayout liveLayout = new javax.swing.GroupLayout(live);
        live.setLayout(liveLayout);
        liveLayout.setHorizontalGroup(
            liveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(liveLayout.createSequentialGroup()
                .addGroup(liveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(liveLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(316, 316, 316))
                    .addGroup(liveLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(liveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(liveLayout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cariLive, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        liveLayout.setVerticalGroup(
            liveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(liveLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addGroup(liveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cariLive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(189, Short.MAX_VALUE))
        );

        mainPanel.add(live, "card6");

        history.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Sitka Text", 1, 36)); // NOI18N
        jLabel8.setText("HISTORY");

        tabelHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tabelHistory);

        javax.swing.GroupLayout historyLayout = new javax.swing.GroupLayout(history);
        history.setLayout(historyLayout);
        historyLayout.setHorizontalGroup(
            historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historyLayout.createSequentialGroup()
                .addGroup(historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(historyLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(historyLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        historyLayout.setVerticalGroup(
            historyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(245, Short.MAX_VALUE))
        );

        mainPanel.add(history, "card7");

        home.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Sitka Text", 1, 36)); // NOI18N
        jLabel3.setText("Praktikum Pemrograman");

        jLabel34.setFont(new java.awt.Font("Sitka Text", 1, 36)); // NOI18N
        jLabel34.setText("HOME");

        jLabel35.setFont(new java.awt.Font("Sitka Text", 1, 36)); // NOI18N
        jLabel35.setText("Berorientasi Objek");

        jLabel36.setFont(new java.awt.Font("Sitka Text", 1, 24)); // NOI18N
        jLabel36.setText("Refanda Dicky Pradana");

        jLabel37.setFont(new java.awt.Font("Sitka Text", 1, 24)); // NOI18N
        jLabel37.setText("123210029");

        jLabel38.setFont(new java.awt.Font("Sitka Text", 1, 24)); // NOI18N
        jLabel38.setText("Ahlul Fadhly");

        jLabel39.setFont(new java.awt.Font("Sitka Text", 1, 24)); // NOI18N
        jLabel39.setText("123210091");

        javax.swing.GroupLayout homeLayout = new javax.swing.GroupLayout(home);
        home.setLayout(homeLayout);
        homeLayout.setHorizontalGroup(
            homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeLayout.createSequentialGroup()
                .addGap(232, 232, 232)
                .addGroup(homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel35)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(homeLayout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addGroup(homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                .addGroup(homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addComponent(jLabel39))
                .addGap(202, 202, 202))
            .addGroup(homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(homeLayout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(336, Short.MAX_VALUE)))
        );
        homeLayout.setVerticalGroup(
            homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeLayout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel35)
                .addGap(343, 343, 343)
                .addGroup(homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jLabel39))
                .addContainerGap(160, Short.MAX_VALUE))
            .addGroup(homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(homeLayout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(712, Short.MAX_VALUE)))
        );

        mainPanel.add(home, "card2");

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
                .addContainerGap())
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel1ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1ComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1ComponentHidden

    private void btnhomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhomeActionPerformed
        // remove panel
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
        // add panel
        mainPanel.add(home);
        mainPanel.repaint();
        mainPanel.revalidate();
        
    }//GEN-LAST:event_btnhomeActionPerformed

    private void btnupdatestockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdatestockActionPerformed
        // TODO add your handling code here:
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
        // add panel
        mainPanel.add(updatestok);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnupdatestockActionPerformed

    private void btnnewprodukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewprodukActionPerformed
        // TODO add your handling code here:
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
        // add panel
        mainPanel.add(newproduk);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnnewprodukActionPerformed

    private void btnhistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhistoryActionPerformed
        // TODO add your handling code here:
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
        // add panel
        mainPanel.add(history);
        mainPanel.repaint();
        mainPanel.revalidate();
        
    }//GEN-LAST:event_btnhistoryActionPerformed

    private void btnlivestokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlivestokActionPerformed
        // TODO add your handling code here:
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
        // add panel
        mainPanel.add(live);
        mainPanel.repaint();
        mainPanel.revalidate();
      
    }//GEN-LAST:event_btnlivestokActionPerformed

    private void btnlogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlogoutActionPerformed
        // TODO add your handling code here:
        Login login = new Login();
        login.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnlogoutActionPerformed

    private void btnmasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmasukActionPerformed
        if (idstok == null) {
            JOptionPane.showMessageDialog(null, "Select A Product First");
        }else{
            String jumlah = JOptionPane.showInputDialog(null, "Quantity","Incoming Stock", JOptionPane.WARNING_MESSAGE);
            System.out.println(jumlah);
            try {
                int i = Integer.parseInt(jumlah);
                int tambahanStokMasuk = i;
                BarangMasuk barangMasuk = new BarangMasuk(cur, idstok);
                Barang barang1 = barangMasuk;
                barangMasuk.tambahStokBarang(tambahanStokMasuk);
                barang1.tampilkanInfo();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Wrong Input");
            }finally{
                idstok = null;
                jLabel27.setText("-");
                jLabel28.setText("-");
                jLabel29.setText("-");
                jLabel30.setText("-");
                jLabel31.setText("-");
            }
        }  
    }//GEN-LAST:event_btnmasukActionPerformed

    private void btnkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkeluarActionPerformed
        if (idstok == null) {
            JOptionPane.showMessageDialog(null, "Select A Product First");
        }else{
            String jumlah = JOptionPane.showInputDialog(null, "Quantity","Out Stock", JOptionPane.WARNING_MESSAGE);
            System.out.println(jumlah);
            try {
                int i = Integer.parseInt(jumlah);
                int tambahanStokKeluar = i;
                BarangKeluar barangKeluar = new BarangKeluar(cur, idstok);
                Barang barang2 = barangKeluar;
                barangKeluar.kurangiStokBarang(tambahanStokKeluar);
                barang2.tampilkanInfo();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Wrong Input");
            } finally{
                idstok = null;
                jLabel27.setText("-");
                jLabel28.setText("-");
                jLabel29.setText("-");
                jLabel30.setText("-");
                jLabel31.setText("-");
            }
        }  
    }//GEN-LAST:event_btnkeluarActionPerformed

    private void cariProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariProdukActionPerformed
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_cariProdukActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
        if (idUp == null) {
            JOptionPane.showMessageDialog(null, "Select A Product First");
        }else{
            hapusProduk();
        }
        
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnkonfirmnewprodukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkonfirmnewprodukActionPerformed
        // TODO add your handling code here:
        String id = randomID(args);
        String nama = (String)namaProduk.getText();
        String sat = (String)satuan.getSelectedItem();
        String stokawal = stokAwal.getText();
        String beli = hargaBeli.getText();
        String jual = hargaJual.getText();
        try {
            String sql = "INSERT INTO produk (id, nama_produk, satuan, stok_awal, harga_beli, harga_jual) VALUES (?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, id);
            pst.setString(2, nama);
            pst.setString(3, sat);
            pst.setString(4, stokawal);
            pst.setString(5, beli);
            pst.setString(6, jual);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Tersimpan");
        } catch (Exception e) {
            System.out.println("Terjadi Kesalahan");
        }finally{
            clear();
            load();
        }

    }//GEN-LAST:event_btnkonfirmnewprodukActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (idUp == null) {
            JOptionPane.showMessageDialog(null, "Select A Product First");
        }else{
            String nama = (String)namaProduk.getText();
            String sat = (String)satuan.getSelectedItem();
            String stokawal = stokAwal.getText();
            String beli = hargaBeli.getText();
            String jual = hargaJual.getText();
            try {
                String sql = "UPDATE produk SET nama_produk = '" + nama + "', satuan = '" + sat + "', stok_awal = '" + stokawal + "', harga_beli = '" + beli + "', harga_jual = '" + jual + "' WHERE id = '" + this.idUp + "'";
                pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Data update");
                String info = "Update "+nama+" product data ("+idUp+")";
                String ket = "EDIT";
                history(info , ket);
                
            } catch (Exception e) {
                System.out.println("Terjadi Kesalahan");
            }finally{
                clear();
                load();
            }
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void tabelProdukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelProdukMouseClicked

        try {
            int row = tabelProduk.getSelectedRow();
            String idKilk = (tabelProduk.getValueAt(row,0).toString());
            String sql = "SELECT * FROM produk WHERE id = '" + idKilk + "'";
            stm = con.createStatement();
            rs = stm.executeQuery(sql);
            if(rs.next()){
                this.idUp = rs.getString("id");
                IDProduk.setText("ID  : " + idUp);
                String b = rs.getString("nama_produk");
                namaProduk.setText(b);
                String ca = rs.getString("satuan");
                satuan.setSelectedItem(ca);
                String d = rs.getString("stok_awal");
                stokAwal.setText(d);
                String e = rs.getString("harga_beli");
                hargaBeli.setText(e);
                String f = rs.getString("harga_jual");
                hargaJual.setText(f);
            }
            
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tabelProdukMouseClicked

    private void btndelete2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndelete2ActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btndelete2ActionPerformed

    private void tabelStokMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelStokMouseClicked
        try {
            int row = tabelStok.getSelectedRow();
            String idKilk = (tabelStok.getValueAt(row,0).toString());
            String sql = "SELECT * FROM produk WHERE id = '" + idKilk + "'";
            stm = con.createStatement();
            rs = stm.executeQuery(sql);
            if(rs.next()){
                cur = rs.getInt("stok_awal") + rs.getInt("masuk") - rs.getInt("keluar");
                this.idstok = rs.getString("id");
                jLabel27.setText(idstok);
                String b = rs.getString("nama_produk");
                jLabel28.setText(b);
                String c = rs.getString("harga_beli");
                jLabel29.setText("Rp "+c);
                String d = rs.getString("harga_jual");
                jLabel30.setText("Rp "+d);
                String e = String.valueOf(cur) + " "+rs.getString("satuan") ;
                jLabel31.setText(e);
            }
            
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tabelStokMouseClicked

    private void cariLiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariLiveActionPerformed
        // TODO add your handling code here:
        cariLive();
    }//GEN-LAST:event_cariLiveActionPerformed

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IDProduk;
    private javax.swing.JPanel body;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btndelete2;
    private javax.swing.JButton btnhistory;
    private javax.swing.JButton btnhome;
    private javax.swing.JButton btnkeluar;
    private javax.swing.JButton btnkonfirmnewproduk;
    private javax.swing.JButton btnlivestok;
    private javax.swing.JButton btnlogout;
    private javax.swing.JButton btnmasuk;
    private javax.swing.JButton btnnewproduk;
    private javax.swing.JButton btnupdatestock;
    private javax.swing.JTextField cariLive;
    private javax.swing.JTextField cariProduk;
    private javax.swing.JTextField hargaBeli;
    private javax.swing.JTextField hargaJual;
    private javax.swing.JPanel history;
    private javax.swing.JPanel home;
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
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel live;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField namaProduk;
    private javax.swing.JPanel newproduk;
    private javax.swing.JComboBox<String> satuan;
    private javax.swing.JTextField stokAwal;
    private javax.swing.JTable tabelHistory;
    private javax.swing.JTable tabelLive;
    private javax.swing.JTable tabelProduk;
    private javax.swing.JTable tabelStok;
    private javax.swing.JPanel updatestok;
    // End of variables declaration//GEN-END:variables
}
