package service;

import domeniu.Inchiriere;
import domeniu.Masina;
import repository.DuplicateEntityException;
import repository.EntityNotFoundException;
import repository.IRepository;
import repository.RepositoryException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;


public class MasinaService
{
    IRepository<Masina> repository;

    public MasinaService(IRepository<Masina> repository)
    {
        this.repository=repository;
    }

    public void add(int id, String marca, String model) throws RepositoryException, DuplicateEntityException{
        repository.add(new Masina(id, marca, model));
    }

    public void modify(int id, String marcaNoua, String modelNou)
    {
        Masina masina = readMasina(id);
        if(readMasina(id) !=null)
        {

            masina.setMarca(marcaNoua);
            masina.setModel(modelNou);
        }
        else
            throw new IllegalArgumentException("masina nu exista");
    }

   public void remove(int id) throws RepositoryException, EntityNotFoundException {


       if(readMasina(id) !=null)
       {
           repository.remove(id);
       }
       else
           throw new IllegalArgumentException("inchirierea nu exista");

   }

    public Collection<Masina> getAll(){
        try {
            return repository.getAll();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    public Masina readMasina(int id)
    {
        Collection<Masina> masini = getAll();
        for (Masina m : masini)
        {
            if(m.getId()==id)
            {
                return m;
            }
        }
        return null;
    }

    public void update(int id,  String marca, String model) throws RepositoryException, EntityNotFoundException {
        Masina masina = readMasina(id);

        if(readMasina(id) != null)
        {
            masina.setMarca(marca);
            masina.setModel(model);
            repository.update(id, masina);

        }
        else
            throw new EntityNotFoundException("masina nu exista");
    }



}
