package com.lapots.breed.dictionary.fx;

import com.lapots.breed.dictionary.domain.Language;
import com.lapots.breed.dictionary.repository.QueryTemplate;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DictionaryFxController implements Initializable {

    @Inject
    private QueryTemplate queryTemplate;

    @FXML
    private ComboBox<Language> language_list;

    @FXML
    private Label status_label;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        language_list.setCellFactory(new Callback<ListView<Language>, ListCell<Language>>() {
            @Override
            public ListCell<Language> call(ListView<Language> param) {
                return new ListCell<Language>() {
                    @Override
                    protected void updateItem(Language t, boolean bln) {
                        super.updateItem(t, bln);

                        if (t != null) {
                            setText(t.getName());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        // bind rx
        JavaFxObservable.valuesOf(language_list.valueProperty())
                .subscribe(v -> status_label.setText(v.getName()), e -> System.out.println("Error occurred: " + e));

        // generate data
        List<Language> langs = Stream.of("English", "French", "German", "Hebrew")
                .map(langLabel -> {
                    Language lang = new Language();
                    lang.setName(langLabel);
                    return lang;
                }).collect(Collectors.toList());

        queryTemplate.insert(langs);

        language_list
                .getItems()
                .setAll(queryTemplate.select("from Language"));
        language_list.getSelectionModel().selectFirst();
    }
}
