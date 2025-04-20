package com.example.demoexamen.ui;

import com.example.demoexamen.exception.PartnerAndProductNotFound;
import com.example.demoexamen.exception.ProductTypeNotFoundException;
import com.example.demoexamen.file_import.DataImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.io.File;

@Component
public class ImportDataWindow extends JFrame {

    @Autowired
    private DataImportService dataImportService;

    public ImportDataWindow() {
        setTitle("Импортировать данные");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите файл");
        fileChooser.setFileSelectionMode(DISPOSE_ON_CLOSE);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));
        buttonPanel.add(getProductFileButton(fileChooser));
        buttonPanel.add(getPartnerFileButton(fileChooser));
        buttonPanel.add(getProductTypeFileButton(fileChooser));
        buttonPanel.add(getMaterialFileButton(fileChooser));
        buttonPanel.add(getSalesHistoryButton(fileChooser));

        JPanel groupPanel = new JPanel(new BorderLayout());
        groupPanel.setBorder(BorderFactory.createTitledBorder("Импорт данных")); // Заголовок группы
        groupPanel.add(buttonPanel, BorderLayout.CENTER);

        add(groupPanel);
    }

    private JButton getPartnerFileButton(JFileChooser fileChooser) {
        JButton buttonPartnerFile = new JButton("Импортировать партнеров");

        buttonPartnerFile.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    dataImportService.readPartnerFromExel(file.getAbsolutePath());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(this, "Добавлено успешно");
            }
        });
        return buttonPartnerFile;
    }

    private JButton getProductFileButton(JFileChooser fileChooser) {
        JButton buttonProductFile = new JButton("Импорт продуктов");

        buttonProductFile.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    dataImportService.readProductFromExel(file.getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "Добавлено успешно");
                } catch (ProductTypeNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, "Сначала добавьте тип продуктов");
                } catch (Exception exe) {
                    throw new RuntimeException(exe);
                }
            }
        });
        return buttonProductFile;
    }

    private JButton getProductTypeFileButton(JFileChooser fileChooser) {
        JButton buttonProductTypeFile = new JButton("Импорт типов продуктов");

        buttonProductTypeFile.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    dataImportService.readProductTypeFormExel(file.getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "Добавлено успешно");
                } catch (Exception exe) {
                    throw new RuntimeException(exe);
                }
            }
        });
        return buttonProductTypeFile;
    }

    private JButton getMaterialFileButton(JFileChooser fileChooser) {
        JButton buttonMaterialFile = new JButton("Импорт материалов");

        buttonMaterialFile.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    dataImportService.readMaterialFromExel(file.getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "Добавлено успешно");
                } catch (Exception exe) {
                    throw new RuntimeException(exe);
                }
            }
        });
        return buttonMaterialFile;
    }

    private JButton getSalesHistoryButton(JFileChooser fileChooser) {
        JButton buttonSalesHistoryFile = new JButton("Импорт истории реализации");

        buttonSalesHistoryFile.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    dataImportService.readSalesHistoryFromExel(file.getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "Добавлено успешно");
                } catch (PartnerAndProductNotFound exception) {
                    JOptionPane.showMessageDialog(this, "Сначала добавьте продукты и партнеров");
                } catch (Exception exe) {
                    throw new RuntimeException(exe);
                }
            }
        });
        return buttonSalesHistoryFile;
    }
}








