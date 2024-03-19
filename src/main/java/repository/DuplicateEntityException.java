package repository;

public class DuplicateEntityException extends  Exception
{
    public DuplicateEntityException(String message)
    {
        super(message);
    }
}
