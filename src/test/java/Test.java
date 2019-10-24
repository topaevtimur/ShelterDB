import com.opentable.db.postgres.embedded.EmbeddedPostgreSQL;
import org.junit.Before;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Test {
    //Shelter shelter = new Shelter();

    @org.junit.Test
    public void test1() {
        try (EmbeddedPostgreSQL pg = EmbeddedPostgreSQL.builder().start();
        Connection con = pg.getPostgresDatabase().getConnection()) {
           // Shelter shelter = new Shelter();
           // shelter.getList(Animal.Dog);



        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


    }
 /*   @Before
    public static void before() {

    }*/




}
