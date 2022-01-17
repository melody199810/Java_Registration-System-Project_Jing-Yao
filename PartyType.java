
public enum PartyType {
	BIRTHDAY("Birthday"),
	WEDDING("Wedding"), 
	POTLUCK("Potluck");


	private String description;
	private PartyType(String description){
		this.description = description;
	}
	
	public String getDisplayString(){
		return this.description;
	}

	public static int getNumOptions() {
		return PartyType.values().length;
	}

	public static PartyType getOption(int num) {
		return PartyType.values()[num];
	}

	public static String getMenuOptions() {
		String prompt = "";

		for(PartyType m : PartyType.values()){ //array from the enum
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
