package io.battlerune.content.activity.impl.allvsone;

public final class AllVsOneData {

	/**
	 * 
	 * @author Adam_#6723
	 * Stores the NPC Data for All Vs One
	 */
	public enum WaveData {
		WAVE_1(239), WAVE_2(763, 763), WAVE_3(6593), WAVE_4(6499), WAVE_5(2265), WAVE_6(2267), WAVE_7(2266, 5947),
		WAVE_8(6766), WAVE_9(6767, 6766), WAVE_10(4005, 7940, 7939), WAVE_11(6619), WAVE_12(6618), WAVE_13(5874, 5874),
		WAVE_14(2215, 2217, 2218, 2216), WAVE_15(2205, 2207), WAVE_16(3162), WAVE_17(3129, 3132, 3130, 3131),
		WAVE_18(6505), WAVE_19(7585), WAVE_20(6609), WAVE_21(1160, 1157, 1158), WAVE_22(3024, 6615, 6616, 6616),
		WAVE_23(5862, 7935), WAVE_24(7859), WAVE_25(7858), WAVE_26(7860), WAVE_27(8060), WAVE_28(5129), WAVE_29(8095);
		/* WAVE_2(6593, 6499), WAVE_3(2265, 2267, 2266, 5947), WAVE_4(6766, 6766),
		WAVE_5(4005, 7940, 7939), WAVE_6(6619, 6618, 5874, 5874), WAVE_7(2215, 2217, 2218, 2216),
		WAVE_8(2205, 2207), WAVE_9(3162, 3165, 3164, 3163), WAVE_10(3129, 3132, 3130, 3131),
		WAVE_11(6505, 7148, 7149, 7148), WAVE_12(6609, 1160, 1157, 1158), WAVE_13(3024, 6615, 6616, 6616),
		WAVE_14(5862, 7935), WAVE_15(5129), WAVE_16(8095);*/

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
