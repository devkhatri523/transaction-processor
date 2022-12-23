package com.demo.induction.tp.service;

import com.demo.induction.tp.pojo.Transaction;
import com.demo.induction.tp.pojo.Violation;
import com.demo.induction.tp.exception.TransactionProcessingException;

import java.io.InputStream;
import java.util.List;


public interface TransactionProcessor {

    void importTransactions(InputStream is) throws TransactionProcessingException;

    List<Transaction> getImportedTransactions();

    List<Violation> validate();

    boolean isBalanced();
}
