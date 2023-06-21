package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
@Entity
@Table(name = "stop_lists")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StopList {
    @Id
    @GeneratedValue(generator = "stop_list_gen",
            strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "stop_list_gen",
            sequenceName = "stop_list_seq",
            allocationSize = 1)
    private Long id;
    private String reason;
    private LocalDate date;
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private MenuItem menuItem;

    public StopList(String reason, LocalDate date) {
        this.reason = reason;
        this.date = date;
    }
}
