package com.contestmodule.contest.validator;

import com.contestmodule.contest.entity.Contest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ContestDateRangeValidator implements ConstraintValidator<ContestDateRange, Contest> {

    @Override
    public void initialize(ContestDateRange constraintAnnotation){

    }

    @Override //In this method we define our validation rules
    public boolean isValid(Contest contest, ConstraintValidatorContext context){

        return (contest.getStartDate().equals(LocalDate.now()) || contest.getStartDate().isAfter(LocalDate.now()))
                && contest.getEndDate().isAfter(contest.getStartDate());
    }

}
