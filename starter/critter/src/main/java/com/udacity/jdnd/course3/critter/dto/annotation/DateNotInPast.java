package com.udacity.jdnd.course3.critter.dto.annotation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

@Constraint(validatedBy = DateNotInPast.DateNotInPastValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateNotInPast {

    String message() default "Scheduled date must be in present or future only";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class DateNotInPastValidator implements ConstraintValidator<DateNotInPast, LocalDate> {

        @Override
        public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
            return !date.isBefore(LocalDate.now());
        }

    }
}
