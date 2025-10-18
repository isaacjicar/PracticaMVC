// src/main/java/org/example/mvcpractice/ui/RolesPanel.java
package org.example.mvcpractice.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.*;
import com.fasterxml.jackson.databind.*;

public class RolesPanel extends JPanel {
    private static final String BASE_URL = "http://localhost:8080/api/roles";
    private final ObjectMapper om = new ObjectMapper();

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Descripción"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);
    private final JTextField txtQ = new JTextField(15);
    private final JComboBox<String> cmbSort = new JComboBox<>(new String[]{"ASC", "DESC"});

    // Formulario
    private final JTextField txtName = new JTextField(20);
    private final JTextField txtDesc = new JTextField(20);

    public RolesPanel() {
        setLayout(new BorderLayout(8,8));

        // Top (filtro)
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Buscar nombre (q):"));
        top.add(txtQ);
        top.add(new JLabel("Orden:"));
        top.add(cmbSort);
        JButton btnLoad = new JButton("Cargar");
        btnLoad.addActionListener(e -> load());
        top.add(btnLoad);
        add(top, BorderLayout.NORTH);

        // Center (tabla)
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Right (form)
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.add(new JLabel("Nombre:"));
        form.add(txtName);
        form.add(Box.createVerticalStrut(8));
        form.add(new JLabel("Descripción:"));
        form.add(txtDesc);
        form.add(Box.createVerticalStrut(12));

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

        // Cargar inicial
        load();
    }

    private void load() {
        try {
            String q = txtQ.getText() == null ? "" : txtQ.getText().trim();
            String dir = Objects.toString(cmbSort.getSelectedItem(), "ASC");
            // tu controlador: GET /api/roles?q=&dir=ASC|DESC
            String url = BASE_URL + "?q=" + encode(q) + "&dir=" + dir;
            String json = HttpClientHelper.get(url);

            // roleResponse[] → id, name, description, createdAt, updatedAt
            List<Map<String,Object>> list = Arrays.asList(om.readValue(json, Map[].class));
            model.setRowCount(0);
            for (Map<String,Object> r : list) {
                model.addRow(new Object[]{
                        r.get("id"),
                        r.get("name"),
                        r.get("description")
                });
            }
        } catch (Exception ex) {
            showErr(ex);
        }
    }

    private void create() {
        try {
            String name = txtName.getText().trim();
            String desc = txtDesc.getText().trim();
            var payload = Map.of("name", name, "description", desc);
            String json = HttpClientHelper.postJson(BASE_URL, new ObjectMapper().writeValueAsString(payload));
            JOptionPane.showMessageDialog(this, "Creado: " + json);
            load();
        } catch (Exception ex) {
            showErr(ex);
        }
    }

    private void updateSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Seleccione un rol."); return; }
        int id = (int) model.getValueAt(row, 0);
        String name = txtName.getText().trim();
        String desc = txtDesc.getText().trim();

        try {
            var payload = Map.of("name", name, "description", desc);
            String url = BASE_URL + "/" + id;
            String json = HttpClientHelper.putJson(url, new ObjectMapper().writeValueAsString(payload));
            JOptionPane.showMessageDialog(this, "Actualizado: " + json);
            load();
        } catch (Exception ex) {
            showErr(ex);
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Seleccione un rol."); return; }
        int id = (int) model.getValueAt(row, 0);
        int ok = JOptionPane.showConfirmDialog(this, "¿Eliminar rol " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return;

        try {
            HttpClientHelper.delete(BASE_URL + "/" + id);
            JOptionPane.showMessageDialog(this, "Eliminado.");
            load();
        } catch (Exception ex) {
            showErr(ex);
        }
    }

    private static String encode(String s) {
        return s.replace(" ", "%20");
    }
    private static void showErr(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
