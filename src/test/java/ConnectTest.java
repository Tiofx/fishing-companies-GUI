import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static junit.framework.TestCase.fail;

public class ConnectTest {

    @Test
    public void connect() {
        try {
            Connection connection = null;
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/test_for_tdb";
            String user = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, user, password);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

}
