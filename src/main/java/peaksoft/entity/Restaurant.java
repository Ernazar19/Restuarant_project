package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.enums.RestaurantType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(generator = "restaurant_gen",
            strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "restaurant_gen",
            sequenceName = "restaurant_seq",
            allocationSize = 1)
    private Long id;
    private String name;
    private String location;
    private RestaurantType restType;
    private int numberOfEmployees;
    private int service;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();
    @OneToMany(mappedBy ="restaurant" ,cascade =CascadeType.ALL )
    private List<MenuItem>menuItems = new ArrayList<>();

    public Restaurant(Long id, String name, String location, RestaurantType restType, int numberOfEmployees, int service) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.restType = restType;
        this.numberOfEmployees = numberOfEmployees;
        this.service = service;
    }

    public Restaurant(String name, String location, RestaurantType restType, int numberOfEmployees, int service) {
        this.name = name;
        this.location = location;
        this.restType = restType;
        this.numberOfEmployees = numberOfEmployees;
        this.service = service;
    }
}
