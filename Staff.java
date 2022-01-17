import java.util.Map;
import java.util.UUID;

//Staff class that contains attributes of pay and description.

public class Staff extends User implements PartyAction {
	private double hourlyPay; 
	private String description;

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getHourlyPay() {
		return hourlyPay;
	}

	public void setHourlyPay(double hourlyPay) {
		this.hourlyPay = hourlyPay;
	}

	public Staff(String email, String name, String passwordString, double hourlyPay, String desString) {
		super(email, name, passwordString);
		this.type = GuestType.STAFF;
		this.hourlyPay =hourlyPay;
		description = desString;
	}

	public Staff(Guest guest, String passwordString, double hourlyPay, String desString) {
		super(guest, passwordString);
		this.type = GuestType.STAFF;
		this.hourlyPay = hourlyPay;
		this.description = desString;
	}

	// return the number of registered user
	public int checkAttendance(UUID id, Map<UUID, Party> partyMap){
		return partyMap.get(id).getInviteList().size();
	}

	public String toString(){
		return super.toString()+"/ "+ hourlyPay+"/ "+description;
	}
}
