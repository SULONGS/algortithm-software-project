package com.hutb.view;


import com.hutb.service.QuestionBankService;
import com.hutb.service.impl.QuestionBankServiceImpl;
import com.hutb.utils.Chart;


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
                bg = ImageIO.read(new File("src/main/resources/image/4.png"));
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    private JTextField answer;
    private JLabel lun = new JLabel("第1轮");
    private JLabel scorelb = new JLabel("得分:");
    private static JLabel question = new JLabel("____________");
    private JLabel num = new JLabel("1");
    private JLabel time = new JLabel("计时：00:00");
    private JComboBox language = new JComboBox();
    private JLabel score = new JLabel("0");
    private JButton nextbtn = new JButton("下一题");
    private JButton drawlb = new JButton("成绩图表");
    private final JButton startbtn = new JButton("开始测试");
    private QuestionBankService questionBankService = new QuestionBankServiceImpl();

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
    public TestFrame() throws IOException {
        setBounds(100, 100, 821, 643);

        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        lun.setForeground(Color.BLUE);
        lun.setHorizontalAlignment(SwingConstants.CENTER);
        lun.setFont(new Font("华为行楷", Font.BOLD, 42));
        lun.setBackground(Color.BLUE);
        lun.setBounds(339, 80, 139, 71);
        panel.add(lun);

        scorelb.setFont(new Font("微软雅黑", Font.BOLD, 21));
        scorelb.setBounds(252, 339, 72, 46);
        panel.add(scorelb);

        num.setToolTipText("");
        num.setHorizontalAlignment(SwingConstants.CENTER);
        num.setBorder(new LineBorder(new Color(0, 204, 51), 3, true));
        num.setFont(new Font("华文楷体", Font.BOLD, 17));
        num.setBackground(new Color(0, 255, 255));
        num.setBounds(234, 209, 45, 34);
        panel.add(num);

        question.setForeground(new Color(51, 204, 255));
        question.setFont(new Font("华文楷体", Font.BOLD, 32));
        question.setBounds(298, 205, 276, 53);
        panel.add(question);

        answer = new JTextField();
        answer.setFont(new Font("华文楷体", Font.BOLD, 32));
        answer.setBounds(588, 205, 128, 53);
        answer.setColumns(10);
        panel.add(answer);

        time.setFont(new Font("微软雅黑", Font.BOLD, 21));
        time.setBounds(255, 429, 186, 46);
        panel.add(time);

        language.setEditable(true);
        language.setBounds(582, 68, 72, 24);
        language.addItem("中文简体");
        language.addItem("繁体");
        language.addItem("英文");
        panel.add(language);

        score.setForeground(Color.RED);
        score.setFont(new Font("宋体", Font.BOLD, 22));
        score.setHorizontalAlignment(SwingConstants.CENTER);
        score.setBounds(298, 346, 56, 34);
        panel.add(score);

        nextbtn.setBackground(new Color(204, 204, 255));
        nextbtn.setFont(new Font("宋体", Font.BOLD, 17));
        nextbtn.setBounds(479, 334, 113, 46);
        panel.add(nextbtn);
        /**
         * next question operation.
         */
        nextbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean isend;
                isend = nextbtn.getText().equals("退出系统");
                if (!isend) {
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
                        num.setText("" + count);
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
            }
        });


        drawlb.setFont(new Font("宋体", Font.BOLD, 17));
        drawlb.setBackground(new Color(204, 204, 255));
        drawlb.setBounds(479, 432, 113, 46);
        panel.add(drawlb);
        drawlb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Chart demo = new Chart(scorelist);
                demo.setVisible(true);
            }
        });
//注释

        startbtn.setForeground(new Color(255, 0, 51));
        startbtn.setBackground(new Color(255, 204, 204));
        startbtn.setFont(new Font("楷体", Font.BOLD, 20));
        startbtn.setBounds(164, 58, 128, 41);
        panel.add(startbtn);

        startbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startTime = new Date().getTime();
                timer();
                question.setText(question(count));
            }
        });


    }

    public void timer() {
        new Thread() {
            public void run() {
                while (count < 21) {
                    try {
                        Thread.sleep(1000);
                        time.setText("计时" + timeUse());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    public void viewPaper() {
        JFrame frame1 = new JFrame("查看试卷");
        JTextArea area = new JTextArea();
        JLabel label = new JLabel(" ");
        label.setBounds(10, 10, 10, 10);
        area.setBounds(130, 10, 1000, 900);
        frame1.setLayout(null);
        frame1.add(label);
        frame1.add(area);
        frame1.setSize(300, 150);
        frame1.setLocation(300, 200);
        frame1.setVisible(true);
        area.setText(getContent());
        area.setEditable(false);
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
            num.setText("" + count);
            clun = clun + 1;
            lun.setText("第 " + clun + " 轮");
            scorelist.add(sum);
            startTime = new Date().getTime();
            timer();
            sum = 0;
            score.setText(String.valueOf(sum));
        } else {
            scorelist.add(sum);
            nextbtn.setText("退出系统");
            nextbtn.addActionListener(new ActionListener() {
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


    public String getContent() {
        int id;
        String content = "\n";
        for (int i = 0; i < 20; i++) {
            id = ids[i];
            content = content + questionBankService.printPaper(id) + "\n";
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


