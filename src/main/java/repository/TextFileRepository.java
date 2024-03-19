package repository;


import domeniu.Entity;
import domeniu.IEntityConverter;

import java.util.Collection;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Collection;

public class TextFileRepository<T extends Entity> extends MemoryRepository<T>
{
    private String fileName;
    private IEntityConverter<T> converter;

    public TextFileRepository(String fileName, IEntityConverter<T> converter)
    {
        this.fileName = fileName;
        this.converter = converter;
        try{
            loadFile();
        }catch (IOException e)
        {
            throw new RuntimeException(e);
        }
       
    }
    private void loadFile() throws IOException {
        // delete whatever is in the repo's list
        entitati.clear();

        // BufferedReader - reads data ahead into a buffer :)
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while (line != null && !line.isEmpty()) {
                entitati.add(converter.fromString(line));
                line = br.readLine();
            }
        }
    }

    private void saveFile() throws IOException {
        try (FileWriter fw = new FileWriter(fileName)) {
            for (T entitate : entitati) {
                fw.write(converter.toString(entitate));
                fw.write("\r\n");
            }
        }
    }


    public void add(T entitate) throws DuplicateEntityException, RepositoryException {
        super.add(entitate);
        try {
            saveFile();
        } catch (IOException e) {
            throw new RepositoryException("eroare la salvarea obiectului", e);
        }
    }



    public void update(int id, T newEntity) throws EntityNotFoundException, RepositoryException{

        T oldEntity = find(id);
        if(find(id) == null)
            throw new EntityNotFoundException("nu exista aceasta entitate");
        int index=-1;
        for(T entity:entitati)
            if(entity.getId() == id)
                index = entitati.indexOf(entity);

        entitati.set(index, newEntity);
        try {
            saveFile();
        } catch (IOException e) {
            throw new RuntimeException("nu s-a putut modifica", e);
        }
    }
    public void remove(int id) throws EntityNotFoundException, RepositoryException {
        super.remove(id);
        try {
            saveFile();
        } catch (IOException e) {
            throw new RepositoryException("nu s-a putut sterge obiectul", e);
        }
    }
    public Collection<T> getAll() throws RepositoryException {
        try {
            saveFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return super.getAll();


    }

}






