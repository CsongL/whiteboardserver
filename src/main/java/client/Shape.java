package client;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * @Author: SongLin Chang
 * @Description: TODO
 * @Date: Created in 17:13 2021/5/16
 */
public class Shape implements Serializable {
    private int x1, y1, x2, y2;
    private int arcWidth, arcHeight;
    private String text;
    private String type;
    private Color color;

    public Shape(int x1, int y1, int x2, int y2, Color color, String type, String text){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.type = type;
        this.text = text;
    }
    public Shape(int x1, int y1, String text, String type, Color color){
        this.x1  = x1;
        this.y1 = y1;
        this.text = text;
        this.type = type;
        this.color = color;
    }

    @Override
    public String toString(){
        return this.type+" "+this.x1+" "+this.y1+ " " +this.x2 + " " +this.y2 +" "+ " "+this.color + " "+ this.text;
    }
    public void draw(Graphics2D g){
        if(type.equals("Line") || type.equals("Pencil")){
            g.setColor(color);
            g.drawLine(x1,y1, x2, y2);
        }
        if(type.equals("Brush") || type.equals("Easier")){
            g.setStroke(new BasicStroke(8));
            g.setColor(color);
            g.drawLine(x1,y1,x2,y2);
            g.setStroke(new BasicStroke(1));
        }
        if(type.equals("Rect")){
            g.setColor(color);
            int beginPoint = Math.min(x1, x2);
            int endPoint = Math.min(y1, y2);
            int width = Math.abs(x1-x2);
            int height = Math.abs(y1-y2);
            g.drawRect(beginPoint, endPoint, width, height);
        }
        if(type.equals("Circle")){
            g.setColor(color);
            int beginPoint = Math.min(x1, x2);
            int endPonint = Math.min(y1, y2);
            int width = Math.abs(x1-x2);
            int height = Math.abs(y1-y2);
            g.drawRoundRect(beginPoint, endPonint, width, width, width, width);
        }
        if(type.equals("Oval")){
            g.setColor(color);
            int beginPoint = Math.min(x1, x2);
            int endPonint = Math.min(y1, y2);
            int width = Math.abs(x1-x2);
            int height = Math.abs(y1-y2);
            g.drawOval(beginPoint,endPonint, width, height);
        }
        if(type.equals("Text")){
            g.setColor(color);
            System.out.println(this);
            g.drawString(text, x1, y1);
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
