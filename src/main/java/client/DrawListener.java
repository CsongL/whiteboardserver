package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.alibaba.fastjson.JSON;

/**
 * @Author: SongLin Chang
 * @Description: TODO
 * @Date: Created in 15:38 2021/5/16
 */
public class DrawListener extends MouseAdapter implements ActionListener {
    private int x1, y1, x2, y2,x3,y3,sx,sy,ex,ey;
    private Graphics2D g2;
    private String str = "Line";
    private Color color = Color.BLACK;
    private ArrayList<Shape> shapeList = new ArrayList<Shape>();
    private PrintWriter pw;

    public void setG(Graphics g, PrintWriter pw){
        g2 =(Graphics2D) g;
        this.pw = pw;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void setColor(Color color) {
        this.color = color;
        System.out.println(this.color);
    }
    public ArrayList<Shape> getShapeList(){
        return shapeList;
    }

    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        str = button.getText();
    }
    public void mouseClicked(MouseEvent e){
        x1 = e.getX();
        y1 = e.getY();
    }
    public void mousePressed(MouseEvent e){
        x1 = e.getX();
        y1 = e.getY();
        ex = e.getX();
        ey = e.getY();
        g2.setColor(color);
    }
    public void mouseMoved(MouseEvent e){
        x2 = e.getX();
        y2 = e.getX();
    }
    public void mouseReleased(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        if(str.equals("Line")){
            Shape s = new Shape(x1, y1, x2, y2,color, "Line", null);
            s.draw(g2);
            shapeList.add(s);
            pw.println(JSON.toJSONString(s));
        }
        if(str.equals("Rect")){
            Shape s = new Shape(x1,y1, x2,y2,color,"Rect", null);
            s.draw(g2);
            shapeList.add(s);
            pw.println(JSON.toJSONString(s));
        }
        if(str.equals("Oval")){
            Shape s = new Shape(x1,y1,x2,y2,color,"Oval", null);
            s.draw(g2);
            shapeList.add(s);
            pw.println(JSON.toJSONString(s));
        }
        if(str.equals("Circle")){
            Shape s = new Shape(x1, y1, x2, y2, color, "Circle", null);
            s.draw(g2);
            shapeList.add(s);
            pw.println(JSON.toJSONString(s));
        }
        if(str.equals("Text")){
            String text = "Hello World";
            Shape s = new Shape(x1,y1,text,"Text", color);
            s.draw(g2);
            shapeList.add(s);
            pw.println(JSON.toJSONString(s));
        }
    }
    public void mouseDragged(MouseEvent e){
        x3 = e.getX();
        y3 = e.getY();
        if(str.equals("Brush")){
            Shape s= new Shape(ex, ey, x3, y3,color, "Brush", null);
            s.draw(g2);
            shapeList.add(s);
            pw.println(JSON.toJSONString(s));
        }
        if(str.equals("Easier")){
            Shape s= new Shape(ex, ey, x3, y3,Color.white, "Easier", null);
            s.draw(g2);
            shapeList.add(s);
            pw.println(JSON.toJSONString(s));
        }
        if(str.equals("Pencil")){
            Shape s = new Shape(ex, ey, x3, y3, color, "Pencil",null);
            s.draw(g2);
            shapeList.add(s);
            pw.println(JSON.toJSONString(s));
        }
        ex = x3;
        ey = y3;
    }
}
