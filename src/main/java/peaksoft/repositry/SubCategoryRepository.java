package peaksoft.repositry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.entity.SubCategory;

import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    @Query("select  new  peaksoft.dto.response.SubCategoryResponse(s.id,s.name,s.category.name) from SubCategory s")
    Page<SubCategoryResponse> findSubcategoriesByCategoryId(Long id, Pageable pageable);

    Optional<SubCategoryResponse> finId(Long id);
}

