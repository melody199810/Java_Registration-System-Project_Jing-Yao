import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


//Birthday class, a type of party.
//host can set age of birthday person, and set birthdaySong for party.
public class BirthdayParty extends Party {
	private int age;
	private String birthdaySong;	
	
	/**
	 * @return the age of the birthday person
	 */
	public int getAge() {
		return age;
	}


	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}


	/**
	 * @return the birthdaySong
	 */
	public String getBirthdaySong() {
		return birthdaySong;
	}


	/**
	 * @param birthdaySong the birthdaySong to set
	 */
	public void setBirthdaySong(String birthdaySong) {
		this.birthdaySong = birthdaySong;
	}
	

	public BirthdayParty(String partyName, String host, Date date, int age, String birthdaySong) {
		super(PartyType.BIRTHDAY, partyName, host, date);
		this.age = age;
		this.birthdaySong = birthdaySong;
	}

	public BirthdayParty(String partyName, String host, Date date, ArrayList<String> inviteList, UUID id, int age, String birthdaySong) {
		super(PartyType.BIRTHDAY, partyName, host, date, inviteList, id);
		this.age = age;
		this.birthdaySong = birthdaySong;
	}
	

	public String basicInfo(){
		String info = super.basicInfo();
		info += "\nBirthday age: ";
		info += age;
		info += "\nBirthday song: ";
		info += birthdaySong;
		return info;
	}

	public String toString(){
		String info = super.toString();
		info += "/ "+ age+"/ "+ birthdaySong;
		return info;
	}

}
