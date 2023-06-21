package peaksoft.dto.response;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor

public class StopListResponse {
    private Long id;
    private String reason;
    private LocalDate date;
    private String menuItemName;

    public StopListResponse(Long id, String reason, LocalDate date, String menuItemName) {
        this.id = id;
        this.reason = reason;
        this.date = date;
        this.menuItemName = menuItemName;
    }
}
