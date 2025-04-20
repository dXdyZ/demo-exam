package com.example.demoexamen;

import com.example.demoexamen.ui.WindowController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class DemoExamenApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = new SpringApplicationBuilder(DemoExamenApplication.class)
                .headless(false)
                .run(args);

        SwingUtilities.invokeLater(() -> {
            WindowController windowController = context.getBean(WindowController.class);
            windowController.openMainWindow();
        });
    }

}
