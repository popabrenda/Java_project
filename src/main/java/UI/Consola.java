package UI;

import domeniu.Inchiriere;
import domeniu.Masina;
import repository.DuplicateEntityException;
import repository.EntityNotFoundException;
import repository.RepositoryException;
import service.InchiriereService;
import service.MasinaService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import java.util.Scanner;

public class Consola
{
    InchiriereService inchiriereService;
    MasinaService masinaService;

    public Consola(InchiriereService inchiriereService, MasinaService masinaService)
    {
        this.inchiriereService=inchiriereService;
        this.masinaService=masinaService;
    }

    private void printMenu()
    {
        System.out.println("1. Adaugare masina");
        System.out.println("2. Modificare masina");
        System.out.println("3. Stergere masina");
        System.out.println("4. Adaugare inchiriere");
        System.out.println("5. Modificare inchiriere");
        System.out.println("6. Stergere inchiriere");
        System.out.println("m. Afisare masini");
        System.out.println("i. Afisare inchirieri");
        System.out.println("0. Iesire");
    }
    public void addMasini()
    {
        try{
//            masinaService.add(9,"masina", "alba");
//            masinaService.add(2,"ford", "mondeo");
//            masinaService.add(3,"toyota", "chr");
//            masinaService.add(4,"audi", "a6");
//            masinaService.add(5,"bmw","x6");
            LocalDate dataInceput = LocalDate.of(2021,11,14);
            LocalDate dataSfarsit = LocalDate.of(2021,11,19);
            Masina masina = new Masina(1,"audi", "a4");
//            inchiriereService.add(1,masina ,dataInceput, dataSfarsit);
//            LocalDate dataInceput1 = LocalDate.of(2022,2,10);
//            LocalDate dataSfarsit1 = LocalDate.of(2022,3,04);
//            Masina masina2 = new Masina(2,"ford", "mondeo");
//            inchiriereService.add(1,masina ,dataInceput1, dataSfarsit1);


        }catch (Exception ex)
        {
            System.out.println(ex.toString());
        }


    }
    public void runMenu() throws DuplicateEntityException, IllegalArgumentException {

        addMasini();
        while (true)
        {
            printMenu();
            String option;

            Scanner scanner = new Scanner(System.in);
            option=scanner.next();
            switch (option)
            {
                case "1":
                {
                    try
                    {
                        System.out.println("id masina: ");
                        int id = scanner.nextInt();
                        System.out.println("marca masina: ");
                        String marca = scanner.next();
                        System.out.println("model masina: ");
                        String model = scanner.next();
                        masinaService.add(id, marca, model);
                    } catch (Exception ex)
                    {
                        System.out.println(ex.toString());
                    }
                    break;
                }
                case "2":
                {
                    try
                    {
                        System.out.println("Dati id-ul masinii pe care doriti sa o modificati");
                        int id = scanner.nextInt();
                        System.out.println("Dati un marca nou: ");
                        String marca = scanner.next();
                        System.out.println("Dati un model nou");
                        String model = scanner.next();
                        masinaService.update(id, marca, model);
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex.toString());
                    }
                    break;
                }
                case "3":
                {
                    try
                    {
                        System.out.println("Dati id-ul masinii pe care doriti sa il stergeti");
                        int id = scanner.nextInt();
                        masinaService.remove(id);

                    }
                    catch (EntityNotFoundException ex)
                    {
                        System.out.println(ex.toString());
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex.toString());
                    }
                    break;
                }
                case "0":
                {
                    System.out.println("ceaooo!");
                    return;
                }
                case "4":
                {
                    try
                    {
                        System.out.println("id inchiriere: ");
                        int idInchiriere = scanner.nextInt();
                        Collection<Inchiriere> inchirieri = inchiriereService.getAll();
                        System.out.println("Selectati perioada de inchiriere: ");
                        System.out.println("ziua in care incepeti inchirierea: ");
                        int ziInceput = scanner.nextInt();
                        System.out.println("luna in care incepeti inchirierea: ");
                        int lunaInceput = scanner.nextInt();
                        System.out.println("anul in care incepeti inchirierea: ");
                        int anInceput = scanner.nextInt();
                        System.out.println("ziua in care terminati inchirierea: ");
                        int ziSfarsit = scanner.nextInt();
                        System.out.println("luna in care terminati inchirierea: ");
                        int lunaSfarsit = scanner.nextInt();
                        System.out.println("anul in care terminati inchirierea: ");
                        int anSfarsit = scanner.nextInt();
                        LocalDate dataInceput = LocalDate.of(anInceput,lunaInceput, ziInceput);
                        LocalDate dataSfarsit = LocalDate.of(anSfarsit, lunaSfarsit, ziSfarsit);


                        System.out.println("id-ul masinii pe care doriti sa o inchiriati: ");
                        int idMasina = scanner.nextInt();

                        ArrayList<Masina> masini = (ArrayList<Masina>) masinaService.getAll();
                        for(Masina m : masini)
                        {
                            if(m.getId() == idMasina)
                            {
//                                System.out.println("masina exista");
                                Masina masina = masinaService.readMasina(idMasina);
                            }

                        }
//                        System.out.println("masina gasita in consola:");
                        Masina masina = masinaService.readMasina(idMasina);
//                        System.out.println(masina.getId() + " " + masina.getMarca() + " " + masina.getModel());
                        inchiriereService.add(idInchiriere, masina, dataInceput, dataSfarsit);
                    }catch (Exception ex)
                    {
                        System.out.println(ex.toString());
                    }

                    break;
                }
                case "5":
                {

                    try {
                        System.out.println("id inchiriere modificata: ");
                        int idInchiriere = scanner.nextInt();
                        Collection<Inchiriere> inchirieri = inchiriereService.getAll();
                        System.out.println("Selectati perioada de inchiriere: ");
                        System.out.println("ziua in care incepeti inchirierea: ");
                        int ziInceput = scanner.nextInt();
                        System.out.println("luna in care incepeti inchirierea: ");
                        int lunaInceput = scanner.nextInt();
                        System.out.println("anul in care incepeti inchirierea: ");
                        int anInceput = scanner.nextInt();
                        System.out.println("ziua in care terminati inchirierea: ");
                        int ziSfarsit = scanner.nextInt();
                        System.out.println("luna in care terminati inchirierea: ");
                        int lunaSfarsit = scanner.nextInt();
                        System.out.println("anul in care terminati inchirierea: ");
                        int anSfarsit = scanner.nextInt();
                        LocalDate dataInceput = LocalDate.of(anInceput,lunaInceput, ziInceput);
                        LocalDate dataSfarsit = LocalDate.of(anSfarsit, lunaSfarsit, ziSfarsit);
                        inchiriereService.update(idInchiriere, dataInceput, dataSfarsit);

                    } catch (RepositoryException e) {
                        System.out.println("repository exception");
                    } catch (EntityNotFoundException e) {
                        System.out.println("inchirierea nu exista");
                    }
                    break;

                }
                case "6":
                {
                    try
                    {
                        System.out.println("Dati id-ul inchirierii pe care doriti sa il stergeti");
                        int id = scanner.nextInt();
                        inchiriereService.remove(id);

                    }
                    catch (EntityNotFoundException ex)
                    {
                        System.out.println(ex.toString());
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex.toString());
                    }
                    break;
                }
                case "m":
                {
                    Collection<Masina> masini = null;
                    masini = masinaService.getAll();
                    for(Masina m: masini)
                        System.out.println(m);
                    break;
                }
                case "i":
                {
                    Collection<Inchiriere> inchirieri = null;
                    try {
                        inchirieri = inchiriereService.getAll();
                    } catch (RepositoryException e) {
                        throw new RuntimeException(e);
                    }
                    for(Inchiriere i: inchirieri)
                        System.out.println(i);
                    break;
                }
                default:
                {
                    System.out.println("Optiune gresita! Reincercati!");
                }
            }
        }
    }

}
