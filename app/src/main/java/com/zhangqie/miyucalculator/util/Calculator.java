package com.zhangqie.miyucalculator.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Created by zhangqie on 2017/7/14.
 */

public class Calculator {

    private final Stack<Double> numStack = new Stack<Double>();
    private final Stack<Character> opStack = new Stack<Character>();

    private char currentOperator;
    private char opStackTop;

    private int i;
    private String expression;

    Double dllll=1.1;
    @SuppressWarnings("rawtypes")
    public String exec(String expression) {
        try {
            clean();
            if (expression == null || expression.isEmpty()) {
                throw new IllegalArgumentException("Blank Expression!");
            }
            this.expression = expression;
            opStack.push(TERMINATE_TOKENS.START_END_MARK);
            List tokens = TOKENIZER.exec(expression
                    + TERMINATE_TOKENS.START_END_MARK);
            for (; i < tokens.size(); i++) {
                final Object token = tokens.get(i);
                if (token instanceof Double) {
                    processOperand((double) token);
                } else {
                    dllll=processOperator((char) token);
                }
            }
        } catch (Throwable e) {
            System.err.println(String.format(
                    "Incorret Expression: %s\nError: %s", expression,
                    e.getMessage()));
        }
        return String.valueOf(dllll);
    }

    private void processOperand(final double operand) {
        numStack.push(operand);
    }

    private Double processOperator(final char currentOperator) {
        this.currentOperator = currentOperator;
        this.opStackTop = opStack.peek();
        char calMode = CALCULATE_MODE.getRule(currentOperator, opStackTop);
        switch (calMode) {
            case '>':
                processStackHigerPriorityOperator();
                break;
            case '<':
                processStackLowerPriorityOperator();
                break;
            case '=':
                return processStackEqualPriorityOperator();
            default:
                break;
        }
        return 0.0;
    }

    private void processStackLowerPriorityOperator() {
        opStack.push(currentOperator);
    }

    private void processStackHigerPriorityOperator() {
        numStack.push(CALCULATE.exec(opStack.pop(), numStack.pop(),
                numStack.pop()));
        --i; // pointer back to the previous operator.
    }

    private Double processStackEqualPriorityOperator() {
        if (TERMINATE_TOKENS.START_END_MARK == currentOperator) {
            System.out.println(expression + " ===== " + numStack.peek());
            return numStack.peek();
        } else if (')' == currentOperator) {
            opStack.pop();
            return 0.0;
        }
        return 0.0;
    }

    public void clean() {
        numStack.clear();
        opStack.clear();
        i = 0;
    }

    public static void main(String[] args) {
       /* Calculator cal = new Calculator();
        cal.exec("4+(3*(3-1)+2)/2"); // = 8
        cal.exec("4 + (-3 * ( 3 - 1 ) + 2)"); // = 0
        cal.exec("4 +-/ (-3 * ( 3 - 1 ) + 2)"); // incorrect expression!
        cal.exec("4.5+(3.2+3)/2"); // = 7.6
        cal.exec("4.5+(3.2:3)/2"); // incorrect expression!
        cal.exec("-4.5+(3.2-3)/2"); // = -4.4*/
    }
}

enum CALCULATE {
    INSTANCE;

    public static double exec(final char operator, final double right,
                              final double left) {
        switch (operator) {
            case '+':
                return left + right;
            case '-':
                return left - right;
            case '*':
                return left * right;
            case '/':
                return left / right;
            default:
                throw new IllegalArgumentException("Unsupported operator: "
                        + operator);
        }
    }
}

enum TERMINATE_TOKENS {
    INSTANCE;

    public static final char START_END_MARK = '#';
    private static final Map<Character, Integer> TOKENs = new HashMap<Character, Integer>();

    static {
        // token, token id
        TOKENs.put('+', 0);
        TOKENs.put('-', 1);
        TOKENs.put('*', 2);
        TOKENs.put('/', 3);
        TOKENs.put('(', 4);
        TOKENs.put(')', 5);
        TOKENs.put(START_END_MARK, 6);
    }

    private static Set<Character> NEGATIVE_NUM_SENSITIVE = new HashSet<Character>();

    public static synchronized Set<Character> getNegativeNumSensitiveToken() {
        if (NEGATIVE_NUM_SENSITIVE.size() == 0) {
            NEGATIVE_NUM_SENSITIVE.addAll(TOKENs.keySet());
            NEGATIVE_NUM_SENSITIVE.remove(')');
        }
        return NEGATIVE_NUM_SENSITIVE;
    }

    public static boolean isTerminateToken(final char token) {
        Set<Character> keys = TOKENs.keySet();
        return keys.contains(token);
    }

    public static int getTokenId(final char token) {
        return TOKENs.get(token) == null ? -1 : TOKENs.get(token);
    }

    public static int getTokenSize() {
        return TOKENs.size();
    }

}

enum CALCULATE_MODE {
    INSTANCE;

    private static char[][] RULES = {
            // + - * / ( ) #
            { '>', '>', '<', '<', '<', '>', '>' }, // +
            { '>', '>', '<', '<', '<', '>', '>' }, // -
            { '>', '>', '>', '>', '<', '>', '>' }, // *
            { '>', '>', '>', '>', '<', '>', '>' }, // /
            { '<', '<', '<', '<', '<', '=', 'o' }, // (
            { '>', '>', '>', '>', 'o', '>', '>' }, // )
            { '<', '<', '<', '<', '<', 'o', '=' }, // #
    };

    static {
        if (RULES.length != TERMINATE_TOKENS.getTokenSize() || RULES.length < 1
                || RULES[0].length != TERMINATE_TOKENS.getTokenSize()) {
            throw new IllegalArgumentException("Rules matrix is incorrect!");
        }
    }

    public static char getRule(final char currentOperator, final char opStackTop) {
        try {
            return RULES[TERMINATE_TOKENS.getTokenId(opStackTop)][TERMINATE_TOKENS
                    .getTokenId(currentOperator)];
        } catch (Throwable e) {
            throw new RuntimeException("No rules were defined for some token!");
        }
    }
}

enum TOKENIZER {
    INSTANCE;

    private static final StringBuilder BUFFER = new StringBuilder();

    private static String clearExpression(String expression) {
        return expression.replaceAll(" ", "");
    }

    private static Character PREVIOUS_CHAR;

    private static void clean() {
        BUFFER.delete(0, BUFFER.length());
        PREVIOUS_CHAR = null;
    }

    private static boolean processNegativeNumbers(final String exp,
                                                  final int index) {
        char c = exp.charAt(index);
        if (('+' == c || '-' == c)
                && (PREVIOUS_CHAR == null || TERMINATE_TOKENS
                .getNegativeNumSensitiveToken().contains(PREVIOUS_CHAR))
                && !TERMINATE_TOKENS.isTerminateToken(exp.charAt(index + 1))) {
            BUFFER.append(c);
            return true;
        }
        return false;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<?> exec(final String expression) {
        clean();
        String exp = clearExpression(expression);
        List result = new LinkedList();
        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);
            if (TERMINATE_TOKENS.isTerminateToken(c)) {

                if (processNegativeNumbers(exp, i))
                    continue;
                if (BUFFER.length() > 0) {
                    result.add(Double.valueOf(BUFFER.toString()));
                    BUFFER.delete(0, BUFFER.length());
                }
                result.add(c);
            } else {
                BUFFER.append(c);
            }
            PREVIOUS_CHAR = c;
        }
        return Collections.unmodifiableList(result);
    }

}
