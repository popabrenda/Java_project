package repository;

import domeniu.Entity;
import domeniu.Inchiriere;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public interface IRepository<T extends Entity> extends Iterable<T>
{
    public void add(T entitate) throws DuplicateEntityException, RepositoryException;
    public void remove(int id) throws EntityNotFoundException, RepositoryException;
    public T find(int id);
    public Collection<T> getAll() throws RepositoryException;
    public void update(int id, T newEntity) throws EntityNotFoundException, RepositoryException, RuntimeException;

    public void setAll(Collection<T> entitati);
}
