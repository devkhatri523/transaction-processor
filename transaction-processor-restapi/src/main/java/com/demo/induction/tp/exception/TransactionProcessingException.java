package com.demo.induction.tp.exception;

public class TransactionProcessingException extends RuntimeException {

    public TransactionProcessingException(){
        super("Something went wrong while processing transactions.");
    }

    public TransactionProcessingException(String message){
        super(message);
    }

    public TransactionProcessingException(String message, Throwable throwable){
        super(message, throwable);
    }

}
