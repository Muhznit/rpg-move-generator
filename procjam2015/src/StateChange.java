import org.json.JSONObject;
import org.json.JSONStringer;

public class StateChange {
	private MoveDesignSpecifics m_designSpecifics;
	private boolean m_doStart = false;
	private String m_id = "";
	public StateChange() {
		
	}
	private InputFileHandler getInFileHandler() {
		return Main.getInputFileHandler();
	}
	private MoveDesignSpecifics getDesignSpecifics() {
		return m_designSpecifics;
	}
	public StateChange setMoveDesignSpecifics(MoveDesignSpecifics p_designSpecifics) {
		this.m_designSpecifics = p_designSpecifics;
		return this;
	}
	public void randomize() {
		m_doStart = this.getDesignSpecifics().hasMaliciousIntent();
		if (this.getInFileHandler() != null) {
			m_id = this.getInFileHandler().getStatusFromType(this.getDesignSpecifics().getType());
		}
	}
	public String toJSONString() {
		JSONStringer stringer = new JSONStringer();
		
		stringer.object()
			.key("operation").value(m_doStart ? "start" : "stop")
			.key("status").value(m_id.toString())
		.endObject();
		
		return stringer.toString();
	}
	public JSONObject toJSONObject() {
		return new JSONObject(this.toJSONString());
	}
}
