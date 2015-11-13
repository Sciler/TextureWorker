package de.sciler.textureworker.desktop;


import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DesktopLauncher extends Application {
    private List<String> fileList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //WARNING: PRE_ALPHA!
        Alert alphaAlert = new Alert(Alert.AlertType.WARNING);
        alphaAlert.setTitle("Pre-Alpha status");
        alphaAlert.setHeaderText("Unstable Pre-Alpha");
        alphaAlert.setContentText("This is a unstable Pre-Alpha build.");
        alphaAlert.showAndWait();


        //GENERATE WINDOW
        primaryStage.setTitle("TextureWorker");

        BorderPane rootLayout = new BorderPane();
        HBox bottomLayout = new HBox();
        VBox leftSideMenuBar = new VBox();
        VBox rightSideMenuBar = new VBox();
        rootLayout.setBottom(bottomLayout);
        rootLayout.setLeft(leftSideMenuBar);
        rootLayout.setRight(rightSideMenuBar);

        //CREATE COMPONENTS
        Button abortButton = new Button("Abort");
        Button generateButton = new Button("Generate");
        Button infoButton = new Button("Info");

        MenuBar menu = new MenuBar();


        //TODO: ADD MORE MENU ITEMS
        Menu fileMenu = new Menu("File");
        Menu viewMenu = new Menu("View");
        Menu helpMenu = new Menu("Help");

        MenuItem prefMenuItem = new MenuItem("Preferences");

        MenuItem docsMenuItem = new MenuItem("Documentation");

        //ADD TO MENU
        fileMenu.getItems().add(prefMenuItem);

        helpMenu.getItems().add(docsMenuItem);

        //ADD TO MENUBAR
        menu.getMenus().addAll(fileMenu, viewMenu, helpMenu);
        
        menu.setUseSystemMenuBar(true);

        //ADD TO LEFT SIDEBAR
        Accordion leftSideMenu = new Accordion();
        TitledPane sourcePane = new TitledPane();
        TitledPane settingsPane = new TitledPane();

        sourcePane.setText("Source");
        settingsPane.setText("Pack Settings");

        //ADD TO RIGHT SIDEBAR
        Accordion rightSideMenu = new Accordion();
        TitledPane previewPane = new TitledPane();

        previewPane.setText("Preview");

        //SOURCE
        VBox sourceHolder = new VBox();
        HBox packName = new HBox();
        packName.getChildren().add(new Label("Name:"));
        TextField nameField = new TextField();
        packName.getChildren().add(nameField);
        sourceHolder.getChildren().add(packName);

        HBox inputDir = new HBox();
        inputDir.getChildren().add(new Label("Input:"));
        TextField inputField = new TextField();
        Button getIDir = new Button("Get input directory");
        inputDir.getChildren().add(inputField);
        inputDir.getChildren().add(getIDir);
        sourceHolder.getChildren().add(inputDir);

        HBox outputDir = new HBox();
        outputDir.getChildren().add(new Label("Output:"));
        TextField outputField = new TextField();
        Button getODir = new Button("Get output directory");
        outputDir.getChildren().add(outputField);
        outputDir.getChildren().add(getODir);
        sourceHolder.getChildren().add(outputDir);

        sourcePane.setContent(sourceHolder);

        leftSideMenu.getPanes().add(sourcePane);

        //PREVIEW
        VBox previewHolder = new VBox();
        HBox previewButtonHolder = new HBox();
        ComboBox<String> previewLister = new ComboBox<>();

        Button nextButton = new Button("Next");
        Button previousButton = new Button("Previous");
        previewButtonHolder.getChildren().addAll(previousButton, nextButton);
        previewHolder.getChildren().addAll(previewLister, previewButtonHolder);

        previewPane.setContent(previewHolder);

        rightSideMenu.getPanes().add(previewPane);

        //SETTINGS PANE
        VBox settingsHolder = new VBox();
        HBox settingsMaxSize = new HBox();

        HBox settingsMinSize = new HBox();

        TextField minWidthField = new TextField();
        TextField minHeightField = new TextField();

        minWidthField.setMaxWidth(50);
        minHeightField.setMaxWidth(50);

        settingsMinSize.getChildren().addAll(new Label("Min Width:"), minWidthField, new Label("Min Height:"), minHeightField);
        settingsHolder.getChildren().add(settingsMinSize);

        TextField maxWidthField = new TextField();
        TextField maxHeightField = new TextField();

        maxWidthField.setMaxWidth(50);
        maxHeightField.setMaxWidth(50);

        settingsMaxSize.getChildren().addAll(new Label("Max Width:"), maxWidthField, new Label("Max Height:"), maxHeightField);
        settingsHolder.getChildren().add(settingsMaxSize);

        settingsPane.setContent(settingsHolder);

        leftSideMenu.getPanes().add(settingsPane);

        //ADD TO LAYOUT
        bottomLayout.getChildren().add(abortButton);
        bottomLayout.getChildren().add(infoButton);
        bottomLayout.getChildren().add(generateButton);
        bottomLayout.setAlignment(Pos.BOTTOM_RIGHT);

        leftSideMenuBar.getChildren().add(leftSideMenu);
        rightSideMenuBar.getChildren().add(rightSideMenu);

        rootLayout.setTop(menu);

        ImageView preview = new ImageView();
        ScrollPane sp = new ScrollPane();
        sp.setContent(preview);
        rootLayout.setCenter(sp);

        primaryStage.setScene(new Scene(rootLayout, 900, 700));
        primaryStage.show();

        //ADD LISTENER
        abortButton.setOnAction(e -> System.exit(0));

        infoButton.setOnAction(e -> {
            Alert infoDialog = new Alert(Alert.AlertType.INFORMATION);
            infoDialog.setContentText("Open Source Texture packer GUI for the LibGDX Texture packer");
            infoDialog.setHeaderText(null);
            infoDialog.show();
        });

        getIDir.setOnAction(e -> {
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Get input directory");

            File dir = dirChooser.showDialog(primaryStage);

            if(dir != null){
                inputField.setText(String.valueOf(dir));
            }
        });

        getODir.setOnAction(e -> {
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Get output directory");

            File dir = dirChooser.showDialog(primaryStage);

            if(dir != null){
                outputField.setText(String.valueOf(dir));
            }
        });

        generateButton.setOnAction(e -> {
            if(!inputField.getText().isEmpty() && !outputField.getText().isEmpty() && !nameField.getText().isEmpty()) {
                previewLister.getItems().clear();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                PrintStream sumOut = new PrintStream(outputStream);
                PrintStream recovery = System.out;
                System.setOut(sumOut);

                //GET SETTINGS
                TexturePacker.Settings tSettings = new TexturePacker.Settings();
                if(!maxWidthField.getText().isEmpty() && !maxHeightField.getText().isEmpty()) {
                    tSettings.pot = false;
                    tSettings.maxWidth = Integer.parseInt(maxWidthField.getText());
                    tSettings.maxHeight = Integer.parseInt(maxHeightField.getText());
                }
                if(pack(tSettings, inputField.getText(), outputField.getText(), nameField.getText())) {

                    System.out.flush();
                    System.setOut(recovery);

                    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                    infoAlert.setHeaderText("Information");
                    infoAlert.setContentText(outputStream.toString());
                    infoAlert.showAndWait();

                    File[] fileArrayLocal = new File(outputField.getText()).listFiles();
                    fileList = new ArrayList<>();

                    assert fileArrayLocal != null;
                    for (File file : fileArrayLocal) {
                        if (file.isFile()) {
                            if (file.getName().substring(file.getName().lastIndexOf(".")).equals(".png")) {
                                fileList.add(String.valueOf(file));
                                previewLister.getItems().add(file.getName());
                            }
                        }
                    }
                    previewLister.getSelectionModel().selectFirst();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Empty fields");
                alert.setContentText("All fields need to be filled");
                alert.showAndWait();
            }
        });

        previewLister.setOnAction(e -> preview.setImage(new Image("file://" + fileList.get(previewLister.getSelectionModel().getSelectedIndex()))));

        nextButton.setOnAction(e -> previewLister.getSelectionModel().selectNext());

        previousButton.setOnAction(e -> previewLister.getSelectionModel().selectPrevious());
    }
    public boolean pack(TexturePacker.Settings settings, String input, String output, String name){
        try {
            TexturePacker.process(settings, input, output, name);
            return true;
        }catch (RuntimeException ex){
            Alert exceptionAlert = new Alert(Alert.AlertType.ERROR);
            exceptionAlert.setTitle("Error!");
            exceptionAlert.setHeaderText("Exception Error");
            exceptionAlert.setContentText("Error packing Images");

            BorderPane dialogLayout = new BorderPane();
            dialogLayout.setTop(new Label("The exception was:"));

            TextArea exArea = new TextArea();

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);

            exArea.setText(sw.toString());
            dialogLayout.setCenter(exArea);

            exceptionAlert.getDialogPane().setExpandableContent(dialogLayout);

            exceptionAlert.showAndWait();
            return false;
        }
    }
}
