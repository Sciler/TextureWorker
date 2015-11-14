package de.sciler.textureworker.desktop;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FullscreenPreview extends Stage {
    public FullscreenPreview(Image popupImage){
        setTitle("Preview");

        ImageView fullscreenView = new ImageView(popupImage);
        ButtonBar fullscreenBar = new ButtonBar();

        ScrollPane fullscreenScroll = new ScrollPane(fullscreenView);

        Button zoomPlus = new Button("+");
        Button zoomMinus = new Button("-");
        Button abortFullscreen = new Button("Abort");

        setWidth(700);
        setHeight(500);

        fullscreenScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        fullscreenScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        ButtonBar.setButtonData(zoomPlus, ButtonBar.ButtonData.OTHER);
        ButtonBar.setButtonData(zoomMinus, ButtonBar.ButtonData.OTHER);
        ButtonBar.setButtonData(abortFullscreen, ButtonBar.ButtonData.CANCEL_CLOSE);

        fullscreenBar.getButtons().addAll(zoomPlus, zoomMinus, abortFullscreen);

        setScene(new Scene(new BorderPane(fullscreenScroll, null, null, fullscreenBar, null)));

        setResizable(true);

        zoomPlus.setOnAction(zoomPlusEvent -> {
            fullscreenView.setScaleX(fullscreenView.getScaleX() * 1.2);
            fullscreenView.setScaleY(fullscreenView.getScaleY() * 1.2);
        });

        zoomMinus.setOnAction(zoomPlusEvent -> {
            fullscreenView.setScaleX(fullscreenView.getScaleX() * 0.8);
            fullscreenView.setScaleY(fullscreenView.getScaleY() * 0.8);
        });


        abortFullscreen.setOnAction(abortEvent -> close());
    }
}
