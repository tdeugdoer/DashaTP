import javax.swing.plaf.IconUIResource;
import java.util.Arrays;
import java.util.Scanner;

public class Sort {
    private static final Object object = new Object();
    private static long startTime = 0;
    private static long endTime = 0;

    static class QuickSortThread implements Runnable {
        private static int count = 0; //количество работающих потоков
        private final Employee[] employees;
        private final int low;
        private final int high;

        public QuickSortThread(Employee[] employees, int low, int high) {
            this.employees = employees;
            this.low = low;
            this.high = high;
        }

        @Override
        public void run() {
            count++;
            int number = count; //запоминаем, потому что на момент завершения работы этого потока поле count может поменять своё значение
            System.out.println("Поток №" + number + " запущен");
            quickSort(employees, low, high, false);
            System.out.println("Поток №" + number + " завершил работу");
            count--;
        }
    }

    public static void main(String[] args) {
        System.out.println("Введите количество работников: ");
        int count = inputPositiveInt(); // количество работников массива. Вызываем функцию inputInt() (в конце класса она находится)
        if (count < 1) {
            System.out.println("Количество работников не должно быть равно нулю. Завершение программы");
            return;
        }
        Employee[] array = new Employee[count];

        System.out.println("Заполните массив цифрами, которые будут являться ID работников");
        for (int i = 0; i < array.length; i++) { // проходимся по всему массиву
            int id = inputPositiveInt(); // получаем число, которое является id работника
            array[i] = new Employee(id); // создаём работника с id
        }

        System.out.println("Массив до сортировки: " + Arrays.toString(array));
        startTime = System.nanoTime();
        quickSort(array, 0, array.length - 1, true);
        endTime = System.nanoTime();
        System.out.println("Массив после сортировки: " + Arrays.toString(array));
        System.out.println("Продолжительность сортировки: " + ((endTime - startTime) / 1_000_000_000.0));
    }

    public static void quickSort(Employee[] array, int low, int high, boolean bool) { //рекурсивный метод сортировки
        if (low < high) {
            int pivotIndex = partition(array, low, high); //индекс опорного элемента
            endTime = System.nanoTime();
            System.out.println("Время выполнения: " + ((endTime - startTime) / 1_000_000.0) + "; Опорный элемент: " + array[pivotIndex] + "; Массив: " + Arrays.toString(array));

            if (bool) { // если bool - true, тогда запускаем рекурсию в двух потока
                Thread leftThread = new Thread(new QuickSortThread(array, low, pivotIndex - 1)); //сортировка для элементов левее опорного
                Thread rightThread = new Thread(new QuickSortThread(array, pivotIndex + 1, high)); //сортировка для элементов правее опорного

                leftThread.start();
                rightThread.start();

                try {
                    leftThread.join();
                    rightThread.join();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }

            } else { // иначе в одном потоке
                quickSort(array, low, pivotIndex - 1, false);
                quickSort(array, pivotIndex + 1, high, false);
            }
        }

    }

    public static int partition(Employee[] array, int low, int high) { //деление массива на две части по опорному элементу
        int pivot = array[high].getId(); //pivot - опорный элемент
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j].getId() < pivot) {
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, high);

        return i + 1;
    }

    public static void swap(Employee[] array, int i, int j) { //методы, чтобы поменять элементы местами
        Employee temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static int inputPositiveInt() { // функция для считывания положительных чисел с консоли
        Scanner sc = new Scanner(System.in); // объект для считывания консоли
        while (true) {
            try {
                int number = Integer.parseInt(sc.nextLine());
                if (number < 0) {
                    System.out.println("Число должно быть положительным, повторите попытку");
                    continue;
                }
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели не число, повторите попытку");;
            }
        }

    }
}