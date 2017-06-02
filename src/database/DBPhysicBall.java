package database;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by pepe on 05/04/2017.
 * MyPhysics-Balls
 */
public class DBPhysicBall {
    private String bd = "pruebabolas";
    private String url = "jdbc:mysql://localhost/" + bd;

    Connection conectar() {
        Connection link = null;
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            String password = "";
            String login = "root";
            link = DriverManager.getConnection(this.url, login, password);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return link;
    }
}

