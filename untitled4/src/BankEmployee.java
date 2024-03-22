import java.util.LinkedList;
import java.util.List;

public class BankEmployee {
    private static int count = 0;
    private int id;
    private String name;
    private double salary;

    public BankEmployee() {}

    public BankEmployee(String name, double salary) {
        this.id = ++count;
        this.name = name;
        this.salary = salary;

    }

    public static List<BankEmployee> getListBankEmployee() {
        List<BankEmployee> bankEmployees = new LinkedList<>();
        bankEmployees.add(new BankEmployee("Арсений", 423.3));
        bankEmployees.add(new BankEmployee("Катя", 342));
        bankEmployees.add(new BankEmployee("Лёша", 1000));
        bankEmployees.add(new BankEmployee("Елизавета", 9999));
        bankEmployees.add(new BankEmployee("Ульяна", 854.75));
        return bankEmployees;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "BankEmployee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}