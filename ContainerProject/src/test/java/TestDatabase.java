import static org.junit.Assert.*;

import org.junit.Test;

public class TestDatabase {
	
	@Test
	public void test() {
		Database db = new Database();
		db.createJourney("cph","par", "icecream", "bilka");
		db.storeActiveJourneys(db.getJourney());
		
		
	}

}
