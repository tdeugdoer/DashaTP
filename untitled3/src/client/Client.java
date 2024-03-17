package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (DatagramSocket clientSocket = new DatagramSocket()) { // создаём сокет
            Scanner sc = new Scanner(System.in); // объект для считывания консоли

            int totalCount, countSendMessage = 1;
            while (true) {
                System.out.println("Введите общее количество сообщений");
                String input = sc.nextLine();
                try {
                    totalCount = Integer.parseInt(input);
                    if (totalCount < 1) {
                        System.out.println("Количество сообщений должно быть больше 0. Повторите попытку");
                    } else break;
                } catch (NumberFormatException e) {
                    System.out.println("Вы ввели не число, повторите попытку");
                }
            }

            byte[] buf; // буфер для хранения данных
            for (; countSendMessage < totalCount + 1; countSendMessage++) {
                System.out.println("Введите строку (только латинские буквы) или 'break' для выхода: ");
                String clientMessage = sc.nextLine(); // считываем консоль
                if (clientMessage.equalsIgnoreCase("break")) { // проверяем, хочет ли пользователь закончить работу
                    buf = ("1;" + (countSendMessage - 1) + ";" + totalCount).getBytes();
                    DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), 7070); // создаём UDP-пакет для отправки (передаём 1 - буфер, 2 - длину буфера, 3 - адрес сервера (у нас localhost), порт сервера)
                    clientSocket.send(datagramPacket); // отправляем данные на сервер, передав в сокет наше сообщение
                    break;
                }
                if (!isLatinAlphabet(clientMessage)) { // проверяем, содержит ли введённая строка только латинские буквы
                    System.out.println("Введённая строка содержит не только латинские буквы. Повторите попытку");
                    countSendMessage--;
                    continue;
                }

                buf = (clientMessage+ ";" + countSendMessage + ";" + totalCount).getBytes(); // записываем в буфер строку пользователя, переведённую в массив байт
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), 7070); // создаём UDP-пакет для отправки (передаём 1 - буфер, 2 - длину буфера, 3 - адрес сервера (у нас localhost), порт сервера)
                clientSocket.send(datagramPacket); // отправляем данные на сервер, передав в сокет наше сообщение

                buf = new byte[1024]; // очищаем буфер для получения данных
                DatagramPacket serverPacket = new DatagramPacket(buf, buf.length); // UDP-пакет, хранящий сообщений
                clientSocket.receive(serverPacket); // клиент получает сообщение и сохраняет его в serverPacket
                String serverMessage = new String(serverPacket.getData(), 0, serverPacket.getLength()); // создаём строку, которая содержит текст из полученного сообщения. В конструкторе из serverPacket получаем данные, начиная с 0 символа до "последнего" (индекс последнего является значение length, которое храниться в serverPacket)
                System.out.println("Строка от сервера: (" + serverPacket.getAddress().toString() + "): " + serverMessage); // выводим сообщение от сервера
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Сервер завершил работу");
        }
    }

    public static boolean isLatinAlphabet(String input) {
        return input.matches("[a-zA-Z]+") && !input.matches(".*\\d.*");
    }
}
