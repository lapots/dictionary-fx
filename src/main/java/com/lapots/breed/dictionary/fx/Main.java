package com.lapots.breed.dictionary.fx;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Injector injector = Guice.createInjector(new ApplicationModule());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dictionary.fxml"));
        loader.setControllerFactory(injector::getInstance);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Dictionary");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // TODO: reactive sample
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
