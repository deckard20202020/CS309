package coms309.players;

public class Players {

    private String firstName;

    private String lastName;

    private String sport;

    private Integer id;

    public Players(){

    }

    public Players(String firstName, String lastName, String sport, Integer id){
        this.firstName = firstName;
        this.lastName = lastName;
        this.sport = sport;
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSport() {
        return this.sport;
    }

    public void setSport(String address) {
        this.sport = sport;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return firstName + " "
                + lastName + " "
                + sport + " "
                + id;
    }
}
