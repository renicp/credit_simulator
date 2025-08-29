package com.simulator.util;

import java.util.Scanner;

public class InputUtil {
    private static Scanner sc = new Scanner(System.in);

    public static String nextLine(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }
}