import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class MainProgram {
	private BFF bff;
	Map<UUID, Party> partyMap;
	Map<String, Guest> guestMap;
	User currentUser;

	public MainProgram() {
		bff = new BFF();
		partyMap = new HashMap<>();
		guestMap = new HashMap<>();
		readFromText();
		currentUser = null;
	}


	private ArrayList<UUID> parseUUID(String s){
		ArrayList<UUID> result = new ArrayList<>();
		if(s.length()>1){
			String[] arrOfStr = s.split(",");
			for(String str:arrOfStr){
				result.add(UUID.fromString(str));
			}
		}
		return result;
	}

	private ArrayList<String> parseInviteList(String s){
		
		String[] arrOfStr = s.split(",");
		ArrayList<String> result = new ArrayList<>( Arrays.asList(arrOfStr));
		return result;
	}

	private void readFromText(){
		ArrayList<String> guestMapData;
		ArrayList<String> partyMapData;
		try {
			System.out.println("reading from txt");
			guestMapData = FileReader.readFile("guest.txt");
			for(String guestString: guestMapData){
				String[] arrOfStr = guestString.split("/ ");
				Guest newGuest;
				if(arrOfStr.length == 3){
					newGuest = new Guest(arrOfStr[1], arrOfStr[2]);
				}else{
					//System.out.println(arrOfStr[3]);
					newGuest = new Guest(arrOfStr[1], arrOfStr[2], parseUUID(arrOfStr[3]));
				}
				GuestType type = GuestType.valueOf(arrOfStr[0]);
				
				switch(type){
					//Guest
					case GUEST:
						guestMap.put(newGuest.getEmail(), newGuest);
						break;

					//User
					case USER:
						User newUser = new User(newGuest, arrOfStr[4]);
						guestMap.put(newUser.getEmail(), newUser);
						break;
					
					//host
					case HOST:
						Host newHost = new Host(newGuest, arrOfStr[4], parseUUID(arrOfStr[5]));
						guestMap.put(newHost.getEmail(), newHost);
						break;
					//staff
					case STAFF:
						
						Staff newStaff = new Staff(newGuest, arrOfStr[4], Double.parseDouble(arrOfStr[5]), arrOfStr[6]);
						guestMap.put(newStaff.getEmail(), newStaff);
						break;
					default:
						break;
				}
			}
			
			partyMapData = FileReader.readFile("party.txt");
			for(String partyString: partyMapData){
				String[] arrOfStr = partyString.split("/ ");
				final PartyType[] partyMenuVals = PartyType.values();
				String partyName = arrOfStr[1]; 
				String host = arrOfStr[2];
				Date date = new SimpleDateFormat("MM-dd-yyyy").parse(arrOfStr[3]); 
				ArrayList<String> inviteList = parseInviteList(arrOfStr[4]);
				UUID id = UUID.fromString(arrOfStr[5]);
				Party party = null;
				switch(PartyType.valueOf(arrOfStr[0])){
					case BIRTHDAY:
						party = new BirthdayParty( partyName, host, date, inviteList, id, Integer.valueOf(arrOfStr[6]), arrOfStr[7]);
						partyMap.put(party.getId(), party);
						break;
					case WEDDING:
						party = new Wedding( partyName, host, date, inviteList, id, arrOfStr[6], arrOfStr[7], arrOfStr[8]);
						partyMap.put(party.getId(), party);
						break;
					case POTLUCK:
						party = new PotLuck( partyName, host, date, inviteList, id, Cuisine.valueOf(arrOfStr[6]));
						
						break;
				}

				partyMap.put(party.getId(), party);

			}
		// for error-checking purposes
		}catch (Exception e){
		}
	}

	// save guest-input information to txt file

	private void saveToTxt(){
		try {
			PrintWriter  fileWriter = new PrintWriter ("guest.txt"); //overwrites file
			for (Map.Entry<String,Guest> entry : guestMap.entrySet()){
				fileWriter.write(entry.getValue().toString()+"\n");
				fileWriter.flush();
			}
			fileWriter.close();
			PrintWriter  partyWriter = new PrintWriter ("party.txt"); //overwrites file
			for (Map.Entry<UUID,Party> entry : partyMap.entrySet()){
				partyWriter.write(entry.getValue().toString()+"\n");
				partyWriter.flush();
			}
			partyWriter.close();
		} catch (IOException e) {
			//System.out.println("An error occurred.");
			//e.printStackTrace();
		}
	}


	private boolean logIn(){
        String email = bff.inputLine("Please enter the email for logging in: ");
        Guest guest = guestMap.get(email);
        if (guest == null) {
			System.out.println("This email has not been linked to an account.");
			return false;
		} else if(guest.getClass() == Guest.class){
            System.out.println("This email is not registered but is on the invited guest list");
			return false;
        }

        String passString = bff.inputLine("Please enter your password: ");
        User user = (User) guest;
        if(user.checkPassword(passString)){
            currentUser = user;
            System.out.println("You have successfully logged in.");
            return true;
        }
        System.out.println("Wrong password");
        return false;
	}
	//used boolean instead of void to see result of logging out
    private boolean logOut(){
        System.out.println("You have successfully logged out.");
        currentUser = null;
        return true;
    }
    
    //method for staff registration; used boolean instead of void to see result of signing up
    private boolean StaffSignUp(){
        String email = bff.inputLine("Please enter the email that you would like to sign up with: ");
        //check if user already exists as guest
        Guest guest = guestMap.get(email);
        //new guest
        if(guest == null) {
            String name = bff.inputLine("Please enter your name: ");
            String passString = bff.inputLine("Please enter your password: ");
			double hourlySalary = bff.inputDouble("Please enter your hourly salary");
			String desc = bff.inputLine("Please enter your description: ");
            Staff user = new Staff(email, name, passString, hourlySalary,desc);
            guestMap.put(email, user);
            System.out.println("Successfully registered as a staff member.");
			return false;
        }
        else{
            System.out.println("You email is already registered as a user.");
			return true;
        }
    }
    
    //method for guest registration
    private void signUp(){
        String email = bff.inputLine("Please enter the email that you would like to sign up with: ");
        Guest guest = guestMap.get(email);
        if(guest == null) {
            String name = bff.inputLine("Please enter your name: ");
            String passString = bff.inputLine("Please enter your password: ");
            User user = new User(email, name, passString);
            guestMap.put(email, user);
            System.out.println("You have successfully registered as a user.");
        }
        //if email is already in guest invite list but not registered, only ask for password to complete registration
        else if(guest.getClass() == Guest.class){
            System.out.println(guest.getName() +", you have been invited to join.");
            String passString = bff.inputLine("Please enter a password to finish registration: ");
            User user = new User(guest, passString);
            guestMap.replace(user.getEmail(), user);
        }
        else{
            System.out.println("Email already exists as registered user.");
        }
    }

	private boolean checkEmptyUser(){
		return currentUser == null;
	}

	private void printInvitedInfo(){
		if(checkEmptyUser()){
			System.out.println("You have not logged in yet");
			return;
		}
		ArrayList<UUID> invitedAPartyList = currentUser.getInvitedAPartyList();
		for(UUID id: invitedAPartyList){
			System.out.println(partyMap.get(id).basicInfo());
		}

	}

	private void check_attendance(){
		if(checkEmptyUser()){
			System.out.println("You have not logged in yet");
			return;
		}
		if(currentUser.getClass() == Host.class){
			Host host = (Host)currentUser;
			System.out.println("You have host party:");
			ArrayList<UUID> hostList = host.getHostList();
			for(UUID id: hostList){
				System.out.println(partyMap.get(id).getPartyName()+" has "+ host.checkAttendance(id, partyMap)+ " people");
			}

		} else if(currentUser.getClass() == Staff.class){
			Staff staff = (Staff)currentUser;
			ArrayList<UUID> invitedAPartyList = staff.getInvitedAPartyList();
			for(UUID id: invitedAPartyList){
				System.out.println(partyMap.get(id).getPartyName()+" has "+staff.checkAttendance(id, partyMap)+ " people");
			}
		}
		else{
			System.out.println("You don't have access to check attendance(Host and Staff only)");
		}

	}

	private void printHostParty(){
		if(checkEmptyUser()){
			System.out.println("You have not logged in yet");
			return;
		}
		if(currentUser.getClass() != Host.class){
			System.out.println("Please become a host first");
			return;
		}

		Host host = (Host) currentUser;
		System.out.println("\nHere is your host party list:");
		for(UUID id:host.getHostList()){
			Party party = partyMap.get(id);	
			System.out.println(party.hostInfo(guestMap));
		}

	}

	private void inviteGuest(){
		if(checkEmptyUser()){
			System.out.println("You have not logged in yet");
			return;
		}
		if(currentUser.getClass() != Host.class){
			System.out.println("Please become a host first");
			return;
		}

		Host host = (Host) currentUser;
		System.out.println("You have host party:");
		System.out.println(host.hostPartyMenu(partyMap));
		
		int choice = bff.inputInt("Please enter your selection of the party you host", 0, host.hostPartySize()-1);
		Party party = partyMap.get(host.hostList.get(choice));
		String email = bff.inputLine("Please enter the email of the person");
		Guest guest = guestMap.get(email);
		if( guest == null){
			String name = bff.inputLine("Your guest hasn't signup yet, please enter the name of your guest");
			guest = new Guest(email, name);
			guestMap.put(email, guest);
		}
		party.getInviteList().add(guest.getEmail());
		guest.addInvitedAPartyList(party);
		System.out.println("You successfully invite your guest!");
	}

	private void hostParty(){
		if(checkEmptyUser()){
			System.out.println("You have not logged in yet");
			return;
		}
		if(currentUser.getClass() == Staff.class){
			System.out.println("Staff are not allowed to host a party");
			return;
		}

		if(currentUser.getClass() != Host.class){
			currentUser = new Host(currentUser);
			// upgrade user to host
			guestMap.replace(currentUser.getEmail(), currentUser);
		}

		Host host = (Host) currentUser;
		PartyType.printMenuOptions();
		int choice = bff.inputInt("Please enter your selection of the party type", 0, PartyType.getNumOptions()-1);
		final PartyType[] partyTypeVals = PartyType.values();

		Party party = null;
		String partyName = bff.inputLine("Enter the name for the party");
		String hostName = host.getHostNameWithEmail();
		Date date = null;
		while(date == null){
			try{
				date = new SimpleDateFormat("MM-dd-yyyy").parse( bff.inputLine("Enter the date for the party(MM-dd-yyyy)")); 
			} catch (Exception e) {
				System.out.println("Wrong formate");
				date = null;
			}
		}

		switch(partyTypeVals[choice]){
			case BIRTHDAY:
				int age = bff.inputInt("Enter the age for the birthday person");
				String birthdaySong = bff.inputLine("Enter the song for the party");
				party = new BirthdayParty(partyName, hostName, date, age, birthdaySong);
				break;
			case WEDDING:
				String couple1 = bff.inputLine("Enter the name for couple1"); 
				String couple2 = bff.inputLine("Enter the name for couple2"); 
				String witness = bff.inputLine("Enter the name for witness"); 
				party = new Wedding(partyName, hostName, date, couple1, couple2, witness);
				break;
			case POTLUCK:
				Cuisine.printMenuOptions();
			 	int cuiIdx = bff.inputInt("Please select the cuisine for potluck", Cuisine.getNumOptions()-1);
				Cuisine cuisine = Cuisine.getOption(cuiIdx);
				party = new PotLuck(partyName, hostName, date, cuisine);
				break;
		}
		partyMap.put(party.getId(), party);
		host.addToHostList(party);
	}

	private void printStaffInfo(){
		if(checkEmptyUser()){
			System.out.println("You have not logged in yet");
			return;
		}
		if(currentUser.getClass() != Staff.class){
			System.out.println("You are not a staff");
			return;
		}

		Staff staff = (Staff) currentUser;
		for(UUID id: currentUser.getInvitedAPartyList()){
			Party party = partyMap.get(id);	
			String str = "For party "+ party.getPartyName()+ ", your description is "+ staff.getDescription() + ", your hourly pay is "+ staff.getHourlyPay() +"\n";
			System.out.println(str);
		}
	}

	private void addStaff(){
		if(checkEmptyUser()){
			System.out.println("You have not logged in yet");
			return;
		}
		if(currentUser.getClass() != Host.class){
			System.out.println("Error: You are not a host");
			return;
		}

		Host host = (Host) currentUser;

		System.out.println("You have hosted parties:");
		System.out.println(host.hostPartyMenu(partyMap));
		int choice = bff.inputInt("Please enter your selection of the party you host", 0, host.hostPartySize()-1);
		Party party = partyMap.get(host.hostList.get(choice));


		String staffEmail = bff.inputLine("Enter the email of staff");
		Guest guest = guestMap.get(staffEmail);
		if(guest == null){
			System.out.println("Error: can not find the account related to this email");
			return;
		}else if(guest.getClass() != Staff.class){
			System.out.println("Error: It is not a staff account");
			return;
		}else{
			ArrayList<String> inviteList = party.getInviteList();
			inviteList.add(guest.getEmail());
			party.setInviteList(inviteList);
			partyMap.replace(party.getId(), party);
			guest.addInvitedAPartyList(party);
			System.out.println("You have add a staff!");
		}
	}

	public void run(){
		System.out.println("Welcome to the Evite program");
		AccountMenu.printMenuOptions();
		int accountMenuSize = AccountMenu.getNumOptions();
		int choice = bff.inputInt("Please enter your selection", 0, accountMenuSize-1);
		final AccountMenu[] AccountMenuVals = AccountMenu.values();
		while(choice != accountMenuSize-1) {
			switch (AccountMenuVals[choice]) {
			case SIGN_UP:
				signUp();
				saveToTxt();
				break;
			case STAFF_SIGN_UP:
				StaffSignUp();
				saveToTxt();
				break;
			case LOGIN:
				logIn();
				break;
			case LOGOUT:
				logOut();
				break;
			case CHECK_INVITATION:
				printInvitedInfo();
				break;
			case CHECK_ATTENDANCE:
				check_attendance();
				saveToTxt();
				break;
			case CHECK_HOST_PARTY:
				printHostParty();
				break;
			case INVITE_TO_PARTY:
				inviteGuest();
				saveToTxt();
				break;
			case HOST_PARTY:
				hostParty();
				saveToTxt();
				break;
			case ADD_STAFF:
				addStaff();
				saveToTxt();
				break;
			case SEE_STAFF_PARTY:
				printStaffInfo();
				break;
			default:
				break;
			}
			saveToTxt();
			System.out.println("");
			if(choice != accountMenuSize-1){
				AccountMenu.printMenuOptions();
				choice = bff.inputInt("Please enter your selection", 0, accountMenuSize-1);
			}
		}
		System.out.println("Thank you for using Evite program");
	}

	public static void main(String[] args) {
		MainProgram mainProgram = new MainProgram();
		mainProgram.run();
	}
}
