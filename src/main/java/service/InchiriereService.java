package service;

import domeniu.Inchiriere;
import domeniu.Masina;
import repository.DuplicateEntityException;
import repository.EntityNotFoundException;
import repository.IRepository;
import repository.RepositoryException;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class InchiriereService
{
    IRepository<Inchiriere> repository;

    public InchiriereService(IRepository<Inchiriere> repository)
    {
        this.repository = repository;

    }

    public void add(int id, Masina masina, LocalDate dataInceput, LocalDate dataSfarsit) throws DuplicateEntityException, RepositoryException, IOException {
        ArrayList<Inchiriere> inchirieri = (ArrayList<Inchiriere>) repository.getAll();
        int ok=0;
        for(Inchiriere inchiriere: inchirieri)
        {
            Masina m = inchiriere.getMasina();

            if(m.getId() == id && dataInceput.isAfter( inchiriere.getDataInceput()) && dataInceput.isBefore(inchiriere.getDataSfarsit()))
                throw new DuplicateEntityException("Inchirierea se suprapune");
            else if (m.getId() == id && dataSfarsit.isAfter(inchiriere.getDataInceput()) && dataSfarsit.isBefore(inchiriere.getDataSfarsit()))
                throw new DuplicateEntityException("Inchirierea se suprapune");
            else ok=1;
        }
        if (ok==1)
            repository.add(new Inchiriere(id, masina, dataInceput, dataSfarsit));

    }
//    public void add(int id, Masina masina, LocalDate dataInceput, LocalDate dataSfarsit) throws DuplicateEntityException, IOException {
//        try {
//            repository.add(new Inchiriere(id, masina, dataInceput, dataSfarsit));
//        } catch (RepositoryException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public Inchiriere readInchiriere(int id) throws RepositoryException {
        Collection<Inchiriere> inchirieri = getAll();
        for (Inchiriere i : inchirieri)
        {
            if(i.getId()==id)
            {
                return i;
            }
        }
        return null;
    }

    public void remove(int id) throws EntityNotFoundException, RepositoryException
    {

        if(readInchiriere(id) !=null)
        {
            repository.remove(id);
        }
        else
            throw new IllegalArgumentException("inchirierea nu exista");
    }


    public Collection<Inchiriere> getAll() throws RepositoryException {
        return repository.getAll();
    }

    public void update(int id, LocalDate data_inceput_noua, LocalDate data_sfarsit_noua) throws RepositoryException, EntityNotFoundException {

        Inchiriere inchiriere = readInchiriere(id);
        if(readInchiriere(id) !=null)
        {
            inchiriere.setDataInceput(data_inceput_noua);
            inchiriere.setDataSfarsit(data_sfarsit_noua);
            repository.update(id, inchiriere);
        }
        else
        throw new EntityNotFoundException("inchirierea nu exista");
    }



}
