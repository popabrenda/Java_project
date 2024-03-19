package repository;

import domeniu.Masina;
import org.sqlite.SQLiteDataSource;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MasiniRepositoryDB extends MemoryRepository<Masina> implements IDbRepository<Masina>{

        private String JDBC_URL = "jdbc:sqlite:masini.db";

        private Connection connection;

        public MasiniRepositoryDB()
        {
            openConnection();
            createTable();
            try {
                insertRandomData();
            } catch (DuplicateEntityException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void openConnection() {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);

            try {
                if (connection == null || connection.isClosed())
                {
                    connection = ds.getConnection();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            createTable();
        }

        public void closeConnection()
        {
            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        public void createTable()
        {
            try (final Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS masini(id int, marca varchar(400), model  varchar(400) );");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void initTable()
        {
            List<Masina> masini = new ArrayList<>();
            new Masina(1, "audi", "a4");
            Masina masina2 = new Masina(2, "bmw", "x6");
            Masina masina3 = new Masina(3, "audi", "a3");
            masini.add(new Masina(1, "audi", "a4"));
            masini.add(masina2);
            masini.add(masina3);
            try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO masini values (?,?,?);")) {
                for (Masina p: masini)
                {
                    stmt.setInt(1,p.getId());
                    stmt.setString(2,p.getMarca());
                    stmt.setString(3,p.getModel());
                    stmt.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public ArrayList<Masina> getAll()
        {
            ArrayList<Masina> masini = new ArrayList<>();
            try (PreparedStatement stmt = connection.prepareStatement("SELECT * from masini;")) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Masina p = new Masina(rs.getInt(1), rs.getString(2), rs.getString(3));
                    masini.add(p);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return masini;
        }

        public void add(Masina p)
        {
            try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO masini values (?,?,?);")) {
                stmt.setInt(1,p.getId());
                stmt.setString(2,p.getMarca());
                stmt.setString(3,p.getModel());
                int ok = 0;
                ArrayList<Masina> masini = getAll();
                for (Masina masina: masini)
                {
                    if (masina.getId() == p.getId()) {
                        ok = 1;
                        throw new DuplicateEntityException("Masina exista deja");
                    }
                }
                if(ok== 0)
                    stmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DuplicateEntityException e) {
                throw new RuntimeException(e);
            }
        }

    public void update(int id, Masina entitate) throws EntityNotFoundException {
        if(entitate == null)
        {
            throw new EntityNotFoundException("entitatea nu exista");
        }

        try (PreparedStatement statement = connection.prepareStatement("UPDATE masini SET marca = ?, model = ? WHERE id = ?")) {

            statement.setString(1, entitate.getMarca());
            statement.setString(2, entitate.getModel());
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(int id){

        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM masini WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String[] marci = {"Audi", "BMW", "Mercedes", "Volkswagen", "Opel", "Ford", "Renault", "Dacia", "Skoda", "Toyota", "Honda", "Mazda", "Hyundai", "Kia", "Fiat", "Alfa Romeo", "Lancia", "Volvo", "Saab", "Porsche", "Lamborghini", "Ferrari", "Maserati", "Bugatti", "Citroen", "Peugeot", "Seat", "Mini", "Jaguar", "Land Rover", "Lexus", "Jeep", "Suzuki", "Mitsubishi", "Subaru", "Chevrolet", "Chrysler", "Dodge", "Hummer", "Pontiac", "Tesla", "Aston Martin", "Bentley", "Lotus", "Rolls Royce", "Smart", "Daihatsu", "Daewoo", "Lada"};
    private static final String[] modele = {"alb", "negru", "rosu", "albastru", "frumos", "urat", "mic", "mare", "rapid", "lent", "verde", "galben", "portocaliu", "roz", "mov", "maro", "bej", "gri", "argintiu", "auriu", "bronz", "crem", "turcoaz", "visiniu", "lila", "indigo", "carmaziu", "hatzjohnule", "carmaziu", "hatz", "golden", "silver", "black", "white", "red", "blue", "green", "yellow", "orange", "pink", "purple", "brown", "grey", "gray", "beige", "turquoise", "burgundy", "lilac", "indigo", "crimson", "gold", "silver", "black", "white", "red", "blue", "green", "yellow", "orange", "pink", "purple", "brown", "grey", "gray", "beige", "turquoise", "burgundy", "lilac", "indigo", "crimson", "gold", "silver", "black", "white", "red", "blue", "green", "yellow", "orange", "pink", "purple", "brown", "grey", "gray", "beige", "turquoise", "burgundy", "lilac", "indigo", "crimson", "gold", "silver", "black", "white", "red", "blue", "green", "yellow", "orange", "pink", "purple", "brown", "grey", "gray", "beige", "turquoise", "burgundy", "lilac", "indigo", "crimson", "gold", "silver", "black", "white", "red", "blue", "green", "yellow", "orange", "pink", "purple", "brown", "grey", "gray", "beige", "turquoise", "burgundy", "lilac", "indigo", "crimson", "gold", "silver", "black", "white", "red", "blue", "green", "yellow", "orange", "pink", "purple", "brown", "grey", "gray", "beige", "turquoise", "burgundy", "lilac", "indigo", "crimson"};

    public void insertRandomData() throws DuplicateEntityException, IOException {
        if(getAll().size() == 0)
        {
            Random random = new Random();
            for (int i = 0; i < 100; i++) {
                String marca = marci[random.nextInt(marci.length)];
                String model = modele[random.nextInt(modele.length)];
                int randomIdMasina = i;
                add(new Masina(randomIdMasina, marca, model));
            }
        }
    }

    public void deleteAllData()
    {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM masini")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
