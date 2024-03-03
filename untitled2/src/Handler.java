import javax.swing.*;
import java.io.*;
import java.net.Socket;


class Handler implements Runnable {
    private final Socket socket;

    public Handler(Socket socket)  {
        this.socket = socket;
    }

    public void run() {
        try( BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            String clientName = socket.getInetAddress().toString();
            System.out.println("Подключён " + clientName);
            String req, res; //request - то, что получаем; response - то, что отдаём

            while (true) {
                req = in.readLine();
                if(req.equals("Пока")) {
                    break;
                }
                System.out.println("Сообщение от клиента (" + socket.getInetAddress() + "): " + req);

                String reverse =  new StringBuilder(req).reverse().toString();
                String upperString = req.toUpperCase();
                String encrypt = encrypt(req);

                out.write(reverse + "\n");
                out.write(upperString + "\n");
                out.write(encrypt + "\n");
                out.flush();

                writeToFile(clientName, req, reverse, upperString, encrypt);
            }

            System.out.println("Отключён " + socket.getInetAddress());
            socket.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private static String encrypt(String text) {
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (Character.isLetter(currentChar)) {
                char baseChar = Character.isUpperCase(currentChar) ? 'A' : 'a';
                int shiftedChar = ((currentChar - baseChar) + 3) % 33 + baseChar;
                encryptedText.append((char) shiftedChar);
            } else {
                encryptedText.append(currentChar);
            }
        }
        return encryptedText.toString();
    }

    private static void writeToFile(String client, String req, String res1, String res2, String res3) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:/Учёба/Рис/untitled/untitled2/src/file.txt", true))) {
            writer.write("Клиент: " + client + "\n" + "Получено: " + req + "\n" + "Отправлено: " + res1 + "; " + res2 + "; " + res3 + "; " + "\n\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}