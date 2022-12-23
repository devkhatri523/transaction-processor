package com.demo.induction.tp.utils;

import com.demo.induction.tp.pojo.Transaction;
import com.demo.induction.tp.pojo.Violation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.demo.induction.tp.constant.Constants.*;

public class TransactionUtils {

    public static List<Violation> validate(List<Transaction> transactions) {
        List<Violation> violations = new ArrayList<>();
        IntStream.range(0, transactions.size()).forEach(index -> {
            Transaction transaction = transactions.get(index);
            if (!(CREDIT.equals(transaction.getType()) || DEBIT.equals(transaction.getType()))) {
                Violation violation = new Violation(index + 1 , TYPE, TYPE_ERROR_MESSAGE);
                violations.add(violation);
            }
            if (transaction.getAmount().signum() !=1 ) {
                Violation violation = new Violation(index + 1, AMOUNT, AMOUNT_ERROR_MESSAGE);
                violations.add(violation);
            }
        });
        return violations;
    }

    public static boolean isBalanced(List<Transaction> transactions) {
        BigDecimal debitTransactionsSum = calculateSum(transactions, DEBIT);
        BigDecimal creditTransactionsSum = calculateSum(transactions, CREDIT);
        return debitTransactionsSum.equals(creditTransactionsSum);
    }

    private static BigDecimal calculateSum(List<Transaction> transactions, String type) {
        return transactions.stream()
                .filter(transaction -> type.equals(transaction.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
