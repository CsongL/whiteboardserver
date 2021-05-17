package test;

import client.SerializeUtils;

import java.awt.*;
import java.io.*;

/**
 * @Author: SongLin Chang
 * @Description: TODO
 * @Date: Created in 8:49 2021/5/16
 */
public class test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Color color = Color.black;
        String colorString = color.toString();
        Color color1 = new Color(color.getRGB());
        System.out.println(color1);
        Person person = new Person(1,"CS",23,"S");
        String personInfo = SerializeUtils.serialize(person);
        System.out.println(personInfo);
        Person person1 =(Person) SerializeUtils.serializeToObject(personInfo);
        System.out.println(person1.toString());

    }
}
