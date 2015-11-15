
public enum StatBoostOperationEnum {
	ADD, RESET;
	public String toString() {
		String ret = "";
		
		switch (this) {
		case ADD:
			 ret = "add";
			break;
		case RESET:
			ret = "reset";
			break;
		default:
			break;
		
		}
		
		return ret;
	}
}
