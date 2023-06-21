package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(generator = "category_gen",
            strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_gen",
            sequenceName = "category_seq",
            allocationSize = 1)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubCategory>subcategories = new ArrayList<>();
    public void addSubCategory(SubCategory subcategory1) {
        subcategories.add(subcategory1);
    }
}
