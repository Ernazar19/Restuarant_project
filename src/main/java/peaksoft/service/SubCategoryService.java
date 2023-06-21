package peaksoft.service;

import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.dto.response.pagination.PaginationResponseSubCategory;

public interface SubCategoryService {
    PaginationResponseSubCategory findAll(Long id, int pageSize, int currentPage);
    SimpleResponse saveSubcategory(SubCategoryRequest subCategoryRequest);
    SubCategoryResponse findById(Long id);
    SimpleResponse update(Long id,SubCategoryRequest subCategoryRequest);
    SimpleResponse deleteById(Long id);
}
