package com.lapots.breed.dictionary.fx;

import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DictionaryFxController implements Initializable {
    @FXML
    private ComboBox<String> language_list;

    @FXML
    private Label status_label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        language_list.getItems().setAll("English", "French", "German", "Hebrew");
        JavaFxObservable.valuesOf(language_list.valueProperty())
                .subscribe(v -> status_label.setText(v), e -> System.out.println("Error occurred: " + e));
    }
}
