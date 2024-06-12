package gradle.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import gradle.Loader;
import gradle.models.Mahasiswa;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class MahasiswaGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<Mahasiswa> dataMhs;

    public MahasiswaGUI(ArrayList<Mahasiswa> dataMhs) {
        this.dataMhs = dataMhs;
        initializeGUI();
        displayData(dataMhs);
    }

    private void initializeGUI() {
        setTitle("Data Mahasiswa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Tambahkan ComboBox untuk memilih kriteria pengurutan
        JComboBox<String> sortComboBox = new JComboBox<>(new String[] { "NIM", "Nama", "IPK" });
        sortComboBox.addActionListener(e -> {
            String selectedSort = (String) sortComboBox.getSelectedItem();
            if (selectedSort != null) {
                sortData(selectedSort);
            }
        });
        getContentPane().add(sortComboBox, BorderLayout.NORTH);

        setVisible(true);
    }

    private void displayData(ArrayList<Mahasiswa> data) {
        // Menampilkan judul kolom
        tableModel.setColumnIdentifiers(new Object[] { "NIM", "Nama", "SKS Total", "IPK" });

        // Menghapus semua baris yang ada di tabel
        tableModel.setRowCount(0);

        // Menambahkan data mahasiswa ke dalam tabel
        for (Mahasiswa mhs : data) {
            tableModel.addRow(new Object[] { mhs.getNim(), mhs.getNama(), mhs.getTotalSks(), mhs.getIpk() });
        }
    }

    public void sortData(String sortBy) {
        // Mengurutkan data berdasarkan kriteria yang dipilih
        if (sortBy.equals("NIM")) {
            dataMhs.sort(Comparator.comparing(Mahasiswa::getNim));
        } else if (sortBy.equals("Nama")) {
            dataMhs.sort(Comparator.comparing(Mahasiswa::getNama));
        } else if (sortBy.equals("IPK")) {
            dataMhs.sort(Comparator.comparing(Mahasiswa::getIpk));
        }

        // Menampilkan data yang sudah diurutkan
        displayData(dataMhs);
    }

}
