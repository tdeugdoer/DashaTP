import input.Input;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CollectionTest {

    static class SalaryComparator implements Comparator<BankEmployee> {
        @Override
        public int compare(BankEmployee employee1, BankEmployee employee2) {
            return Double.compare(employee1.getSalary(), employee2.getSalary());
        }
    }

    public static void main(String[] args) {
        List<BankEmployee> bankEmployees = BankEmployee.getListBankEmployee(); //

        LinkedList<BankEmployee> linkedList = new LinkedList<>(bankEmployees);
        TreeSet<BankEmployee> treeSet = new TreeSet<>(new SalaryComparator());
        treeSet.addAll(bankEmployees);
        PriorityQueue<BankEmployee> priorityQueue = new PriorityQueue<>(new SalaryComparator());
        priorityQueue.addAll(bankEmployees);

        Scanner scanner = new Scanner(System.in);

        String choice;
        do {
            System.out.println("--------- Меню ---------");
            System.out.println("1. Добавить сотрудника");
            System.out.println("2. Удалить сотрудника");
            System.out.println("3. Редактировать сотрудника");
            System.out.println("4. Вывести список сотрудников");
            System.out.println("5. Вывести список сотрудников в файл");
            System.out.println("6. Найти сотрудника по ID");
            System.out.println("7. Отсортировать сотрудников по возрастанию зарплаты");
            System.out.println("8. Отсортировать сотрудников по убыванию зарплаты");
            System.out.println("9. Выборка сотрудников");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // Добавление сотрудника
                    BankEmployee newEmployee = createEmployee(scanner);
                    linkedList.add(newEmployee);
                    treeSet.add(newEmployee);
                    priorityQueue.add(newEmployee);
                    System.out.println("Сотрудник успешно добавлен.");
                    break;
                case "2":
                    // Удаление сотрудника
                    int removeId = Input.inputInt(scanner, "Введите ID сотрудника для удаления");
                    removeEmployeeById(linkedList, removeId);
                    removeEmployeeById(treeSet, removeId);
                    removeEmployeeById(priorityQueue, removeId);
                    break;
                case "3":
                    // Редактирование сотрудника
                    int editId = Input.inputInt(scanner,"Введите ID сотрудника для редактирования");
                    editEmployeeById(linkedList, editId, scanner);
                    editEmployeeById(treeSet, editId, scanner);
                    editEmployeeById(priorityQueue, editId, scanner);
                    break;
                case "4":
                    // Вывод списка сотрудников
                    System.out.println("Список сотрудников (LinkedList):");
                    System.out.println(linkedList);
                    System.out.println("Список сотрудников (TreeSet):");
                    System.out.println(treeSet);
                    System.out.println("Список сотрудников (PriorityQueue):");
                    System.out.println(priorityQueue);
                    break;
                case "5":
                    // Вывод списка сотрудников в файл
                    saveEmployeesToFile(linkedList);
                    System.out.println("Данные сохранены в файл");
                    break;
                case "6":
                    // Поиск сотрудника по ID
                    int searchId = Input.inputInt(scanner, "Введите ID сотрудника для поиска");
                    searchEmployeeById(linkedList, searchId);
                    searchEmployeeById(treeSet, searchId);
                    searchEmployeeById(priorityQueue, searchId);
                    break;
                case "7":
                    // Сортировка сотрудников по возрастанию зарплаты
                    sortEmployeesBySalary(linkedList);
                    treeSet = sortEmployeesBySalary(treeSet);
                    priorityQueue = sortEmployeesBySalary(priorityQueue);
                    System.out.println("Сотрудники успешно отсортированы по возрастанию зарплаты.");
                    break;
                case "8":
                    // Сортировка сотрудников по убыванию зарплаты
                    sortEmployeesBySalaryDescending(linkedList);
                    treeSet = sortEmployeesBySalaryDescending(treeSet);
                    priorityQueue = sortEmployeesBySalaryDescending(priorityQueue);
                    System.out.println("Сотрудники успешно отсортированы по убыванию зарплаты.");
                    break;
                case "9":
                    // Выборка сотрудников
                    double minSalary = Input.inputDouble(scanner, "Введите минимальную зарплату");
                    double maxSalary = Input.inputDouble(scanner, "Введите максимальную зарплату");
                    filterEmployeesBySalary(linkedList, minSalary, maxSalary);
                    filterEmployeesBySalary(treeSet, minSalary, maxSalary);
                    filterEmployeesBySalary(priorityQueue, minSalary, maxSalary);
                    System.out.println("Сотрудники успешно отфильтрованы по зарплате.");
                    break;
                case "0":
                    // Выход из программы
                    System.out.println("Программа завершена.");
                    break;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
                    break;
            }

            System.out.println();
        } while (!choice.equals("0"));
    }

    private static BankEmployee createEmployee(Scanner scanner) {
        System.out.print("Введите имя сотрудника: ");
        String name = scanner.next();
        double salary = Input.inputDouble(scanner, "Введите зарплату сотрудника: ");
        return new BankEmployee(name, salary);
    }

    private static <T extends Collection<BankEmployee>> void removeEmployeeById(T collection, int id) {
        for (BankEmployee employee : collection) {
            if (employee.getId() == id) {
                collection.remove(employee); // Удаляем объект, соответствующий идентификатору
                System.out.println("Объект с id = " + id +" удалён");
                return; // Прерываем цикл после удаления
            }
        }
        System.out.println("Объект с таким id не найден");
    }

    private static <T extends Collection<BankEmployee>> void editEmployeeById(T collection, int id, Scanner scanner) {
        for (BankEmployee employee : collection) {
            if (employee.getId() == id) {
                System.out.print("Введите новое имя сотрудника: ");
                employee.setName(scanner.next());
                employee.setSalary(Input.inputDouble(scanner, "Введите новую зарплату сотрудника: "));
                return;
            }
        }
        System.out.println("Объект с таким id не найден");
    }

    private static void saveEmployeesToFile(LinkedList<BankEmployee> linkedList, TreeSet<BankEmployee> treeSet, PriorityQueue<BankEmployee> priorityQueue) {
        try (FileWriter fileWriter = new FileWriter("D:/Учёба/Рис/untitled/untitled4/src/file.txt");
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write("LinkedList\n");
            for (BankEmployee employee : linkedList) {
                bufferedWriter.write(employee.toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.write("TreeSet\n");
            for (BankEmployee employee : treeSet) {
                bufferedWriter.write(employee.toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.write("PriorityQueue\n");
            for (BankEmployee employee : priorityQueue) {
                bufferedWriter.write(employee.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static <T extends Collection<BankEmployee>> void searchEmployeeById(T collection, int id) {
        for (BankEmployee employee : collection) {
            if (employee.getId() == id) {
                System.out.println(employee);
                return;
            }
        }
        System.out.println("Объект с таким id не найден");
    }

    private static void sortEmployeesBySalary(LinkedList<BankEmployee> collection) {
        collection.sort(new SalaryComparator());
    }

    private static TreeSet<BankEmployee> sortEmployeesBySalary(TreeSet<BankEmployee> collection) {
        List<BankEmployee> list = new LinkedList<>(collection);
        list.sort(new SalaryComparator());

        TreeSet<BankEmployee> newTreeSet = new TreeSet<>(new SalaryComparator());
        newTreeSet.addAll(list);
        return newTreeSet;
    }

    private static PriorityQueue<BankEmployee> sortEmployeesBySalary(PriorityQueue<BankEmployee> collection) {
        List<BankEmployee> list = new LinkedList<>(collection);
        list.sort(new SalaryComparator());

        PriorityQueue<BankEmployee> newPriorityQueue = new PriorityQueue<>(new SalaryComparator());
        newPriorityQueue.addAll(list);
        return newPriorityQueue;
    }

    private static void sortEmployeesBySalaryDescending(LinkedList<BankEmployee> collection) {
        collection.sort(new SalaryComparator().reversed());
    }

    private static TreeSet<BankEmployee> sortEmployeesBySalaryDescending(TreeSet<BankEmployee> collection) {
        List<BankEmployee> list = new LinkedList<>(collection);
        list.sort(new SalaryComparator().reversed());

        TreeSet<BankEmployee> newTreeSet = new TreeSet<>(new SalaryComparator().reversed());
        newTreeSet.addAll(list);
        return newTreeSet;
    }

    private static PriorityQueue<BankEmployee> sortEmployeesBySalaryDescending(PriorityQueue<BankEmployee> collection) {
        List<BankEmployee> list = new LinkedList<>(collection);
        list.sort(new SalaryComparator().reversed());

        PriorityQueue<BankEmployee> newPriorityQueue = new PriorityQueue<>(new SalaryComparator().reversed());
        newPriorityQueue.addAll(list);
        return newPriorityQueue;
    }

    private static <T extends Collection<BankEmployee>> void filterEmployeesBySalary(T collection, double minSalary, double maxSalary) {
        List<BankEmployee> newCollection = new LinkedList<>();
        for (BankEmployee employee : collection) {
            if (employee.getSalary() > minSalary & employee.getSalary() < maxSalary) {
                newCollection.add(employee);
            }
        }
        collection.clear();
        collection.addAll(newCollection);
    }
}
