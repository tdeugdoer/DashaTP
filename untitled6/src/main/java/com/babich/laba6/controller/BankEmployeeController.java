package com.babich.laba6.controller;

import com.babich.laba6.model.BankEmployee;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BankEmployeeController {

    @FXML
    private Label idValue;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField salaryTextField;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private TableView<BankEmployee> tableView;
    private ObservableList<BankEmployee> employees;

    public BankEmployeeController() {
        employees = BankEmployee.getListBankEmployee();
    }

    @FXML
    private void initialize() {
        ToggleGroup group = new ToggleGroup();
        maleRadioButton.setToggleGroup(group);
        femaleRadioButton.setToggleGroup(group);

        TextFormatter<String> numericFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        });
        salaryTextField.setTextFormatter(numericFormatter);

        fillTable();
        selectedItemListener();
    }

    @FXML
    private void saveButtonAction() {
        if (nameTextField.getText().isEmpty() || salaryTextField.getText().isEmpty()) {
            showAlert("Ошибка", "Заполните все поля!");
        } else {
            String name = nameTextField.getText();
            int salary = Integer.parseInt(salaryTextField.getText());
            String gender = maleRadioButton.isSelected() ? "Мужской" : "Женский";

            BankEmployee employee = new BankEmployee(name, salary, gender);
            employees.add(employee);
            clearFields();

            showAlert("Успех", "Новый работник успешно добавлен!");
        }
    }

    @FXML
    private void updateButtonAction() {
        BankEmployee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            if (nameTextField.getText().isEmpty() || salaryTextField.getText().isEmpty()) {
                showAlert("Ошибка", "Заполните все поля!");
                return;
            }
            selectedEmployee.setName(nameTextField.getText());
            selectedEmployee.setSalary(Integer.parseInt(salaryTextField.getText()));

            if (maleRadioButton.isSelected()) {
                selectedEmployee.setGender("Мужской");
            } else {
                selectedEmployee.setGender("Женский");
            }

            clearFields();
            showAlert("Успех", "Работник успешно отредактирован!");
        } else {
            showAlert("Ошибка", "Необходимо выбрать работника для редактирования!");
        }

    }

    @FXML
    private void deleteButtonAction() {
        BankEmployee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employees.remove(selectedEmployee);

            clearFields();
            showAlert("Успех", "Работник успешно удалён!");
        } else {
            showAlert("Ошибка", "Необходимо выбрать работника для удаления!");
        }
    }

    @FXML
    private void clearButtonAction() {
        clearFields();
    }

    @FXML
    private void saveToFileButtonAction() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:/Учёба/Рис/untitled/untitled6/src/main/java/com/babich/laba6/file.txt", true))) {
            for(BankEmployee employee : employees) {
                writer.write(employee + "\n");
            }
            writer.write("\n---------------------------------------------------------------\n");
        } catch (IOException exception) {
            showAlert("Ошибка", "Произошла ошибка при записи в файл!");
            System.out.println(exception.getMessage());
        }
        showAlert("Успех", "Данные успешно записаны в файл!");
    }

    private void fillTable() {
        tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("salary"));
        tableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("gender"));
        tableView.setItems(employees);
    }

    private void selectedItemListener() {
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                BankEmployee selectedObject = tableView.getSelectionModel().getSelectedItem();

                int id = selectedObject.getId();
                String name = selectedObject.getName();
                int salary = selectedObject.getSalary();
                String gender = selectedObject.getGender();

                idValue.setText(String.valueOf(id));
                nameTextField.setText(name);
                salaryTextField.setText(String.valueOf(salary));

                if (gender.equals("Мужской")) {
                    maleRadioButton.setSelected(true);
                } else {
                    femaleRadioButton.setSelected(true);
                }
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        idValue.setText("");
        nameTextField.setText("");
        salaryTextField.setText("");
        maleRadioButton.setSelected(true);
        tableView.getSelectionModel().clearSelection();
        tableView.refresh();
    }
}
