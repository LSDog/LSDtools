package LSDtools.AI;

import LSDtools.LSDtools;

import java.util.ArrayList;
import java.util.Arrays;

public class MessageClassify {
    public static String Classify(String msg) { //对输入信息的分类,之后再交给其他更细致的分类
        String command = null;
        
        return command;
    }

    public class CutMessage { //将信息剪切开
        public ArrayList cutter(String msg) {
            String[] cutmsg = msg.split("[你我他的是了把]");
            return new ArrayList<String>(Arrays.asList(cutmsg));
        }
    }
}
