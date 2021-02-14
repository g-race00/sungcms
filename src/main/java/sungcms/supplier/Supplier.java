package sungcms.supplier;

public final class Supplier implements java.io.Serializable {
    private String id;
    private String name;
    private String email;
    private String phone;

    public Supplier(){
        this.id = "-1";
        this.name = "";
        this.email = "";
        this.phone = "";
    }

    public Supplier(String id, String name, String email, String phone){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Supplier(String name, String email, String phone){
        this.id = "-1";
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
