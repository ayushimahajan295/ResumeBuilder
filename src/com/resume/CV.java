package com.resume;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

public class CV {
    private JPanel cvPanel;
    private JTextField name;
    private JTextField address;
    private JTextField contact;
    private JTextField email;
    private JTextField skills1;
    private JTextField skills2;
    private JTextField skills3;
    private JTextField skills4;
    private JComboBox<String> work;
    private JTextField college;
    private JTextField qualiA;
    private JButton selectImageButton;
    private JLabel img;
    private JButton generateResumeButton;
    private JTextField qualiB;
    private JTextField location;
    private JComboBox<String> templateSelector;
    JFrame frame = new JFrame();

    public CV() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Resume Builder");
        frame.setSize(600, 800);

        // Initialize the cvPanel
        cvPanel = new JPanel(new GridBagLayout());
        frame.setContentPane(cvPanel);

        // Initialize and add components to the cvPanel
        initializeComponents();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initializeComponents() {
        // Initialize the text fields
        name = new JTextField(20);
        address = new JTextField(20);
        contact = new JTextField(20);
        email = new JTextField(20);
        skills1 = new JTextField(20);
        skills2 = new JTextField(20);
        skills3 = new JTextField(20);
        skills4 = new JTextField(20);
        qualiA = new JTextField(20);
        qualiB = new JTextField(20);
        location = new JTextField(20);
        college = new JTextField(20);

        // Initialize other components
        img = new JLabel();
        selectImageButton = new JButton("Select Image");
        generateResumeButton = new JButton("Generate Resume");

        // Initialize the combo box for work experience
        work = new JComboBox<>(new String[]{"< 1 year", "1-2 years", "2-5 years", "5+ years"});

        // Initialize the combo box for template selection
        templateSelector = new JComboBox<>(new String[]{"Template 1", "Template 2", "Template 3", "Template 4"});

        // Create and set layout for cvPanel using GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        addComponent(new JLabel("Name:"), gbc, 0, 0);
        addComponent(name, gbc, 1, 0);

        addComponent(new JLabel("Address:"), gbc, 0, 1);
        addComponent(address, gbc, 1, 1);

        addComponent(new JLabel("Contact:"), gbc, 0, 2);
        addComponent(contact, gbc, 1, 2);

        addComponent(new JLabel("Email:"), gbc, 0, 3);
        addComponent(email, gbc, 1, 3);

        addComponent(new JLabel("Skills 1:"), gbc, 0, 4);
        addComponent(skills1, gbc, 1, 4);

        addComponent(new JLabel("Skills 2:"), gbc, 0, 5);
        addComponent(skills2, gbc, 1, 5);

        addComponent(new JLabel("Skills 3:"), gbc, 0, 6);
        addComponent(skills3, gbc, 1, 6);

        addComponent(new JLabel("Skills 4:"), gbc, 0, 7);
        addComponent(skills4, gbc, 1, 7);

        addComponent(new JLabel("College:"), gbc, 0, 8);
        addComponent(college, gbc, 1, 8);

        addComponent(new JLabel("Qualification A:"), gbc, 0, 9);
        addComponent(qualiA, gbc, 1, 9);

        addComponent(new JLabel("Qualification B:"), gbc, 0, 10);
        addComponent(qualiB, gbc, 1, 10);

        addComponent(new JLabel("Work Experience:"), gbc, 0, 11);
        addComponent(work, gbc, 1, 11);

        addComponent(new JLabel("Template:"), gbc, 0, 12);
        addComponent(templateSelector, gbc, 1, 12);

        addComponent(selectImageButton, gbc, 0, 13);
        addComponent(location, gbc, 1, 13);

        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        cvPanel.add(img, gbc);

        gbc.gridy = 15;
        cvPanel.add(generateResumeButton, gbc);

        // Add action listeners
        addListeners();
    }

    private void addComponent(Component component, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        cvPanel.add(component, gbc);
    }

