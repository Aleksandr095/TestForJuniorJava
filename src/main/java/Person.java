import org.json.simple.JSONObject;

public class Person {
    private String name;
    private int age;
    private int salary;

    public Person(String name, int age, int salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
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
