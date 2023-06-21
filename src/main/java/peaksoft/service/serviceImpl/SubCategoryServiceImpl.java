package peaksoft.service.serviceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.*;
import peaksoft.dto.response.pagination.PaginationResponseSubCategory;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositry.CategoryRepository;
import peaksoft.repositry.SubCategoryRepository;
import peaksoft.service.SubCategoryService;


@Service
@Transactional
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subcategoryRepository;

    @Override
    public PaginationResponseSubCategory findAll(Long id, int pageSize, int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<SubCategoryResponse> allSubCategory = subcategoryRepository.findSubcategoriesByCategoryId(id, pageable);
        return PaginationResponseSubCategory
                .builder()
                .subCategoryResponseList(allSubCategory.getContent())
                .page(allSubCategory.getNumber() + 1)
                .size(allSubCategory.getTotalPages())
                .build();
    }

    @Override
    public SimpleResponse saveSubcategory(SubCategoryRequest subcategoryRequest) {
        Category category = categoryRepository.findById(subcategoryRequest.getCategoryId())
                .orElseThrow(() -> new NotFoundException("User with id " + subcategoryRequest.getCategoryId() + "is not found!"));
        SubCategory subcategory1 = new SubCategory();
        subcategory1.setName(subcategoryRequest.getName());

        category.addSubCategory(subcategory1);
        subcategory1.setCategory(category);
        categoryRepository.save(category);
        subcategoryRepository.save(subcategory1);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message(String.format("SubCategory with name : %s  " +
                                "successfully saved",
                        subcategoryRequest.getName()))
                .build();
    }

    @Override
    public SubCategoryResponse findById(Long id) {
        return subcategoryRepository.finId(id).orElseThrow(
                () -> new NotFoundException("Subcategory with id : " + id + "is not found!"));
    }

    @Override
    public SimpleResponse update(Long id, SubCategoryRequest subcategoryRequest) {
        SubCategory oldSubcategory = subcategoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Subcategory with id : " + id + "is not found!"));
        Category category = categoryRepository.findById(subcategoryRequest.getCategoryId()).orElseThrow(
                () -> new NotFoundException("Category with id " + subcategoryRequest.getCategoryId() + "is not found!"));
        oldSubcategory.setName(subcategoryRequest.getName());
        oldSubcategory.setCategory(category);
        subcategoryRepository.save(oldSubcategory);
        return SimpleResponse.
                builder()
                .status(HttpStatus.OK)
                .message(String.format("SubCategory with name : %s  " +
                                "successfully update",
                        subcategoryRequest.getName()))
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        if (!subcategoryRepository.existsById(id)) {
            throw new NotFoundException("Subcategory with id : " + id + "is not found");
        }
        subcategoryRepository.deleteById(id);
        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Subcategory with id : %s is deleted!", id)).build();
    }
}