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

public class DelDis extends JFrame {

	private static final long serialVersionUID = 794058826431472348L;
	private static ColorFactory colorFactory = new ColorFactory();

	SqlHolder sqlInf;

	JTextField jtDisID = new JTextField(10);
	JLabel jlDisID, jlDisName, jlDelDis;
	JLabel valDisID, valDisName;
	JButton insertDis = new JButton("DELETE");

	String DelDisID; // ID which will be inserted in suffered list
	String userID = null; // User_id for inserting to suffered list

	public DelDis(SqlHolder sqlInf_, String user_name) {
		super("Delete Disease");
		sqlInf = sqlInf_;

		this.getContentPane().setBackground(colorFactory.getBackgroundColor());
		setBounds(400, 100, 800, 400);

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

	// show disease that user is suffering
	public void showDelDis() {
		// Label for "DiseaseName"
		add(jlDisName = new JLabel("Input Disease ID to Delete", JLabel.CENTER));
		// jlDisName.setBorder(BorderFactory.createBevelBorder(0));
		jlDisName.setBounds(100, 50, 400, 30);

		add(jtDisID);
		jtDisID.setBounds(330, 100, 100, 30);

		add(insertDis);
		insertDis.setBounds(450, 100, 100, 30);

		insertDis.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == insertDis) {
					DelDisID = jtDisID.getText();

					Connection conn = null;
					Statement stmt = null;
					try {
						Class.forName(sqlInf.getDriver());
						conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
						stmt = conn.createStatement();

						String sql;

						sql = "SELECT count(*) AS notexit FROM sufferList WHERE user_id='" + userID + "' AND " + "disease_id='" + DelDisID + "';";

						ResultSet rs = stmt.executeQuery(sql);
						int notExit = -1;

						if (rs.next())
							notExit = rs.getInt("notexit");

						if(notExit == 0) {
							JOptionPane.showMessageDialog(null, "This disease is not registered in list!");
							return ;
						}

						sql = "DELETE FROM sufferList WHERE disease_id=" + DelDisID + " AND user_id=" + userID + ";";

						int result = stmt.executeUpdate(sql);

						if (result != 0) {
							JOptionPane.showMessageDialog(null, "Succeed to Delete!");
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
