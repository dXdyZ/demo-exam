package com.example.demoexamen.ui;

import com.example.demoexamen.dto.PartnerDto;
import com.example.demoexamen.entity.Partner;
import com.example.demoexamen.service.DiscountCalculatorService;
import com.example.demoexamen.service.PartnerService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

@Component
public class PartnerWindow extends JFrame {

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private DiscountCalculatorService discountCalculatorService;


    public PartnerWindow() {
        setTitle("Информация о партнерах");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @PostConstruct
    @Transactional
    public void init() {
        Map<Partner, Integer> partnersAndDiscount = discountCalculatorService
                .calculatePartnerDiscount(partnerService.getAllPartner());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        for (Map.Entry<Partner, Integer> entry : partnersAndDiscount.entrySet()) {
            JPanel panelList = createPartnerPanel(entry.getKey(), entry.getValue());

            panelList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openUpdatePartnerData(entry.getKey().getId());
                    dispose();

                }
            });

            mainPanel.add(panelList);

            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JButton addPartnerButton = new JButton("Добавить партнера");

        addPartnerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openUpdatePartnerData(null);
                dispose();
            }
        });

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(addPartnerButton);

        add(mainPanel);
        pack();
    }


    private JPanel createPartnerPanel(Partner partner, Integer discount) {
        getContentPane().removeAll();

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //Left part
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(partner.getPartnerType() + " | " +
                partner.getName());
        title.setFont(title.getFont().deriveFont(Font.BOLD));

        infoPanel.add(title);

        infoPanel.add(new JLabel(partner.getDirector()));
        infoPanel.add(new JLabel(partner.getPhoneNumber()));
        infoPanel.add(new JLabel("Рейтинг:" + partner.getRating()));

        //Right part with percent

        JLabel percent = new JLabel(discount + "%");
        percent.setHorizontalAlignment(SwingUtilities.RIGHT);
        percent.setFont(percent.getFont().deriveFont(18f));

        panel.add(infoPanel, BorderLayout.WEST);
        panel.add(percent, BorderLayout.EAST);


        revalidate();
        repaint();
        return panel;
    }

    @Transactional
    public void openUpdatePartnerData(Long partnerId) {
        JFrame updateDataFrame = new JFrame("Обновление данных партнера");
        updateDataFrame.setSize(600, 400);
        updateDataFrame.setLocationRelativeTo(this);
        updateDataFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(9, 2, 5, 5));

        JLabel partnerNewNameLabel = new JLabel("Имя");
        JTextField nameField = new JTextField();

        JLabel partnerNewPartnerTypeLabel = new JLabel("Тип партнера");
        JTextField typeField = new JTextField();

        JLabel partnerNewDirectorLabel = new JLabel("Директор");
        JTextField directorField = new JTextField();

        JLabel partnerNewEmailLabel = new JLabel("Электронная почта");
        JTextField emailField = new JTextField();

        JLabel partnerNewPhoneNumberLabel = new JLabel("Номер телефона");
        JTextField phoneNumberField = new JTextField();

        JLabel partnerNewAddressLabel = new JLabel("Адрес");
        JTextField addressField = new JTextField();

        JLabel partnerNewInnLabel = new JLabel("ИНН");
        JTextField innField = new JTextField();

        JLabel partnerNewRatingLabel = new JLabel("Рейтинг");
        JTextField ratingField = new JTextField();

        panel.add(partnerNewNameLabel);
        panel.add(nameField);

        panel.add(partnerNewPartnerTypeLabel);
        panel.add(typeField);

        panel.add(partnerNewDirectorLabel);
        panel.add(directorField);

        panel.add(partnerNewEmailLabel);
        panel.add(emailField);

        panel.add(partnerNewPhoneNumberLabel);
        panel.add(phoneNumberField);

        panel.add(partnerNewAddressLabel);
        panel.add(addressField);

        panel.add(partnerNewInnLabel);
        panel.add(innField);

        panel.add(partnerNewRatingLabel);
        panel.add(ratingField);

        // Добавление панели на форму
        updateDataFrame.add(panel);

        JButton button = new JButton("Обновить данные");
        button.addActionListener(actionEvent -> {
            String name = nameField.getText();
            String partnerType = typeField.getText();
            String director = directorField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();
            String address = addressField.getText();
            Integer rating = null;
            Long inn = null;
            if (!ratingField.getText().isBlank())
                rating = Integer.valueOf(ratingField.getText());
            if (!innField.getText().isBlank())
                inn = Long.valueOf(innField.getText());

            PartnerDto partnerDto = new PartnerDto(
                    name, partnerType, director, email, phoneNumber, address, inn, rating
            );
            if (partnerId != null) {
                partnerService.updatePartnerData(partnerId, partnerDto);

                updateDataFrame.dispose();

                init();
                JOptionPane.showMessageDialog(updateDataFrame, "Данные успешно обновлены!");
                this.setVisible(true);
            } else {
                if (!name.isBlank() && !partnerType.isBlank() && !director.isBlank() && !email.isBlank() &&
                    !phoneNumber.isBlank() && !address.isBlank() && inn != null) {
                    partnerService.savePartner(partnerDto);

                    updateDataFrame.dispose();;
                    init();
                    JOptionPane.showMessageDialog(updateDataFrame, "Партнер успешно создан");
                    this.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(updateDataFrame,
                            "Данные во всех полях должны быть заполнены");
                }
            }
        });
        panel.add(new JLabel());
        panel.add(button);
        updateDataFrame.setVisible(true);
    }
}












