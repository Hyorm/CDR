import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyPage extends JFrame {

	private static final long serialVersionUID = -1040209162608415600L;
	private ColorFactory colorFactory = new ColorFactory();
	public static final int BTNWIDTH = 250;
	public static final int BTNHEIGHT = 50;
	public static final int LABELWIDTH = 400;
	public static final int LABELHEIGHT = 50;

	JLabel jl = new JLabel("My Page");
	JButton buttonInfo = new JButton("My Info");
	JButton buttonPckList = new JButton("Picked List");
	JButton buttonOrdList = new JButton("Ordered List");
	JButton buttonAddDis = new JButton("Add Disease");
	JButton buttonChekDis = new JButton("Check Disease");
	JButton buttonDelDis = new JButton("Delete Disease");
	JButton buttonChPW = new JButton("Change Password");

	public MyPage(SqlHolder sqlInf, String user_name) {
		super("My Page");

		this.getContentPane().setBackground(colorFactory.getBackgroundColor());
		setBounds(100, 100, 1200, 800);

		Container contentPane = this.getContentPane();

		jl.setFont(new Font("Serif", Font.BOLD, 40));

		contentPane.setLayout(null);
		buttonInfo.setSize(BTNWIDTH, BTNHEIGHT);
		buttonPckList.setSize(BTNWIDTH, BTNHEIGHT);
		buttonOrdList.setSize(BTNWIDTH, BTNHEIGHT);
		buttonAddDis.setSize(BTNWIDTH, BTNHEIGHT);
		buttonChekDis.setSize(BTNWIDTH, BTNHEIGHT);
		buttonDelDis.setSize(BTNWIDTH, BTNHEIGHT);
		buttonChPW.setSize(BTNWIDTH, BTNHEIGHT);
		jl.setSize(LABELWIDTH, LABELHEIGHT);

		buttonInfo.setLocation(450, 80);
		buttonPckList.setLocation(450, 150);
		buttonOrdList.setLocation(450, 220);
		buttonAddDis.setLocation(450, 290);
		buttonChekDis.setLocation(450, 360);
		buttonDelDis.setLocation(450, 430);
		buttonChPW.setLocation(450, 500);
		jl.setLocation(500, 0);

		contentPane.add(buttonInfo);
		contentPane.add(buttonPckList);
		contentPane.add(buttonOrdList);
		contentPane.add(buttonAddDis);
		contentPane.add(buttonChekDis);
		contentPane.add(buttonDelDis);
		contentPane.add(buttonChPW);
		contentPane.add(jl);

		buttonInfo.addActionListener(new BtnListener(this, sqlInf, user_name));
		buttonPckList.addActionListener(new BtnListener(this, sqlInf, user_name));
		buttonOrdList.addActionListener(new BtnListener(this, sqlInf, user_name));
		buttonAddDis.addActionListener(new BtnListener(this, sqlInf, user_name));
		buttonChekDis.addActionListener(new BtnListener(this, sqlInf, user_name));
		buttonDelDis.addActionListener(new BtnListener(this, sqlInf, user_name));
		buttonChPW.addActionListener(new BtnListener(this, sqlInf, user_name));

		setVisible(true);
	}
}
