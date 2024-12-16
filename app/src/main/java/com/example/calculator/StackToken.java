package com.example.calculator;

public abstract class StackToken {
    int start;
    int end;

    public abstract int getPrecedence();
    public class TokenNumber extends StackToken{
        @Override
        public int getPrecedence() {
            // Literal have the highest precedent.
            return 9999;
        }
    }
    public class TokenAdd extends StackToken{
        @Override
        public int getPrecedence() {
            return 10;
        }
    }
    public class TokenMinus extends StackToken{
        @Override
        public int getPrecedence() {
            return 10;
        }
    }
    public class TokenMult extends StackToken{
        @Override
        public int getPrecedence() {
            return 11;
        }
    }
    public class TokenDiv extends StackToken{
        @Override
        public int getPrecedence() {
            return 11;
        }
    }

}
