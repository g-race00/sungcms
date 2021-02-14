package sungcms.category;

public final class Category implements java.io.Serializable {
    private String id;
    private String name;
    private String description;

    public Category(){
        this.id = "-1";
        this.name = "";
        this.description = "";
    }

    public Category(String id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Category(String name, String description){
        this.id = "-1";
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
