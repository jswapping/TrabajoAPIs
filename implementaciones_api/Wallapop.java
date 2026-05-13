package implementaciones_api;

public class Wallapop {
	
	private static final String MAIN_URL = "https://api.wallapop.com";
	
	private static final String SEARCH_ENDPOINT = "/api/v3/search";
	
	private static int instancias = 0;
	
	public Wallapop() {
		Wallapop.instancias++;
	}
	
	public String searchProduct(String product) {
		return "";
	}
	
	public int getInstancias() {
		return Wallapop.instancias;
	}
	
}
