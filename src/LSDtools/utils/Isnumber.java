package LSDtools.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Isnumber { //是不是正整数

    boolean isnum = false;
    Pattern Pnum = Pattern.compile("[0-9]*");

    public boolean isnum(String num){

        Matcher Mnum = Pnum.matcher(num);
        if (Mnum.matches()) {
            isnum = true;
        }

        return isnum;
    }
}