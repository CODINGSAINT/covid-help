package com.codingsaint.covidhelp.domains;

public class NeighbourMessageWrapper {
    private Object message;
    private Errors errors;

    public NeighbourMessageWrapper(Object message, Errors errors) {
        this.message = message;
        this.errors = errors;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
