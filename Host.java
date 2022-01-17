import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class Host extends User implements PartyAction{
	protected ArrayList<UUID> hostList;

	public ArrayList<UUID> getHostList() {
		return this.hostList;
	}

	public void addToHostList(Party p) {
		hostList.add(p.getId());
	}

	public void setHostList(ArrayList<UUID> hostList) {
		this.hostList = hostList;
	}
	
	public Host(Guest guest, String passwordString) {
		super(guest, passwordString);
		this.type = GuestType.HOST;
		hostList = new ArrayList<>();
	}

	public Host(Guest guest, String passwordString, ArrayList<UUID> hostList) {
		super(guest, passwordString);
		this.type = GuestType.HOST;
		this.hostList = hostList;
	}

	public Host(User guest) {
		super(guest, guest.getPassword());
		this.type = GuestType.HOST;
		hostList = new ArrayList<>();
	}

	public String getHostNameWithEmail(){
		return getName()+"("+getEmail()+")";
	}

	public String hostPartyMenu(Map<UUID, Party> partyMap){
		String str = "";
		int count =0;
		for(UUID id:hostList){
			Party party = partyMap.get(id);	
			str += count;
			str += ": ";
			str += party.getPartyName();
			str += "\n";
			count ++;
		}
		return str;
	}
	public int hostPartySize(){
		return hostList.size();
	}

	
	public int checkAttendance(UUID id, Map<UUID, Party> partyMap){
		return partyMap.get(id).getInviteList().size();
	}

	public String toString(){

		String hostStr ="";
		for(UUID p:hostList){
			hostStr+=p.toString();
			if(p!= hostList.get(hostList.size()-1)){
				hostStr += ",";
			}
		}

		return super.toString()+"/ "+ hostStr;
	}

}
