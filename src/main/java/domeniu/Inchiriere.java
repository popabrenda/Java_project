package domeniu;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;


public class Inchiriere extends Entity
{
    private static final long serialVersionUID = 1000L;
    Masina masina;
    LocalDate data_inceput;
    LocalDate data_sfarsit;

    public Inchiriere(int id, Masina masina, LocalDate data_inceput, LocalDate data_sfarsit)
    {
        super(id);
        this.masina=masina;
        this.data_inceput=data_inceput;
        this.data_sfarsit=data_sfarsit;
    }


    public Masina getMasina()
    {
        return masina;
    }
    public LocalDate getDataInceput()
    {
        return data_inceput;
    }
    public LocalDate getDataSfarsit()
    {
        return data_sfarsit;
    }

    public void setDataInceput(LocalDate data_inceput)
    {
        this.data_inceput=data_inceput;
    }

    public void setDataSfarsit(LocalDate data_sfarsit)
    {
        this.data_sfarsit=data_sfarsit;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false; // Call the superclass implementation
        Inchiriere inchiriere = (Inchiriere) o;
        return Objects.equals(masina, inchiriere.masina) && Objects.equals(data_inceput, inchiriere.data_inceput) && Objects.equals(data_sfarsit, inchiriere.data_sfarsit);
    }
    public int hashCode() {
        return Objects.hash(super.hashCode(), masina, data_inceput, data_sfarsit);
    }
    @Override
    public String toString()
    {
        return  id + "," + masina + "," + data_inceput + "," + data_sfarsit;
    }
}
