package server;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Server {
    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(7070)) { // создаём сокет

            System.err.println("Сервер стартовал");
            byte[] buf = new byte[1024];; // буфер для хранения данных

            while (true) {
                DatagramPacket clientPacket = new DatagramPacket(buf, buf.length); // UDP-пакет, хранящий сообщение
                serverSocket.receive(clientPacket); // сервер получает сообщение и сохраняет его в clientPacket
                String clientMessage = new String(clientPacket.getData(), 0, clientPacket.getLength()); // создаём строку, которая содержит текст из полученного сообщения. В конструкторе из clientPacket получаем данные, начиная с 0 символа до "последнего" (индекс последнего является значение length, которое храниться в clientPacket)
                System.out.println("Сообщение от клиента (" + clientPacket.getAddress().toString() + "): " + clientMessage); // выводим сообщение от клиента

                String serverMessage = new StringBuilder(clientMessage).reverse() + "; " + clientMessage.toUpperCase() + "; " + encrypt(clientMessage) + "; " + "\n"; // формируем сообщение от сервера
                buf = serverMessage.getBytes(); // записываем в буфер сообщение сервера в виде массива байт
                DatagramPacket serverPacket = new DatagramPacket(buf, buf.length, clientPacket.getAddress(), clientPacket.getPort()); // создаём UDP-пакет для отправки (передаём 1 - буфер, 2 - длину буфера, 3 - адрес клиента, берём из отправленного им пакета, 4 - порт клиента, также из отправленного им пакета)
                serverSocket.send(serverPacket); // отправляем данные клиенту, передав в сокет наше сообщение

                writeToFile(clientPacket.getAddress().toString(), clientMessage, serverMessage); // записываем всё в файл
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Сервер завершил работу");
        }
    }

    private static String encrypt(String text) { //шифрование цезаря
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

    private static void writeToFile(String client, String req, String clientMessage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:/Учёба/Рис/untitled/untitled3/src/file.txt", true))) { //дозапись
            writer.write("Клиент: " + client + "\n" + "Получено: " + req + "\n" + "Отправлено: " + clientMessage + "\n\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
