package dbutil;

public interface DBStorable {
    //Basic CRUD operations
    public void createDBObject();
    public void readDBObject();
    public void updateDBObject();
    public void deleteDBObject();
}
