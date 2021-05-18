package test;

/**
 * @Author: SongLin Chang
 * @Description: TODO
 * @Date: Created in 21:26 2021/5/17
 */
public class Person {
    private int id;
    private String name;
    private int age;
    private String sex;

    public Person(int id, String name, int age,String sex) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    @Override
    public String toString() {
        return this.id+"  "+this.name+"  "+this.age+"  "+this.sex;
    }
}
