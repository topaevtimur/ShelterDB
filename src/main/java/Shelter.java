import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;

public class Shelter {


    public void add(Animal animal, String name, int age) throws Exception {
        if (name.equals("")) {
            throw new Exception("Add animal with name");
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        String query = "INSERT INTO shelter(id, type, name , age, date) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement pst = ConnectDB.getConnection().prepareStatement(query)) {
            pst.setObject(1, java.util.UUID.randomUUID());
            pst.setString(2, animal.toString());
            pst.setString(3, name);
            pst.setInt(4, age);
            pst.setObject(5, localDateTime);
            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public ArrayList<String> getList(Animal animal) {
        ArrayList<String> list = new ArrayList<>();
        try (PreparedStatement pst = ConnectDB.getConnection().prepareStatement(
                "SELECT * FROM shelter " +
                        "WHERE type = \'" + animal.toString() + "\' " +
                        "ORDER BY name");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("type") + ' ' + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;


    }


    public void pickAnimal(Animal animal) {
        try (PreparedStatement pst = ConnectDB.getConnection().prepareStatement(
                "DELETE FROM shelter " +
                        "WHERE rowid IN (" +
                        "SELECT rowid " +
                        "FROM shelter " +
                        "WHERE type = \'" + animal.toString() + "\' " +
                        "ORDER BY date LIMIT 1)")) {

            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void pickAnimal() {
        try (PreparedStatement pst = ConnectDB.getConnection().prepareStatement(
                "DELETE FROM shelter " +
                        "WHERE rowid IN (" +
                        "SELECT rowid " +
                        "FROM shelter " +
                        "ORDER BY date LIMIT 1)")) {
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}