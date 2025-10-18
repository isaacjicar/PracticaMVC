package org.example.mvcpractice.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

import com.fasterxml.jackson.databind.*;

public class PersonsPanel extends JPanel {
    private static final String BASE_URL = "http://localhost:8080/api/person";
    private final ObjectMapper om = new ObjectMapper();

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Apellido", "Email", "Número"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);
    private final JTextField txtQ = new JTextField(15);

    private final JTextField txtName = new JTextField(15);
    private final JTextField txtLast = new JTextField(15);
    private final JTextField txtEmail = new JTextField(15);
    private final JTextField txtNumber = new JTextField(15);

    public PersonsPanel() {
        setLayout(new BorderLayout(8,8));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Buscar (q):"));
        top.add(txtQ);
        JButton btnLoad = new JButton("Cargar");
        btnLoad.addActionListener(e -> load());
        top.add(btnLoad);
        add(top, BorderLayout.NORTH);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.add(new JLabel("Nombre:"));
        form.add(txtName);
        form.add(Box.createVerticalStrut(6));
        form.add(new JLabel("Apellido:"));
        form.add(txtLast);
        form.add(Box.createVerticalStrut(6));
        form.add(new JLabel("Email:"));
        form.add(txtEmail);
        form.add(Box.createVerticalStrut(6));
        form.add(new JLabel("Número:"));
        form.add(txtNumber);
        form.add(Box.createVerticalStrut(10));

        JButton btnCreate = new JButton("Crear");
        btnCreate.addActionListener(e -> create());
        JButton btnUpdate = new JButton("Actualizar seleccionado");
        btnUpdate.addActionListener(e -> updateSelected());
        JButton btnDelete = new JButton("Eliminar seleccionado");
        btnDelete.addActionListener(e -> deleteSelected());

        form.add(btnCreate);
        form.add(Box.createVerticalStrut(6));
        form.add(btnUpdate);
        form.add(Box.createVerticalStrut(6));
        form.add(btnDelete);

        add(form, BorderLayout.EAST);

        load();
    }

    private void load() {
        try {
            String q = txtQ.getText() == null ? "" : txtQ.getText().trim();
            String url = BASE_URL + (q.isEmpty() ? "" : "?q=" + q.replace(" ", "%20"));
            String json = HttpClientHelper.get(url);
            List<Map<String,Object>> list = Arrays.asList(new ObjectMapper().readValue(json, Map[].class));
            model.setRowCount(0);
            for (Map<String,Object> p : list) {
                model.addRow(new Object[]{
                        p.get("id"), p.get("name"), p.get("lastName"),
                        p.get("email"), p.get("number")
                });
            }
        } catch (Exception ex) {
            showErr(ex);
        }
    }

    private void create() {
        try {
            var payload = Map.of(
                    "name", txtName.getText().trim(),
                    "lastName", txtLast.getText().trim(),
                    "email", txtEmail.getText().trim(),
                    "number", txtNumber.getText().trim()
            );
            String json = HttpClientHelper.postJson(BASE_URL, new ObjectMapper().writeValueAsString(payload));
            JOptionPane.showMessageDialog(this, "Creado: " + json);
            load();
        } catch (Exception ex) { showErr(ex); }
    }

    private void updateSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Seleccione una persona."); return; }
        int id = (int) model.getValueAt(row, 0);
        try {
            var payload = Map.of(
                    "name", txtName.getText().trim(),
                    "lastName", txtLast.getText().trim(),
                    "email", txtEmail.getText().trim(),
                    "number", txtNumber.getText().trim()
            );
            String json = HttpClientHelper.putJson(BASE_URL + "/" + id, new ObjectMapper().writeValueAsString(payload));
            JOptionPane.showMessageDialog(this, "Actualizado: " + json);
            load();
        } catch (Exception ex) { showErr(ex); }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Seleccione una persona."); return; }
        int id = (int) model.getValueAt(row, 0);
        int ok = JOptionPane.showConfirmDialog(this, "¿Eliminar persona " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return;
        try {
            HttpClientHelper.delete(BASE_URL + "/" + id);
            JOptionPane.showMessageDialog(this, "Eliminado.");
            load();
        } catch (Exception ex) { showErr(ex); }
    }

    private static void showErr(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
