package de.sciler.textureworker.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class desktopPacker {
    private JTextField inputField;
    private JTextField outputField;
    private JButton generateButton;
    private JButton abortButton;
    private JButton searchButtonInput;
    private JButton searchButtonOutput;
    private JTextField packagenameInput;
    private JLabel inputLabel;
    private JLabel outputLabel;
    private JLabel previewLabel;
    private JLabel packagenameLabel;
    public JPanel mainPanel;
    private JButton lastImageButton;
    private JButton nextImageButton;
    private JPanel previewPanel;
    private JScrollPane previewScroll;
    private JLabel preview;
    private JButton infoButton;
    private JComboBox<String> imageListComponent;
    private List<String> fileList;

    public desktopPacker(){
        ImageIcon iconLogo = new ImageIcon("images/filler.png");
        preview.setIcon(iconLogo);

        generateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!inputField.getText().equals("") && !outputField.getText().equals("") && !packagenameInput.getText().equals("")) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    PrintStream sumOut = new PrintStream(outputStream);
                    PrintStream recovery = System.out;
                    System.setOut(sumOut);
                    pack(inputField.getText(), outputField.getText(), packagenameInput.getText());
                    System.out.flush();
                    System.setOut(recovery);

                    JFrame resultFrame = new JFrame("Result");
                    resultFrame.setSize(600, 400);
                    resultFrame.setVisible(true);
                    JTextArea resultArea = new JTextArea();
                    resultArea.setEditable(false);
                    resultFrame.add(resultArea);

                    resultArea.setText(outputStream.toString());

                    ImageIcon iconLogo = new ImageIcon(outputField.getText() + "/" + packagenameInput.getText() + ".png");
                    preview.setIcon(iconLogo);

                    File[] fileArrayLocal = new File(outputField.getText()).listFiles();
                    fileList = new ArrayList<String>();

                    assert fileArrayLocal != null;
                    for (File file: fileArrayLocal) {
                        if (file.isFile()) {
                            if(file.getName().substring(file.getName().lastIndexOf(".")).equals(".png")) {
                                fileList.add(String.valueOf(file));
                                imageListComponent.addItem(file.getName());
                            }
                        }
                    }
                    imageListComponent.setSelectedIndex(0);

                }else{
                    JOptionPane.showMessageDialog(null, "All fields need to be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        searchButtonInput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame parentFrame = new JFrame();

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setDialogTitle("Input directories");
                int userSelection = fileChooser.showOpenDialog(parentFrame);

                if(userSelection == JFileChooser.APPROVE_OPTION){
                    inputField.setText(String.valueOf(fileChooser.getSelectedFile()));
                }
            }
        });
        abortButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        searchButtonOutput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame parentFrame = new JFrame();

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setDialogTitle("Output directories");
                int userSelection = fileChooser.showSaveDialog(parentFrame);

                if(userSelection == JFileChooser.APPROVE_OPTION){
                    outputField.setText(String.valueOf(fileChooser.getSelectedFile()));
                }
            }
        });
        infoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame infoFrame = new JFrame("Info");
                infoFrame.setSize(600, 400);
                infoFrame.setVisible(true);
            }
        });
        nextImageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(imageListComponent.getSelectedIndex() + 1 < imageListComponent.getItemCount()) imageListComponent.setSelectedIndex(imageListComponent.getSelectedIndex() + 1);
            }
        });
        lastImageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(imageListComponent.getSelectedIndex() - 1 >= 0) imageListComponent.setSelectedIndex(imageListComponent.getSelectedIndex() - 1);
            }
        });
        imageListComponent.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ImageIcon iconLogo = new ImageIcon(String.valueOf(fileList.get(imageListComponent.getSelectedIndex())));
                preview.setIcon(iconLogo);
            }
        });
    }
    public void pack(String input, String output, String name){
        TexturePacker.process(input, output, name);
    }
}
