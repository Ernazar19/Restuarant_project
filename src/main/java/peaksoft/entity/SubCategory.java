package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "sub_categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory {
    @Id
    @GeneratedValue(generator = "sub_category_gen",
            strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sub_category_gen",
            sequenceName = "sub_category_seq",
            allocationSize = 1)
    private Long id;
    private String name;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Category category;
    @OneToMany(mappedBy = "subcategories",cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<MenuItem> menuItems;

    public SubCategory(String name) {
        this.name = name;
    }
    public void addMenuItem(MenuItem menuItem) {
    }
}
