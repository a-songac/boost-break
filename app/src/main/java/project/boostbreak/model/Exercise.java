package project.boostbreak.model;

/**
 * Class to implement exercise model
 */
public class Exercise {

    private long id;
    private String name;
    private String description;
    private int category;

    // order is linked to integer id of a category in the db
    //TODO String array resource
    public static final String[] categoryArr = {"Upper Body", "Lower Body", "stamina", "stretching"};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory(){
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return name;
    }
}
