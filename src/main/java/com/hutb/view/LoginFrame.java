package com.hutb.view;


import com.hutb.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


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

    public static JFrame loginFrame = new JFrame("��¼����");
    private JLabel label1 = new JLabel("�û���:");
    private JTextField userNameIn = new JTextField();
    private JLabel label2 = new JLabel("��  ��:");
    private JLabel label3 = new JLabel("��û���˺ţ�");
    private JPasswordField passWordIn = new JPasswordField();
    private JButton btn1 = new JButton("��¼");
    private JButton btn2 = new JButton("����ע��");

    private String userName;
    private String passWord;

    private int distinguish;//������¼�����ͣ���ĸ�λ��

    private UserService userService;


    public LoginFrame() throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        userService = (UserService) context.getBean("IUserService");
    }

    public void show() {
        loginFrame.setLayout(null);//����ղ���
        loginFrame.setSize(420, 600);
        loginFrame.setLocation(420, 100);

        Font font = new Font("��Բ", Font.BOLD, 20);
        label1.setFont(font);
        label1.setForeground(Color.black);//����������ɫ
        label2.setFont(font);
        label2.setForeground(Color.black);
        label3.setFont(new Font("��Բ", Font.BOLD, 15));
        label3.setForeground(Color.black);
        userNameIn.setOpaque(false);//�����ı���͸��
        userNameIn.setBorder(null);

        passWordIn.setOpaque(false);
        passWordIn.setBorder(null);
        btn1.setFont(font);
        btn1.setBorder(null);
        btn1.setContentAreaFilled(false);
        btn1.setForeground(Color.black);
        btn1.setFocusPainted(false);

        btn2.setContentAreaFilled(false);
        btn2.setBorder(null);
        btn2.setFont(new Font("��Բ", Font.BOLD, 16));
        btn2.setForeground(Color.blue);
        btn2.setFocusPainted(false);
        JPanel bj = new JPanel() {//���ñ���
            protected void paintComponent(Graphics g) {
                Image bg;
                try {
                    bg = ImageIO.read(new File("src/main/resources/image/1.jpg"));
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        label1.setBounds(102, 134, 100, 100);
        userNameIn.setBounds(185, 170, 150, 35);
        label2.setBounds(102, 208, 100, 100);
        passWordIn.setBounds(185, 241, 150, 35);
        label3.setBounds(220, 288, 100, 100);
        btn1.setBounds(75, 303, 100, 30);
        btn2.setBounds(216, 355, 80, 20);
        loginFrame.setContentPane(bj);
        loginFrame.setLayout(null);
        loginFrame.add(label1);
        loginFrame.add(userNameIn);
        loginFrame.add(label2);
        loginFrame.add(passWordIn);
        loginFrame.add(label3);
        loginFrame.add(btn1);
        loginFrame.add(btn2);
        btn1.addMouseListener(this);//���������
        btn2.addMouseListener(this);
        loginFrame.setVisible(true);


    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        btn1.setForeground(Color.white);
        userName = userNameIn.getText();
        passWord = passWordIn.getText();
        if (distinguish == 1) {  //user click the button "��¼"
            if (userService.login(userName, passWord) == 1) { //
                int score = userService.historicScore(userName);
                JOptionPane.showMessageDialog(null, "��ӭ" + userName + "����" + "���ϴ�����÷֣�" + score, "��ʾ", 2);
                userNameIn.setText("");
                passWordIn.setText("");
                distinguish = 4;
                TestFrame testFrame = null;
                try {
                    testFrame = new TestFrame(userName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                testFrame.setTitle("Сѧ��������");
                testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                testFrame.setVisible(true);
                loginFrame.setVisible(false);
            } else if (userService.login(userName, passWord) == 2) {
                JOptionPane.showMessageDialog(null, "�������", "��ʾ", 2);
                passWordIn.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "��id�����ڣ�����ע�ᣡ", "��ʾ", 2);
                userNameIn.setText("");
                passWordIn.setText("");
            }
        }
        if (distinguish == 2) {
            String name = (String) JOptionPane.showInputDialog(null, "���������id��\n", "ע��", JOptionPane.PLAIN_MESSAGE, null, null, "��������");
            String passWord = (String) JOptionPane.showInputDialog(null, "������������룺\n", "ע��", JOptionPane.PLAIN_MESSAGE, null, null, "��������");
            userService.register(name, passWord);
            JOptionPane.showMessageDialog(null, "ע��ɹ���", "��ʾ", 2);
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
            distinguish = 1;//�����ͣ��btn1�����distinguish��1
            btn1.setForeground(Color.white);
            btn1.setBorder(null);


        }
        if (arg0.getSource() == btn2) {
            distinguish = 2;
            btn2.setForeground(Color.white);
        }

    }

    @Override
    public void mouseExited(MouseEvent arg0) {//����˳�����button�����ָ�  
        distinguish = 0;
        label1.setForeground(Color.black);
        label2.setForeground(Color.black);
        userNameIn.setOpaque(false);
        passWordIn.setOpaque(false);
        btn1.setForeground(Color.black);
        btn1.setBorder(null);
        btn2.setContentAreaFilled(false);
        btn2.setBorder(null);
        btn2.setForeground(Color.blue);

    }
} 