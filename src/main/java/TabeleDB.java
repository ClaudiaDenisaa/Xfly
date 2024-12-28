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
            createSeatTable();
            createMenuTable();
            createReservationTable();
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
                id_ticket INT DEFAULT NULL,
                user_name VARCHAR(20) NOT NULL,
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
                class VARCHAR(45) NOT NULL,
                price DECIMAL(4,2),
                departure VARCHAR(45),
                arrival VARCHAR(45),
                h_departure DATETIME,
                h_arrival DATETIME,
                date DATETIME,
                duration VARCHAR(5),
                PRIMARY KEY (id_flight),
                FOREIGN KEY (id_plane) REFERENCES plane(id_plane) ON DELETE CASCADE
            );
        """;

            executeSQL(createFlightTableSQL, "Tabela 'flight' a fost creată cu succes.");
        }

        /**
         * Se creează tabela 'seat'
         */
        private void createSeatTable() {
            String createSeatTableSQL = """
            CREATE TABLE IF NOT EXISTS seat (
                id_seat INT NOT NULL AUTO_INCREMENT,
                class VARCHAR(1) NOT NULL,
                occupied BOOLEAN DEFAULT FALSE,
                price_seat DECIMAL(4,2),
                PRIMARY KEY (id_seat)
            );
        """;

            executeSQL(createSeatTableSQL, "Tabela 'seat' a fost creată cu succes.");
        }

        /**
         * Se creează tabela 'menu'
         */
        private void createMenuTable() {
            String createMenuTableSQL = """
            CREATE TABLE IF NOT EXISTS menu (
                id_menu INT NOT NULL AUTO_INCREMENT,
                id_user INT NOT NULL,
                food TEXT,
                drink TEXT,
                price_menu DOUBLE,
                movie VARCHAR(255),
                PRIMARY KEY (id_menu),
                FOREIGN KEY (id_user) REFERENCES user(id_user)
            );
        """;

            executeSQL(createMenuTableSQL, "Tabela 'menu' a fost creată cu succes.");
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
                id_seat INT NOT NULL,
                id_menu INT NOT NULL,
                PRIMARY KEY (id_reservation),
                FOREIGN KEY (id_flight) REFERENCES flight(id_flight) ON DELETE CASCADE,
                FOREIGN KEY (id_user) REFERENCES user(id_user) ON DELETE CASCADE,
                FOREIGN KEY (id_seat) REFERENCES seat(id_seat) ON DELETE CASCADE,
                FOREIGN KEY (id_menu) REFERENCES menu(id_menu) ON DELETE CASCADE
            );
        """;

            executeSQL(createReservationTableSQL, "Tabela 'reservation' a fost creată cu succes.");
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


