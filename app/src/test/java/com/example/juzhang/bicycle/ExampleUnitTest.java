package com.example.juzhang.bicycle;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        //生成20个随机数
//        List<Integer> list = new ArrayList<>();
//        for(int i=0;i<20;i++){
//            list.add((int) (Math.random()*1000));
//        }
//        //删除能被500整除的数
//        Iterator<Integer> iterator = list.iterator();
//        while(iterator.hasNext()){
//            if(iterator.next()%500==0){//这里看不到整除还是不整除
//                iterator.remove();
//            }
//        }
//        //再次打印输出
//        iterator = list.iterator();
//        while(iterator.hasNext()){
//            System.out.println(iterator.next()+" ");
//        }

        Map<Integer,String> map = new HashMap<>();
        if(map.get(0)==null){
            System.out.print("daole");
        }
    }
}
class Student{
    private String id;
    private String name;
    private float mathScore;
    private float chineseScore;
    private float englishScore;
    private Student tempStudent[];

    public Student(String id, String name, float mathScore, float chineseScore, float englishScore) {
        this.id = id;
        this.name = name;
        this.mathScore = mathScore;
        this.chineseScore = chineseScore;
        this.englishScore = englishScore;
    }
    public void input(){
        System.out.println("请输入8个学生的成绩");
        tempStudent = new Student[8];
        Scanner scan = new Scanner(System.in);
        for(int i=0;i<8;i++){
            tempStudent[i] = new Student(scan.next(),scan.next(),scan.nextFloat(),scan.nextFloat(),scan.nextFloat());
        }
    }
    public void output(Student student){
        System.out.println("学号\t姓名\t总分");
        if(student==null){
            for(int i=0;i<tempStudent.length;i++){
                System.out.println(tempStudent[i].id+"\t"+tempStudent[i].name+"\t"+(tempStudent[i].mathScore+tempStudent[i].chineseScore+tempStudent[i].englishScore));

            }
        }else{
            System.out.println(student.id+"\t"+student.name+"\t"+(student.mathScore+student.chineseScore+student.englishScore+20));
        }
    }
    public void queryStudent(){
        System.out.println("请输入要查询的学生学号:");
        Scanner scan = new Scanner(System.in);
        String id = scan.next();
        int i = 0;
        for(;i<tempStudent.length;i++){
            if(tempStudent[i].id.equals(id)){
                output(tempStudent[i]);
                break;
            }
        }
        if(i>tempStudent.length)
            System.out.println("没有找到学生信息");
    }
    public void sort(){
        for(int i=0;i<tempStudent.length-1;i++){
            for(int o=i+1;i<tempStudent.length;o++){
                if(sum(tempStudent[i])>sum(tempStudent[o])){
                    Student temp;
                    temp =tempStudent[i];
                    tempStudent[i] = tempStudent[o];
                    tempStudent[o] = temp;
                }
            }
        }
    }
    private float sum(Student s){
        return s.englishScore+s.chineseScore+s.mathScore;
    }
}
