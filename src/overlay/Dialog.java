/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overlay;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author Sikadie
 */
public class Dialog extends StackPane {

    private HBox hbox;
    private StackPane dialog;
    private VBox vbox;
    private ImageView searchVehicule;
    private ImageView createVehicule;
    private StackPane stackSearch;
    private StackPane stackCreate;

    public StackPane getStackSearch() {
        return stackSearch;
    }

    public void setStackSearch(StackPane stackSearch) {
        this.stackSearch = stackSearch;
    }

    public StackPane getStackCreate() {
        return stackCreate;
    }

    public void setStackCreate(StackPane stackCreate) {
        this.stackCreate = stackCreate;
    }

    public Dialog(String searchVehiculeURL, String createVehiculeURL, StackPane root, StackPane borderPane) {
        hbox = new HBox();
        vbox = new VBox();
        this.setStyle("-fx-background-radius: 15; -fx-background-color: #ffffff;");
        dialog = this;
        this.searchVehicule = new ImageView(new Image(Dialog.class.getResourceAsStream(searchVehiculeURL)));
        this.createVehicule = new ImageView(new Image(Dialog.class.getResourceAsStream(createVehiculeURL)));
        stackSearch = new StackPane();
        stackCreate = new StackPane();
        stackSearch.getChildren().add(searchVehicule);
        stackCreate.getChildren().add(createVehicule);
        stackSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                /*borderPane.getChildren().remove(2);
                 borderPane.getChildren().remove(1);*/

            }
        });
        stackCreate.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

            }
        });
        hbox.setSpacing(50);
        hbox.setPadding(new Insets(40, 30, 40, 30));
        hbox.getChildren().addAll(stackSearch, stackCreate);
        HBox.setHgrow(stackSearch, Priority.ALWAYS);
        HBox.setHgrow(stackCreate, Priority.ALWAYS);
        this.setPrefSize(350, 210);
        this.setMinSize(350, 210);
        this.setMaxSize(350, 210);

        HBox hboxButton = new HBox();
        //this.setPadding(new Insets(30));
        hboxButton.setAlignment(Pos.CENTER);

        Button boutonCancel = new Button("Cancel");
        //   boutonCancel.setPadding(new Insets(15));
        hboxButton.getChildren().add(boutonCancel);
        vbox.getChildren().add(hbox);
        vbox.getChildren().add(hboxButton);
        /*HBox region1 = new HBox();
         region1.setPrefSize(100, 100);
         vbox.getChildren().add(region1);*/
        this.getChildren().add(vbox);

        stackSearch.setCursor(Cursor.HAND);
        stackSearch.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                stackSearch.setStyle(" -fx-background-color: #cde4fa;");

            }
        });

        stackCreate.setCursor(Cursor.HAND);
        stackCreate.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                stackCreate.setStyle(" -fx-background-color: #cde4fa;");
            }
        });
        stackCreate.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                stackCreate.setStyle(" -fx-background-color: #ffffff;");
            }
        });

        stackSearch.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                stackSearch.setStyle(" -fx-background-color: #ffffff;");
            }
        });

        final Timeline timeline = new Timeline();
        root.setOpacity(0);

        final KeyValue kvv = new KeyValue(this.opacityProperty(), 1, Interpolator.EASE_BOTH);
        final KeyFrame kf = new KeyFrame(Duration.millis(100), kvv);
        timeline.getKeyFrames().add(kf);

        final Timeline timeline1 = new Timeline();

        root.setStyle("-fx-background-color: #333333;");
        final KeyValue kv = new KeyValue(root.opacityProperty(), 0.7, Interpolator.EASE_BOTH);
        final KeyFrame kff = new KeyFrame(Duration.millis(300), kv);

        timeline1.getKeyFrames().add(kff);
        timeline.play();
        timeline1.play();
        boutonCancel.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                TimelineBuilder.create().keyFrames(
                        new KeyFrame(Duration.millis(100),
                                new EventHandler<ActionEvent>() {
                                    public void handle(ActionEvent t) {

                                        dialog.setVisible(false);
                                        borderPane.getChildren().remove(2);
                                    }
                                },
                                new KeyValue(dialog.opacityProperty(), 0, Interpolator.EASE_BOTH)
                        ), new KeyFrame(Duration.millis(300),
                                new EventHandler<ActionEvent>() {
                                    public void handle(ActionEvent t) {

                                        //borderPane.setVisible(false);
                                        borderPane.getChildren().remove(1);
                                    }
                                },
                                new KeyValue(root.opacityProperty(), 0, Interpolator.EASE_BOTH)
                        )).build().play();

            }
        });
    }

    public ImageView getSearchVehicule() {
        return searchVehicule;
    }

    public void setSearchVehicule(ImageView searchVehicule) {
        this.searchVehicule = searchVehicule;
    }

    public ImageView getCreateVehicule() {
        return createVehicule;
    }

    public void setCreateVehicule(ImageView createVehicule) {
        this.createVehicule = createVehicule;
    }

}
