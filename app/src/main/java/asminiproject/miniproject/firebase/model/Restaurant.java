package asminiproject.miniproject.firebase.model;

import androidx.annotation.NonNull;

import java.util.List;

public class Restaurant {
    private int name;
    private Address address;
    private List<String> meals;

    public Restaurant(int _name, Address _address, List<String> _meals) {
        this.name = _name;
        this.address = _address;
        this.meals = _meals;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getMeals() {
        return meals;
    }

    public void setMeals(List<String> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("************ Restaurant ************\n");
        sb.append("Nom : ");
        sb.append(this.getName());
        sb.append("\nAddress : ");
        sb.append(this.getAddress().toString());
        sb.append("\nMeals list: ");
        sb.append(this.getMeals().toString());
        sb.append("************************************\n");

        return sb.toString();
    }
}
