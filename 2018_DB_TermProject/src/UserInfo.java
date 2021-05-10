import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class UserInfo extends JFrame {

	private static final long serialVersionUID = 6736101608904780395L;
	private static ColorFactory colorFactory = new ColorFactory();

	private ArrayList<String> Informs = new ArrayList<String>(); // receive string
								     // in (user_id, user_name, age, gender) order
	JLabel jlId, jlName, jlAge, jlGender;
	JLabel valID, valName, valAge, valGender;

	public UserInfo(JFrame pre_frame, SqlHolder sqlInf, String user_name) {
		super("My Info");

		setPage();
		setBounds(200, 100, 800, 200);

		
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(sqlInf.getDriver());
			conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
			stmt = conn.createStatement();

			String sql;
			sql = "SELECT * FROM userList WHERE user_name='" + user_name + "';";

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				String userId = rs.getString("user_id");
				String userName = rs.getString("user_name");
				String age = rs.getString("age");
				String gender = rs.getString("gender");

				Informs.add(userId);
				Informs.add(userName);
				Informs.add(age);
				Informs.add(gender);
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

		setVisible(true);
	}

	public void setPage() {
		this.getContentPane().setBackground(colorFactory.getBackgroundColor());
	}
		

	public void showUserInfo() {
		// user_id
		add(jlId = new JLabel("ID", JLabel.CENTER));
		jlId.setBorder(BorderFactory.createBevelBorder(0));
		jlId.setBounds(0, 10, 100, 50);
		add(valID = new JLabel(Informs.get(0), JLabel.CENTER));
		valID.setHorizontalAlignment(JTextField.CENTER);
		valID.setBounds(0, 60, 120, 50);
		valID.setText(Informs.get(0));

		// user_name
		add(jlName = new JLabel("Name", JLabel.CENTER));
		jlName.setBorder(BorderFactory.createBevelBorder(0));
		jlName.setBounds(110, 10, 100, 50);
		add(valName = new JLabel(Informs.get(1), JLabel.CENTER));
		valName.setHorizontalAlignment(JTextField.CENTER);
		valName.setBounds(110, 60, 100, 50);
		// age
		add(jlAge = new JLabel("Age", JLabel.CENTER));
		jlAge.setBorder(BorderFactory.createBevelBorder(0));
		jlAge.setBounds(220, 10, 100, 50);
		add(valAge = new JLabel(Informs.get(2), JLabel.CENTER));
		valAge.setHorizontalAlignment(JTextField.CENTER);
		valAge.setBounds(220, 60, 100, 50);

		// gender
		add(jlGender = new JLabel("Gender", JLabel.CENTER));
		jlGender.setBorder(BorderFactory.createBevelBorder(0));
		jlGender.setBounds(330, 10, 100, 50);
		add(valGender = new JLabel(Informs.get(3), JLabel.CENTER));
		valGender.setHorizontalAlignment(JTextField.CENTER);
		valGender.setBounds(330, 60, 100, 50);
	}
}
