package main;

import javafx.application.Application;
import ui.GUI;

import java.io.IOException;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws IOException, SQLException, InterruptedException {
        Application.launch(GUI.class);
    }
}
