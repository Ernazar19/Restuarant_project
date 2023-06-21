package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.pagination.PaginationResponseCategory;
import peaksoft.entity.Category;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repositry.CategoryRepository;
import peaksoft.service.CategoryService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public PaginationResponseCategory getAllCategories(int pageSize, int currentPage) {
        Pageable pageable= PageRequest.of(currentPage-1,pageSize);
        Page<CategoryResponse> allCategory=categoryRepository.getAllCategories(pageable);
        return PaginationResponseCategory
                .builder()
                .categoryResponseList(allCategory.getContent())
                .page(allCategory.getNumber()+1)
                .size(allCategory.getTotalPages())
                .build();

    }

    @Override
    public SimpleResponse saveCategory(CategoryRequest request) {
        List<Category> categories = categoryRepository.findAll()
                .stream().filter(category -> category.getName().equalsIgnoreCase(request.getName())).toList();
        Category category = new Category();
        if (categories.size() == 0) {
            if (category.getName().equalsIgnoreCase(request.getName())) {
                throw new BadRequestException("A category with this name has already been registered ");
            } else {
                categoryRepository.save(category);
                return SimpleResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Category successfully saved")
                        .build();
            }
        } else {
            throw new BadRequestException("Can't be name is 0 ");
        }
    }
    @Override
    public CategoryResponse getById(Long id) {
        return categoryRepository.getCategoriesById(id).
                orElseThrow(() -> new NotFoundException(String.format("Category with id: %s is not found", id)));
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(String.format("Category with id: %s is not found", id)));
            categoryRepository.delete(category);
        } catch (NotFoundException n) {
            System.out.println(n.getMessage());
        }
        return SimpleResponse.builder().
                status(HttpStatus.OK).
                message("Category with id: " + id + " is successfully deleted").
                build();
    }

    @Override
    public SimpleResponse update(Long id, CategoryRequest request) {
        if (id != null || request != null) {
            if (!request.getName().isBlank()) {
                assert id != null;
                Category category = categoryRepository.findById(id).orElseThrow(() ->
                        new NotFoundException(String.format
                                ("There is no Category with this ID %s", id)));
                category.setName(request.getName());
                categoryRepository.save(category);
                return SimpleResponse.builder().
                        status(HttpStatus.OK).message(String.format("Category with id: %s successfully updated", id)).
                        build();
            }
        }
        return null;
    }
}
