
public class MoveDesignSpecifics {
	private boolean m_maliciousIntention = true;
	private boolean m_changesHP = true;
	private boolean m_inflictStatus = true;
	private boolean m_changeStats = true;
	private String m_type;
	public void randomize() {
		this.m_maliciousIntention = (Math.random() > .5);
		this.m_changesHP = (Math.random() > .5);
		
		if (this.m_changesHP && Math.random() > .5) {
			if (Math.random() > .5) {
				this.m_changeStats = false;
			}
			else {
				this.m_inflictStatus = false;
			}
		}
		
	}
	public String getType() {
		return m_type;
	}
	public MoveDesignSpecifics setType(String type) {
		this.m_type = type;
		return this;
	}
	public boolean hasMaliciousIntent() {
		return this.m_maliciousIntention;
	}
	public boolean changesHP() {
		return m_changesHP;
	}
	public boolean changesStatus() {
		return this.m_inflictStatus;
	}
	public boolean changesStats() {
		return this.m_changeStats;
	}
}
