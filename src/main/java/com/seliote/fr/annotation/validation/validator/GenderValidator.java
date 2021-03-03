package com.seliote.fr.annotation.validation.validator;

import com.seliote.fr.annotation.validation.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 性别 JSR 380 校验实现
 *
 * @author seliote
 */
public class GenderValidator implements ConstraintValidator<Gender, Integer> {

    @Override
    public void initialize(Gender constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        int[] genderRange = {0, 1, 2};
        for (var i : genderRange) {
            if (integer == i) {
                return true;
            }
        }
        return false;
    }
}
