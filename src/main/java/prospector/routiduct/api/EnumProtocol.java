package prospector.routiduct.api;

/**
 * Created by Prospector
 */
public enum EnumProtocol {
	RD1("RD1", 4, 4, 2, 50, 10), RD2("RD2", 16, 12, 1, 20, 4);

	public String name;
	public int stacks;
	public int ports;
	public int ticksPerPipe;
	public int ticksToPackage;
	public int ticksToRead;

	EnumProtocol(String prefix, int stacks, int ports, int ticksPerPipe, int ticksToPackage, int ticksToRead) {
		this.name = prefix;
		this.stacks = stacks;
		this.ports = ports;
		this.ticksPerPipe = ticksPerPipe;
		this.ticksToPackage = ticksToPackage;
		this.ticksToRead = ticksToRead;
	}
}
