package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "cheques")
@Getter
@Setter
@NoArgsConstructor

public class Cheque {
    @Id
    @GeneratedValue(generator = "cheque_gen",
            strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "cheque_gen",
            sequenceName = "cheque_seq",
            allocationSize = 1)
    private Long id;
    private int priceAverage;
    private int total;
    private int grandTotal;
    private LocalDate createdAt;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private User user;
    @ManyToMany(mappedBy = "cheques",cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<MenuItem>menuItems;
}
