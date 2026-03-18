package dev.boog.moneyloverdatamanager.exceptions.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldsDependencyValidator.class)
@Repeatable(value = FieldsDependencies.class)
public @interface FieldsDependency {

    String message() default "Invalid field combination";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldName();

    String dependsOn();
}
