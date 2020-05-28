import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class Parser {

    // объекты до удаления
    private static List<Person> people;

    // объкты после удаления
    private static List<Person> peopleNew;

    public Parser() {
        peopleNew = new ArrayList<Person>();
        people = new ArrayList<Person>();
    }

    public int sizePeople() {
        return people.size();
    }

    public int sizePeopleNew() {
        return peopleNew.size();
    }

    public void insertDB(ConnetcionDB database, int avirage) {

        for (Person pi : peopleNew) {

            if (pi.getSalary() > avirage) {
                database.queryInsert(pi.getName(), pi.getAge(), pi.getSalary());
            }
        }
    }

    public void display(ConnetcionDB database, String str) {
            if (str == "display") {
                // распечатать общее количество записей в таблице бд
                // и записи значение полей age которых больше 25 и записать их в файл отчета.
                database.querySelect();
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

    public int avg() throws ArithmeticException {

        int count = 0;
        int sum = 0;

        for (Person pi: peopleNew) {
            sum += pi.getAge();
            count += 1;
        }

        return sum / count;
    }

    public void deserialize(FileReader reader) {
         try {


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

            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
    }

}
