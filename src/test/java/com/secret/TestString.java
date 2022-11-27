package com.secret;

import org.junit.jupiter.api.Test;

import java.text.MessageFormat;

public class TestString {

    @Test
    public void test(){
        String format = MessageFormat.format("{0}, {0}", 11);
        System.out.println(format);
    }
}
