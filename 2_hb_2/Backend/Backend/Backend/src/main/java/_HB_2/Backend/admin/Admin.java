package _HB_2.Backend.admin;

import _HB_2.Backend.user.User;

import javax.persistence.Entity;

@Entity
public class Admin extends User {

    public Admin(String firstName, String lastName, String address, String city, String state, String zip, String email, String phoneNumber, String password) {
        super(firstName, lastName, address, city, state, zip, email, phoneNumber, password);
        this.setAnAdmin(true);
    }

    public Admin() {

    }
}
