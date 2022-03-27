package com.hutb.view;


import com.hutb.dao.IQuestionBankDao;
import com.hutb.service.QuestionBankService;
import com.hutb.service.UserService;
import com.hutb.service.impl.QuestionBankServiceImpl;
import com.hutb.utils.Chart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class TestFrame extends JFrame {

    private JPanel panel = new JPanel() {
        protected void paintComponent(Graphics g) {
            Image bg;
            try {
                bg = ImageIO.read(new File("src/main/resources/image/2.jpg"));
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    private JTextField answer;
    private JLabel lun = new JLabel("第 1 轮");
    private JLabel scorelb = new JLabel("当前得分:");
    private static JLabel question = new JLabel("____________");
    private JLabel num = new JLabel("第 1 题：");
    private JLabel time = new JLabel("00:00");
    private JComboBox language = new JComboBox();
    private JLabel score = new JLabel("0");
    private JButton nextbtn = new JButton("下一题");
    private JButton drawlb = new JButton("成绩图表");
    private final JButton startbtn = new JButton("开始测试");
    private JButton exitbtn = new JButton("退出系统");
    private String userName;
    private QuestionBankService questionBankService;
    private UserService userService;


    long startTime;
    int userAns = 0, count = 1, clun = 1;
    int sum = 0;
    int trueAns = 0;
    ArrayList<Integer> scorelist = new ArrayList<Integer>();

    static Random r = new Random();
    private int[] ids = new int[21];

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            //TODO exception
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginFrame window = new LoginFrame();
                    window.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public TestFrame(String name) throws IOException {
        this.userName = name;
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        questionBankService = (QuestionBankService) context.getBean("IQuestionBankService");
        userService = (UserService) context.getBean("IUserService");
        setBounds(240, 80, 821, 643);

        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        lun.setForeground(Color.black);
        lun.setHorizontalAlignment(SwingConstants.CENTER);
        lun.setFont(new Font("幼圆", Font.BOLD, 42));
        lun.setBackground(Color.black);
        lun.setBounds(335, 260, 300, 71);
        panel.add(lun);

        scorelb.setFont(new Font("幼圆", Font.BOLD, 22));
        scorelb.setBounds(565, 440, 500, 100);
        panel.add(scorelb);

        num.setToolTipText("");
        num.setHorizontalAlignment(SwingConstants.CENTER);
        num.setFont(new Font("幼圆", Font.BOLD, 22));
        num.setBackground(new Color(0, 255, 255));
        num.setBounds(290, 100, 200, 34);
        panel.add(num);

        question.setForeground(Color.black);
        question.setFont(new Font("幼圆", Font.BOLD, 27));
        question.setBounds(325, 135, 276, 53);
        panel.add(question);

        answer = new JTextField();
        answer.setFont(new Font("幼圆", Font.BOLD, 27));
        answer.setBounds(552, 138, 100, 45);
        answer.setColumns(10);
        panel.add(answer);

        time.setFont(new Font("幼圆", Font.BOLD, 25));
        time.setForeground(Color.red);
        time.setBounds(650, 340, 186, 46);
        panel.add(time);

        language.setEditable(true);
        language.setBounds(655, 22, 72, 24);
        language.addItem("中文简体");
        language.addItem("繁体");
        language.addItem("英文");
        panel.add(language);

        score.setForeground(Color.RED);
        score.setFont(new Font("幼圆", Font.BOLD, 40));
        score.setHorizontalAlignment(SwingConstants.CENTER);
        score.setBounds(660, 460, 56, 45);
        panel.add(score);

        nextbtn.setFont(new Font("幼圆", Font.BOLD, 19));
        nextbtn.setBounds(552, 217, 100, 46);
        nextbtn.setBackground(null);
        nextbtn.setBorder(null);
        panel.add(nextbtn);
        /**
         * next question operation.
         */
        nextbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String answerIn = answer.getText();
                if (!judge(answerIn)) {
                    JOptionPane.showConfirmDialog(
                            null,
                            "请输入合法数字：", "提示信息",
                            JOptionPane.DEFAULT_OPTION);
                    answer.setText("");
                } else {
                    test(count);
                    answer.setText("");
                    count = count + 1;
                    question.setText(question(count));
                    num.setText("第 " + count + " 题：");
                    if (count == 21) {
                        int value1 = JOptionPane.showConfirmDialog(null,
                                "本轮答题已结束，用时：" + timeUse() + "当前得分：" + sum + "，是否查看试卷？",
                                " 提示信息",
                                JOptionPane.YES_NO_OPTION);

                        if (value1 == JOptionPane.YES_OPTION) {
                            viewPaper();
                        } else {
                            nextRound();
                        }
                    }
                }
            }
        });


        drawlb.setFont(new Font("幼圆", Font.BOLD, 20));
        drawlb.setBackground(new Color(255, 204, 204));
        drawlb.setForeground(Color.black);
        drawlb.setBounds(58, 225, 128, 46);
        panel.add(drawlb);
        drawlb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Chart demo = new Chart(scorelist);
                demo.setVisible(true);
            }
        });
