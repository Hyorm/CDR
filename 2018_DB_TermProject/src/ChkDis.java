import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ChkDis extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3078030796572868194L;
	private static ColorFactory colorFactory = new ColorFactory();

	SqlHolder sqlInf;

	JLabel TopWords, jlDisID, jlDisName, valDisID, valDisName;

	String userID;
	ArrayList<String> DisList = new ArrayList<String>();

	public ChkDis(SqlHolder _sqlInf, String _user_name) {
		super("Check My Disease");
		sqlInf = _sqlInf;

		this.getContentPane().setBackground(colorFactory.getBackgroundColor());
		setBounds(300, 100, 1000, 600);
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
				userID = rs.getString("user_id");

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException sel) {
			sel.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void getDisName() {
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(sqlInf.getDriver());
			conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
			stmt = conn.createStatement();

			String sql = "SELECT disease_id, disease_name FROM diseasetable WHERE disease_id IN "
					+ "(SELECT disease_id FROM sufferList WHERE user_id='" + userID + "');";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				DisList.add(rs.getString("disease_id"));
				DisList.add(rs.getString("disease_name"));
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException sel) {
			sel.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void showDis() {
		add(TopWords = new JLabel("Your Disease", JLabel.CENTER));
		TopWords.setBounds(0, 10, 500, 50);

		add(TopWords = new JLabel("ID", JLabel.CENTER));
		TopWords.setBounds(300, 20, 200, 30);

		add(TopWords = new JLabel("Name", JLabel.CENTER));
		TopWords.setBounds(450, 20, 200, 30);

		for (int i = 0; i < DisList.size(); i += 2) {
			add(valDisID = new JLabel(DisList.get(i), JLabel.CENTER));
			valDisID.setBounds(300, 30 + (i + 1) * 30, 200, 30);

			add(valDisName = new JLabel(DisList.get(i + 1), JLabel.CENTER));
			valDisName.setBounds(450, 30 + (i + 1) * 30, 200, 30);
		}

		this.setLayout(null);

		setVisible(true);
	}
}
