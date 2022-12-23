package com.demo.induction.tp.controller;

import com.demo.induction.tp.exception.TransactionProcessingException;
import com.demo.induction.tp.pojo.ErrorResponse;
import com.demo.induction.tp.pojo.Violation;
import com.demo.induction.tp.service.TransactionProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class TransactionProcessorController {

    @Autowired
    @Qualifier("csvTransactionProcessor")
    private TransactionProcessor csvTransactionProcessor;

    @Autowired
    @Qualifier("xmlTransactionProcessor")
    private TransactionProcessor xmlTransactionProcessor;

    @PostMapping(value = "/importcsvtransactions", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importCsvTransactions(@RequestParam("file") MultipartFile file){
        InputStream inputFS = getInputStreamFromMultipartFile(file);
        return buildResponse(inputFS, csvTransactionProcessor);
    }

    @PostMapping(value = "/importxmltransactions", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importXmlTransactions(@RequestParam("file") MultipartFile file){
        InputStream inputFS = getInputStreamFromMultipartFile(file);
        return buildResponse(inputFS, xmlTransactionProcessor);
    }

    private ResponseEntity<?> buildResponse(InputStream inputFS, TransactionProcessor transactionProcessor){
        transactionProcessor.importTransactions(inputFS);
        List<Violation> violations = transactionProcessor.validate();
        boolean isBalance = transactionProcessor.isBalanced();
        if(!(violations.isEmpty())){
            return ResponseEntity.badRequest().body(new ErrorResponse(violations, isBalance));
        }else return new ResponseEntity<Void>(HttpStatus.OK);
    }

    private InputStream getInputStreamFromMultipartFile(MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            throw new TransactionProcessingException(e.getMessage(), e);
        }
    }
}
