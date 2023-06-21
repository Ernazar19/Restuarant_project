package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryRequest {
    private Long categoryId;
    @NotEmpty(message = "fill in the field")
    private String name;

}
