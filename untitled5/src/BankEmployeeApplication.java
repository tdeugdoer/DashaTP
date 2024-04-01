import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BankEmployeeApplication extends JFrame {

    private JLabel idValue;
    private JTextField nameTextField;
    private JTextField salaryTextField;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<BankEmployee> employees;
    
    public static void main(String[] args) { new BankEmployeeApplication(); }

    public BankEmployeeApplication() {
        setTitle("Back Employee application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        add(getInputPanel(), BorderLayout.NORTH);
        add(getTablePanel(), BorderLayout.CENTER);
        add(getButtonPanel(), BorderLayout.SOUTH);

        employees = BankEmployee.getListBankEmployee();
        for(BankEmployee employee : employees) {
            tableModel.addRow(new Object[] {employee.getId(), employee.getName(), employee.getSalary(), employee.getGender()});
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel getInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        JLabel idLabel = new JLabel("Id:");
        JLabel nameLabel = new JLabel("Имя:");
        JLabel salaryLabel = new JLabel("Зарплата:");
        JLabel genderLabel = new JLabel("Пол:");

        idValue = new JLabel();
        nameTextField = new JTextField();
        salaryTextField = new JTextField();
        salaryTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        maleRadioButton = new JRadioButton("Мужской");
        femaleRadioButton = new JRadioButton("Женский");

        ButtonGroup genderButtonGroup = new ButtonGroup();
        genderButtonGroup.add(maleRadioButton);
        maleRadioButton.setSelected(true);
        genderButtonGroup.add(femaleRadioButton);

        inputPanel.add(idLabel);
        inputPanel.add(idValue);
        inputPanel.add(nameLabel);
        inputPanel.add(nameTextField);
        inputPanel.add(salaryLabel);
        inputPanel.add(salaryTextField);
        inputPanel.add(genderLabel);
        inputPanel.add(maleRadioButton);
        inputPanel.add(new JLabel());  // Пустая метка для выравнивания
        inputPanel.add(femaleRadioButton);

        return inputPanel;
    }

    private JPanel getTablePanel() {
        tableModel = new DefaultTableModel(new Object[]{"Id","Имя", "Зарплата", "Пол"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Запрещаем редактирование всех ячеек
            }
        };

        table = new JTable(tableModel);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Проверка, что выбор строки завершен
                int selectedRow = table.getSelectedRow();
                // Добавьте свой код для обработки выбора строки
                if (selectedRow != -1) {
                    // Получение значений выбранной строки
                    idValue.setText(table.getValueAt(selectedRow, 0).toString());
                    nameTextField.setText(table.getValueAt(selectedRow, 1).toString());
                    salaryTextField.setText(table.getValueAt(selectedRow, 2).toString());
                    String gender = table.getValueAt(selectedRow, 3).toString();
                    if (gender.equals("Мужской")) {
                        maleRadioButton.setSelected(true);
                    } else femaleRadioButton.setSelected(true);
                }
            }
        });

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Сохранить");
        JButton updateButton = new JButton("Редактировать");
        JButton deleteButton = new JButton("Удалить");
        JButton clearButton = new JButton("Очистить");
        JButton saveToFileButton = new JButton("Сохранить в файл");

        saveButton.addActionListener(e -> { // обработка нажатия кнопки сохранения
            if (nameTextField.getText().isEmpty() || salaryTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Заполните все поля!");
            } else {
                String name = nameTextField.getText();
                int salary = Integer.parseInt(salaryTextField.getText());
                String gender;

                if (maleRadioButton.isSelected()) {
                    gender = "Мужской";
                } else gender = "Женский";

                BankEmployee employee = new BankEmployee(name, salary, gender);
                employees.add(employee);
                tableModel.addRow(new Object[]{employee.getId(), employee.getName(), employee.getSalary(), employee.getGender()});

                clearFields();
                JOptionPane.showMessageDialog(null, "Новый работник успешно добавлен!");
            }
        });

        updateButton.addActionListener(e -> { // обработка нажатия кнопки редактирования
            int selectedRowIndex = table.getSelectedRow();
            if (selectedRowIndex != -1) {
                int employeeId = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());

                if (nameTextField.getText().isEmpty() || salaryTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Заполните все поля!");
                    return;
                }

                String newName = nameTextField.getText();
                int newSalary = Integer.parseInt(salaryTextField.getText());
                String newGender;
                if (maleRadioButton.isSelected()) {
                    newGender = "Мужчина";
                } else newGender = "Женщина";

                BankEmployee foundEmployee = findBankEmployee(employeeId);
                if(foundEmployee.getName().equals(newName) && foundEmployee.getGender().equals(newGender) && foundEmployee.getSalary() == (newSalary)) {
                    JOptionPane.showMessageDialog(null, "Данные работника не были изменены!");
                    return;
                }

                foundEmployee.setName(newName);
                foundEmployee.setSalary(newSalary);
                foundEmployee.setGender(newGender);

                table.setValueAt(newName ,selectedRowIndex, 1);
                table.setValueAt(newSalary ,selectedRowIndex, 2);
                table.setValueAt(newGender ,selectedRowIndex, 3);

                clearFields();
                JOptionPane.showMessageDialog(null, "Работник успешно отредактирован!");
            } else JOptionPane.showMessageDialog(null, "Необходимо выбрать работника для редактирования!");
        });

        deleteButton.addActionListener(e -> { // обработка нажатия кнопки удаления
            int selectedRowIndex = table.getSelectedRow();
            if (selectedRowIndex != -1) {
                int employeeId = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
                employees.remove(findBankEmployee(employeeId));
                tableModel.removeRow(selectedRowIndex);

                clearFields();
                JOptionPane.showMessageDialog(null, "Работник успешно удалён!");
            } else JOptionPane.showMessageDialog(null, "Необходимо выбрать работника для удаления!");
        });

        saveToFileButton.addActionListener(e -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:/Учёба/Рис/untitled/untitled5/src/file.txt", true))) {
                for(BankEmployee employee : employees) {
                    writer.write("Id" + employee.getId() + "Имя: " + employee.getName() + "\n" + "Зарплата: " + employee.getSalary() + "\n" + "Пол: " + employee.getGender() + "\n\n");
                }
                writer.write("\n---------------------------------------------------------------\n");
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Данные успешно записаны в файл!");
        });

        clearButton.addActionListener(e -> clearFields());
        buttonPanel.add(saveButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveToFileButton);
        buttonPanel.add(clearButton);

        return buttonPanel;
    }


    private void clearFields() {
        idValue.setText("");
        nameTextField.setText("");
        salaryTextField.setText("");
        maleRadioButton.setSelected(true);
        table.clearSelection();
    }

    private BankEmployee findBankEmployee(int id) {
        for(BankEmployee employee : employees) {
            if(employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }
}