package com.hutb.view;


import com.hutb.service.UserService;
import com.hutb.service.impl.UserServiceImpl;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class LoginFrame implements MouseListener {

    public static JFrame loginFrame = new JFrame("登录窗口");
    private JLabel label1 = new JLabel("用户名:");
    private JTextField userNameIn = new JTextField();
    private JLabel label2 = new JLabel("密  码:");
    private JLabel label3 = new JLabel("还没有账号？");
    private JPasswordField passWordIn = new JPasswordField();
    private JButton btn1 = new JButton("登录");
    private JButton btn2 = new JButton("立即注册");

    private String userName;
    private String passWord;

    private int distinguish;//用来记录鼠标悬停在哪个位置


    UserService userService = new UserServiceImpl();

    public LoginFrame() throws IOException {
    }

    public void show() {
        loginFrame.setLayout(null);//定义空布局
        loginFrame.setSize(460, 380);
        loginFrame.setLocation(400, 200);

        Font font = new Font("幼圆", Font.BOLD, 20);
        label1.setFont(font);
        label1.setForeground(Color.black);//设置字体颜色
        label2.setFont(font);
        label2.setForeground(Color.black);
        label3.setFont(new Font("幼圆",Font.BOLD,15));
        label3.setForeground(Color.black);
        userNameIn.setOpaque(false);//设置文本框透明
        passWordIn.setOpaque(false);

        btn1.setFont(font);
        btn1.setBackground(Color.getHSBColor(220,40,30));
        btn1.setForeground(Color.orange);
        btn1.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件
        btn1.setFocusPainted(false);
        btn2.setContentAreaFilled(false);
        btn2.setBorder(null);
        btn2.setFont(new Font("幼圆",Font.BOLD,16));
        btn2.setForeground(Color.getHSBColor(0,50,50));
        btn2.setFocusPainted(false);
        JPanel bj = new JPanel() {//设置背景
            protected void paintComponent(Graphics g) {
                Image bg;
                try {
                    bg = ImageIO.read(new File("src/main/resources/image/7.png"));
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        label1.setBounds(113, 65, 100, 100);
        userNameIn.setBounds(193, 100, 150, 35);
        label2.setBounds(113, 110, 100, 100);
        passWordIn.setBounds(193, 145, 150, 35);
        label3.setBounds(10, 265, 100, 100);
        btn1.setBounds(113, 200, 230, 40);
        btn2.setBounds(105, 304, 80, 20);
        loginFrame.setContentPane(bj);
        loginFrame.setLayout(null);
        loginFrame.add(label1);
        loginFrame.add(userNameIn);
        loginFrame.add(label2);
        loginFrame.add(passWordIn);
        loginFrame.add(label3);
        loginFrame.add(btn1);
        loginFrame.add(btn2);
        btn1.addMouseListener(this);//添加鼠标监听
        btn2.addMouseListener(this);
        loginFrame.setVisible(true);


    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        btn1.setForeground(Color.white);
        userName = userNameIn.getText();
        System.out.println(userName);
        passWord= passWordIn.getText();
        if (distinguish == 1) {  //user click the button "登录"
            if (userService.login(userName, passWord) == 1) { //
                JOptionPane.showMessageDialog(null, "登录成功！", "提示", 2);
                userNameIn.setText("");
                passWordIn.setText("");
                distinguish = 4;
                TestFrame testFrame = null;
                try {
                    testFrame = new TestFrame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                testFrame.setTitle("小学四则运算");
                testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                testFrame.setVisible(true);
                loginFrame.setVisible(false);
            } else if (userService.login(userName, passWord) == 2) {
                JOptionPane.showMessageDialog(null, "密码错误！", "提示", 2);
                passWordIn.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "此id不存在，请先注册！", "提示", 2);
                userNameIn.setText("");
                passWordIn.setText("");
            }
        }
        if (distinguish == 2) {
            String name = (String) JOptionPane.showInputDialog(null, "请输入你的id：\n", "注册", JOptionPane.PLAIN_MESSAGE, null, null, "在这输入");
            String passWord = (String) JOptionPane.showInputDialog(null, "请输入你的密码：\n", "注册", JOptionPane.PLAIN_MESSAGE, null, null, "在这输入");
            userService.register(name, passWord);
            JOptionPane.showMessageDialog(null, "注册成功！", "提示", 2);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        if (arg0.getSource() == btn1) {
            distinguish = 1;//鼠标悬停在btn1处则把distinguish置1  
            btn1.setBackground(Color.getHSBColor(220,40,30));//改变颜色
            btn1.setForeground(Color.white);
            btn2.setForeground(Color.getHSBColor(0,50,50));
            btn2.setBorder(null);

        }
        if (arg0.getSource() == btn2) {
            distinguish = 2;
            btn1.setForeground(Color.orange);
            btn1.setBackground(Color.getHSBColor(220,40,30));
            btn1.setBorder(BorderFactory.createRaisedBevelBorder());
            btn2.setForeground(Color.white);
            btn2.setBackground(Color.darkGray);


        }

    }

    @Override
    public void mouseExited(MouseEvent arg0) {//鼠标退出三个button组件后恢复  
        distinguish = 0;
        label1.setForeground(Color.black);
        label2.setForeground(Color.black);
        userNameIn.setOpaque(false);
        passWordIn.setOpaque(false);
        btn1.setForeground(Color.orange);
        btn1.setBorder(BorderFactory.createRaisedBevelBorder());
        btn2.setContentAreaFilled(false);
        btn2.setBorder(null);
        btn2.setForeground(Color.getHSBColor(0,50,50));

    }
} 