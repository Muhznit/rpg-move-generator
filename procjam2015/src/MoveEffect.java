import java.util.HashMap;
import java.util.Vector;

import org.json.JSONObject;
import org.json.JSONStringer;

public class MoveEffect {
	private boolean useConditions = false;
	private MoveDesignSpecifics m_designSpecifics;
	private Vector<Condition> m_ANDconditions = new Vector<Condition>();
	private Vector<Condition> m_ORconditions = new Vector<Condition>();
	private HashMap<String, Integer> m_boosts = new HashMap<String, Integer>();
	private StateChange m_StateChange = new StateChange();
	private int chance = 100;

	public MoveEffect() {
		
	}
	public MoveEffect setParent(MoveEffect p_parent) {
		return this;
	}
	private InputFileHandler getInFileHandler() {
		return Main.getInputFileHandler();
	}
	private MoveDesignSpecifics getDesignSpecifics() {
		return m_designSpecifics;
	}
	public MoveEffect setMoveDesignSpecifics(MoveDesignSpecifics p_designSpecifics) {
		this.m_designSpecifics = p_designSpecifics;
		return this;
	}
	public String toJSONString() {
		JSONStringer stringer = new JSONStringer();
		stringer.object();
			if (this.useConditions) {
				stringer.key("AND").value(JSONObject.wrap(m_ANDconditions))
				.key("OR").value(JSONObject.wrap(m_ORconditions));
			}
			if (this.m_StateChange != null) {
				stringer.key("status").value(m_StateChange.toJSONObject());
			}
			if (this.m_boosts != null) {
				stringer.key("boosts").value(JSONObject.wrap(m_boosts));
			}
			if (this.getDesignSpecifics().changesHP() && !this.getDesignSpecifics().hasMaliciousIntent()) {
				stringer.key("heal").value(".5");
			}
			stringer.key("chance").value(chance);
		stringer.endObject();
		
		return stringer.toString();
	}
	public void randomize() {
		int statusCost = 0;
		int statBoostCost = 0;
		
		if (this.getDesignSpecifics().changesStatus()) {
			statusCost += 3;
			
			m_StateChange.setMoveDesignSpecifics(getDesignSpecifics());
			m_StateChange.randomize();
		}
		else {
			m_StateChange = null;
		}
		
		if (this.getDesignSpecifics().changesStats()) {
			int maxBoostCount = 0;
			maxBoostCount = (int) (1 + Math.random() * 2);
			maxBoostCount += this.getDesignSpecifics().changesHP() ? 0 : 1;
			statBoostCost = maxBoostCount;
			for (int i = 0; i < maxBoostCount; i++) {
				if (this.getInFileHandler() != null) {
					String key = getInFileHandler().getRandomStat();
					boolean hasMaliciousIntent = this.getDesignSpecifics().hasMaliciousIntent();
					if (Math.random() > .5) {
						if (!hasMaliciousIntent) {
							key = getInFileHandler().getBoostFromType(this.getDesignSpecifics().getType());
						}
						else {
							key = getInFileHandler().getNerfFromType(this.getDesignSpecifics().getType());
						}
					}
					int value = hasMaliciousIntent ? -1 : 1;
					int currValue = (m_boosts.get(key) != null) ? m_boosts.get(key) : 0;
					m_boosts.put(key, currValue + value);
				}
			}
		}
		else {
			m_boosts = null;
		}
		
		chance = 100;
		chance -= statusCost * 10;
		chance -= statBoostCost * 10;
		if (this.getDesignSpecifics().changesHP()) {
			chance -= 30;
		}
		
		if (!this.getDesignSpecifics().hasMaliciousIntent() && !this.getDesignSpecifics().changesHP()) {
			chance = 100;
		}
	}
	public int getChance() {
		return chance;
	}
	public JSONObject toJSONObject() {
		return new JSONObject(this.toJSONString());
	}
}
