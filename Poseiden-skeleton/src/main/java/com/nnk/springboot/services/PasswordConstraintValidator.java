package com.nnk.springboot.services;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;


/**
 * The password validator. We use org.passay:passay to enforce our custom password policy.
 *
 * @author SimonC.
 * @version 1.0
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword arg0) {
    }

    /**
     * We create the password validator PasswordConstraintValidator – and we'll define the rules for the password.
     * LengthRule(8, 65) – enforces the password length to be between 8 and 65 characters (to avoid Bcrpyt 60 length error).
     * CharacterRule(EnglishCharacterData.UpperCase, 1) – enforces the password to have at least 1 upper-case character.
     * CharacterRule(EnglishCharacterData.LowerCase, 1) – enforces the password to have at least 1 lower-case character.
     * CharacterRule(EnglishCharacterData.Special, 1) – enforces the password to have at least 1 symbol (special character).
     * CharacterRule(EnglishCharacterData.Digit, 1) – enforces the password to have at least 1 digit character.
     * WhitespaceRule – enforces the password does not contain a whitespace.
     * We're creating the new constraint violation here and disabling the default one as well – in case the password is not valid.
     *
     * @param password The password to validate.
     * @param context  The data and operations when applying the constraint validator.
     * @return The result of the validation process.
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 65),                                      // -> max set to 65 to avoid Bcrpyt 60 length error
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new WhitespaceRule()
        ));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);
        String messageTemplate = String.join(",", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
