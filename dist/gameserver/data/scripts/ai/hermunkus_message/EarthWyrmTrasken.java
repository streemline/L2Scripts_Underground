package ai.hermunkus_message;

import l2s.gameserver.ai.DefaultAI;
import l2s.gameserver.data.xml.holder.SkillHolder;
import l2s.gameserver.model.Creature;
import l2s.gameserver.model.Skill;
import l2s.gameserver.model.entity.Reflection;
import l2s.gameserver.model.instances.NpcInstance;

import instances.MemoryOfDisaster;

/**
 * @author : Ragnarok
 * @date : 01.04.12  17:03
 */
public class EarthWyrmTrasken extends DefaultAI
{
	private static final int RHAND_ID = 15280;
	private static final int ENRAGED_SKILL_ID = 14505;
	private static final int BODY_STRIKE_SKILL_ID_1 = 14337;
	private static final int BODY_STRIKE_SKILL_ID_2 = 14338;

	public EarthWyrmTrasken(NpcInstance actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		getActor().setRHandId(RHAND_ID);
		addTimer(1, 50);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		super.onEvtTimer(timerId, arg1, arg2);
		Skill sk;
		switch(timerId)
		{
			case 1:
				sk = SkillHolder.getInstance().getSkill(ENRAGED_SKILL_ID, 1);
				addTaskBuff(getActor(), sk);
				doTask();
				break;
			case 2:
				sk = SkillHolder.getInstance().getSkill(BODY_STRIKE_SKILL_ID_1, 1);
				addTaskBuff(getActor(), sk);
				doTask();
				break;
			case 3:
				sk = SkillHolder.getInstance().getSkill(BODY_STRIKE_SKILL_ID_2, 1);
				addTaskBuff(getActor(), sk);
				doTask();
				break;
		}
	}

	@Override
	protected void onEvtFinishCasting(Skill skill, Creature target, boolean success)
	{
		switch(skill.getId())
		{
			case ENRAGED_SKILL_ID:
				Reflection r = getActor().getReflection();
				if(r instanceof MemoryOfDisaster)
					((MemoryOfDisaster) r).startFinalScene();
				addTimer(2, 50);
				break;
			case BODY_STRIKE_SKILL_ID_1:
				addTimer(3, 50);
				break;
		}
	}
}