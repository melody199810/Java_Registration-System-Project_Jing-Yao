//Enum that stores menu options to display to users when they use the system.
//contains methods to get enum content

public enum AccountMenu {
    SIGN_UP("Sign up for a user account (non-staff)"),
    STAFF_SIGN_UP("Sign up for a staff account"),
	LOGIN("Log in to an existing account"),
	LOGOUT("Log out"),
	CHECK_INVITATION("Print the parties that you have been invited to"),
	CHECK_ATTENDANCE("Check the attendance of a party"),
	HOST_PARTY("Host a party"),
	CHECK_HOST_PARTY("(Host Only) See the list of parties that you host"),
	INVITE_TO_PARTY("(Host Only) Invite guests to your selected party"),
	ADD_STAFF("(Host Only) Add staff to your party"),
	SEE_STAFF_PARTY("(Staff Only) See the staff information to this party"),
	QUIT("Quit program");
	
	private String description;
	private AccountMenu(String description){
		this.description = description;
	}
	
	public String getDisplayString(){
		return this.description;
	}
	public static int getNumOptions() {
		return AccountMenu.values().length;
	}
	
	public static AccountMenu getOption(int num) {
		return AccountMenu.values()[num];
	}
	public static String getMenuOptions() {
		String prompt = "*****\tMenu\t*****";

		for(AccountMenu m : AccountMenu.values()){ //array from the enum
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
