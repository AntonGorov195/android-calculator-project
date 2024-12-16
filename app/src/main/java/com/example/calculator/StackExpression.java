package com.example.calculator;

import java.util.ArrayList;

// Stack based expression parser and evaluator.
// It pushes token on the stack into it.
// If the operator is of a lower precedence than previous,
// evaluate previous expression, put it on the stack, and then add the operator,
// e.g. 1*2+3. -> token(1) token(2) token(*) token(3) token(+)  -> 6
// 1+2*3 -> token(1) token(2) token(+) -> token(1) token(2) token(3) token(*) token(+) -> 7
// 1+2*3^4 -> token(1) token(2) token(+) -> token(1) token(2) token(3) token(*) token(+) ->
// -> token(1) token(2) token(3) token(4) token(^) token(*) token(+) -> 163
public class StackExpression {
    public ArrayList<StackToken> stack;
    public StackExpression(){
        stack = new ArrayList<StackToken>();
    }
    // Parse and evaluate expression.
    public double EvalExpression(String expr) throws StackExpression.Exception {
        // Start of the next token.
        int current = 0;
        return 1.2f;
    }

    public class Result {
        public double value;
        // The index of the last char (in "1+3" it will be 3)
        public int end;
    }
    public class Exception extends java.lang.Exception {
        public String Reason;
    }
}
