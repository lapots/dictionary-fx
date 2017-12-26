package com.lapots.breed.dictionary.fx;

import io.reactivex.disposables.Disposable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.application.Application;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception { }

    private void reactive(Stage primaryStage) {
        VBox vBox = new VBox();
        Button button = new Button("Press Me");
        Button unsubscribeButton = new Button("Unsubscribe");
        Label countLabel = new Label("0");

        Disposable disposable = JavaFxObservable.actionEventsOf(button)
                .map(ae -> 1)
                .scan(0, (x, y) -> x + y)
                .subscribe(clickCount -> countLabel.setText(clickCount.toString()));
        unsubscribeButton.setOnAction(e -> disposable.dispose());

        vBox.getChildren().addAll(button, unsubscribeButton, countLabel);

        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();
    }
}
