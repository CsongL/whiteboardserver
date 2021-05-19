package test;

import java.io.*;
import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
/**
 * @Author: SongLin Chang
 * @Description: TODO
 * @Date: Created in 8:49 2021/5/16
 */
public class test {
    private static int totalNumber = 0;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println(totalNumber++);
        Person person12 = new Person(1,"CS",23,"S");
        String personJson = JSON.toJSONString(person12);
        System.out.println("text"+personJson);
        Person person2 = JSON.parseObject(personJson, Person.class);
        System.out.println(person2.toString());
        ArrayList<Person> arrayList = new ArrayList<Person>();
        arrayList.add(person12);
        String arrayString = JSON.toJSONString(arrayList);
        System.out.println(arrayString);

    }
}
