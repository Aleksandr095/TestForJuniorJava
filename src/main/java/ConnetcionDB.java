
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class ConnetcionDB {

    //  Database credentials
    static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    static final String USER = "postgres";
    static final String PASS = "admin";

    private Connection connection;

    public void connection() {

        System.out.println("Connection to PostgreSQL JDBC");

        connection = null;
        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void queryInsert(String name, int age, int salary) {

        try {

            String sql = "INSERT INTO Person (Name, Age, Salary) Values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setInt(3, salary);

            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public void querySelect() {

        try {
            String sql = "Select *, (select count(id) from person) as count " +
                            "FROM Person " +
                            "WHERE Age > 25";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){

                String name = resultSet.getString(2);
                int age = resultSet.getInt(3);
                int salary = resultSet.getInt(4);
                int count = resultSet.getInt(5);
                System.out.println(name + " " + age + " " + salary + " " + count);


                try(FileWriter writer = new FileWriter("src/main/resources/Отчет.txt", true))
                {
                    writer.write(name + " " + age + " " + salary + " " + count);
                    writer.append("\n");
                    writer.flush();
                }
                catch(IOException ex){

                    System.out.println(ex.getMessage());
                }
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }


    }



}
