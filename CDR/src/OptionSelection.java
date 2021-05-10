import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class OptionSelection extends JFrame implements ActionListener {

	private JButton jbSelect;
	private JButton jbRecommendList;
	private JButton jbNext;
	public static JLabel jlChoice;
	private JLabel jlChoiceText;
	private JPanel jpLeft;
	private JPanel jpRight;
	public static int chooseID = 1; //default value is 1
	private ColorFactory colorFactory = new ColorFactory();
	public static JCheckBox jcConsiderDisease = new JCheckBox("  Do you want to consider disease?");

	// private SqlHolder sqlInf;

	private static String sqlDomain = "203.252.121.222:3306/CDR?serverTimezone=UTC";
	private static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String DB_URL = "jdbc:mysql://" + sqlDomain;

	private static String USERNAME = "root";
	private static String PASSWORD = "dbzara";
	private static SqlHolder sqlInf = new SqlHolder(JDBC_DRIVER, DB_URL, USERNAME, PASSWORD);

	public OptionSelection(SqlHolder sqlInf_) {
		super("Select Option!");
		sqlInf = sqlInf_;

		// Initialize Components
		jbSelect = new JButton("Set Option");
		jbRecommendList = new JButton("Recommend");
		jbNext = new JButton("next");

		jlChoice = new JLabel("no");
		Font myFont1 = new Font("arial", Font.BOLD, 20);
		jlChoice.setHorizontalAlignment(SwingConstants.CENTER);
		jlChoice.setVerticalAlignment(SwingConstants.CENTER);
		jlChoice.setFont(myFont1);

		jlChoiceText = new JLabel("my option");
		Font myFont2 = new Font("arial", Font.BOLD, 30);
		jlChoiceText.setFont(myFont2);
		jlChoiceText.setHorizontalAlignment(SwingConstants.CENTER);
		jlChoiceText.setVerticalAlignment(SwingConstants.CENTER);

		// Add ActionListener
		jbSelect.addActionListener(this);
		jbRecommendList.addActionListener(this);
		jbNext.addActionListener(this);
		jcConsiderDisease.addActionListener(this);

		// Setting Panel (Add components to each Panel)
		jpLeft = new JPanel();
		jpLeft.setLayout(new GridLayout(2, 1));
		jpLeft.add(jlChoiceText);
		jpLeft.add(jlChoice);
		jpLeft.setBorder(BorderFactory.createEmptyBorder(100, 10, 100, 10));
		jpLeft.setBackground(colorFactory.getBackgroundColor());

		jpRight = new JPanel();
		jpRight.setLayout(new GridLayout(4, 1));
		jpRight.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
		jpRight.setBackground(colorFactory.getBackgroundColor());
		jpRight.add(jcConsiderDisease);
		jpRight.add(jbSelect);
		jpRight.add(jbRecommendList);
		jpRight.add(jbNext);

		// Add Panel to Frame
		this.setLayout(new GridLayout(1, 2));
		this.add(jpLeft);
		this.add(jpRight);

		// Set Frame visible
		this.setSize(1000, 400);
		this.setLocation(300, 150);
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbSelect) {
			new OptionChoose(sqlInf);
		} else if (e.getSource() == jbRecommendList) {
			new OptionRecommend(sqlInf);
		} else if (e.getSource() == jbNext) {
			this.dispose();
		}

	}

	public static void setChooseID(int id) {
		chooseID = id;
	}

	public static void main(String[] args) {
		new OptionSelection(sqlInf);
	}

}