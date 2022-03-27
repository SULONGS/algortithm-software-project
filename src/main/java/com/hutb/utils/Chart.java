package com.hutb.utils;

import java.awt.Color;
import java.awt.Graphics;  
import java.awt.Graphics2D;
import java.util.ArrayList;
  
import javax.swing.JFrame;  
  
public class Chart extends JFrame{  

    //绘制柱形统计图  
	ArrayList<Integer> ran=new  ArrayList<Integer>();
    public Chart(ArrayList<Integer> scores)
    {  
        super();  
        getContentPane().setForeground(Color.CYAN);
        setForeground(Color.CYAN);
        setBackground(Color.CYAN);
        for(int i=0;i<scores.size();i++)
        {
        	ran.add(scores.get(i));
        	System.out.println(scores.get(i));
        }
          
        setTitle("绘制柱形图");  
        setBounds(200, 90,600,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }  
    @Override  
    public void paint(Graphics g)
    {  
        int Width = getWidth();  
        int Height = getHeight();  
        int leftMargin = 20;//柱形图左边界  
        int topMargin = 80;//柱形图上边界  
        Graphics2D g2 = (Graphics2D) g;  
        int ruler = Height-topMargin;  
        int rulerStep = ruler/40;//将当前的高度评分为20个单位  
        g2.setColor(Color.WHITE);//绘制白色背景  
        g2.fillRect(0, 0, Width, Height);//绘制矩形图  
        g2.setColor(Color.LIGHT_GRAY);  
        for(int i=0;i<=20;i++){//绘制灰色横线和百分比  
            g2.drawString(100-(5*i)+"", 15, topMargin+rulerStep*i*2);//写下百分比  
            g2.drawLine(15, topMargin+rulerStep*i*2+7, Width, topMargin+rulerStep*i*2+7);//绘制灰色横线  
        }  
        g2.setColor(Color.PINK);
        for(int i=0;i<ran.size();i++){//绘制柱形图  
        	

            int step = (i+1)*40;//设置每隔柱形图的水平间隔为40  
            //绘制矩形  
            g2.fillRoundRect(leftMargin+step*2, Height-ran.get(i)*9+100, 40, ran.get(i)*9, 40, 10);  
            //列出产品的编号  
            g2.drawString("第"+(i+1)+"轮"+ran.get(i)+"分", leftMargin+step*2, Height-ran.get(i)*10);  
        }  
    }
    
}  
