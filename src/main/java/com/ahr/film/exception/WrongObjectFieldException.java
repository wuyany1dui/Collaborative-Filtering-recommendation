package com.ahr.film.exception;

public class WrongObjectFieldException extends Exception{
    public WrongObjectFieldException(){
        super();
    }

    public WrongObjectFieldException(String s){
        super(s);
    }
}
