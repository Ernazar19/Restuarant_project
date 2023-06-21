package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@NoArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(generator = "menu_item_gen",
            strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "menu_item_gen",
            sequenceName = "menu_item_seq",
            allocationSize = 1)
    private Long id;
    private String name;
    private String image;
    private int price;
    private String description;
    private Boolean isVegetarian;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private  Restaurant restaurant;
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private SubCategory subcategories;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Cheque> cheques = new ArrayList<>();
    @OneToOne(mappedBy = "menuItem",cascade = {CascadeType.MERGE,CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH})
    private  StopList stopList;

    public MenuItem(String name, String image, int price, String description, Boolean isVegetarian) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }
}
