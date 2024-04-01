import java.util.LinkedList;
import java.util.List;

public class BankEmployee {
    private static int count = 0;
    private int id;
    private String name;
    private int salary;
    private String gender;

    public BankEmployee() {}

    public BankEmployee(String name, int salary, String gender) {
        this.id = ++count;
        this.name = name;
        this.salary = salary;
        this.gender = gender;
    }

    public static List<BankEmployee> getListBankEmployee() {
        List<BankEmployee> bankEmployees = new LinkedList<>();
        bankEmployees.add(new BankEmployee("Арсений", 423, "Мужской"));
        bankEmployees.add(new BankEmployee("Катя", 342, "Женский"));
        bankEmployees.add(new BankEmployee("Лёша", 1000, "Мужской"));
        bankEmployees.add(new BankEmployee("Елизавета", 9999, "Женский"));
        bankEmployees.add(new BankEmployee("Ульяна", 854, "Женский"));
        return bankEmployees;
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}