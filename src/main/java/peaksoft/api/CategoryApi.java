package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.pagination.PaginationResponseCategory;
import peaksoft.service.CategoryService;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import java.util.List;
@RestController
@RequestMapping("/api/restaurant/category")
@RequiredArgsConstructor
public class CategoryApi {
    private final CategoryService categoryService;
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SimpleResponse save(@RequestBody CategoryRequest request){
        return categoryService.saveCategory(request);
    }
    @GetMapping("/getAll")public PaginationResponseCategory getAllCategories(int pageSize, int currentPage) {
        return categoryService.getAllCategories(pageSize,currentPage);
    }
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SimpleResponse delete(@PathVariable Long id){
        return categoryService.deleteById(id);
    }
    @GetMapping("/get/{id}")
    public CategoryResponse getById(@PathVariable Long id){
        return categoryService.getById(id);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SimpleResponse update(@PathVariable Long id, @RequestBody CategoryRequest request){
        return categoryService.update(id,request);
    }

}

