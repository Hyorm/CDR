import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class OrderPage extends JFrame {

	private ButtonGroup bgRadioGroup;
	private ColorFactory colorFactory = new ColorFactory();
	public static String user_id;
	private String drink_name;
	private String option_id = "1";
	private int drinkIdx = 0;
	public static String orderDrink_id;
	private String[] drink_id;
	private String[] temSize;
	private static String orderTempSize;
	private String optionList = "";
	private String ingList = "";
	private int optionNum = 0;
	private ArrayList<String[]> queryRe;
	private SqlHolder sqlInf;
	private static int price = 0;
	private static OptionSelection optionConstructor;
	private JLabel jlWarnning = new JLabel();
	private boolean warnningFlag = false;

	public OrderPage(String user_id, SqlHolder sqlInf, String drink_name, int flag) {
		super("OrderPage");
		this.sqlInf = sqlInf;
		this.user_id = user_id;
		//this.user_id = "" + 2;
		this.drink_name = drink_name;

		setPage();
		if (flag == 0)
			setOrderPage();
		if (flag == 1)
			setOrderInfoPage();
		this.setSize(500, 900);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void setPage() {

		this.getContentPane().setBackground(colorFactory.getBackgroundColor());
		this.setLayout(new GridLayout(15, 1));

	}

	public void setOrderPage() {

		JLabel whatDrinkNameLab = new JLabel("Drink Name: ");
		whatDrinkNameLab.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel drinkNameLab = new JLabel(this.drink_name);
		drinkNameLab.setHorizontalAlignment(SwingConstants.CENTER);

		String queryStr = "SELECT drink_id, price, temparature, size FROM drink Where drink_name=\"" + drink_name
				+ "\";";
		int queryNum = 4;
		String[] queryList = { "drink_id", "price", "temparature", "size" };
		query(queryStr, queryNum, queryList);
		this.drinkIdx = queryRe.size();
		this.drink_id = new String[drinkIdx];
		this.temSize = new String[drinkIdx];
		for (int i = 0; i < queryRe.size(); i++) {
			this.drink_id[i] = queryRe.get(i)[0];
			this.temSize[i] = queryRe.get(i)[2] + "/" + queryRe.get(i)[3];
		}

		JLabel whatTSLab = new JLabel("Temparature/Size: ");
		whatTSLab.setHorizontalAlignment(SwingConstants.CENTER);
		JRadioButton[] tsBtn = new JRadioButton[drinkIdx];

		bgRadioGroup = new ButtonGroup();

		for (int i = 0; i < drinkIdx; i++) {
			tsBtn[i] = new JRadioButton(temSize[i]);
			bgRadioGroup.add(tsBtn[i]);
			tsBtn[i].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					for (int i = 0; i < drinkIdx; i++)
						if (e.getSource() == tsBtn[i]) {
							orderTempSize = temSize[i];
							orderDrink_id = drink_id[i];
							price = Integer.parseInt(queryRe.get(i)[1]);
						}
				}
			});
		}

		JButton whatOptionBtn = new JButton("Option Select");
		whatOptionBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// get option_id
				optionConstructor = new OptionSelection(sqlInf);
			}
		});

		JLabel nilLab = new JLabel("");
		JButton orderBtn = new JButton("Order");
		orderBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (orderTempSize != null) {
					new OrderPage(user_id, sqlInf, drink_name, 1);
					dispose();
				} else
					JOptionPane.showMessageDialog(null, "Choose Temperature/Size", "Choose Temperature/Size",
							JOptionPane.WARNING_MESSAGE);
			}
		});

		JCheckBox considerDiseaseCB = new JCheckBox("  Do you want to consider disease?");
		considerDiseaseCB.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// consider disease
				considerDisease();
			}
		});
		/* ------------ bokyoung ----------- */
		Font myFont1 = new Font("arial", Font.BOLD, 20);
		Font myFont2 = new Font("arial", Font.BOLD, 30);
		JPanel jpDrink = new JPanel(new GridLayout(1, 1));
		jpDrink.setBackground(colorFactory.getBackgroundColor());
		jpDrink.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		whatDrinkNameLab.setFont(myFont2);
		whatDrinkNameLab.setText(whatDrinkNameLab.getText() + "'" + drinkNameLab.getText() + "'");
		jpDrink.add(whatDrinkNameLab);

		JPanel jpTS = new JPanel(new GridLayout(1, 3, 5, 5)); // size and temperature
		jpTS.setBackground(colorFactory.getBackgroundColor());
		jpTS.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		whatTSLab.setFont(myFont1);
		jpTS.add(whatTSLab);

		for (int i = 0; i < drinkIdx; i++) {
			tsBtn[i].setHorizontalAlignment(SwingConstants.CENTER);
			jpTS.add(tsBtn[i]);
		}

		JPanel jpOption = new JPanel(new GridLayout(1, 1));
		jpOption.setBackground(colorFactory.getBackgroundColor());
		jpOption.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		jpOption.add(whatOptionBtn);

		JPanel jpOrder = new JPanel(new GridLayout(1, 1));
		jpOrder.setBackground(colorFactory.getBackgroundColor());
		jpOrder.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		jpOrder.add(orderBtn);

		JPanel jpWarnningCheck = new JPanel(new GridLayout(1, 2, 10, 10)); // size and temperature
		jpWarnningCheck.setBackground(colorFactory.getBackgroundColor());
		jpWarnningCheck.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		considerDiseaseCB.setFont(myFont1);
		jpWarnningCheck.add(considerDiseaseCB);
		jlWarnning.setFont(myFont1);
		jlWarnning.setText("     " + jlWarnning.getText());
		jpWarnningCheck.add(jlWarnning);

		this.setLayout(new GridLayout(5, 1, 10, 10));
		this.add(jpDrink);
		this.add(jpTS);
		this.add(jpWarnningCheck);
		this.add(jpOption);
		this.add(jpOrder);

		/* ---------------------end---------------------- */


	}

	public void setOrderInfoPage() {

		option_id = Integer.toString(optionConstructor.chooseID);

		String queryStr = "SELECT IF(\"null\" IN (option1), 0, option1) as option1, IF(\"null\" IN (option2), 0, option2) as option2,IF(\"null\" IN (option3), 0, option3) as option3 From chooseList where choose_id="
				+ option_id + ";";
		int queryNum = 3;

		String[] queryList = { "option1", "option2", "option3" };
		query(queryStr, queryNum, queryList);
		for (int i = 0; i < 3; i++) {
			if (queryRe.get(0)[i].equals("0") == false) {
				this.optionList += ", ";
				this.optionList += queryRe.get(0)[i];
			}
		}
		queryStr = "SELECT ingredient_name FROM recipe Where drink_id=" + orderDrink_id + ";";
		queryNum = 1;

		String[] queryList2 = { "ingredient_name" };
		query(queryStr, queryNum, queryList2);
		for (int i = 0; i < queryRe.size(); i++) {
			this.ingList += queryRe.get(i)[0] + "  ";
		}

		JLabel whatDrinkNameLab = new JLabel("Order Drink: ");
		whatDrinkNameLab.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel drinkNameLab = new JLabel(this.drink_name + " - " + this.orderTempSize + this.optionList);
		drinkNameLab.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel whatIngLab = new JLabel("Ingredient List: ");
		whatIngLab.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel ingLab = new JLabel(this.ingList);
		ingLab.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel whatPriceLab = new JLabel("Price: ");
		whatPriceLab.setHorizontalAlignment(SwingConstants.CENTER);
		queryStr = "SELECT sum(IF(\"null\" IN (option1), 0, 1))+sum(IF(\"null\" IN (option2), 0, 1))+sum(IF(\"null\" IN (option3), 0, 1)) as sum FROM chooseList WHERE choose_id="
				+ option_id + ";";
		queryNum = 1;
		String[] queryList3 = { "sum" };
		query(queryStr, queryNum, queryList3);
		for (int i = 0; i < queryRe.size(); i++)
			this.optionNum = Integer.parseInt(queryRe.get(i)[0]);
		price += 500 * this.optionNum;
		JLabel priceLab = new JLabel(Integer.toString(price));
		priceLab.setHorizontalAlignment(SwingConstants.CENTER);

		JButton accountBtn = new JButton("Account");
		accountBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				Statement stmt = null;
				try {
					Class.forName(sqlInf.getDriver());
					conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
					stmt = conn.createStatement();

					// Find is arleady there? Ordered List
					String sql;
					sql = "SELECT count FROM orderedList Where user_id=\"" + user_id + "\" and drink_id=" + orderDrink_id
							+ " and choose_id=" + option_id + ";";
					int queryNum = 1;
					String[] queryList4 = { "count" };
					query(sql, queryNum, queryList4);

					System.out.println(sql);
					String ordered_count;
					if (queryRe.size()==0) {
						sql = "INSERT INTO orderedList VALUES(\"" + user_id + "\", " + orderDrink_id + ", " + option_id
								+ ", 1);";
					} else {
						ordered_count = queryRe.get(0)[0];
						System.out.println(ordered_count);
						int ordered_count_int = Integer.parseInt(ordered_count) + 1;
						System.out.println(ordered_count_int);
						sql = "UPDATE orderedList SET count=" + ordered_count_int + " WHERE user_id=\"" + user_id
								+ "\" and drink_id=" + orderDrink_id + " and choose_id=" + option_id + ";";
					}
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

				JOptionPane.showMessageDialog(null, "Success", "Order Success", JOptionPane.PLAIN_MESSAGE);
				dispose();
			}
		});
		this.add(whatDrinkNameLab);
		this.add(drinkNameLab);
		this.add(whatIngLab);
		this.add(ingLab);
		this.add(whatPriceLab);
		this.add(priceLab);
		this.add(accountBtn);
	}

	public void query(String sql, int num, String[] sqlList) {

		Connection conn = null;
		Statement stmt = null;
		this.queryRe = new ArrayList<String[]>();
		System.out.println(sql);

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
				this.queryRe.add(newList);
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

	public void considerDisease() {
		Connection conn = null;
		Statement stmt = null;
		String sql = "";

		try {
			Class.forName(sqlInf.getDriver());
			conn = DriverManager.getConnection(sqlInf.getUrl(), sqlInf.getName(), sqlInf.getPWD());
			stmt = conn.createStatement();
			ResultSet rs;

			/* ------------------------------ */
			ArrayList<String> diseaseIDList = new ArrayList<String>();
			ArrayList<String> avoidIngredientList = new ArrayList<String>();

			// String userID = "1"; // TODO 실제 로그인한 userID로 바꾸기.
			String userID = OrderPage.user_id;
			sql = "create or replace view myDisease as select disease_id from sufferList where user_id=\"" + userID+"\""; // create
			System.out.println(sql);																									// view
			stmt.executeUpdate(sql);

			sql = "select disease_id from myDisease";
			rs = stmt.executeQuery(sql);
			System.out.println(sql); 

			while (rs.next()) {
				if (!diseaseIDList.contains(rs.getString("disease_id")))
					diseaseIDList.add(rs.getString("disease_id"));
			}
			for (int i = 0; i < diseaseIDList.size(); i++) {
				System.out.println("diseaseIDList " + diseaseIDList.get(i));
			}

			for (int i = 0; i < diseaseIDList.size(); i++) {
				sql = "select Ingredient_name from causeList where disease_id=" + diseaseIDList.get(i);
				rs = stmt.executeQuery(sql);
				System.out.println(sql); 
				while (rs.next()) {
					if (!avoidIngredientList.contains(rs.getString("ingredient_name")))
						avoidIngredientList.add(rs.getString("ingredient_name"));
				}
			}

			for (int i = 0; i < avoidIngredientList.size(); i++) {
				System.out.println("avoidIngredientList " + avoidIngredientList.get(i));
			}
			/* ------------------------------ */
			
			sql = "create or replace view ingredientListView as select ingredient_name "
					+ "from recipe where drink_id="+orderDrink_id; //TODO drink_id 가져오기.
			System.out.println(sql);
			stmt.executeUpdate(sql);
			
			warnningFlag = false;
			ArrayList<String> warnningList = new ArrayList<String>();
			for(int i=0; i<avoidIngredientList.size(); i++) {
				sql = "select ingredient_name from ingredientListView where ingredient_name='"+avoidIngredientList.get(i)+"'";
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					if(!warnningList.contains(rs.getString("ingredient_name"))) {
						warnningList.add(rs.getString("ingredient_name"));
						warnningFlag = true;
					}
						
				}
			}
			
			//if drink contains ingredient that is dangerous for user
			if(warnningFlag) {
				jlWarnning.setText("warnning!");
			} else {
				jlWarnning.setText("safe");
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
