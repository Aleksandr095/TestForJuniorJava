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

    public Parser() {
        peopleNew = new ArrayList<Person>();
        people = new ArrayList<Person>();
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        Parser.people = people;
    }

    public List<Person> getPeopleNew() {
        return peopleNew;
    }

    public void setPeopleNew(List<Person> peopleNew) {
        Parser.peopleNew = peopleNew;
    }


    public void insertDB(ConnetcionDB database, int avirage) {
        // database.connection();

        for (Person pi : peopleNew) {

            if (pi.getSalary() > avirage) {
                database.queryInsert(pi.getName(), pi.getAge(), pi.getSalary());
            }
        }
    }

    public void display(ConnetcionDB database, String str) {
        // database.connection();
        try {
            if (str == "display") {
                // распечатать общее количество записей в таблице бд
                // и записи значение полей age которых больше 25 и записать их в файл отчета.
                database.querySelect();
            }
        } catch (Exception e) {
            System.out.println("Не задан аргумент командной строки");
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

    public int avg() {

        int count = 0;
        int sum = 0;

        for (Person pi: peopleNew) {
            sum += pi.getAge();
            count += 1;
        }

        return sum / count;
    }

    public void deserialize() {
        try {
            // считывание файла JSON


            FileReader reader = new FileReader(filePath);


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
