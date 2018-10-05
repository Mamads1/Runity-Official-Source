package io.battlerune.content.activity.impl.inferno;

public final class InfernoWaveData {

	/**
	 * 
	 * @author Adam_#6723
	 * Stores the NPC Data for Inferno
	 */
	public static final int 
	JAL_MEJRAH = 7692, 
	JAL_AK = 7693, 
	JAL_AKREK_MEJ = 7694,
	JAL_AKREK_XIL = 7695,
	JAL_AKREK_KET = 7696,
    JAL_IMKOT = 7697,
    JAL_XIL = 7698,
	JAL_ZEK = 7699, 
	JALTOK_JAD = 7700,
	YT_HURKOT = 7705,
	TZKAL_ZUK = 7706, //last NPC.
	ANCESTRAL_GLYPH = 7707,
	JAL_MEJJAK = 7708;

	public static final int JAL_NIB = 7691;

	
	public enum WaveData {
		
		WAVE_1(JAL_NIB, JAL_NIB, JAL_NIB, JAL_MEJRAH), WAVE_2(JAL_NIB, JAL_NIB, JAL_NIB, JAL_MEJRAH, JAL_MEJRAH), 
		WAVE_3(JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB), 
		WAVE_4(JAL_NIB, JAL_NIB, JAL_NIB, JAL_AK), 
		WAVE_5(JAL_NIB, JAL_NIB, JAL_NIB, JAL_AK, JAL_MEJRAH), 
		WAVE_6(JAL_NIB, JAL_NIB, JAL_NIB, JAL_AK, JAL_MEJRAH, JAL_MEJRAH), 
		WAVE_7(JAL_NIB, JAL_NIB, JAL_NIB, JAL_AK, JAL_AK),
		WAVE_8(JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB), 
		WAVE_9(JAL_NIB, JAL_NIB, JAL_NIB, JAL_IMKOT), 
		WAVE_10(JAL_NIB, JAL_NIB, JAL_NIB, JAL_IMKOT, JAL_MEJRAH),
		WAVE_11(JAL_NIB, JAL_NIB, JAL_NIB, JAL_IMKOT, JAL_MEJRAH, JAL_MEJRAH), 
		WAVE_12(JAL_NIB, JAL_NIB, JAL_NIB, JAL_IMKOT, JAL_MEJRAH), 
		WAVE_13(JAL_NIB, JAL_NIB, JAL_NIB, JAL_IMKOT, JAL_MEJRAH, JAL_AK),
		WAVE_14(JAL_NIB, JAL_NIB, JAL_NIB, JAL_IMKOT, JAL_MEJRAH, JAL_MEJRAH, JAL_AK), 
		WAVE_15(JAL_NIB, JAL_NIB, JAL_NIB, JAL_AK, JAL_AK, JAL_IMKOT), 
		WAVE_16(JAL_NIB, JAL_NIB, JAL_NIB, JAL_IMKOT, JAL_IMKOT), 
		WAVE_17(JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB),
		WAVE_18(JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL), 
		WAVE_19(JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_MEJRAH), 
		WAVE_20(JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_MEJRAH, JAL_MEJRAH), 
		WAVE_21(JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_AK), 
		WAVE_22(JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_AK, JAL_MEJRAH),
		WAVE_23(JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_MEJRAH, JAL_MEJRAH, JAL_AK), 
		WAVE_24(JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_AK, JAL_AK),
		WAVE_25(JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_IMKOT),
		WAVE_26(JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_IMKOT, JAL_MEJRAH),
		WAVE_27(JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_MEJRAH, JAL_MEJRAH, JAL_IMKOT), 
		WAVE_28(JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_IMKOT, JAL_AK), 
		WAVE_29(JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_AK, JAL_IMKOT),
		WAVE_30(JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_IMKOT, JAL_MEJRAH, JAL_MEJRAH, JAL_AK),
		WAVE_31(JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_AK, JAL_AK, JAL_IMKOT),
		WAVE_32(JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_IMKOT, JAL_IMKOT),
		WAVE_33(JAL_NIB, JAL_NIB, JAL_NIB, JAL_XIL, JAL_XIL),
		WAVE_34(JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB, JAL_NIB),
		WAVE_35(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK),
		WAVE_36(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH),
		WAVE_37(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_MEJRAH),
		WAVE_38(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_AK),
		WAVE_39(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_AK, JAL_MEJRAH),
		WAVE_40(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_MEJRAH, JAL_AK),
		WAVE_41(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_AK, JAL_AK),
		WAVE_42(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_IMKOT),
		WAVE_43(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_IMKOT, JAL_MEJRAH),
		WAVE_44(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_MEJRAH, JAL_IMKOT),
		WAVE_45(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_IMKOT, JAL_AK),
		WAVE_46(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_AK, JAL_IMKOT),
		WAVE_47(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_MEJRAH, JAL_AK, JAL_IMKOT),
		WAVE_48(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_AK, JAL_AK, JAL_IMKOT),
		WAVE_49(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_IMKOT, JAL_IMKOT),
		WAVE_50(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_XIL),
		WAVE_51(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_XIL),
		WAVE_52(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_XIL, JAL_MEJRAH, JAL_MEJRAH),
		WAVE_53(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_XIL, JAL_AK),
		WAVE_54(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_XIL, JAL_AK),
		WAVE_55(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_MEJRAH, JAL_AK, JAL_XIL),
		WAVE_56(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_AK, JAL_AK, JAL_XIL),
		WAVE_57(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_ZEK, JAL_IMKOT),
		WAVE_58(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_IMKOT, JAL_XIL),
		WAVE_59(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_MEJRAH, JAL_IMKOT, JAL_XIL),
		WAVE_60(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_AK, JAL_IMKOT, JAL_XIL),
		WAVE_61(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_AK, JAL_IMKOT, JAL_XIL),
		WAVE_62(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_MEJRAH, JAL_MEJRAH, JAL_AK, JAL_IMKOT, JAL_XIL),
		WAVE_63(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_AK, JAL_AK, JAL_IMKOT, JAL_XIL),
		WAVE_64(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_IMKOT, JAL_IMKOT, JAL_XIL),
		WAVE_65(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_XIL, JAL_XIL),
		WAVE_66(JAL_NIB, JAL_NIB, JAL_NIB, JAL_ZEK, JAL_ZEK, JAL_ZEK),
		WAVE_67(JALTOK_JAD),
		WAVE_68(JALTOK_JAD,JALTOK_JAD,JALTOK_JAD),
		WAVE_69(6609),
		;


		private final int[] monster;

		WaveData(int... monster) {
			this.monster = monster;
		}

		public int[] getMonster() {
			return monster;
		}

		public static WaveData getOrdinal(int ordinal) {
			for (WaveData wave : values()) {
				if (wave.ordinal() == ordinal)
					return wave;
			}
			return null;
		}

		public static WaveData getNext(int current) {
			return getOrdinal(current + 1);
		}
	}
}
