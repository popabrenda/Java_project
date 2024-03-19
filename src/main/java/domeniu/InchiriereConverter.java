package domeniu;


import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;

public class InchiriereConverter implements IEntityConverter<Inchiriere>
{
    @Override
    public String toString(Inchiriere object) {
        return object.getId() + "," + object.getMasina() + "," + object.getDataInceput() + "," + object.getDataSfarsit();
    }

    @Override
    public Inchiriere fromString(String line)
    {
        String[] token = line.split(",");
        int id = Integer.parseInt(token[0]);
        int idMasina = Integer.parseInt(token[1]);
        String marca = token[2];
        String model = token[3];
        LocalDate dataInceput = LocalDate.parse(token[4]);
        LocalDate dataSfarsit = LocalDate.parse(token[5]);

        Masina masina = new Masina(idMasina, marca, model);
        return new Inchiriere(id, masina, dataInceput, dataSfarsit);
    }


}






