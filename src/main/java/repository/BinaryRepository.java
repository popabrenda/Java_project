package repository;

import domeniu.Entity;
import repository.DuplicateEntityException;
import repository.MemoryRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class BinaryRepository <T extends Entity> extends MemoryRepository<T> {
    private final String fileName;

    public BinaryRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void add(T entity) throws DuplicateEntityException
    {
        List<T> entities = loadEntities();
        if (entity == null)
        {
            throw new IllegalArgumentException("entitatea nu poate fi null");
        }

        if (find(entity.getId()) != null)
        {
            throw new DuplicateEntityException("duplicat, aceasta entitate exista deja");
        }

        entities.add(entity);
        saveEntities(entities);
    }

    @Override
    public void remove(int id)
    {
        List<T> entities = loadEntities();
        boolean removed = entities.removeIf(e -> e.getId() == id);
        if (!removed) {
            throw new IllegalArgumentException("nu exista aceasta entitate");
        }
        saveEntities(entities);
    }

    public void update(int id, T newEntity) throws EntityNotFoundException, RepositoryException {
        List<T> entities = loadEntities();
        if (find(id) == null) {
            throw new EntityNotFoundException("Entity doesn't exist!");
        }
        int index=-1;
        for(T entity:entities)
            if(entity.getId()==id){
                index=entities.indexOf(entity);
            }
        entities.set(index, newEntity);
        saveEntities(entities);
    }


    @Override
    public T find(int id)
    {
        List<T> entities = loadEntities();
        return entities.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Collection<T> getAll()
    {
        return new ArrayList<T>(loadEntities());
    }


    private void saveEntities(List<T> entities)
    {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName)))
        {
            outputStream.writeObject(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<T> loadEntities()
    {
        List<T> entities = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName)))
        {
            entities = (List<T>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e)
        {
            System.out.println("Repo deschide un nou fisier");
        }
        return entities;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new ArrayList<T>(loadEntities()).iterator();
    }
}
