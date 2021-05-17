package test;

import client.SerializeUtils;

import java.awt.*;
import java.io.*;
import com.alibaba.fastjson.JSON;
/**
 * @Author: SongLin Chang
 * @Description: TODO
 * @Date: Created in 8:49 2021/5/16
 */
public class test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Person person12 = new Person(1,"CS",23,"S");
        String personJson = JSON.toJSONString(person12);
        System.out.println("text"+personJson);
        Person person2 = JSON.parseObject(personJson, Person.class);
        System.out.println(person2.toString());
    }
}
