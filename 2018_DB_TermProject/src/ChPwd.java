import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ChPwd extends JFrame {

	private static final long serialVersionUID = 191379860817852542L;
	private static ColorFactory colorFactory = new ColorFactory();

	SqlHolder sqlInf;

	JTextField jtInsPW = new JTextField(10);
	JLabel jlprePW;
	JLabel valprePW;
	JButton exeChPW = new JButton("Change");

	String prePW, newPW; // Previous, New Password
	int userID = -1; // User_id for inserting to suffered list

	public ChPwd(SqlHolder sqlInf_, String user_name) {
		super("Change PassWord");
		sqlInf = sqlInf_;

		this.getContentPane().setBackground(colorFactory.getBackgroundColor());
		setBounds(250, 100, 800, 400);

		this.setLayout(null);

		setVisible(true);
	}

	public void setUserID(String user_name) {
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(sqlInf.getDriver());
			conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
			stmt = conn.createStatement();

			String sql = "SELECT user_id FROM userList WHERE user_name='" + user_name + "';";

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next())
				userID = rs.getInt("user_id");

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException sel) {
			sel.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void showChPwd() {
		// Label for "PreviousPW"
		add(jlprePW = new JLabel("Previous Password", JLabel.CENTER));
		jlprePW.setBounds(150, 100, 200, 30);

		// Search previous password of user
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(sqlInf.getDriver());
			conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
			stmt = conn.createStatement();

			String sql;
			sql = "SELECT passwd FROM userList;";

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				prePW = rs.getString("passwd");
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException sel) {
			sel.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		add(valprePW = new JLabel(prePW, JLabel.CENTER));
		valprePW.setBounds(350, 100, 150, 30);

		// set new password
		add(jtInsPW);
		jtInsPW.setBounds(300, 150, 100, 30);

		add(exeChPW);
		exeChPW.setBounds(400, 150, 100, 30);

		exeChPW.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == exeChPW) {
					newPW = jtInsPW.getText();

					try {
						Connection conn = null;
						Statement stmt = null;

						Class.forName(sqlInf.getDriver());
						conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
						stmt = conn.createStatement();

						String sql;
						sql = "UPDATE userList SET passwd=" + newPW + " WHERE user_id=" + userID;

						int result = stmt.executeUpdate(sql);

						if (result != 0) {
							JOptionPane.showMessageDialog(null, "Succeed to Change your PASSWORD!");
						}

						stmt.close();
						conn.close();
					} catch (SQLException sel) {
						sel.printStackTrace();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
	}
}
