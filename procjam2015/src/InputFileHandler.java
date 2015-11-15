import org.json.JSONArray;
import org.json.JSONObject;

public class InputFileHandler {
	private JSONObject m_data = null;
	public InputFileHandler() {
		
	}
	public InputFileHandler setData(JSONObject p_data) {
		m_data = p_data;
		return this;
	}
	public String getRandomState() {
		JSONArray arr = m_data.getJSONArray("states");
		if (arr != null) {
			int index = (int) (Math.random() * arr.length());
			return arr.getString(index);
		}
		return "";
	}
	public String getRandomStat() {
		JSONArray arr = this.m_data.getJSONArray("stats");
		if (arr != null) {
			int index = (int) (Math.random() * arr.length());
			return arr.getString(index);
		}
		return "";
	}
	public String getRandomType() {
		JSONArray arr = this.m_data.getJSONArray("types");
		if (arr != null) {
			int index = (int) (Math.random() * arr.length());
			return arr.getString(index);
		}
		return "";
	}
	public String getBoostFromType(String type) {
		String ret = null;
		JSONObject arr = this.m_data.getJSONObject("typeBoosts");
		if (arr != null) {
			ret = arr.getString(type);
		}
		if (ret == null)
			ret = "";
		return ret;
	}
	public String getNerfFromType(String type) {
		String ret = null;
		JSONObject arr = this.m_data.getJSONObject("typeNerfs");
		if (arr != null) {
			ret = arr.getString(type);
		}
		if (ret == null)
			ret = "";
		return ret;
	}
	public String getStatusFromType(String type) {
		String ret = null;
		JSONObject arr = this.m_data.getJSONObject("typeStatuses");
		if (arr != null) {
			ret = arr.getString(type);
		}
		if (ret == null)
			ret = "";
		return ret;
	}
}