//注释

        startbtn.setBackground(new Color(255, 204, 204));
        startbtn.setFont(new Font("幼圆", Font.BOLD, 20));
        startbtn.setForeground(Color.black);
        startbtn.setBounds(58, 135, 128, 46);
        panel.add(startbtn);

        startbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startTime = new Date().getTime();
                timer();
                question.setText(question(count));
            }
        });

        exitbtn.setBackground(new Color(255, 204, 204));
        exitbtn.setFont(new Font("幼圆", Font.BOLD, 20));
        exitbtn.setForeground(Color.black);
        exitbtn.setBounds(58, 315, 128, 46);
        panel.add(exitbtn);
        exitbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int value2 = JOptionPane.showConfirmDialog(
                        null,
                        "是否退出",
                        "byebye",
                        JOptionPane.YES_NO_OPTION);
                if (value2 == JOptionPane.YES_OPTION) {
                    System.exit(1);
                }
            }
        });
    }

    public void timer() {
        new Thread() {
            public void run() {
                while (count < 21) {
                    try {
                        Thread.sleep(1000);
                        time.setText(timeUse());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void viewPaper() {

        JFrame frame1 = new JFrame("查看试卷");
        JPanel bj = new JPanel() {//设置背景
            protected void paintComponent(Graphics g) {
                Image bg;
                try {
                    bg = ImageIO.read(new File("src/main/resources/image/5.png"));
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        frame1.setContentPane(bj);
        JTextArea area1 = new JTextArea();
        JTextArea area2 = new JTextArea();
        JLabel label = new JLabel(" ");
        area1.setFont(new Font("幼圆", Font.BOLD, 20));
        area2.setFont(new Font("幼圆", Font.BOLD, 20));
        area1.setForeground(Color.black);
        area2.setForeground(Color.black);
        label.setBounds(10, 10, 10, 10);
        area1.setBounds(110, 75, 200, 510);
        area2.setBounds(310, 75, 320, 510);
        frame1.setLayout(null);
        frame1.add(label);
        frame1.add(area1);
        frame1.add(area2);
        frame1.setSize(890, 700);
        frame1.setLocation(210, 30);
        frame1.setVisible(true);
        area1.setText(getContentPart1());
        area2.setText(getContentPart2());
        area1.setEditable(false);
        area2.setEditable(false);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                nextRound();
            }
        });
    }

    public void nextRound() {
        int value = JOptionPane.showConfirmDialog(
                null,
                "是否进入下一轮？",
                "提示信息",
                JOptionPane.YES_NO_OPTION);
        if (value == JOptionPane.YES_OPTION) {
            nextbtn.setText("下一题");
            count = 1;
            num.setText("第 " + count + " 题：");
            clun = clun + 1;
            lun.setText("第 " + clun + " 轮");
            scorelist.add(sum);
            startTime = new Date().getTime();
            timer();
            sum = 0;
            score.setText(String.valueOf(sum));
        } else {
            scorelist.add(sum);
            userService.recordSum(sum, userName);
        }
    }


    public void test(int count) {
        System.out.println(trueAns);
        System.out.println(userAns);
        if (trueAns == userAns && userAns != 0) {
            sum = sum + 5;
            score.setText(String.valueOf(sum));
            questionBankService.recordScore(5, ids[count - 1]);
        } else {
            score.setText(String.valueOf(sum));
            questionBankService.recordScore(0, ids[count - 1]);
        }
        questionBankService.recordAnswer(userAns, ids[count - 1]);
    }

    public String question(int count) {
        int qid;
        String qus;
        qid = r.nextInt(20) + 1;
        try {
            ids[count - 1] = qid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        qus = questionBankService.findQuestion(qid);
        trueAns = questionBankService.findResult(qid);
        return qus;
    }


    public Boolean judge(String answerIn) {
        for (int i = 0; i < answerIn.length(); i++) {
            if (!Character.isDigit(answerIn.charAt(i))) {
                return false;
            }
        }
        if (answerIn.equals("")) {
            answerIn = "" + 0;
        }
        userAns = Integer.parseInt(answerIn.trim());
        return true;
    }


    public String getContentPart1() {
        int id;
        String content = "\n";
        for (int i = 0; i < 20; i++) {
            id = ids[i];
            content = content + questionBankService.printQueAndAns(id) + "\n";
        }
        return content;
    }

    public String getContentPart2() {
        int id;
        String content = "\n";
        for (int i = 0; i < 20; i++) {
            id = ids[i];
            content = content + questionBankService.printResult(id) + "\n";
        }
        return content;
    }


    public String timeUse() {
        long seconds = (new Date().getTime() - startTime) / 1000;
        long mm = seconds / 60;
        long ss = seconds % 60;
        String duration = (mm < 10 ? "0" + mm : "" + mm) + ":" + (ss < 10 ? "0" + ss : "" + ss);
        return duration;
    }
}


