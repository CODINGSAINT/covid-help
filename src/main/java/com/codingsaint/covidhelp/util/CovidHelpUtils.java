package com.codingsaint.covidhelp.util;

import com.codingsaint.covidhelp.constants.NeighbourHelpConstants;
import com.codingsaint.covidhelp.domains.Error;
import com.codingsaint.covidhelp.domains.Errors;
import com.codingsaint.covidhelp.domains.NeighbourMessageWrapper;

import java.util.ArrayList;
import java.util.List;

public class CovidHelpUtils {
    public static NeighbourMessageWrapper getError(String code, String message){
        Error error = new Error(code, message);
        List<Error> errorList = new ArrayList<>();
        errorList.add(error);
        Errors errors = new Errors();
        errors.setErrors(errorList);
        NeighbourMessageWrapper neighbourMessageWrapper = new NeighbourMessageWrapper(null, errors);
        return neighbourMessageWrapper;

    }
}
