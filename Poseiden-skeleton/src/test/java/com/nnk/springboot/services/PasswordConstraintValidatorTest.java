package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordConstraintValidatorTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testIsValid() {
        User user = new User();
        user.setFullName("john");
        user.setUsername("john");
        user.setPassword("Qwerty321_");
        user.setRole("USER");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertEquals(constraintViolations.size(), 0);
    }
    @Test
    public void testIsInvalid() {
        User user = new User();
        user.setFullName("john");
        user.setUsername("john");
        user.setPassword("john");
        user.setRole("USER");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        assertEquals(constraintViolations.size(), 1);
    }
}
