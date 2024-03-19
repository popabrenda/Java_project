package repository;

import domeniu.Inchiriere;
import domeniu.Masina;
import org.sqlite.SQLiteDataSource;
import service.MasinaService;


import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class InchirieriRepositoryDB extends MemoryRepository<Inchiriere> implements IDbRepository<Inchiriere>{

    private String JDBC_URL = "jdbc:sqlite:inchirieri.db";

    private Connection connection;

    public InchirieriRepositoryDB()
    {
        openConnection();
        createTable();
        generareListaInchirieri();
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
            stmt.execute("CREATE TABLE IF NOT EXISTS inchirieri(id int, id_masina int,marca varchar(400), model  varchar(400) ,  data_inceput date, data_sfarsit date );");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initTable()
    {
        List<Inchiriere> inchirieri = new ArrayList<>();
        Inchiriere i1 = new Inchiriere(1,new Masina(1,"audi", "a4"), Date.valueOf("2020-01-01").toLocalDate(), Date.valueOf("2020-01-02").toLocalDate());
        Inchiriere i2 = new Inchiriere(2,new Masina(2,"bmw", "x6"), Date.valueOf("2022-01-15").toLocalDate(), Date.valueOf("2022-02-22").toLocalDate());
        Masina masina2 = new Masina(2, "bmw", "x6");
        Masina masina3 = new Masina(3, "audi", "a3");
        inchirieri.add(i1);
        inchirieri.add(i2);
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO inchirieri values (?,?,?,?,?,?);")) {
            for (Inchiriere p: inchirieri)
            {
                stmt.setInt(1,p.getId());
                stmt.setInt(2,p.getMasina().getId());
                stmt.setString(3,p.getMasina().getMarca());
                stmt.setString(4,p.getMasina().getModel());
                stmt.setDate(5, Date.valueOf(p.getDataInceput()));
                stmt.setDate(6, Date.valueOf(p.getDataSfarsit()));
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Inchiriere> getAll()
    {

        ArrayList<Inchiriere> inchirieri = new ArrayList<>();
        try
        {
            String sql = "SELECT * FROM Inchirieri;";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            var result = stmt.getResultSet();
            while(result.next())
            {
                int id = result.getInt("id");
                int idMasina = result.getInt("id_masina");
                String numeMarca = result.getString("marca");
                String numeModel = result.getString("model");
                LocalDate dataInceput = result.getDate("data_inceput").toLocalDate();
                LocalDate dataSfarsit = result.getDate("data_sfarsit").toLocalDate();
                inchirieri.add(new Inchiriere(id, new Masina(idMasina, numeMarca, numeModel), dataInceput, dataSfarsit));
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return inchirieri;
    }

    public void add(Inchiriere p)
    {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO Inchirieri (id, id_masina, marca, model, data_inceput, data_sfarsit) VALUES (?, ?, ?, ?, ?, ?);")) {
            ArrayList<Masina> masini = new ArrayList<>();
            masini = new MasiniRepositoryDB().getAll();
            for(Masina m : masini)
            {
                Masina masina = p.getMasina();
                if(m.getId() == masina.getId())
                {

                    stmt.setInt(1,p.getId());
                    stmt.setInt(2,masina.getId());
                    stmt.setString(3,masina.getMarca());
                    stmt.setString(4,masina.getModel());
                    stmt.setDate(5, Date.valueOf(p.getDataInceput()));
                    stmt.setDate(6, Date.valueOf(p.getDataSfarsit()));
                    int ok = 0;
                    ArrayList<Inchiriere> inchirieri = getAll();
                    for (Inchiriere inchiriere: inchirieri)
                    {
                        if (inchiriere.getId() == p.getId()) {
                            ok = 1;
                            throw new DuplicateEntityException("Inchirierea exista deja");
                        }
                    }
                    if(ok== 0)
                        stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(int id, Inchiriere entitate) throws  EntityNotFoundException {
        if(entitate == null)
        {
            throw new EntityNotFoundException("entitatea nu exista");
        }
        try
        {
            String sql = "UPDATE Inchirieri SET id_masina = ?, marca = ?, model = ?, data_inceput = ?, data_sfarsit = ? WHERE id = ?;";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, entitate.getMasina().getId());
            stmt.setString(2, entitate.getMasina().getMarca());
            stmt.setString(3, entitate.getMasina().getModel());
            stmt.setDate(4, Date.valueOf(entitate.getDataInceput()));
            stmt.setDate(5, Date.valueOf(entitate.getDataSfarsit()));
            stmt.setInt(6, id);
            stmt.execute();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void remove(int id)
    {
        try
        {
            String sql = "DELETE FROM Inchirieri WHERE id = ?;";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static LocalDate generareDataAleatoare(LocalDate dataStart, LocalDate dataSfarsit) {
        long start = dataStart.toEpochDay();
        long end = dataSfarsit.toEpochDay();
        long randomDay = start + new Random().nextInt((int) (end - start + 1));
        return LocalDate.ofEpochDay(randomDay);
    }

    // Funcție care generează o listă de 100 de închirieri aleatorii
    public void generareListaInchirieri() {




        List<Masina> masini = new ArrayList<>();
        masini = (List<Masina>) new MasinaService(new repository.MasiniRepositoryDB()).getAll();

        if (getAll().size() == 0) {

            Random random = new Random();
            int masiniCount = masini.size();

            for (int i = 0; i < 100; i++) {
                // Alege aleator o mașină din lista existentă
                Masina masinaAleasa = masini.get(random.nextInt(masiniCount));

                // Generează date aleatoare pentru început și sfârșit
                LocalDate dataInceput = generareDataAleatoare(LocalDate.now(), LocalDate.now().plusMonths(12));
                LocalDate dataSfarsit = generareDataAleatoare(dataInceput, dataInceput.plusDays(30));

                // Creează închirierea și adaugă-o la lista de închirieri
                Inchiriere inchiriere = new Inchiriere(i + 1, masinaAleasa, dataInceput, dataSfarsit);
                add(inchiriere);
                System.out.println("generare inchiriere: " + inchiriere);
            }
        }

    }





}

