
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;

import java.nio.file.*;
import java.util.regex.Pattern;


public class Analyzer {


    public static void main(String[] args) {

        String str = "";
        ConnetcionDB database = new ConnetcionDB();
        Parser parser = new Parser();

        database.connection();
        // database.createTable("Create Table Person (id Serial PRIMARY KEY, name CHARACTER VARYING(30), age INTEGER, salary INTEGER)");


        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get("src/main/test_temp");
            WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    str = event.context().toString();
                }
                if (regx(str)) {

                    parser.deserialize();
                    // количество исходных объектов
                    System.out.println(parser.getPeople().size());
                    parser.delete();

                    // количество объектов после удаления
                    //System.out.println(parser.getPeopleNew().size());

                    parser.insertDB(database, parser.avg());

                    try {
                        parser.display(database, "display");
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Аргументы командной строки не заданы");
                    }

                } else {
                    // логирование
                    System.out.println(str);
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

