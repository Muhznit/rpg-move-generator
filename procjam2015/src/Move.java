import org.json.JSONObject;
import org.json.JSONStringer;

public class Move {
	private MoveDesignSpecifics m_designSpecifics;
	private MoveEffect secondaryEffect = new MoveEffect();
	private String type;
	private int damage = 0;
	public Move() {
		
	}

	private MoveDesignSpecifics getDesignSpecifics() {
		return m_designSpecifics;
	}
	public Move setMoveDesignSpecifics(MoveDesignSpecifics p_designSpecifics) {
		this.m_designSpecifics = p_designSpecifics;
		return this;
	}
	public String toJSONString() {
		JSONStringer stringer = new JSONStringer();
		String ret;
		
		stringer.object()
			.key("type").value(type)
			.key("effect").value(secondaryEffect.toJSONObject())
			.key("target").value(this.getDesignSpecifics().hasMaliciousIntent() ? "foe" : "ally");
			if (damage > 0) {
				stringer.key("damage").value(damage);
			}
		
		stringer.endObject();
		
		ret = stringer.toString();
		
		return ret;
	}
	public void randomize() {
		if (this.getDesignSpecifics().changesHP() && this.getDesignSpecifics().hasMaliciousIntent()) {
			damage = (int) (8 + Math.random() * 12); 
		}
		this.type = this.getDesignSpecifics().getType();
		this.secondaryEffect.setMoveDesignSpecifics(getDesignSpecifics());
		this.secondaryEffect.randomize();
		
		double damageReduction = secondaryEffect.getChance() / 100.0;
		damage *= damageReduction;
		damage *= 10;
	}
	public JSONObject toJSONObject() {
		return new JSONObject(toJSONString());
	}
}