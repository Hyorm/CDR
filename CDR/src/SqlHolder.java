
public class SqlHolder {
	private String JDBC_DRIVER;
	private String DB_URL;

	private String USERNAME = "root";
	private String PASSWORD = "dbzara";

	public SqlHolder(String _driver, String _url, String usr_name, String passwd) {
		this.JDBC_DRIVER = _driver;
		this.DB_URL = _url;
		this.USERNAME = usr_name;
		this.PASSWORD = passwd;
	}

	public String getDriver() {
		return JDBC_DRIVER;
	}

	public String getUrl() {
		return DB_URL;
	}

	public String getName() {
		return USERNAME;
	}

	public String getPWD() {
		return PASSWORD;
	}
}
