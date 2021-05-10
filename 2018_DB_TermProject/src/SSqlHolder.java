public class SSqlHolder {
	
	
	private static String sqlDomain = "203.252.121.222:3306/CDR?serverTimezone=UTC";
	private static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String DB_URL = "jdbc:mysql://" + sqlDomain;

	private static String USERNAME = "root";
	private static String PASSWORD = "dbzara";
	
	
	private static SSqlHolder theInstance = new SSqlHolder();

	SSqlHolder(){
		
	}
	
	public static SSqlHolder getInstance() {
			
		return theInstance;
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