package Main;

import UI.Consola;
import com.example.lab4_fx.HelloApplication;
import domeniu.*;
import repository.*;
import service.InchiriereService;
import service.MasinaService;

import java.io.IOException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws DuplicateEntityException, RepositoryException, IOException {
        IEntityConverter<Masina> masinaConverter = new MasiniConverter();
        IEntityConverter<Inchiriere> inchiriereConverter = new InchiriereConverter();
        IRepository<Masina> repositoryMasina= new MemoryRepository<>();
        IRepository<Inchiriere> repositoryInchiriere = new MemoryRepository<>();

        IRepository<Masina> dbrepositoryMasina = new MasiniRepositoryDB();
        ((MasiniRepositoryDB) dbrepositoryMasina).openConnection();


        Settings setari = Settings.getInstance();

        if (Objects.equals(setari.getRepoType(), "memorie")) {
            repositoryMasina = new MemoryRepository<>();
            repositoryInchiriere = new MemoryRepository<>();
        }
        if (Objects.equals(setari.getRepoType(), "text")){
            repositoryMasina = new TextFileRepository<>(setari.getRepoMasina(), masinaConverter);
            repositoryInchiriere = new TextFileRepository<>(setari.getRepoInchiriere(), inchiriereConverter);
        }
        if (Objects.equals(setari.getRepoType(), "binar")){
            repositoryMasina = new BinaryRepository<>(setari.getRepoMasina());
            repositoryInchiriere = new BinaryRepository<>(setari.getRepoInchiriere());
        }

        if(Objects.equals(setari.getRepoType(), "database")){
            repositoryMasina = new MasiniRepositoryDB();
            repositoryInchiriere = new InchirieriRepositoryDB();
            ((MasiniRepositoryDB) repositoryMasina).openConnection();
            ((InchirieriRepositoryDB) repositoryInchiriere).openConnection();
        }

        MasinaService masinaService = new MasinaService(repositoryMasina);
        InchiriereService inchiriereService = new InchiriereService(repositoryInchiriere);
        Consola consola = new Consola(inchiriereService,masinaService);
        HelloApplication helloApplication = new HelloApplication();
        //helloApplication.main(args);
        consola.runMenu();
    }
}

