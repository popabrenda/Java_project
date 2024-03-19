package domeniu;

import java.util.Objects;

public class Masina extends Entity
{
    private static final long serialVersionUID = 1000L;
    String marca;
    String model;


    public Masina(int id)
    {
        super(id);
    }

    public Masina(int id, String marca, String model)
    {
        super(id);
        this.marca=marca;
        this.model=model;
    }

    public String getMarca()
    {
        return marca;
    }
    public String getModel()
    {
        return model;
    }
    public void setMarca(String marca)
    {
        this.marca=marca;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false; // Call the superclass implementation
        Masina masina = (Masina) o;
        return ( Objects.equals(marca, masina.marca) && Objects.equals(model, masina.model) );
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), marca, model);
    }

    @Override
    public String toString()
    {
        return id + "," + marca + "," + model;
    }

}



