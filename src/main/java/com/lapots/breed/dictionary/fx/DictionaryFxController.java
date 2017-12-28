package com.lapots.breed.dictionary.fx;

import com.lapots.breed.dictionary.domain.Language;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.annotation.PreDestroy;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class DictionaryFxController implements Initializable {
    @FXML
    private ComboBox<Language> language_list;

    @FXML
    private Label status_label;

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

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
        List<String> supportedLanguages = Arrays.asList("English", "French", "German", "Hebrew");
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            supportedLanguages.forEach(lang -> {
                Language language = new Language();
                language.setId(lang.toUpperCase() + "_ID"); // TODO: move to generator
                language.setName(lang);
                session.save(language);
            });

            tx.commit();
        }

        try (Session session = sessionFactory.openSession()) {
            List<Language> languages = session.createQuery("from Language").list();
            language_list.getItems().setAll(languages);
        }
        language_list.getSelectionModel().selectFirst();
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(sessionFactory.getSessionFactoryOptions().getServiceRegistry());
    }
}
