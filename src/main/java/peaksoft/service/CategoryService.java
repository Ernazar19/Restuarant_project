package peaksoft.service;

import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.pagination.PaginationResponseCategory;
import peaksoft.entity.Category;

import java.util.List;

public interface CategoryService {
    PaginationResponseCategory getAllCategories(int pageSize, int currentPage);
    SimpleResponse saveCategory(CategoryRequest request);

    CategoryResponse getById(Long id);
    SimpleResponse deleteById(Long id);
    SimpleResponse update(Long id,CategoryRequest request);
}
