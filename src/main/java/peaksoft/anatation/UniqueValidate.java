package peaksoft.anatation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import peaksoft.entity.Category;
import peaksoft.exceptions.BadRequestException;
import peaksoft.repositry.CategoryRepository;

import java.util.List;
@RequiredArgsConstructor
public class UniqueValidate implements ConstraintValidator<Unique,String> {
    private  final CategoryRepository categoryRepository;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        List<Category> categories = categoryRepository.findAll().stream().filter(category -> category.getName().equalsIgnoreCase(s)).toList();
        if (categories.size() == 0) {
            return false;
        } else {
            throw new BadRequestException("The name must be unique!");
        }
    }
}
