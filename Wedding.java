import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

//wedding class where host users can set both of the couple getting married and name of the witness

public class Wedding extends Party {
	private String couple1;
	private String couple2;
	private String witness;

	public String getCouple1() {
		return this.couple1;
	}

	public void setCouple1(String couple1) {
		this.couple1 = couple1;
	}

	public String getCouple2() {
		return this.couple2;
	}

	public void setCouple2(String couple2) {
		this.couple2 = couple2;
	}

	public String getWitness() {
		return this.witness;
	}

	public void setWitness(String witness) {
		this.witness = witness;
	}


	public Wedding(String partyName, String host, Date date, String couple1, String couple2, String witness) {
		super(PartyType.WEDDING, partyName, host, date);
		this.couple1 = couple1;
		this.couple2 = couple2;
		this.witness = witness;
	}

	public Wedding(String partyName, String host, Date date, ArrayList<String> inviteList, UUID id, String couple1, String couple2, String witness) {
		super(PartyType.WEDDING, partyName, host, date, inviteList, id);
		this.couple1 = couple1;
		this.couple2 = couple2;
		this.witness = witness;
	}
	
	public String toString(){
		String info = super.toString();
		info += "/ " + couple1 +"/ " + couple2 +"/ "+witness;
		return info;
	}

}
