package ru.demo.downpicandmus;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class DownPicPlayMusic {

    private static final String IN_FILE_TXT = "C:\\Users\\mishi\\IdeaProjects\\untitled6\\src\\ru\\demo" +
            "\\downpicandmus\\inFile.txt";
    private static Scanner in = new Scanner(System.in);


    public static void main(String[] args){
        String string;
        //Создание объекта для чтения из файла с путём и ссылкой
        try (BufferedReader inFile = new BufferedReader(new FileReader(IN_FILE_TXT))) {
            while ((string = inFile.readLine()) != null) {
                String[] stringarray = string.split(" ");//разделение строки ссылки и пути
                String forurl = stringarray[0];//ссылка
                String forpath = stringarray[1];//путь
                downloadUsingNIO(forurl, forpath);//загрузка файлов
                play();//вызов метода для проигрывания музыки
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private static void downloadUsingNIO(String strUrl, String file) throws IOException {
        URL url = new URL(strUrl);
        ReadableByteChannel byteChannel = Channels.newChannel(url.openStream());
        FileOutputStream stream = new FileOutputStream(file);
        stream.getChannel().transferFrom(byteChannel, 0, Long.MAX_VALUE);
        stream.close();
        byteChannel.close();
        System.out.println("Скачивание завершено");
    }

    //Проигрыватель музыки
    private static void play(){
        try (FileInputStream inputStream = new FileInputStream("src\\ru\\demo\\downpicandmus" +
                "\\music.mp3")) {
            try {
                Player player = new Player(inputStream);//создание потока для плеера
                player.play();//запуск плеера
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
    }
    }
}