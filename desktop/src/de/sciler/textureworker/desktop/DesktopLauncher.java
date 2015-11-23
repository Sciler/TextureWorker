package de.sciler.textureworker.desktop;


import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import javafx.application.Application;
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
        ButtonBar bottomMenu = new ButtonBar();

        Button abortButton = new Button("Abort");
        Button generateButton = new Button("Generate");
        Button infoButton = new Button("Info");

        ButtonBar.setButtonData(abortButton, ButtonBar.ButtonData.LEFT);
        ButtonBar.setButtonData(infoButton, ButtonBar.ButtonData.OTHER);
        ButtonBar.setButtonData(generateButton, ButtonBar.ButtonData.RIGHT);
        bottomMenu.getButtons().addAll(abortButton, infoButton, generateButton);
        rootLayout.setBottom(bottomMenu);

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
        TitledPane proSettingsPane = new TitledPane();

        sourcePane.setText("Source");
        settingsPane.setText("Pack Settings");
        proSettingsPane.setText("Professional Settings");

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
        Button popupButton = new Button("Popup");

        previewButtonHolder.getChildren().addAll(previousButton, nextButton);
        previewHolder.getChildren().addAll(previewLister, previewButtonHolder, popupButton);

        previewPane.setContent(previewHolder);
        rightSideMenu.getPanes().add(previewPane);

        //SETTINGS PANE
        VBox settingsHolder = new VBox();
        HBox settingsMaxSize = new HBox();
        HBox settingsMinSize = new HBox();
        HBox settingsPadHolder = new HBox();
        HBox settingsFormat = new HBox();
        HBox settingsQualityHolder = new HBox();
        HBox settingsAlias = new HBox();

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

        TextField paddingX = new TextField();
        TextField paddingY = new TextField();

        paddingX.setMaxWidth(50);
        paddingY.setMaxWidth(50);

        settingsPadHolder.getChildren().addAll(new Label("Padding X:"), paddingX, new Label("Padding Y:"), paddingY);
        settingsHolder.getChildren().add(settingsPadHolder);

        CheckBox aliasCheck = new CheckBox("Alias");
        aliasCheck.setSelected(true);

        settingsAlias.getChildren().add(aliasCheck);
        settingsHolder.getChildren().add(settingsAlias);

        ChoiceBox<String> formatChoice = new ChoiceBox<>();
        formatChoice.getItems().add("PNG");
        formatChoice.getItems().add("JPG/JPEG");

        settingsFormat.getChildren().addAll(new Label("Quality:"), formatChoice);

        settingsHolder.getChildren().add(settingsFormat);

        Slider settingsQualitySlider = new Slider();

        settingsQualitySlider.setMax(1);
        settingsQualitySlider.setValue(1);

        settingsQualityHolder.getChildren().addAll(new Label("JPG Quality:"), settingsQualitySlider);

        settingsHolder.getChildren().add(settingsQualityHolder);
        settingsPane.setContent(settingsHolder);

        leftSideMenu.getPanes().add(settingsPane);

        //PROFESSIONAL SETTINGS
        VBox proHolder = new VBox();
        VBox proRotate = new VBox();
        VBox combineSubDir = new VBox();
        VBox stripWhite = new VBox();
        VBox proGrid = new VBox();
        HBox proDebug = new HBox();

        CheckBox rotationCheck = new CheckBox("Rotation");
        rotationCheck.setSelected(false);

        CheckBox combineSubDirCheck = new CheckBox("Combine subdirectories");
        combineSubDirCheck.setSelected(false);

        CheckBox stripWhiteCheckX = new CheckBox("Strip whitespace X");
        stripWhiteCheckX.setSelected(false);

        CheckBox stripWhiteCheckY = new CheckBox("Strip whitespace Y");
        stripWhiteCheckY.setSelected(false);

        CheckBox gridCheck = new CheckBox("Grid");
        gridCheck.setSelected(false);

        CheckBox debugCheck = new CheckBox("Debug");
        debugCheck.setSelected(false);

        proRotate.getChildren().add(rotationCheck);
        proHolder.getChildren().add(proRotate);

        combineSubDir.getChildren().add(combineSubDirCheck);
        proHolder.getChildren().add(combineSubDir);

        proHolder.getChildren().add(new Separator());

        stripWhite.getChildren().addAll(stripWhiteCheckX, stripWhiteCheckY);
        proHolder.getChildren().add(stripWhite);

        proHolder.getChildren().add(new Separator());

        proGrid.getChildren().add(gridCheck);
        proHolder.getChildren().add(proGrid);

        proDebug.getChildren().add(debugCheck);
        proHolder.getChildren().add(proDebug);

        proSettingsPane.setContent(proHolder);
        leftSideMenu.getPanes().add(proSettingsPane);

        //ADD TO LAYOUT
        leftSideMenuBar.getChildren().add(leftSideMenu);
        rightSideMenuBar.getChildren().add(rightSideMenu);

        rootLayout.setTop(menu);

        ImageView preview = new ImageView();
        ScrollPane sp = new ScrollPane();
        sp.setContent(preview);
        rootLayout.setCenter(sp);

        primaryStage.setScene(new Scene(rootLayout, 900, 700));
        primaryStage.show();

        formatChoice.getSelectionModel().selectFirst();
        settingsQualitySlider.setDisable(true);

        //ADD LISTENER
        abortButton.setOnAction(e -> System.exit(0));

        popupButton.setOnAction(e -> new FullscreenPreview(preview.getImage()).show());

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

        formatChoice.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() == 0){
                settingsQualitySlider.setDisable(true);
            }else{
                settingsQualitySlider.setDisable(false);
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
                tSettings.pot = false;

                //SET SETTINGS VALUE
                if(!maxWidthField.getText().isEmpty()) {
                    tSettings.maxWidth = Integer.parseInt(maxWidthField.getText());
                }
                if(!maxHeightField.getText().isEmpty()){
                    tSettings.maxHeight = Integer.parseInt(maxHeightField.getText());
                }
                if(!minWidthField.getText().isEmpty()){
                    tSettings.minWidth = Integer.parseInt(minWidthField.getText());
                }
                if(!minHeightField.getText().isEmpty()){
                    tSettings.minHeight = Integer.parseInt(minHeightField.getText());
                }
                if(!paddingX.getText().isEmpty()){
                    tSettings.paddingX = Integer.parseInt(paddingX.getText());
                }
                if(!paddingY.getText().isEmpty()) {
                    tSettings.paddingY = Integer.parseInt(paddingY.getText());
                }
                if(formatChoice.getSelectionModel().isSelected(1)){
                    tSettings.outputFormat = "jpg";
                    tSettings.jpegQuality = (float) settingsQualitySlider.getValue();
                }else if(formatChoice.getSelectionModel().isSelected(0)){
                    tSettings.outputFormat = "png";
                }


                tSettings.alias = aliasCheck.isSelected();
                tSettings.debug = debugCheck.isSelected();
                tSettings.rotation = rotationCheck.isSelected();
                tSettings.grid = gridCheck.isSelected();
                tSettings.combineSubdirectories = combineSubDirCheck.isSelected();
                tSettings.stripWhitespaceX = stripWhiteCheckX.isSelected();
                tSettings.stripWhitespaceY = stripWhiteCheckY.isSelected();

                //PACK EVERYTHING
                if(pack(tSettings, inputField.getText(), outputField.getText(), nameField.getText())) {
                    System.out.flush();
                    System.setOut(recovery);

                    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                    infoAlert.setHeaderText("Information");
                    infoAlert.setContentText(outputStream.toString());
                    infoAlert.showAndWait();

                    previewLister.getItems().clear();

                    File[] fileArrayLocal = new File(outputField.getText()).listFiles();
                    fileList = new ArrayList<>();

                    assert fileArrayLocal != null;
                    for (File file : fileArrayLocal) {
                        if (file.isFile()) {
                            if (file.getName().substring(file.getName().lastIndexOf(".")).equals("." + tSettings.outputFormat)) {
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

        previewLister.setOnAction(e -> {
            if(previewLister.getSelectionModel().getSelectedIndex() >= 0) {
                preview.setImage(new Image("file://" + fileList.get(previewLister.getSelectionModel().getSelectedIndex())));
            }
        });

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
