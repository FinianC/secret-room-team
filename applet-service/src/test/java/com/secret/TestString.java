package com.secret;

import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class TestString {

    @Test
    public void test(){
//        String format = MessageFormat.format("{0}, {0}", 11);
//        System.out.println(format);
//        String [] array = new String[]{"ab", "a"};
//        System.out.println(longestCommonPrefix(array));;

        System.out.println(isValid("]"));;
    }

    public String longestCommonPrefix(String[] strs) {
        int minlen = Integer.MAX_VALUE;
        for (String s:strs) {
            minlen=Math.min(s.length(),minlen);
        }
        StringBuilder tmp = new StringBuilder();
        for(int i = 0; i < minlen ; i++){
            for(int t=0;t<strs.length -1 ;t++){
                if (strs[t].charAt(i)!=strs[t+1].charAt(i)){
                    return tmp.toString();
                }
            }
            tmp.append(strs[0].charAt(i));
        }
        return tmp.toString();
    }

    public boolean isValid(String s) {
        if(s.length() ==1 ){
            return false;
        }
        Map<Character,Character> map = new HashMap();
        map.put(')','(');
        map.put(']','[');
        map.put('}','{');
        Stack<Character> stack = new Stack<>();
        for(int i =0 ;i<s.length() ;i++) {
            Character character = map.get(s.charAt(i));
            if(! (character == null ? stack.push(s.charAt(i)) !=null : (!stack.empty() &&  stack.pop().equals(character)))){
                return false;
            }
        }
        return stack.empty();
    }

    public boolean isValidV2(String s) {
        if(s.length() ==1 ){
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for(int i =0 ;i<s.length() ;i++) {
            char str = s.charAt(i);
            if (str == '(' || str == '[' || str == '{') {
                stack.push(str);
            } else {
                if(stack.size() == 0) return false;
                if (str == ')' && stack.pop() != '(') {
                    return false;
                } else if (str == ']' && stack.pop() != '[') {
                    return false;
                } else if (str == '}' && stack.pop() != '{') {
                    return false;
                }
            }
        }
        return stack.empty();
    }
}
