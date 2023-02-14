package it.univr.satella.controllers;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SensorValidator {

    public static String getErrorMessage(int error) {
        switch (error) {
            case 1:
                return "Il modello non può contenere spazi vuoti";
            case 2:
                return "Il campo \"Ampere minimo\" deve essere un numero";
            case 3:
                return "Il campo \"Ampere massimo\" deve essere un numero";
            case 4:
                return "Il \"Voltaggio minimo\" deve essere un numero";
            case 5:
                return "Il \"Voltaggio minimo\" deve essere un numero";
            case 6:
                return "Il campo \"Ampere minimo\" deve essere inferiore al campo \"Ampere massimo\" ";
            case 7:
                return "Il \"Voltaggio minimo\" deve essere inferiore al \"Voltaggio massimo\" ";
            case 8:
                return "I nuovi valori non rispettano i limiti dello slots";
        }
        return null;
    }
}