package pt.iul.poo.firefight.starterpack;

import java.util.List;

import pt.iul.ista.poo.utils.Point2D;

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
	
	public void trySpreadFire();
}