import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class Parser {
    private static final String filePath = "src/main/resources/sample.json";
    // объекты до удаления
    private static List<Person> people;
    // объкты после удаления
    private static List<Person> peopleNew;
    private static int avirage;

    static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    static final String USER = "postgres";
    static final String PASS = "admin";



    public static void main(String[] args) {


        Parser parser = new Parser();
        parser.desieialaze();

        // количество исходных объектов
        System.out.println(people.size());

        parser.delete();

        // количество объектов после удаления
        System.out.println(peopleNew.size());

        parser.avg();

        ConnetcionDB connetcion = new ConnetcionDB();
        connetcion.connection();

        for (Person pi : peopleNew) {

            if (pi.salary > avirage) {
                connetcion.queryInsert(pi.name, pi.age, pi.salary);
            }
        }

        if (args[0].equals("display")) {
            // распечатать общее количество записей в таблице бд
            // и записи значение полей age которых больше 25 и записать их в файл отчета.
            connetcion.querySelect();
        }

    }


    public void delete() {
        // удаление объектов
        Iterator it = people.iterator();
        while(it.hasNext())
        {
            Person person = (Person) it.next();
            if(!peopleNew.contains(person))
            {
                peopleNew.add(person);
            }

        }
    }

    public void avg() {

        int count = 0;
        int sum = 0;

        for (Person pi: peopleNew) {
            sum += pi.salary;
            count += 1;
        }

        avirage = sum / count;
    }

    public void desieialaze() {
        try {
            // считывание файла JSON


            FileReader reader = new FileReader(filePath);
            peopleNew = new ArrayList<Person>();
            people = new ArrayList<Person>();




            Object jsonParser = new JSONParser().parse(reader);
            JSONArray array = (JSONArray) jsonParser;


            Iterator i1 = array.iterator();


            while (i1.hasNext()) {

                JSONObject object1 = (JSONObject) i1.next();

                people.add(
                        new Person(
                                object1.get("name").toString(),
                                Integer.parseInt(object1.get("age").toString()),
                                Integer.parseInt(object1.get("salary").toString()))
                );

            }



        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
    }




}
