import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

//party class where user can define attributes of the party they host and also see information of invite list 
public abstract class Party{
	private PartyType partyType;
	private String partyName;
	private String host;
	private Date date;
	private ArrayList<String> inviteList; // email of the guest
	private UUID id;


	public Party(PartyType partyType, String partyName, String host, Date date, ArrayList<String> inviteList, UUID id) {
		this.partyType = partyType;
		this.partyName = partyName;
		this.host = host;
		this.date = date;
		this.inviteList = inviteList;
		this.id = id;
	}

	public Party(PartyType partyType, String partyName, String host, Date date) {
		this.partyType = partyType;
		this.partyName = partyName;
		this.host = host;
		this.date = date;
		this.inviteList = new ArrayList<>();
		this.id = UUID.randomUUID();;
	}
	
	public PartyType getPartyType() {
		return this.partyType;
	}

	public void setPartyType(PartyType partyType) {
		this.partyType = partyType;
	}

	public Date getDate() {
		return this.date;
	}
	public String getDateString(){
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");  
        String strDate = dateFormat.format(date);  
		return strDate;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPartyName() {
		return this.partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public ArrayList<String> getInviteList() {
		return inviteList;
	}
	
	public void setInviteList(ArrayList<String> inviteList) {
		this.inviteList = inviteList;
	}
	//party information in basic form
	public String basicInfo(){
		String info = "\nParty Name: ";
		info += partyName;
		info +="\nParty date: ";
		info += getDateString();
		info +="\nParty host: ";
		info+= host;
		return info;
	}

	
	//party info with invite list & staff list
	public String hostInfo(Map<String, Guest> guestMap){
		String info = basicInfo();
		info +="\nParty guest: \n";
		for(String g:inviteList){
			Guest guest = guestMap.get(g);
			if(guest.getClass() != Staff.class){
				info = info + guest.getEmail()+"("+guest.getName()+")\t";
			}
		}

		info +="\nParty staff: \n";
		for(String g:inviteList){
			Guest guest = guestMap.get(g);
			if(guest.getClass() == Staff.class){
				Staff staff = (Staff) guest;
				info = info + staff.getEmail()+"("+staff.getName()+"), description:"+ staff.getDescription()+", hourly salary:"+ staff.getHourlyPay()+"\t";
			}
		}
		return info;
	}
	
	public String toString(){
		String info = partyType.toString() + "/ "+partyName +"/ "+host+"/ "+ getDateString()+"/ ";
		for(String p:inviteList){
			info += p;
			if(p!= inviteList.get(inviteList.size()-1)){
				info += ",";
			}
		}
		info += "/ ";
		info += id.toString();
		return info;
	}
}
