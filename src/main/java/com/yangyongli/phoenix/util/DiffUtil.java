package com.yangyongli.phoenix.util;

import com.yangyongli.phoenix.util.DiffMatchPatch;

import java.nio.channels.Pipe;
import java.util.LinkedList;

/**
 * Created by yangyongli on 9/29/16.
 */
public class DiffUtil {

    public static void main(String[] args) {
        String left = "直接接入付款平台的业务线，付款平台将采用这种方式通知业务线，通知的地址为：1、业务线在付款平台注册时提供的固定url OR 2、业务线在调用接口 - 付款(pay)时传递的notifyurl参数。";
        String right = "直接接入付款平台的业务线，付款平台会采用这种方式通知业务线，通知的地址为：1、业务线在付款平台注册时提供的固定url 或者 2、业务线在调用接口 - 付款(pay)时传递的notifyurl参数。";
        diff(left, right);
    }

    public static void diff(String left, String right){
        DiffMatchPatch differ = new DiffMatchPatch();
        LinkedList<DiffMatchPatch.Diff> diffs = differ.diff_main(left, right, false);
        System.out.println("<div>");
        for (DiffMatchPatch.Diff diff : diffs) {
            //System.out.println(diff);
            if(diff.operation == DiffMatchPatch.Operation.EQUAL){
                System.out.println("<span>" + diff.text + "</span>");
            }else if(diff.operation == DiffMatchPatch.Operation.INSERT){
                System.out.println("<ins style=\"background:#e6ffe6;text-decoration: underline;\">" + diff.text + "</ins>");
            }else if(diff.operation == DiffMatchPatch.Operation.DELETE){
                System.out.println("<del style=\"background:#ffe6e6;text-decoration: line-through;\">" + diff.text + "</del>");
            }
        }
        System.out.println("</div>");
    }
}
