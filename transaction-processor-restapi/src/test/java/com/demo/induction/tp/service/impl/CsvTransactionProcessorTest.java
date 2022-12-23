package com.demo.induction.tp.service.impl;

import com.demo.induction.tp.pojo.Transaction;
import com.demo.induction.tp.service.impl.CsvTransactionProcessor;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CsvTransactionProcessorTest {

    @Test
    public void shouldCreateTransactionList_importTransactions() throws IOException {
        //given
        File inputFile = new File("src/test/resources/valid_transactions.csv");
        InputStream inputFS = new FileInputStream(inputFile);
        CsvTransactionProcessor csvTransactionProcessor = new CsvTransactionProcessor();
        //when
        csvTransactionProcessor.importTransactions(inputFS);
        List<Transaction> transactions = csvTransactionProcessor.getImportedTransactions();
        //then
        assertThat(transactions.isEmpty(), is(false));
    }
}
