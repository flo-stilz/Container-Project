package model;

public interface JourneyContainerPersistency {

	public void storeActiveJourneys();
	public void readActiveJourneyFile();
	public void storeEndedJourneys();
	public void readEndedJourneyFile();
	public void storeContainerWarehouse();
	public void readContainerWarehouseFile();
}
