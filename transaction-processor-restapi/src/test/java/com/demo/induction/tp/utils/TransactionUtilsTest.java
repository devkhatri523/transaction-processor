package com.demo.induction.tp.utils;

import com.demo.induction.tp.pojo.Transaction;
import com.demo.induction.tp.pojo.Violation;
import com.demo.induction.tp.utils.TransactionUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.demo.induction.tp.constant.Constants.*;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.core.Is.is;

public class TransactionUtilsTest {

    private List<Transaction> transactions;

    @Before
    public void setUp() {
        transactions = new ArrayList<Transaction>(){{
            add(new Transaction(DEBIT, new BigDecimal(200),"Electricity bill"));
            add(new Transaction(DEBIT, new BigDecimal(1000), "Social security payment"));
            add(new Transaction(DEBIT, new BigDecimal(200), "Payment sent to x"));
            add(new Transaction(CREDIT, new BigDecimal(1950), "Salary"));
            add(new Transaction(DEBIT, new BigDecimal(550), "Car rental"));
        }};
    }

    @Test
    public void shouldReturnEmptyViolationList_validate(){
        //when
        List<Violation> violations = TransactionUtils.validate(transactions);
        //then
        assertThat(violations.isEmpty(), is(true));
    }

    @Test
    public void shouldReturnNonEmptyViolationList_validate(){
        //given
        List<Transaction> invalidTransactions = new ArrayList<Transaction>(){{
            add(new Transaction("A", new BigDecimal(200), "Electricity bill"));
            add(new Transaction(DEBIT, BigDecimal.ZERO,"Social security payment"));
        }};
        //when
        List<Violation> violations = TransactionUtils.validate(invalidTransactions);
        //then
        assertThat(violations.isEmpty(), is(false));
    }

    @Test
    public void shouldReturnNonEmptyViolationListWithViolationObjectWithPropertyAmount_validate(){
        //given
        List<Transaction> invalidTransactions = new ArrayList<Transaction>(){{
            add(new Transaction(DEBIT, new BigDecimal(200), "Payment sent to x"));
            add(new Transaction(CREDIT, new BigDecimal(1950), "Salary"));
            add(new Transaction(DEBIT, BigDecimal.ZERO,"Social security payment"));
        }};
        Violation expectedViolation = new Violation(3, AMOUNT, AMOUNT_ERROR_MESSAGE);
        //when
        List<Violation> violations = TransactionUtils.validate(invalidTransactions);
        //then
        assertThat(violations, hasItem(is(samePropertyValuesAs(expectedViolation))));
    }

    @Test
    public void shouldReturnNonEmptyViolationListWithViolationObjectWithPropertyType_validate(){
        //given
        List<Transaction> invalidTransactions = new ArrayList<Transaction>(){{
            add(new Transaction("A", new BigDecimal(200), "Electricity bill"));
        }};
        Violation expectedViolation = new Violation(1, TYPE, TYPE_ERROR_MESSAGE);
        //when
        List<Violation> violations = TransactionUtils.validate(invalidTransactions);
        //then
        assertThat(violations, hasItem(is(samePropertyValuesAs(expectedViolation))));
    }

    @Test
    public void shouldReturnTrue_isBalanced(){
        //when
        boolean isBalanced = TransactionUtils.isBalanced(transactions);
        //then
        assertThat(isBalanced, is(true));
    }

    @Test
    public void shouldReturnFalse_isBalanced(){
        //given
        List<Transaction> unBalancedTransactions = new ArrayList<>(transactions);
        unBalancedTransactions.add(new Transaction(CREDIT, new BigDecimal(500), "Bank Interest"));
        //when
        boolean isBalanced = TransactionUtils.isBalanced(unBalancedTransactions);
        //then
        assertThat(isBalanced, is(false));
    }
}
