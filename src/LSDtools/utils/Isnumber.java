package LSDtools.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Isnumber {

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