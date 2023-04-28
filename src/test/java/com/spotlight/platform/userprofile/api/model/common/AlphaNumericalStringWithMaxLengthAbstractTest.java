package com.spotlight.platform.userprofile.api.model.common;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import io.dropwizard.jersey.validation.Validators;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AlphaNumericalStringWithMaxLengthAbstractTest<T> {
    private final Validator validator = Validators.newValidator();

    protected abstract T getInstance(String value);

    @Test
    void validation_correctValueIsValid() {
        var violations = validator.validate(getInstance("some-value"));

        assertThat(violations).isEmpty();
    }

    @Test
    void validation_emptyIsNotValid() {
        var violations = validator.validate(getInstance(""));

        assertThat(violations).extracting(ConstraintViolation::getMessage).containsExactly("length must be between 1 and 1024");
    }

    @Test
    void validation_longStringIsNotValid() {
        var violations = validator.validate(getInstance("a".repeat(2000)));

        assertThat(violations).extracting(ConstraintViolation::getMessage).containsExactly("length must be between 1 and 1024");
    }

    @Test
    void validation_nonAsciiIsNotValid() {
        var violations = validator.validate(getInstance("ö"));

        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("must match \"[A-Za-z0-9_-]*\"");
    }

    @Test
    void validation_notAlphaNumericIsNotValid() {
        var violations = validator.validate(getInstance("$"));

        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("must match \"[A-Za-z0-9_-]*\"");
    }
}