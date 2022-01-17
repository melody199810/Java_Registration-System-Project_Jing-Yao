
public class User extends Guest{
	private String password;

	public User(String email, String name, String passwordString) {
		super(email, name);
		this.type = GuestType.USER;
		setPassword(passwordString);
	}

	public User(Guest guest, String passwordString) {
		super(guest.getEmail(), guest.getName(),guest.invitedAPartyList);
		this.type = GuestType.USER;
		setPassword(passwordString);
		this.invitedAPartyList = guest.invitedAPartyList;
	}

	public boolean checkPassword(String password){
		if(this.password.equals(password)){
			return true;
		}
		return false;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString(){
		return super.toString()+"/ "+ password;
	}
}
