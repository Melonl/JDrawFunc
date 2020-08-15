package net.melonl;

import java.util.HashSet;

public class Function {

    private static HashSet<Character> operatorSet;
    private static HashSet<String> funcNames;

    static {
        operatorSet = new HashSet<>(10);
        operatorSet.add('+');
        operatorSet.add('-');
        operatorSet.add('*');
        operatorSet.add('/');
        operatorSet.add('^');
        operatorSet.add('(');
        operatorSet.add(')');

        funcNames = new HashSet<>(10);
        funcNames.add("sin");
        funcNames.add("cos");
        funcNames.add("sqrt");
        funcNames.add("ln");
        funcNames.add("lg");
    }

    public static Array<String> splitExp(String exp) {
        Array<String> splitExp = new Array<>();

        for (int i = 0; i < exp.length(); ++i) {
            char ch = exp.charAt(i);
            if (ch == 'x') {
                splitExp.addLast("x");
                continue;
            }
            StringBuilder num = new StringBuilder("");
            while (Character.isDigit(ch) || ch == '.') {
                num.append(ch);
                if (i + 1 < exp.length()) ch = exp.charAt(++i);
                else break;
            }
            if (num.length() != 0) splitExp.addLast(num.toString());
            StringBuilder fx = new StringBuilder();
            while (Character.isLetter(ch)) {
                fx.append(ch);
                if (i + 1 < exp.length()) ch = exp.charAt(++i);
                else break;
            }
            if (fx.length() != 0) {
                if (!funcNames.contains(fx.toString())) return null;
                else splitExp.addLast(fx.toString());
            }

            if (operatorSet.contains(ch)) splitExp.addLast(String.valueOf(ch));
        }
        return splitExp;
    }

    public boolean parseExpression(String exp) {
        Array<String> splitExp = splitExp(exp);
        if (splitExp == null) return false;


        return true;
    }


}
