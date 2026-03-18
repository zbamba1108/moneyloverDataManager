package dev.boog.moneyloverdatamanager.exceptions.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class FieldsDependencyValidator implements ConstraintValidator<FieldsDependency, Object> {

    private String fieldName;
    private String dependsOn;

    @Override
    public void initialize(FieldsDependency constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.dependsOn = constraintAnnotation.dependsOn();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Object fieldValue = getField(value, fieldName);
            Object dependsOnValue = getField(value, dependsOn);

            if (fieldValue != null && dependsOnValue == null) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(
                                dependsOn + " must be provided when " + fieldName + " is set"
                        ).addPropertyNode(dependsOn)
                        .addConstraintViolation();
                return false;
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    private Object getField(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }
}
