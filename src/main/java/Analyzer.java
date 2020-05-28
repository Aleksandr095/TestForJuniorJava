
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.*;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;


public class Analyzer {


    public static void main(String[] args) {

        String filePath;
        String str = "";
        ConnetcionDB database = new ConnetcionDB();
        Parser parser = new Parser();

        database.connection();
        //database.createTable("Create Table Person (id Serial PRIMARY KEY, name CHARACTER VARYING(30), age INTEGER, salary INTEGER)");
        //database.dropTable("DROP TABLE Person");


        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get("src/main/test_temp");
            WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent event : key.pollEvents()) {
                    str = event.context().toString();


                    // Если файл json
                    if (regx(str)) {

                        // проверяем скопировался ли файл
                        boolean flag = false;
                        do {
                            sleep(100);

                            filePath = "src/main/test_temp/" + str;
                            try (FileReader reader = new FileReader(filePath)) {
                                parser.deserialize(reader);
                            } catch (IOException e) {
                                flag = true;
                                System.out.println("Файл занят, ждите" + e);
                            }
                        } while (flag);

                        // количество исходных объектов
                        System.out.println(parser.sizePeople());
                        parser.delete();

                        // количество объектов после удаления
                        // System.out.println(parser.sizePeopleNew());

                        parser.insertDB(database, parser.avg());

                        try {
                            parser.display(database, args[0]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Не задан аргумент командной строки");
                        }

                    } else {
                        // логирование
                        //System.out.println(str);

                        Logger logger = Logger.getLogger(Analyzer.class.getName());
                        logger.info(str);
                    }
                }
                key.reset();

            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static boolean regx(String str) {
        // String regx = "[.]json";
        // Pattern pattern = Pattern.matches(".+[.]json", str);
        boolean f;

        if (Pattern.matches(".+[.]json", str))
            return true;
        else
            return false;
    }

}

