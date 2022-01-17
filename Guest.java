import java.util.ArrayList;
import java.util.UUID;

//guest class. Allows logged-in user to see list of parties they have been invited to.
//contains toString method that lists out information of the guest


public class Guest {
	protected GuestType type;
	private String email;
	private String name;
	protected ArrayList<UUID> invitedAPartyList;

	public ArrayList<UUID> getInvitedAPartyList() {
		return this.invitedAPartyList;
	}

	public Boolean addInvitedAPartyList(Party p) {
		try {
			invitedAPartyList.add(p.getId());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Guest(String email, String name) {
		this.type = GuestType.GUEST;
		this.email = email;
		this.name = name;
		invitedAPartyList = new ArrayList<>();
	}

	public Guest(String email, String name, ArrayList<UUID> invitedAPartyList) {
		this.type = GuestType.GUEST;
		this.email = email;
		this.name = name;
		this.invitedAPartyList = invitedAPartyList;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String toString(){
		String partylist ="";
		for(UUID p:invitedAPartyList){
			partylist+=p.toString();
			if(p!= invitedAPartyList.get(invitedAPartyList.size()-1)){
				partylist += ",";
			}
		}
		if(partylist.equals("")) {
			partylist= " ";
		}
		return type+ "/ "+ email+"/ "+name+"/ "+partylist;
	}

}
