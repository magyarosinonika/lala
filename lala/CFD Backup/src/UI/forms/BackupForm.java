/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.forms;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class BackupForm extends JFrame {

    private JLabel labelDays;
    private JLabel labelHours;
    private JLabel labelMinutes;
    private JLabel labelSeconds;

    public BackupForm(String name) {
        super(name);
        initComponents();
    }

    public void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        this.add(panel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2));
        panel.add(buttonsPanel, BorderLayout.NORTH);

        JButton buttonNormalBackup = new JButton();
        buttonNormalBackup.setText("Normal Backup");
        buttonsPanel.add(buttonNormalBackup);

        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(1, 4));
        panel.add(timePanel, BorderLayout.CENTER);
        timePanel.setBorder(BorderFactory.createTitledBorder("Time: "));

        labelDays = new JLabel();
        labelHours = new JLabel();
        labelMinutes = new JLabel();
        labelSeconds = new JLabel();
        timePanel.add(labelDays);
        timePanel.add(labelHours);
        timePanel.add(labelMinutes);
        timePanel.add(labelSeconds);

        buttonNormalBackup.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //
                Date d1 = new Date();

                for (int i = 0; i < 10000000; ++i) {
                    for (int j = 0; j < 20000; ++j) {
                        //
                    }

                }
                Date d2 = new Date();
                long diff = d2.getTime() - d1.getTime();

                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);
                labelDays.setText(diffDays +" days ");
                labelHours.setText(diffHours +" hours ");
                labelMinutes.setText(diffMinutes +" minutes ");
                labelSeconds.setText(diffSeconds +" seconds ");

            }
        });

        JButton buttonBackupWithDependencies = new JButton();
        buttonBackupWithDependencies.setText("Backup with dependencies");
        buttonsPanel.add(buttonBackupWithDependencies);
        buttonBackupWithDependencies.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });




    }
}
