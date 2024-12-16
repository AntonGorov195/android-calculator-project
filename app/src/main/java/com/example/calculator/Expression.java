package com.example.calculator;

import androidx.annotation.NonNull;

public class Expression {
    // The current value of the evaluated expression
    double value;
    // The index of the first character to be parsed after
    int remainder_start;

    /*
     * This simple parse will use recursive descend to evaluate the expression.
     * Better methods exist, but this one came my mind first and it is quite simple.
     *
     * expr => add
     * add => mult "+" (add)* | mult "-" (add)*
     * mult =>  unary "*" (mult)* | unary "/" (mult)*
     * unary => "-" unary | "+" unary | number
     * number => [0-9]+ ("." [0-9]*)?
     * */
    public static Expression EvaluateExpression(String expr, int start) {
        // Expression is empty. Return NaN
        if (start >= expr.length()) {
            Expression empty = new Expression();
            empty.remainder_start = -1;
            return empty;
        }
        return add(expr, start);
    }

    private static Expression add(String expr, int start) {
        Expression result = mult(expr, start);
        if (result.remainder_start >= expr.length() || result.remainder_start == -1) {
            return result;
        }
        while (result.remainder_start < expr.length()) {
            char c = expr.charAt(result.remainder_start);
            if (c == '+') {
                result.remainder_start += 1;
                if (result.remainder_start >= expr.length()) {
                    result.remainder_start = -1;
                    return result;
                }
                Expression right = mult(expr, result.remainder_start);
                if (right.remainder_start == -1) {
                    return right;
                }
                result.value += right.value;
                result.remainder_start = right.remainder_start;
            } else if (c == '-') {
                result.remainder_start += 1;
                if (result.remainder_start >= expr.length()) {
                    result.remainder_start = -1;
                    return result;
                }
                Expression right = mult(expr, result.remainder_start);
                if (right.remainder_start == -1) {
                    return right;
                }
                result.value -= right.value;
                result.remainder_start = right.remainder_start;
            } else {
                // Encounter unknown token.
                return result;
            }
        }
        return result;
    }

    private static Expression mult(String expr, int start) {
        Expression result = unary(expr, start);
        if (result.remainder_start >= expr.length() || result.remainder_start == -1) {
            return result;
        }
        while (result.remainder_start < expr.length()) {
            char c = expr.charAt(result.remainder_start);
            if (c == '*') {
                result.remainder_start += 1;
                if (result.remainder_start >= expr.length()) {
                    result.remainder_start = -1;
                    return result;
                }
                Expression right = unary(expr, result.remainder_start);
                if (right.remainder_start == -1) {
                    return right;
                }
                result.value *= right.value;
                result.remainder_start = right.remainder_start;
            } else if (c == '/') {
                result.remainder_start += 1;
                if (result.remainder_start >= expr.length()) {
                    result.remainder_start = -1;
                    return result;
                }
                Expression right = unary(expr, result.remainder_start);
                if (right.remainder_start == -1) {
                    return right;
                }
                result.value /= right.value;
                result.remainder_start = right.remainder_start;
            } else {
                // Encounter unknown token.
                return result;
            }
        }
        return result;
    }

    private static Expression unary(String expr, int start) {
        Expression result;
        char c = expr.charAt(start);
        if (c == '+') {
            start += 1;
            if (start >= expr.length()) {
                result = new Expression();
                result.remainder_start = -1;
                return result;
            }
            return unary(expr, start);
        } else if (c == '-') {
//            result.remainder_start = next(result.remainder_start, expr);
            start += 1;
            if (start >= expr.length()) {
                result = new Expression();
                result.remainder_start = -1;
                return result;
            }
            result = unary(expr, start);
            result.value = -result.value;
            return result;
        }
        return number(expr, start);
    }

    private static Expression number(String expr, int start) {
        Expression result = new Expression();
        result.remainder_start = start;
        char c = expr.charAt(result.remainder_start);
        if (c == '(') {
            result = EvaluateExpression(expr, result.remainder_start + 1);
            if (result.remainder_start >= expr.length() || result.remainder_start == -1) {
                return result;
            }
            c = expr.charAt(result.remainder_start);
            if (c != ')') {
                result.remainder_start = -1;
                return result;
            }
            result.remainder_start++;
            return result;
        }
        if ('0' > c || c > '9') {
            result.remainder_start = -1;
            return result;
        }
        while (c <= '9' && c >= '0') {
            result.remainder_start += 1;
            if (result.remainder_start >= expr.length()) {
                return ParseNumber(expr, start, result);
            }
            c = expr.charAt(result.remainder_start);
        }
        if (c == '.') {
            do {
                result.remainder_start += 1;
                if (result.remainder_start >= expr.length()) {
                    return ParseNumber(expr, start, result);
                }
                c = expr.charAt(result.remainder_start);
            } while (c <= '9' && c >= '0');
        }
        return ParseNumber(expr, start, result);
    }

    @NonNull
    private static Expression ParseNumber(String expr, int start, Expression result) {
        result.value = Double.parseDouble(expr.substring(start, result.remainder_start));
        return result;
    }

    private static int next(int current, String expr) {
        current += 1;
        if (current >= expr.length()) {
            return -1;
        }
        return current;
    }
}
