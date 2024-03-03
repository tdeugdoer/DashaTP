import javax.swing.plaf.IconUIResource;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {
        try(Socket socket = new Socket(InetAddress.getLocalHost(), 5050);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())))
        {
            System.err.println("Вы подключились к серверу");
            Scanner sc = new Scanner(System.in);
            String req, res; //request - то, что получаем; response - то, что отдаём

            while (true) {
                System.out.println("Введите строку (только латинские буквы): ");
                res = sc.nextLine();
                if(!isLatinAlphabet(res)) {
                    System.out.println("Введённая строка содержит не только латинские буквы. Повторите попытку");
                    continue;
                }
                out.write(res + "\n");
                out.flush();
                if (res.equals("")) {
                    break;
                }

                req = in.readLine();
                System.out.println("Перевёрнутая строка: " + req);
                req = in.readLine();
                System.out.println("Строка в верхнем регистре: " + req);
                req = in.readLine();
                System.out.println("Зашифрованная строка: " + req);
            }
            System.err.println("Вы отключились от сервера");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean isLatinAlphabet(String input) {
        return input.matches("[a-zA-Z]+") && !input.matches(".*\\d.*");
    }
}