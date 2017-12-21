package com.lapots.breed.generator;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import lombok.AllArgsConstructor;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ScriptGeneratorController implements Initializable {
    @FXML
    private TextField tou_id;
    @FXML
    private TextField author_name;
    @FXML
    private TextField author_email;
    @FXML
    private TextField releaseVersion;
    @FXML
    private TextField question_id;
    @FXML
    private CheckBox is_mandatory;
    @FXML
    private CheckBox is_pick;
    @FXML
    private TableView<String> select_values;
    @FXML
    private TextField output;

    @FXML
    private TableColumn<String, String> select_value_column;

    private TemplateCompiler templateCompiler = new TemplateCompiler();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");

    @FXML
    protected void handleGenerateScriptButtonAction(ActionEvent event) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tou_id", tou_id.getText().toUpperCase());
        parameters.put("author_name", author_name.getText());
        parameters.put("author_email", author_email.getText());
        parameters.put("release_version", releaseVersion.getText());
        parameters.put("question_id", question_id.getText().toUpperCase());
        parameters.put("is_mandatory", is_mandatory.isSelected());
        parameters.put("changeset_id", LocalDateTime.now().format(formatter));

        if (is_pick.isSelected()) {
            PickValues pickValues = processPickValues();
            parameters.put("inserts", pickValues.inserts);
            parameters.put("rollbacks", pickValues.rollbacks);
        } else {
            parameters.put("inserts", Collections.emptyList());
            parameters.put("rollbacks", Collections.emptyList());
        }

        templateCompiler.compileAndOutput("/templates/question.ftl", parameters);
        templateCompiler.compileToFile("/templates/question.ftl", parameters, output.getText());
    }

    @FXML
    protected void handleOnCheckPickQuestion(ActionEvent event) {
        List<String> emptyTen = Arrays.asList("", "", "", "", "", "", "", "", "", "", "");
        select_values.getItems().addAll(emptyTen);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        select_value_column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()));
        select_value_column.setCellFactory(TextFieldTableCell.forTableColumn());
        select_value_column.setOnEditCommit((TableColumn.CellEditEvent<String, String> event) -> {
            TablePosition<String, String> pos = event.getTablePosition();
            String selectValueId = event.getNewValue();
            int row = pos.getRow();
            select_values.getItems().set(row, selectValueId);
        });
    }

    @AllArgsConstructor
    private static class PickValues {
        private List<String> inserts;
        private List<String> rollbacks;
    }

    // can be refactored though
    private PickValues processPickValues() {
        if (select_values.getItems().size() == 0) {
            return new PickValues(Collections.emptyList(), Collections.emptyList());
        } else {
            String touId = tou_id.getText();
            List<String> inserts = new ArrayList<>();
            List<String> rollbacks = new ArrayList<>();
            ObservableList<TableColumn<String, ?>> columns = select_values.getColumns();

            for (Object row : select_values.getItems()) {
                for (TableColumn column : columns) {
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("select_value_id", column.getCellObservableValue(row).getValue());
                    parameters.put("tou_id", touId);
                    inserts.add(
                            templateCompiler.compile("/templates/insert.ftl", parameters));
                    rollbacks.add(
                            templateCompiler.compile("/templates/rollback.ftl", parameters));
                }
            }
            return new PickValues(inserts, rollbacks);
        }
    }
}
