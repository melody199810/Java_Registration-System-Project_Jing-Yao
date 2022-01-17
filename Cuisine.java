//enum containing different types of cuisine for party hosts to choose from
//contains a display cuisine menu option
public enum Cuisine {
	MEDITERRANEAN("Mediterranean"),
	ITALIAN("Italian"), 
	FRENCH("French"), 
	CHINESE("Chinese"), 
	KOREAN("Korean"), 
	THAI("Thai");

	private String description;
	private Cuisine(String description){
		this.description = description;
	}
	
	public String getDisplayString(){
		return this.description;
	}

	public static int getNumOptions() {
		return Cuisine.values().length;
	}

	public static Cuisine getOption(int num) {
		return Cuisine.values()[num];
	}

	public static String getMenuOptions() {
		String prompt = "";

		for(Cuisine m : Cuisine.values()){ //array from the enum
			prompt += "\n" + (m.ordinal()) + ": " + m.getDisplayString();
		}
		prompt += "\n**********************************************\n";
		return prompt;
	}
	
	public static void printMenuOptions() {
		String prompt = getMenuOptions();
		System.out.println(prompt);
	}
	
}
