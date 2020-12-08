package com.contestmodule.contest.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ContestDateRangeValidator.class) //This defines which class is going to validate our field
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContestDateRange {
    String message() default "Start date should be after todays date and end date after start date."; //This is the errormessage
    Class<?>[] groups() default {}; //Boilerplate code to conform to Spring standards
    Class<? extends Payload>[] payload() default {}; //Boilerplate code to conform to Spring standards

}
