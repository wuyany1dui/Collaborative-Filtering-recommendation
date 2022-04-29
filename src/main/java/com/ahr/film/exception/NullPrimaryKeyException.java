package com.ahr.film.exception;

public class NullPrimaryKeyException extends Exception {
    public NullPrimaryKeyException(){
        super();
    }
    public NullPrimaryKeyException(String s){
        super(s);
    }
}
