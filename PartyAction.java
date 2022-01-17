import java.util.Map;
import java.util.UUID;

// return the number of registered users
public interface PartyAction {
	public int checkAttendance(UUID id, Map<UUID, Party> partyMap);
}
