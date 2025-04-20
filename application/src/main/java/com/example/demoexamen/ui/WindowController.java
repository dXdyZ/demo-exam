package com.example.demoexamen.ui;

import org.springframework.stereotype.Component;

@Component
public class WindowController {
    private final MainWindow mainWindow;

    public WindowController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void openMainWindow() {
        mainWindow.setVisible(true);
    }
}
