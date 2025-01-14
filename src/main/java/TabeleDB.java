import java.sql.Connection;
import java.sql.Statement;

public class TabeleDB {

        private final Connection connection;

        /**
         * Constructor
         * @param connection - conexiunea la baza de date
         */
        public TabeleDB(Connection connection) {
            this.connection = connection;

        }

        /**
         * Creează toate tabelele necesare
         */
        public void createTables() {
            createDbXflyDatabase();
            createUserTable();
            createPlaneTable();
            createFlightTable();
            createReservationTable();
            createFoodTable();
            createDrinkTable();
        }

    /**
     * Creează baza de date 'db_xfly' dacă nu există
     */
         private void createDbXflyDatabase() {
             try (Statement statement = connection.createStatement()) {
                 String checkDbSQL = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'db_xfly'";
                 var resultSet = statement.executeQuery(checkDbSQL);

                 if (!resultSet.next()) {
                     String createDbSQL = "CREATE DATABASE db_xfly";
                     statement.executeUpdate(createDbSQL);
                     System.out.println("Baza de date 'db_xfly' a fost creată.");
                 }

                 connection.setCatalog("db_xfly");

             } catch (Exception e) {
                 System.err.println("Eroare la crearea sau conectarea la baza de date: " + e.getMessage());
                 e.printStackTrace();
             }
         }


    /**
         * Se creează tabela 'user'
         */
        private void createUserTable() {
            String createUserTableSQL = """
            CREATE TABLE IF NOT EXISTS user (
                id_user INT NOT NULL AUTO_INCREMENT,
                username VARCHAR(20) NOT NULL,
                nr_phone INT,
                cnp BIGINT,
                type VARCHAR(1),
                email VARCHAR(45),
                passworld VARCHAR(50),
                PRIMARY KEY (id_user)
            );
        """;

            executeSQL(createUserTableSQL, "Tabela 'user' a fost creată cu succes.");
        }


        /**
         * Se creează tabela 'plane'
         */
        private void createPlaneTable() {
            String createPlaneTableSQL = """
            CREATE TABLE IF NOT EXISTS plane (
                id_plane INT NOT NULL AUTO_INCREMENT,
                model VARCHAR(45) NOT NULL,
                capacityA INT,
                capacityB INT,
                PRIMARY KEY (id_plane)
            );
        """;

            executeSQL(createPlaneTableSQL, "Tabela 'plane' a fost creată cu succes.");
        }

        /**
         * Se creează tabela 'flight'
         */
        private void createFlightTable() {
            String createFlightTableSQL = """
            CREATE TABLE IF NOT EXISTS flight (
                id_flight INT NOT NULL AUTO_INCREMENT,
                id_plane INT NOT NULL,
                classA VARCHAR(45) NOT NULL,
                price DECIMAL(6,2),
                departure VARCHAR(45),
                arrival VARCHAR(45),
                h_departure DATETIME,
                h_arrival DATETIME,
                date DATETIME,
                duration VARCHAR(5),
                classB VARCHAR(45),
                PRIMARY KEY (id_flight),
                FOREIGN KEY (id_plane) REFERENCES plane(id_plane) ON DELETE CASCADE ON UPDATE CASCADE
            );
        """;

            executeSQL(createFlightTableSQL, "Tabela 'flight' a fost creată cu succes.");
        }


    /**
     * Creează tabela 'reservation'
     */
    private void createReservationTable() {
        String createReservationTableSQL = """
    CREATE TABLE IF NOT EXISTS reservation (
        id_reservation INT NOT NULL AUTO_INCREMENT,
        id_flight INT NOT NULL,
        id_user INT NOT NULL,
        price_reservation DECIMAL(6,2),
        class VARCHAR(2),
        movie VARCHAR(255) NULL DEFAULT NULL ;
        id_food INT NOT NULL,
        id_drink INT NOT NULL,
        PRIMARY KEY (id_reservation),
        FOREIGN KEY (id_flight) REFERENCES flight(id_flight) ON DELETE CASCADE ON UPDATE NO ACTION,
        FOREIGN KEY (id_user) REFERENCES user(id_user) ON DELETE CASCADE ON UPDATE NO ACTION,
        FOREIGN KEY (id_food) REFERENCES food(id_food) ON DELETE NO ACTION ON UPDATE NO ACTION,
        FOREIGN KEY (id_drink) REFERENCES drink(id_drink) ON DELETE NO ACTION ON UPDATE NO ACTION
    );
    """;

        executeSQL(createReservationTableSQL, "Tabela 'reservation' a fost creată cu succes.");
    }

    /**
     * Se creează tabela 'food'
     */
    private void createFoodTable() {
        String createFoodTableSQL = """
    CREATE TABLE IF NOT EXISTS food (
        id_food INT NOT NULL AUTO_INCREMENT,
        name VARCHAR(255) NOT NULL,
        price DECIMAL(6,2) NOT NULL,
        PRIMARY KEY (id_food)
    );
    """;

        executeSQL(createFoodTableSQL, "Tabela 'food' a fost creată cu succes.");
    }

    /**
     * Se creează tabela 'drink'
     */
    private void createDrinkTable() {
        String createDrinkTableSQL = """
    CREATE TABLE IF NOT EXISTS drink (
        id_drink INT NOT NULL AUTO_INCREMENT,
        name VARCHAR(255) NOT NULL,
        price DECIMAL(6,2) NOT NULL,
        PRIMARY KEY (id_drink)
    );
    """;

        executeSQL(createDrinkTableSQL, "Tabela 'drink' a fost creată cu succes.");
    }

        /**
         * Metoda pentru executarea unui SQL
         * @param sql Comanda SQL
         * @param successMessage Mesaj de succes la executare
         */
        private void executeSQL(String sql, String successMessage) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(sql);
                System.out.println(successMessage);
            } catch (Exception e) {
                System.err.println("Eroare la crearea tabelei: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


