package com.example.demoexamen.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class MainWindow extends JFrame {

    @Autowired
    private PartnerWindow partnerWindow;

    @Autowired
    private ImportDataWindow importDataWindow;

    @Autowired
    private SalesHistoryWindow salesHistoryWindow;

    public MainWindow() {
        setTitle("Главное окно");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        JButton buttonImportDataWindow = new JButton("Импортировать данные");
        buttonImportDataWindow.addActionListener(actionEvent -> {
            importDataWindow.setVisible(true);
        });
        JButton buttonPartnerWindow = new JButton("Данные о партнерах");
        buttonPartnerWindow.addActionListener(actionEvent -> {
            partnerWindow.setVisible(true);
        });

        JButton buttonSalesHistory = new JButton("История реализации");
        buttonSalesHistory.addActionListener(actionEvent -> {
            salesHistoryWindow.setVisible(true);
        });

        panel.add(buttonPartnerWindow);
        panel.add(buttonImportDataWindow);
        panel.add(buttonSalesHistory);
        add(panel);
    }
}









