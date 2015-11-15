
public enum Condition {
	LOWHP, HIGHHP, CHANCE;
	public String toString() {
		String ret = "";
		
		switch (this) {
		case CHANCE:
			ret = "at a % rate";
			break;
		case HIGHHP:
			ret = "if above 50% HP";
			break;
		case LOWHP:
			ret = "if below 50% HP";
			break;
		default:
			break;
		
		}
		
		return ret;
	}
}
