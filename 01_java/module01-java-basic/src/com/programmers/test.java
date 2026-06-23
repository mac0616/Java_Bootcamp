package com.programmers;

public class test {
    public int solution(int a, int b) {
        String strAB = String.valueOf(a) + String.valueOf(b);
        int val1 = Integer.parseInt(strAB);

        int val2 = 2 * a * b;

        return Math.max(val1, val2);
    }
}
