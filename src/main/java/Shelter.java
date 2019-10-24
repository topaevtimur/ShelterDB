import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class Shelter {

    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    static final String USER = "postgres";
    static final String PASS = "123123123-";

    private Connection connection;

    public Shelter() {
        System.out.println("Testing connection to PostgreSQL JDBC");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");

        connection = null;
        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }

    }
    public void add(Animal animal, String name, int age) {
        //add animal to database

        LocalDateTime localDateTime = LocalDateTime.now();

        String query = "INSERT INTO shelter(id, type, name , age, date) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setObject(1,java.util.UUID.randomUUID());
            pst.setString(2,animal.toString());
            pst.setString(3,name);
            pst.setInt(4,age);
            pst.setObject(5,localDateTime);
            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void getList(Animal animal) {
        LinkedList <String> list = new LinkedList<>();
        StringBuilder line;
        try(PreparedStatement pst = connection.prepareStatement("SELECT * FROM shelter where type = \'" + animal.toString()+"\'");
            ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                line = new StringBuilder(" ");
                line.append(rs.getString(2));
                line.append(" ");
                line.append(rs.getString(3));
                line.append(" ");
                line.append(rs.getInt(4));

                System.out.print(rs.getString(2));
                System.out.print(" ");
                System.out.print(rs.getString(3));
                System.out.println(rs.getInt(4));
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public void pickAnimal(Animal animal) {
        try(PreparedStatement pst = connection.prepareStatement(
                "DELETE FROM shelter WHERE ctid IN (SELECT ctid FROM shelter where type = \'" + animal.toString()+"\' ORDER BY date LIMIT 1)")) {
                pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void pickAnimal() {
        try(PreparedStatement pst = connection.prepareStatement(
                "DELETE FROM shelter WHERE ctid IN (SELECT ctid FROM shelter ORDER BY date LIMIT 1)")){
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}