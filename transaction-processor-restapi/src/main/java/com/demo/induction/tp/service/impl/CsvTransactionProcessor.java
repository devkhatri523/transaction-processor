package com.demo.induction.tp.service.impl;

import com.demo.induction.tp.pojo.Transaction;
import com.demo.induction.tp.pojo.Violation;
import com.demo.induction.tp.exception.TransactionProcessingException;
import com.demo.induction.tp.service.TransactionProcessor;
import com.demo.induction.tp.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("csvTransactionProcessor")
public class CsvTransactionProcessor implements TransactionProcessor {

    private List<Transaction> transactions;

    @Override
    public void importTransactions(InputStream is) throws TransactionProcessingException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            transactions = br.lines().map(this::buildTransactionObject).collect(Collectors.toList());
        } catch (IOException e) {
            throw new TransactionProcessingException(e.getMessage(), e);
        }
    }

    private Transaction buildTransactionObject(String line){
        List<String> record = Arrays.asList(line.split(","));
        BigDecimal amount;
        try {
            amount = new BigDecimal(record.get(1));
        }catch (NumberFormatException e){
            amount = new BigDecimal(-1);
        }
        return new Transaction(record.get(0), amount, record.get(2));
    }

    @Override
    public List<Transaction> getImportedTransactions() {
       return transactions;
    }

    @Override
    public List<Violation> validate() {
        return TransactionUtils.validate(transactions);
    }

    @Override
    public boolean isBalanced() {
        if(validate().isEmpty()){
            return TransactionUtils.isBalanced(transactions);
        }else return false;
    }
}
