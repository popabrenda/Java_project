package repository;

import domeniu.Entity;
import domeniu.Inchiriere;
import domeniu.Masina;

import java.io.IOException;
import java.util.*;

public class MemoryRepository<T extends Entity> implements IRepository<T>
{

    List<T> entitati =  new ArrayList<T>();
    @Override
    public void add(T entitate) throws DuplicateEntityException, RepositoryException {
        if(entitate == null)
            throw new IllegalArgumentException("Dati o entitate care e diferita de null!");

        if(find(entitate.getId()) != null)
            throw new DuplicateEntityException("Aceasta entitate exista deja!");

        entitati.add(entitate);
    }


    @Override
    public void remove(int id) throws EntityNotFoundException, RepositoryException {
        if (find(id) == null) {
            throw new EntityNotFoundException("Entitatea nu exista!");
        }
        Iterator<T> iterator = entitati.iterator();
        while (iterator.hasNext()) {
            T entity = iterator.next();
            if (entity.getId() == id)
            {
                iterator.remove();
                break;
            }
        }
    }



    @Override
    public T find(int id) {
        for(T entitate : entitati)
        {
            if(entitate.getId()==id)
            {
                return entitate;
            }
        }
        return null;
    }

    @Override
    public Collection<T> getAll() throws RepositoryException {
        return new ArrayList<T>(entitati);
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayList<T>(entitati).iterator();
    }

    @Override
    public void update(int id, T newEntity) throws EntityNotFoundException, RepositoryException, RuntimeException {

        T oldEntity = find(id);
        if(find(id) == null)
            throw new EntityNotFoundException("nu exista aceasta entitate");
        int index = entitati.indexOf(oldEntity);
        entitati.set(index, newEntity);
    }

    public void setAll(Collection<T> entitati) {
        this.entitati = new ArrayList<T>(entitati);
    }
}
