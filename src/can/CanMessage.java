package can;

public class CanMessage {
	
	private int id, dataLow, dataHigh;
	public static final int ALL = 0x6010F;
	public static final int FRONT_RIGHT = 0x60101;
	public static final int FRONT_LEFT = 0x60102;
	public static final int REAR_LEFT = 0x60104;
	public static final int REAR_RIGHT = 0x60108;
	public static final int FRONT = 0x60103;
	public static final int REAR = 0x6010C;
	public static final int LEFT = 0x6010A;
	public static final int RIGHT = 0x60105;
	public static final int CLIGNO_ON = 0xC0;
	public static final int CLIGNO_OFF = 0x40;
	public static final int POS_ON = 0x25;
	public static final int POS_OFF = 0x15;
	public static final int CROIS_ON = 0x08;
	public static final int CROIS_OFF = 0x15;
	public static final int PHARE_ON = 0x02;
	public static final int PHARE_OFF = 0x01;
	public static final int STOP_ON = 0x02;
	public static final int STOP_OFF = 0x01;
	public static final int RECULE_ON = 0x08;
	public static final int RECULE_OFF = 0x04;
	
	public CanMessage(int id, int dataLow, int dataHigh) {
		this.id = id;
		this.dataLow = dataLow;
		this.dataHigh = dataHigh;
	}
	
	public CanMessage(byte bytes[]) {
		this.id = (bytes[0]<<24) | (bytes[1]<<16) | (bytes[2]<<8) | bytes[3];
		this.dataLow = (0x0FF & bytes[4]);
		this.dataHigh = (0x0FF & bytes[5]);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setDataLow(int dataLow) {
		this.dataLow = dataLow;
	}
	
	public int getDataLow() {
		return this.dataLow;
	}
	
	public void setDataHight(int dataHigh) {
		this.dataHigh = dataHigh;
	}
	
	public int getDataHigh() {
		return this.dataHigh;
	}
	
	public boolean isUseless() {
		if ((this.getDataHigh() == 0) && (this.getDataHigh() == 0))
			return false;
		return true;
	}
	
	public byte[] getBytes() {
		byte bytes[] = new byte[6];
		bytes[0] = (byte) (this.id>>24);
		bytes[1] = (byte) (this.id>>16);
		bytes[2] = (byte) (this.id>>8);
		bytes[3] = (byte) (this.id);
		bytes[4] = (byte) this.dataLow;
		bytes[5] = (byte) this.dataHigh;
		return bytes;
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("CanMessage");
		
		string.append(" 0x");
		string.append(Integer.toHexString(this.getId()));
		
		string.append(" 0b");
		string.append(Integer.toBinaryString(this.getDataLow()));
		
		string.append(" 0b");
		string.append(Integer.toBinaryString(this.getDataHigh()));
		
		return string.toString();
	}
	
}
