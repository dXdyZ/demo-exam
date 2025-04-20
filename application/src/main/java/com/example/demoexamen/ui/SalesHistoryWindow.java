package com.example.demoexamen.ui;

import com.example.demoexamen.entity.SalesHistory;
import com.example.demoexamen.service.SalesHistoryService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

@Component
public class SalesHistoryWindow extends JFrame {

    @Autowired
    private SalesHistoryService salesHistoryService;


    public SalesHistoryWindow() {
        setTitle("История реализации");
        setSize(600, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @PostConstruct
    public void init() {
        // Получаем данные из сервиса
        List<SalesHistory> salesHistories = salesHistoryService.getAllSaleHistory();

        String[] columnNames = {"Продукция", "Наименования партнера", "Количество продукции", "Дата продажи"};
        Object[][] data = new Object[salesHistories.size()][4];

        for (int i = 0; i < salesHistories.size(); i++) {
            SalesHistory salesHistory = salesHistories.get(i);
            data[i][0] = salesHistory.getProduct().getName();
            data[i][1] = salesHistory.getPartner().getName();
            data[i][2] = salesHistory.getQuantity();
            data[i][3] = salesHistory.getSalesDate();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        // Создаем панель для текстового поля и кнопки
        JPanel inputPanel = new JPanel(new GridLayout(1, 3, 5, 5)); // 1 строка, 3 столбца

        JLabel partnerNameLabel = new JLabel("Имя партнера");
        JTextField nameField = new JTextField();

        JButton button = new JButton("Поиск");
        button.addActionListener(actionEvent -> {
            if (nameField.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Поле не должно быть пустым");
            } else {
                historyByPartnerName(nameField.getText()).setVisible(true);
            }
        });

        inputPanel.add(partnerNameLabel);
        inputPanel.add(nameField);
        inputPanel.add(button);

        // Главная панель с вертикальным BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(inputPanel, BorderLayout.NORTH); // Панель с полем и кнопкой сверху
        mainPanel.add(scrollPane, BorderLayout.CENTER); // Таблица в центре

        // Добавляем главную панель в окно
        add(mainPanel);
    }

    public JFrame historyByPartnerName(String name) {
        JFrame frame = new JFrame("История реализации продукта партнером");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        List<SalesHistory> salesHistories = salesHistoryService.getSaleHistoryByPartnerName(name);

        String[] columnName = {"Продукция", "Количество продукции", "Дата продажи"};
        Object[][] data = new Object[salesHistories.size()][3];

        for (int i = 0; i < salesHistories.size(); i++) {
            SalesHistory salesHistory = salesHistories.get(i);
            data[i][0] = salesHistory.getProduct().getName();
            data[i][1] = salesHistory.getQuantity();
            data[i][2] = salesHistory.getSalesDate();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnName);

        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(600, 250);
        frame.setLocationRelativeTo(this);
        return frame;
    }
}







