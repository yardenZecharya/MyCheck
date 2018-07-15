package main.java.myCheck;

public class Item {
	
	private final long itemID;
    private final String value;
	
    
    public Item(long itemID, String value) {
        this.itemID = itemID;
        this.value = value;
    }

			
	public long getItemID() {
		return itemID;
	}

	public String getValue() {
		return value;
	}
	
	
	
	
}
