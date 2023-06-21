package peaksoft.anatation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Documented
@Constraint(validatedBy = UniqueValidate.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    String message() default "A category with this name has already been registered!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
