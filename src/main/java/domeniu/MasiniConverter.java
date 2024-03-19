package domeniu;

public class MasiniConverter implements IEntityConverter<Masina> {

    public Masina fromString(String line)
    {
        String[] token = line.split(",");
        int id = Integer.parseInt(token[0]);
        String marca = token[1];
        String model = token[2];

        return new Masina(id, marca, model);
    }

    @Override
    public String toString(Masina object) {
        return object.getId() + "," + object.getMarca() + "," + object.getModel();

    }
}
