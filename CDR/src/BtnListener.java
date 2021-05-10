import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtnListener implements ActionListener {

	private MyPage p;
	private SqlHolder sqlInf;
	private String user_name;

	public BtnListener(MyPage _p, SqlHolder _sqlInf, String _user_name) {
		this.p = _p;
		this.sqlInf = _sqlInf;
		this.user_name = _user_name;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == p.buttonInfo) // Show user's info
		{
			UserInfo usrinf = new UserInfo(p, sqlInf, user_name);
			usrinf.showUserInfo();
		} else if (e.getSource() == p.buttonPckList) // Show picked list
		{
			PickList pckLst = new PickList(p, sqlInf, user_name);
			//pckLst.showPckList();
		} else if (e.getSource() == p.buttonOrdList) // Show ordered list
		{
			OrderList ordLst = new OrderList(sqlInf, user_name);
			ordLst.setUserID(user_name);
			ordLst.setTPles();
		} else if (e.getSource() == p.buttonAddDis) // Add disease to user's disease list
		{
			AddDis addDis = new AddDis(p, sqlInf, user_name);
			addDis.showAllDis(user_name);
		} else if (e.getSource() == p.buttonChekDis) // Show disease that user suffer
		{
			ChkDis chkDis = new ChkDis(sqlInf, user_name);
			chkDis.setUserID(user_name);
			chkDis.getDisName();
			chkDis.showDis();
		} else if (e.getSource() == p.buttonDelDis) // Delete disease from user's disease list
		{
			DelDis delDis = new DelDis(sqlInf, user_name);
			delDis.setUserID(user_name);
			delDis.showDelDis();
		} else if (e.getSource() == p.buttonChPW) // Change Passwd from userList
		{
			ChPwd chPwd = new ChPwd(sqlInf, user_name);
			chPwd.setUserID(user_name);
			chPwd.showChPwd();
		}
	}
}
