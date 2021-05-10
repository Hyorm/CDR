import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;

public class Main {

    private static String sqlDomain = "203.252.121.222:3306/CDR?serverTimezone=UTC";
    private static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String DB_URL = "jdbc:mysql://" + sqlDomain;

    private static String USERNAME = "root";
    private static String PASSWORD = "dbzara";

    private static String custom_name = "Noah";

    private static SqlHolder sqlInf = new SqlHolder(JDBC_DRIVER, DB_URL, USERNAME, PASSWORD);

    private static ArrayList<String[]> queryRe;

    private static ColorFactory colorFactory = new ColorFactory();

    private static int verFlag = 0;

    public static void main(String[] args) {

        logIN();
    }

    public static void mainPage() {
        JFrame frame = new JFrame();

        frame.setLayout(new GridLayout(3, 1));
	ImageIcon logo;
        Image logoRe;
	logo = new ImageIcon("./data/BestJC.jpeg");
        logoRe = logo.getImage();
        logoRe = logoRe.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
        logo = new ImageIcon(logoRe);
	JLabel jcLab = new JLabel(logo);
        JButton myPageBtn = new JButton("Mypage");
        myPageBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                new MyPage(sqlInf, queryRe.get(0)[1]);
            }
        });

        JButton OrderBtn = new JButton("Order");
        OrderBtn.addActionListener(new ActionListener() {
            // add Recommend and pass drink_id
            public void actionPerformed(ActionEvent e) {
                new StartPage(sqlInf, queryRe.get(0)[0]);
            }
        });

        /* *************************************************** */
        // add Decoration - 0524/bokyoung
        JPanel p1 = new JPanel(new GridLayout(2, 1));

        Font myFont1 = new Font("arial", Font.BOLD, 70);
        Font myFont2 = new Font("arial", Font.BOLD, 30);

        JLabel jlCDR = new JLabel("CDR");
        jlCDR.setFont(myFont1);
        jlCDR.setHorizontalAlignment(SwingConstants.CENTER);
        jlCDR.setVerticalAlignment(SwingConstants.BOTTOM);

        JLabel jlUserName = new JLabel("user:" + queryRe.get(0)[1]);
        jlUserName.setFont(myFont2);
        jlUserName.setHorizontalAlignment(SwingConstants.CENTER);
        jlCDR.setVerticalAlignment(SwingConstants.TOP);

        p1.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        p1.setBackground(colorFactory.getBackgroundColor());
        p1.add(jlCDR);
        p1.add(jlUserName);

        JPanel p2 = new JPanel(new GridLayout(2, 1));
        p2.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        p2.setBackground(colorFactory.getBackgroundColor());
        p2.add(myPageBtn);
        p2.add(OrderBtn);

	JPanel p3 = new JPanel(new GridLayout(1, 1));
	p3.setBackground(colorFactory.getBackgroundColor());
	p3.add(jcLab);

        frame.add(p1);
        frame.add(p2);
	frame.add(p3);
	frame.setLocation(300,100);
        /* *************************************************** */

        // frame.add(myPageBtn);
        // frame.add(OrderBtn);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);
        frame.setSize(500, 800);
        frame.setVisible(true);

    }

    public static void logIN() {

        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(6, 1));

        JLabel idLab = new JLabel("ID");
        idLab.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel pwLab = new JLabel("PW");
        pwLab.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField inputID = new JTextField(10);
        JTextField inputPW = new JTextField(10);

        JButton logBtn = new JButton("Login");
        logBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                int queryNum = 4;
                if (inputID.getText().length() != 0) {
                    String[] queryList = { "user_id", "user_name", "age", "gender" };
                    String queryStr = "SELECT user_id, user_name, age, gender FROM userList Where user_id=\""
                            + inputID.getText() + "\" and passwd=\"" + inputPW.getText() + "\";";
                    System.out.println(queryStr);
                    query(queryStr, queryNum, queryList);

                    if (queryRe.size() != 0) {
                        mainPage();
                        frame.dispose();

                    } else
                        JOptionPane.showMessageDialog(null, "Wrong ID/PW", "Wrong", JOptionPane.WARNING_MESSAGE);
                } else
                    JOptionPane.showMessageDialog(null, "Wrong ID/PW", "Wrong", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton regBtn = new JButton("Register");
        regBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                register();
                frame.dispose();
            }
        });

        /* -------- bokyoung ----------- */

        ColorFactory colorFactory = new ColorFactory();
        JLabel jlLoginPage = new JLabel("Welcome to CDR, Please Login");
        jlLoginPage.setOpaque(true);
        jlLoginPage.setBackground(colorFactory.getBackgroundColor());
        jlLoginPage.setFont(new Font("arial", Font.BOLD, 30));
        jlLoginPage.setHorizontalAlignment(SwingConstants.CENTER);

        idLab.setFont(new Font("arial", Font.BOLD, 30));
        pwLab.setFont(new Font("arial", Font.BOLD, 30));

        JPanel jpTop = new JPanel(new GridLayout(2, 2, 5, 5));
        jpTop.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jpTop.setBackground(colorFactory.getBackgroundColor());
        jpTop.add(idLab);
        jpTop.add(inputID);
        jpTop.add(pwLab);
        jpTop.add(inputPW);

        JPanel jpBottom = new JPanel(new GridLayout(2, 1));
        jpBottom.setBackground(colorFactory.getBackgroundColor());
        jpBottom.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jpBottom.add(logBtn);
        jpBottom.add(regBtn);

        frame.setLayout(new GridLayout(3, 1));
        frame.add(jlLoginPage);
        frame.add(jpTop);
        frame.add(jpBottom);

        // frame.add(idLab);
        // frame.add(inputID);
        // frame.add(pwLab);
        // frame.add(inputPW);
        // frame.add(logBtn);
        // frame.add(regBtn);

        /* -----------end----------------- */

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setBackground(Color.WHITE);
        frame.setSize(500, 600);
        frame.setLocation(300, 100);
        frame.setVisible(true);

    }

    public static void register() {

        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(8, 0));
	frame.getContentPane().setBackground(colorFactory.getBackgroundColor());
        JLabel idLab = new JLabel("ID:");
        idLab.setHorizontalAlignment(SwingConstants.CENTER);

        JButton verBtn = new JButton("Verify ID");
        JLabel pwLab = new JLabel("PW: ");
        pwLab.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel nameLab = new JLabel("Name:");
        nameLab.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel ageLab = new JLabel("Age:");
        ageLab.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel genderLab = new JLabel("Gender:");
        genderLab.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField inputID = new JTextField(10);
        JTextField inputPW = new JTextField(10);
        JTextField inputName = new JTextField(50);
        JComboBox inputAge = new JComboBox();
        for (int i = 1; i < 60; i++)
            inputAge.addItem(i + "");

        JComboBox inputGender = new JComboBox();
        inputGender.addItem("M");
        inputGender.addItem("F");

        frame.add(idLab);
        frame.add(inputID);
        frame.add(pwLab);
        frame.add(inputPW);
        frame.add(nameLab);
        frame.add(inputName);
        frame.add(ageLab);
        frame.add(inputAge);
        frame.add(genderLab);
        frame.add(inputGender);
        frame.add(verBtn);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        verBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (inputID.getText().length() != 0) {
                    int queryNum = 1;
                    String[] queryList = { "user_id" };
                    String queryStr = "SELECT user_id FROM userList Where user_id=\"" + inputID.getText() + "\";";
                    System.out.println(queryStr);
                    query(queryStr, queryNum, queryList);
                    if (queryRe.size() == 0) {
                        verFlag = 1;
                        JOptionPane.showMessageDialog(null, "Use This ID", "Valid ID", JOptionPane.WARNING_MESSAGE);
                    } else
                        JOptionPane.showMessageDialog(null, "Wrong ID", "Already registered ID",
                                JOptionPane.WARNING_MESSAGE);
                } else
                    JOptionPane.showMessageDialog(null, "Wrong ID", "Already registered ID",
                            JOptionPane.WARNING_MESSAGE);
            }

        });

        JButton regBtn = new JButton("Register");
        regBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (verFlag == 1 && inputName.getText().length()!=0&& (inputID.getText().length() != 0) && inputPW.getText().length() != 0) {
                    JOptionPane.showMessageDialog(null, "Registered", "Success", JOptionPane.WARNING_MESSAGE);
                    Connection conn = null;
                    Statement stmt = null;
                    try {
                        Class.forName(sqlInf.getDriver());
                        conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
                        stmt = conn.createStatement();

                        // Find is arleady there? Ordered List
                        String sql = "INSERT INTO userList VALUES(\"" + inputID.getText() + "\", \""
                                + inputName.getText() + "\", \"" + inputAge.getSelectedItem() + "\",\""
                                + inputGender.getSelectedItem() + "\",\"" + inputPW.getText() + "\");";
                        System.out.println(sql);
                        stmt.executeUpdate(sql);
                        stmt.close();
                        conn.close();
                    } catch (SQLException sel) {
                        sel.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            if (stmt != null)
                                stmt.close();
                        } catch (SQLException se2) {
                        }
                        try {
                            if (conn != null)
                                conn.close();
                        } catch (SQLException se) {
                            se.printStackTrace();
                        }
                    }

                    logIN();

                    frame.dispose();
                } else
                    JOptionPane.showMessageDialog(null, "Wrong ID/PW", "Wrong", JOptionPane.WARNING_MESSAGE);

            }
        });

        frame.add(regBtn);

        frame.setBackground(Color.WHITE);
        frame.setSize(500, 900);
        frame.setVisible(true);

    }

    public static void query(String sql, int num, String[] sqlList) {

        Connection conn = null;
        Statement stmt = null;
        queryRe = new ArrayList<String[]>();

        try {
            Class.forName(sqlInf.getDriver());
            conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String[] newList = new String[num];
                for (int i = 0; i < num; i++) {
                    newList[i] = rs.getString(sqlList[i]);
                }
                queryRe.add(newList);
            }
            rs.close();

            stmt.close();
            conn.close();
        } catch (SQLException sel) {
            sel.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }
}
