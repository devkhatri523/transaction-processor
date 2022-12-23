package com.demo.induction.tp.service.impl;

import com.demo.induction.tp.pojo.Transaction;
import com.demo.induction.tp.pojo.Violation;
import com.demo.induction.tp.exception.TransactionProcessingException;
import com.demo.induction.tp.service.TransactionProcessor;
import com.demo.induction.tp.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.demo.induction.tp.constant.Constants.AMOUNT;
import static com.demo.induction.tp.constant.Constants.NARRATION;
import static com.demo.induction.tp.constant.Constants.TYPE;

@Service
@Qualifier("xmTransactionProcessor")
public class XmlTransactionProcessor implements TransactionProcessor {

    private List<Transaction> transactions;

    @Override
    public void importTransactions(InputStream is) throws TransactionProcessingException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(is);
            NodeList nodeList = document.getElementsByTagName("Transaction");
            transactions = new ArrayList<>();
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                Element element = (Element) node;
                transactions.add(buildTransactionObject(element));
            }
        }catch (ParserConfigurationException | SAXException | IOException e){
            throw new TransactionProcessingException(e.getMessage(), e);
        }
    }

    private Transaction buildTransactionObject(Element element){
        BigDecimal amount;
        try {
            amount = new BigDecimal(element.getAttribute(AMOUNT));
        }catch (NumberFormatException e){
            amount = new BigDecimal(-1);
        }
        return new Transaction(element.getAttribute(TYPE), amount, element.getAttribute(NARRATION));
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
