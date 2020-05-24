import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.io.FileReader;

public class Parser {
    private static final String filePath = "src/main/resources/sample.json";
    private static List<Person> people;
    private static List<Person> peopleNew;
    private static int avirage, sum = 0, count = 0;;


    public static void main(String[] args) {

        /*if (args[0].equals("display")) {
            // распечатать общее количество записей в таблице бд
            // и записи значение полей age которых больше 25 и записать их в файл отчета.
        }*/


        ConnetcionDB c = new ConnetcionDB();
        c.connection();

        // создание таблицы Person
        String query = "CREATE TABLE Person " +
                "(Id SERIAL PRIMARY KEY,\n" +
                "    Name CHARACTER VARYING(30),\n" +
                "    Age INTEGER,\n"+
                "Salary INTEGER)";

        String addEntryQuery = "INSERT INTO Person (Name, Age, Salary)" +
                "VALUES ('Vasy', 25, 200000)";

        // удаление таблицы
        String queryDROP = "DROP TABLE Person";

        try {
            c.executeUpdate(query);
            c.executeUpdate(addEntryQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void desieialaze() {
        try {
            // считывание файла JSON


            FileReader reader = new FileReader(filePath);
            String str, str1;
            int count = 0;
            peopleNew = new ArrayList<Person>();
            people = new ArrayList<Person>();




            Object jsonParser = new JSONParser().parse(reader);
            JSONArray array = (JSONArray) jsonParser;


            Iterator i1 = array.iterator();


            while (i1.hasNext()) {

                JSONObject object1 = (JSONObject) i1.next();

                sum += Integer.parseInt(object1.get("salary").toString());
                count += 1;

                people.add(
                        new Person(
                                object1.get("name").toString(),
                                Integer.parseInt(object1.get("age").toString()),
                                Integer.parseInt(object1.get("salary").toString()))
                );


            }

            System.out.println(people.size());

            int i = 0, j = 0;

            Iterator it = people.iterator();
            while(it.hasNext())
            {
                Person person = (Person) it.next();
                if(!peopleNew.contains(person))
                {
                    peopleNew.add(person);
                }

            }

            avirage = sum / count;

            for (Person pi : peopleNew) {
                if (pi.salary > avirage) {

                }
            }

            System.out.println(peopleNew.size());
            System.out.println(avirage);


        }  catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
    }



}
