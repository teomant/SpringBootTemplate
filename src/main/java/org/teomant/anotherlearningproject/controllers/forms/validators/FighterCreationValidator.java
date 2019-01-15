package org.teomant.anotherlearningproject.controllers.forms.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.teomant.anotherlearningproject.controllers.forms.FighterCreationForm;
import org.teomant.anotherlearningproject.controllers.forms.RegistrationForm;
import org.teomant.anotherlearningproject.services.FighterService;

@Component
public class FighterCreationValidator implements Validator {

    @Autowired
    FighterService fighterService;

    @Override
    public boolean supports(Class<?> aClass) {
        return FighterCreationForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace( errors ,
                "name" ,
                "" ,
                "Can`t be empty" );
        ValidationUtils.rejectIfEmptyOrWhitespace( errors ,
                "strength" ,
                "" ,
                "Can`t be empty" );
        ValidationUtils.rejectIfEmptyOrWhitespace( errors ,
                "agility" ,
                "" ,
                "Can`t be empty" );
        ValidationUtils.rejectIfEmptyOrWhitespace( errors ,
                "mind" ,
                "" ,
                "Can`t be empty" );

        FighterCreationForm fighterCreationForm = (FighterCreationForm) o;

        if (fighterService.findByName(fighterCreationForm.getName())!=null){
            errors.rejectValue("name", "", "This fighter already exist");
        }

        try {
             if (Integer.parseInt(fighterCreationForm.getStrength()) < 1) {
                 errors.rejectValue("strength", "", "Must be 1 or more");
             }
        } catch(NumberFormatException er) {
            errors.rejectValue("strength", "", "Must be integer");
        }

        try {
            if (Integer.parseInt(fighterCreationForm.getAgility()) < 1) {
                errors.rejectValue("agility", "", "Must be 1 or more");
            }
        } catch(NumberFormatException er) {
            errors.rejectValue("agility", "", "Must be integer");
        }

        try {
            if (Integer.parseInt(fighterCreationForm.getMind()) < 1) {
                errors.rejectValue("mind", "", "Must be 1 or more");
            }
        } catch(NumberFormatException er) {
            errors.rejectValue("mind", "", "Must be integer");
        }
        try {
            if (Integer.parseInt(fighterCreationForm.getStrength())
                    + Integer.parseInt(fighterCreationForm.getAgility())
                    + Integer.parseInt(fighterCreationForm.getMind()) != 20) {
                errors.rejectValue("strength", "", "Summ must be 20");
                errors.rejectValue("agility", "", "Summ must be 20");
                errors.rejectValue("mind", "", "Summ must be 20");
            }
        } catch (NumberFormatException e) {
        }

    }
}
