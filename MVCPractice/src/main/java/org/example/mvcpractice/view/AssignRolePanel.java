package org.example.mvcpractice.view;

import javax.swing.*;
import java.awt.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AssignRolePanel extends JPanel {
    private static final String BASE_URL = "http://localhost:8080/api/person-roles";

    private final JTextField txtPersonId = new JTextField(8);
    private final JTextField txtRoleId   = new JTextField(8);
    private final JTextArea  txtOut      = new JTextArea(10, 40);

    private final JTextField txtPRId      = new JTextField(8);
    private final JTextField txtNewRoleId = new JTextField(8);

    public AssignRolePanel() {
        setLayout(new BorderLayout(8,8));

        JPanel assign = new JPanel(new FlowLayout(FlowLayout.LEFT));
        assign.setBorder(BorderFactory.createTitledBorder("Asignar rol"));
        assign.add(new JLabel("personId:"));
        assign.add(txtPersonId);
        assign.add(new JLabel("roleId:"));
        assign.add(txtRoleId);
        JButton btnAssign = new JButton("Asignar");
        btnAssign.addActionListener(e -> doAssign());
        assign.add(btnAssign);

        JPanel update = new JPanel(new FlowLayout(FlowLayout.LEFT));
        update.setBorder(BorderFactory.createTitledBorder("Cambiar rol en asociación"));
        update.add(new JLabel("personRoleId:"));
        update.add(txtPRId);
        update.add(new JLabel("newRoleId:"));
        update.add(txtNewRoleId);
        JButton btnUpdate = new JButton("Actualizar rol");
        btnUpdate.addActionListener(e -> doUpdate());
        update.add(btnUpdate);

        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.add(assign);
        north.add(update);

        add(north, BorderLayout.NORTH);
        txtOut.setEditable(false);
        add(new JScrollPane(txtOut), BorderLayout.CENTER);
    }

    private void doAssign() {
        try {

            String body = """
                {"personId": %s, "roleId": %s}
            """.formatted(txtPersonId.getText().trim(), txtRoleId.getText().trim());

            String json = HttpClientHelper.postJson(BASE_URL, body);
            txtOut.setText("Asignado:\n" + prettify(json));
        } catch (Exception ex) {
            showErr(ex);
        }
    }

    private void doUpdate() {
        try {

            String url  = BASE_URL + "/" + txtPRId.getText().trim() + "/role";
            String body = """
                {"roleId": %s}
            """.formatted(txtNewRoleId.getText().trim());

            String json = HttpClientHelper.putJson(url, body);
            txtOut.setText("Actualizado:\n" + prettify(json));
        } catch (Exception ex) {
            showErr(ex);
        }
    }

    private static void showErr(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static String prettify(String json) {
        try {
            var om = new ObjectMapper();
            Object tree = om.readValue(json, Object.class);
            return om.writerWithDefaultPrettyPrinter().writeValueAsString(tree);
        } catch (Exception e) { return json; }
    }
}
