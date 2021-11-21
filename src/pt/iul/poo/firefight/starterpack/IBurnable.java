package pt.iul.poo.firefight.starterpack;

public interface IBurnable {
	public int getBurnTime();
	public void setBurnTime(int burnTime);
	
	public default void burn() {
		if (!isBurnt()) {
			setBurnTime(getBurnTime() - 1);
		}
	}
	
	public default boolean isBurnt() {
		return getBurnTime() == 0;
	}	
}