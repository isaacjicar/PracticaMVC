package org.example.mvcpractice.view;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    public MainMenuFrame() {
        super("MVC Practice - Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);

        JMenuBar bar = new JMenuBar();
        JMenu mArchivo = new JMenu("Archivo");
        JMenuItem miSalir = new JMenuItem("Salir");
        miSalir.addActionListener(e -> System.exit(0));
        mArchivo.add(miSalir);

        JMenu mVer = new JMenu("Ver");
        JMenuItem miRoles = new JMenuItem("Roles");
        JMenuItem miPersonas = new JMenuItem("Personas");
        JMenuItem miAsignar = new JMenuItem("Asignar Rol");

        bar.add(mArchivo);
        bar.add(mVer);
        mVer.add(miRoles);
        mVer.add(miPersonas);
        mVer.add(miAsignar);
        setJMenuBar(bar);

        // Tabs
        JTabbedPane tabs = new JTabbedPane();
        RolesPanel rolesPanel = new RolesPanel();
        PersonsPanel personsPanel = new PersonsPanel();
        AssignRolePanel assignPanel = new AssignRolePanel();

        tabs.addTab("Roles", rolesPanel);
        tabs.addTab("Personas", personsPanel);
        tabs.addTab("Asignar Rol", assignPanel);
        add(tabs, BorderLayout.CENTER);


        miRoles.addActionListener(e -> tabs.setSelectedIndex(0));
        miPersonas.addActionListener(e -> tabs.setSelectedIndex(1));
        miAsignar.addActionListener(e -> tabs.setSelectedIndex(2));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuFrame().setVisible(true));
    }
}
