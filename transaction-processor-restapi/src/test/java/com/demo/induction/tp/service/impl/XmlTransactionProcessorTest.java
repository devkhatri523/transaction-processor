package com.demo.induction.tp.service.impl;

import com.demo.induction.tp.pojo.Transaction;
import com.demo.induction.tp.service.impl.XmlTransactionProcessor;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class XmlTransactionProcessorTest {

    @Test
    public void shouldCreateTransactionList_importTransactions() throws IOException {
        //given
        File inputFile = new File("src/test/resources/valid_transactions.xml");
        InputStream inputFS = new FileInputStream(inputFile);
        XmlTransactionProcessor xmlTransactionProcessor = new XmlTransactionProcessor();
        //when
        xmlTransactionProcessor.importTransactions(inputFS);
        List<Transaction> transactions = xmlTransactionProcessor.getImportedTransactions();
        //then
        assertThat(transactions.isEmpty(), is(false));
    }
}
