import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddDis extends JFrame {

	private static final long serialVersionUID = -3935288679396305458L;
	private static ColorFactory colorFactory = new ColorFactory();

	SqlHolder sqlInf;

	JTextField jtDisID = new JTextField(10);
	JLabel jlDisID, jlDisName, jlInptDis;
	JLabel valDisID, valDisName;
	JButton insertDis = new JButton("INSERT");

	String AddDisID; // ID which will be inserted in suffered list
	int user_id = -1; // User_id for inserting to suffered list

	public AddDis(JFrame pre_frame, SqlHolder sqlInf_, String user_name) {
		super("Insert Disease");
		sqlInf = sqlInf_;

		this.getContentPane().setBackground(colorFactory.getBackgroundColor());
		setBounds(250, 100, 1000, 600);

		this.setLayout(null);

		// Label for "DiseaseID"
		add(jlDisID = new JLabel("Disease_ID", JLabel.CENTER));
		jlDisID.setBorder(BorderFactory.createBevelBorder(0));
		jlDisID.setBounds(50, 10, 150, 50);
		// Label for "DiseaseName"
		add(jlDisName = new JLabel("Disease_Name", JLabel.CENTER));
		jlDisName.setBorder(BorderFactory.createBevelBorder(0));
		jlDisName.setBounds(200, 10, 300, 50);


		setVisible(true);
	}

	public void showAllDis(String user_name) {
		ArrayList<String> AllDis = new ArrayList<String>();

		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(sqlInf.getDriver());
			conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
			stmt = conn.createStatement();

			String sql;
			sql = "SELECT * FROM diseasetable;";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String DisID = rs.getString("disease_id");
				String DisName = rs.getString("disease_name");

				AllDis.add(DisID);
				AllDis.add(DisName);
			}

			sql = "SELECT user_id FROM userList WHERE user_name='" + user_name + "';";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				user_id = rs.getInt("user_id");
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException sel) {
			sel.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		for (int i = 0; i < AllDis.size(); i += 2) {
			// Value for "Disease ID"
			add(valDisID = new JLabel(AllDis.get(i), JLabel.CENTER));
			valDisID.setBounds(50, 70 + 15 * (i), 150, 30);
			// Value for "Disease Name"
			add(valDisName = new JLabel(AllDis.get(i + 1), JLabel.CENTER));
			valDisName.setBounds(200, 70 + 15 * (i), 300, 30);
		}

		// Label for "DiseaseName"
		add(jlDisName = new JLabel("Input Disease ID", JLabel.CENTER));
		jlDisName.setBounds(500, 400, 300, 30);

		add(jtDisID);
		jtDisID.setBounds(750, 400, 100, 30);

		add(insertDis);
		insertDis.setBounds(850, 400, 100, 30);

		insertDis.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == insertDis) {
					AddDisID = jtDisID.getText();

					Connection conn = null;
					Statement stmt = null;
					try {
						Class.forName(sqlInf.getDriver());
						conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
						stmt = conn.createStatement();

						String sql;

						sql = "SELECT count(*) AS isexit FROM sufferList WHERE user_id='" + user_id + "' AND " + "disease_id='" + AddDisID + "';";

						ResultSet rs = stmt.executeQuery(sql);
						int isExit = -1;

						if (rs.next())
							isExit = rs.getInt("isexit");

						if(isExit > 0) {
							JOptionPane.showMessageDialog(null, "This disease already registered in list!");
							return ;
						}
							

						sql = "INSERT INTO sufferList(user_id, disease_id) VALUES(" + user_id + ", '" + AddDisID
								+ "');";

						int result = stmt.executeUpdate(sql);

						if (result != 0) {
							JOptionPane.showMessageDialog(null, "Succeed to Insert!");
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
