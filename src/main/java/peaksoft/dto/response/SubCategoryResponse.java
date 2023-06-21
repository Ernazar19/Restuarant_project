package peaksoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor

public class SubCategoryResponse {
    private Long id;
    private String name;
    private String categoryName;

    public SubCategoryResponse(Long id, String name, String categoryName) {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
    }
}
