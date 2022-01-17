import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class PotLuck extends Party {
	private Cuisine cuisine;

	public Cuisine getCuisine() {
		return this.cuisine;
	}

	public void setCuisine(Cuisine cuisine) {
		this.cuisine = cuisine;
	}
	public PotLuck(String partyName, String host, Date date, Cuisine cuisine) {
		super(PartyType.POTLUCK, partyName, host, date);
		this.cuisine = cuisine;
	}

	public PotLuck(String partyName, String host, Date date, ArrayList<String> inviteList, UUID id, Cuisine cuisine) {
		super(PartyType.WEDDING, partyName, host, date, inviteList, id);
		this.cuisine = cuisine;
	}

	public String toString(){
		String info = super.toString();
		info += "/ " + cuisine;
		return info;
	}
	
}
