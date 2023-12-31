package peaksoft.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StopListRequest {
    private String reason;
    private LocalDate date;
    private String menuItemName;
}
