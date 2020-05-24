import org.json.simple.JSONObject;

public class Person {
    String name;
    int age;
    int salary;

    public Person(String name, int age, int salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    @Override
    public boolean equals(Object obj){

        if (!(obj instanceof Person)) return false;

        Person p = (Person) obj;
        return this.name.equals(p.name);
    }

    @Override
    public int hashCode()
    {
        int result = name.hashCode();
        return result;
    }


}