    private void addListeners() {
        selectImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png");
                fileChooser.addChoosableFileFilter(filter);
                int rs = fileChooser.showOpenDialog(null);
                if (rs == JFileChooser.APPROVE_OPTION) {
                    File selectedImage = fileChooser.getSelectedFile();
                    location.setText(selectedImage.getAbsolutePath());
                    img.setIcon(resize(location.getText()));
                }
            }
        });

        generateResumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name.getText().isEmpty() || contact.getText().isEmpty() || address.getText().isEmpty() || email.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter all details to generate CV.");
                } else {
                    try {
                        String selectedTemplate = (String) templateSelector.getSelectedItem();
                        switch (selectedTemplate) {
                            case "Template 1":
                                generatePDFTemplate1();
                                break;
                            case "Template 2":
                                generatePDFTemplate2();
                                break;
                            case "Template 3":
                                generatePDFTemplate3();
                                break;
                            case "Template 4":
                                generatePDFTemplate4();
                                break;
                        }
                        JOptionPane.showMessageDialog(null, "Resume was successfully generated.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });
    }

    public ImageIcon resize(String path) {
        ImageIcon myImg = new ImageIcon(path);
        Image image = myImg.getImage();
        Image newImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }

    private void generatePDFTemplate1() throws Exception {
        String filePath = "C:\\Users\\Dell\\Downloads\\resume_template1.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        addCommonContent(document, BaseColor.BLACK, FontFactory.HELVETICA);
        document.close();
    }

    private void generatePDFTemplate2() throws Exception {
        String filePath = "C:\\Users\\Dell\\Downloads\\resume_template2.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        addCommonContent(document, BaseColor.BLUE, FontFactory.COURIER);
        document.close();
    }

    private void generatePDFTemplate3() throws Exception {
        String filePath = "C:\\Users\\Dell\\Downloads\\resume_template3.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        addCommonContent(document, BaseColor.CYAN, FontFactory.TIMES);
        document.close();
    }

    private void generatePDFTemplate4() throws Exception {
        String filePath = "C:\\Users\\Dell\\Downloads\\resume_template4.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        addCommonContent(document, BaseColor.MAGENTA, FontFactory.TIMES_ITALIC);
        document.close();
    }

    private void addCommonContent(Document document, BaseColor baseColor, String fontType) throws Exception {
        com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(location.getText());
        img.setAbsolutePosition(473f, 750f);
        img.scaleAbsolute(80f, 70f);
        PdfPTable table = new PdfPTable(2);

        document.add(img);
        document.add(new Paragraph(name.getText(), FontFactory.getFont(fontType, 32, com.itextpdf.text.Font.BOLD, baseColor)));
        document.add(new Paragraph("", FontFactory.getFont(fontType, 9, baseColor)));
        document.add(new Paragraph("", FontFactory.getFont(fontType, 9, baseColor)));
        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------", FontFactory.getFont(fontType, 6, baseColor)));
        document.add(new Paragraph("CONTACT DETAILS", FontFactory.getFont(fontType, 9, com.itextpdf.text.Font.BOLD, baseColor)));
        document.add(new Paragraph(email.getText(), FontFactory.getFont(fontType, 7, baseColor)));
        document.add(new Paragraph(contact.getText(), FontFactory.getFont(fontType, 7, baseColor)));
        document.add(new Paragraph(address.getText(), FontFactory.getFont(fontType, 7, baseColor)));
        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------", FontFactory.getFont(fontType, 6, baseColor)));
        document.add(new Paragraph("SKILLS", FontFactory.getFont(fontType, 9, com.itextpdf.text.Font.BOLD, baseColor)));
        table.setHeaderRows(1);
        table.addCell(skills1.getText());
        table.addCell(skills2.getText());
        table.addCell(skills3.getText());
        table.addCell(skills4.getText());
        document.add(table);
        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------", FontFactory.getFont(fontType, 6, baseColor)));
        document.add(new Paragraph("QUALIFICATIONS", FontFactory.getFont(fontType, 9, com.itextpdf.text.Font.BOLD, baseColor)));
        document.add(new Paragraph(college.getText(), FontFactory.getFont(fontType, 7, baseColor)));
        document.add(new Paragraph(qualiA.getText(), FontFactory.getFont(fontType, 7, baseColor)));
        document.add(new Paragraph(qualiB.getText(), FontFactory.getFont(fontType, 7, baseColor)));
        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------", FontFactory.getFont(fontType, 6, baseColor)));
        document.add(new Paragraph("WORK EXPERIENCE", FontFactory.getFont(fontType, 10, com.itextpdf.text.Font.BOLD, baseColor)));
        document.add(new Paragraph("" + work.getSelectedItem(), FontFactory.getFont(fontType, 7, baseColor)));
        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------", FontFactory.getFont(fontType, 6, baseColor)));
        document.add(new Paragraph("REFERENCES", FontFactory.getFont(fontType, 9, com.itextpdf.text.Font.BOLD, baseColor)));
        document.add(new Paragraph("Available upon request", FontFactory.getFont(fontType, 6, baseColor)));
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CV();
            }
        });
    }
}