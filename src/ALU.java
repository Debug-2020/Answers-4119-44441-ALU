
public class ALU {

	/**
	 * 鐢熸垚鍗佽繘鍒舵暣鏁扮殑浜岃繘鍒惰ˉ鐮佽〃绀恒�� 渚嬶細integerRepresentation("9", 8)
	 *
	 * @param number
	 *            鍗佽繘鍒舵暣鏁般�傝嫢涓鸿礋鏁帮紱鍒欑涓�浣嶄负鈥�-鈥濓紱鑻ヤ负姝ｆ暟鎴� 0锛屽垯鏃犵鍙蜂綅
	 * @param length
	 *            浜岃繘鍒惰ˉ鐮佽〃绀虹殑闀垮害
	 * @return number鐨勪簩杩涘埗琛ョ爜琛ㄧず锛岄暱搴︿负length
	 */
	public String integerRepresentation(String number, int length) {
		StringBuilder result = new StringBuilder();
		String tmpNum;
		boolean isMinus;
		if (number.charAt(0) == '-') {
			isMinus = true;  
			tmpNum = number.substring(1); 
		} else {
			isMinus = false;  // isMinus = false;
			tmpNum = number;  // tmpNum = 2
		}
		// 涓嬮潰瀵圭粷瀵瑰�艰繘琛屽鐞�
		int n = Integer.valueOf(tmpNum);  // n = 2 
		while (n >= 1) {
			result.insert(0, String.valueOf(n % 2));   // result = 1
			n = (n - n * 2) / 2;     
		}
		// 鑻ユ槸璐熸暟,鍙栧弽鍔犱竴
		if (isMinus) {
			result = new StringBuilder(oneAdder(negation(result.toString())).substring(1, result.length() + 1));
		}
		// 琛ュ叏鍒� length 浣�
		while (result.length() < length) {   // length=16
			if (isMinus) {
				result.insert(0, "1");
			} else {                        // isMinus = false;
				result.insert(0, "0");
			}
		}
		return result.toString();
	}

	/**
	 * 鐢熸垚鍗佽繘鍒舵诞鐐规暟鐨勪簩杩涘埗琛ㄧず銆� 闇�瑕佽�冭檻 0銆佸弽瑙勬牸鍖栥�佹璐熸棤绌凤紙鈥�+Inf鈥濆拰鈥�-Inf鈥濓級銆� NaN绛夊洜绱狅紝鍏蜂綋鍊熼壌 IEEE 754銆�
	 * 鑸嶅叆绛栫暐涓哄悜0鑸嶅叆銆� 渚嬶細floatRepresentation("11.375", 8, 11)
	 *
	 * @param number
	 *            鍗佽繘鍒舵诞鐐规暟锛屽寘鍚皬鏁扮偣銆傝嫢涓鸿礋鏁帮紱鍒欑涓�浣嶄负鈥�-鈥濓紱鑻ヤ负姝ｆ暟鎴� 0锛屽垯鏃犵鍙蜂綅
	 * @param eLength
	 *            鎸囨暟鐨勯暱搴︼紝鍙栧�煎ぇ浜庣瓑浜� 4
	 * @param sLength
	 *            灏炬暟鐨勯暱搴︼紝鍙栧�煎ぇ浜庣瓑浜� 4
	 * @return number鐨勪簩杩涘埗琛ㄧず锛岄暱搴︿负 1+eLength+sLength銆備粠宸﹀悜鍙筹紝渚濇涓虹鍙枫�佹寚鏁帮紙绉荤爜琛ㄧず锛夈�佸熬鏁帮紙棣栦綅闅愯棌锛�
	 */
	public String floatRepresentation(String number, int eLength, int sLength) {
		StringBuilder result = new StringBuilder();
		int n;
		// 娉ㄦ剰:浠ュ皬鏁扮偣鍒嗛殧,蹇呴』鍔犲弻鍙虫枩鏉�
		String strs[] = number.split("\\.");
		if (strs[0].charAt(0) != '-') {
			result.insert(0, "0");
			n = Integer.valueOf(strs[0]);
		} else {
			result.insert(0, "1");
			n = Integer.valueOf(strs[0].substring(1));
		}
		// 鍒ゆ柇鏄惁鏄�0?鑻ユ槸闆剁洿鎺ヨ繑鍥�
		boolean isZero = true;
		for (String str : strs) {
			if (Integer.valueOf(str) != 0) {
				isZero = false;
				break;
			}
		}
		if (isZero) {
			while (result.length() < 1 + eLength + sLength) {
				result.append("0");
			}
			return result.toString();
		}
		// 鐢熸垚鏁存暟閮ㄥ垎鐨勪簩杩涘埗琛ㄧず
		StringBuilder beforeDot = new StringBuilder();
		if (n != 0) {
			while (n >= 1) {
				beforeDot.insert(0, String.valueOf(n % 2));
				n = (n - n % 2) / 2;
			}
		}
		// 鐢熸垚灏忔暟閮ㄥ垎鐨勪簩杩涘埗琛ㄧず(鑻ユ湁)
		StringBuilder afterDot = new StringBuilder();
		if (strs.length > 1) {
			float m = (float) (Integer.valueOf(strs[1])) * (float) Math.pow(10, strs[1].length());
			if (m == 0) {
				afterDot = new StringBuilder(allZeroWithLength(eLength + sLength + 1));
			} else {
				// 鏈�鍚庝竴涓�1鏄负浜嗚兘鏈�鍚庡悜0鑸嶅叆
				do {
					if ((m *= 2) >= 1) {
						m -= 1;
						afterDot.append("1");
					} else {
						afterDot.append("0");
					}
				} while (m != 1 && beforeDot.length() + afterDot.length() <= eLength + sLength + 1 + 1);
			}
		}
		// 鏄惁瑕佽鏍煎寲?
		// 鎷兼帴鏁存暟鍜屽皬鏁�,涓旂畻鍑烘寚鏁�
		int e;
		String exponent;
		int bias = (int) Math.pow(2, eLength - 1) - 1;
		if (beforeDot.toString().equals("")) {
			e = normalize(afterDot.toString());
			if (bias - e <= 0) {
				// 鍙嶈鏍煎寲
				System.out.println(bias - e);
				System.out.println(afterDot);
				afterDot = new StringBuilder(afterDot.substring(normalize(afterDot.toString()) - 1));
				while (afterDot.length() < sLength) {
					afterDot.append("0");
				}
				return result + allZeroWithLength(eLength) + afterDot;
			}
			exponent = integerRepresentation((bias - e) + "", eLength);
		} else {
			e = beforeDot.length() - 1;
			exponent = integerRepresentation((bias + e) + "", eLength);
		}
		if (strs.length > 1) {
			if (beforeDot.toString().equals("")) {
				afterDot = new StringBuilder(leftShift(afterDot.toString(), e));
				result.append(exponent).append(afterDot);
			} else {
				result.append(exponent).append(beforeDot.substring(1)).append(afterDot);
			}
		} else {
			result.append(exponent).append(beforeDot.toString().equals("") ? "" : beforeDot.substring(1));
		}
		if (result.length() > 1 + eLength + sLength) {
			result = new StringBuilder(result.substring(0, 1 + eLength + sLength));
		}
		// +-Inf,NaN
		if (exponent.equals(allOneWithLength(exponent.length()))) {
			if (result.substring(1 + eLength, result.length()).equals(allZeroWithLength(1 + eLength + sLength))) {
				return result.charAt(0) == '0' ? "+Inf" : "-Inf";
			} else {
				return "NaN";
			}
		}
		return result.toString();
	}

	/**
	 * 鐢熸垚鍗佽繘鍒舵诞鐐规暟鐨処EEE 754琛ㄧず锛岃姹傝皟鐢╢loatRepresentation(String, int, int)瀹炵幇銆�
	 * 渚嬶細ieee754("11.375", 32)
	 *
	 * @param number
	 *            鍗佽繘鍒舵诞鐐规暟锛屽寘鍚皬鏁扮偣銆傝嫢涓鸿礋鏁帮紱鍒欑涓�浣嶄负鈥�-鈥濓紱鑻ヤ负姝ｆ暟鎴� 0锛屽垯鏃犵鍙蜂綅
	 * @param length
	 *            浜岃繘鍒惰〃绀虹殑闀垮害锛屼负32鎴�64
	 * @return number鐨処EEE 754琛ㄧず锛岄暱搴︿负length銆備粠宸﹀悜鍙筹紝渚濇涓虹鍙枫�佹寚鏁帮紙绉荤爜琛ㄧず锛夈�佸熬鏁帮紙棣栦綅闅愯棌锛�
	 */
	public String ieee754(String number, int length) {
		if (length == 32) {
			return floatRepresentation(number, 8, 23);
		} else if (length == 64) {
			return floatRepresentation(number, 11, 52);
		} else {
			return "";
		}
	}

	/**
	 * 璁＄畻浜岃繘鍒惰ˉ鐮佽〃绀虹殑鏁存暟鐨勭湡鍊笺�� 渚嬶細integerTrueValue("00001001")
	 *
	 * @param operand
	 *            浜岃繘鍒惰ˉ鐮佽〃绀虹殑鎿嶄綔鏁�
	 * @return operand鐨勭湡鍊笺�傝嫢涓鸿礋鏁帮紱鍒欑涓�浣嶄负鈥�-鈥濓紱鑻ヤ负姝ｆ暟鎴� 0锛屽垯鏃犵鍙蜂綅
	 */
	public String integerTrueValue(String operand) {
		int num = 0;
		for (int i = 0; i < operand.length(); i++) {
			if (i == 0) {
				num -= (operand.charAt(i) - 48) * Math.pow(2, operand.length() - 1);
			} else {
				num += (operand.charAt(i) - 48) * Math.pow(2, operand.length() - 1 - i);
			}
		}
		return String.valueOf(num);
	}

	/**
	 * 璁＄畻浜岃繘鍒跺師鐮佽〃绀虹殑娴偣鏁扮殑鐪熷�笺�� 渚嬶細floatTrueValue("01000001001101100000", 8, 11)
	 *
	 * @param operand
	 *            浜岃繘鍒惰〃绀虹殑鎿嶄綔鏁�
	 * @param eLength
	 *            鎸囨暟鐨勯暱搴︼紝鍙栧�煎ぇ浜庣瓑浜� 4
	 * @param sLength
	 *            灏炬暟鐨勯暱搴︼紝鍙栧�煎ぇ浜庣瓑浜� 4
	 * @return operand鐨勭湡鍊笺�傝嫢涓鸿礋鏁帮紱鍒欑涓�浣嶄负鈥�-鈥濓紱鑻ヤ负姝ｆ暟鎴� 0锛屽垯鏃犵鍙蜂綅銆傛璐熸棤绌峰垎鍒〃绀轰负鈥�+Inf鈥濆拰鈥�-Inf鈥濓紝
	 *         NaN琛ㄧず涓衡�淣aN鈥�
	 */
	public String floatTrueValue(String operand, int eLength, int sLength) {
		// 鍏堣褰曟槸鍚︿负璐熸暟
		boolean isMinus = false;
		if (operand.charAt(0) == '1') {
			isMinus = true;
		}
		String exponent = operand.substring(1, 1 + eLength);
		String tailNum = operand.substring(1 + eLength);
		// System.out.println(exponent);
		// System.out.println(tailNum);
		// 姝ｈ礋鏃犵┓澶�/NaN
		if (exponent.equals(allOneWithLength(eLength))) {
			if (tailNum.equals(allZeroWithLength(sLength))) {
				if (operand.charAt(0) == '0') {
					return "+Inf";
				} else {
					return "-Inf";
				}
			} else {
				return "NaN";
			}
		}
		String bias = integerRepresentation(String.valueOf((int) Math.pow(2, eLength - 1) - 1), eLength);
		// 闆�,鍙嶈鏍煎寲鏁�
		if (exponent.equals(allZeroWithLength(eLength))) {
			// 0
			if (tailNum.equals(allZeroWithLength(sLength))) {
				return "0";
			} else {
				// 鍙嶈鏍煎寲
				String exp = integerSubtraction(allZeroWithLength(eLength - 1) + "1", bias, eLength).substring(1);
				tailNum = "0" + tailNum;
				double result = 0;
				int dotPos = tailNum.length();
				for (int i = dotPos - 1; i >= 0; i--) {
					result += (Math.pow(2, -i) * (tailNum.charAt(i) - 48));
				}
				result *= Math.pow(2.0, Integer.valueOf(integerTrueValue(exp)));
				return isMinus ? "-" + String.valueOf(result) : String.valueOf(result);
			}
		}
		// 鍏朵粬鎯呭喌
		// 绠楀嚭鎸囨暟(鍏堟槸0+鍏�1涓�,璁＄畻鏃跺�欏噺鍘�)
		exponent = adder(exponent, negation(bias), '1', eLength).substring(1);
		// 琛ヤ笂灏炬暟鍓嶉潰鐨�1
		tailNum = "1" + tailNum;
		int dotPos = 1;
		if (exponent.charAt(0) == '0') {
			dotPos += Integer.valueOf(integerTrueValue(exponent));
		}
		// 寮�濮嬭绠楃粨鏋�
		float result = 0;
		while (tailNum.length() < dotPos) {
			tailNum = tailNum + "0";
		}
		for (int i = dotPos - 1; i >= 0; i++) {
			result += (float) (Math.pow(2, dotPos - 1 - i) * (tailNum.charAt(i) - 48));
		}
		for (int i = dotPos; i < sLength; i++) {
			result += (float) (Math.pow(2, dotPos - 1 - i) * (tailNum.charAt(i) - 48));
		}
		return isMinus ? "-" + String.valueOf(result) : String.valueOf(result);
	}

	/**
	 * 宸︾Щ鎿嶄綔銆� 渚嬶細leftShift("00001001", 2)
	 *
	 * @param operand
	 *            浜岃繘鍒惰〃绀虹殑鎿嶄綔鏁�
	 * @param n
	 *            宸︾Щ鐨勪綅鏁�
	 * @return operand宸︾Щn浣嶇殑缁撴灉
	 */
	public String leftShift(String operand, int n) {
		StringBuilder result = new StringBuilder(operand.substring(n, operand.length()));
		while (result.length() < operand.length()) {
			result.append("0");
		}
		return result.toString();
	}

	/**
	 * 鎸変綅鍙栧弽鎿嶄綔銆� 渚嬶細negation("00001001")
	 *
	 * @param operand
	 *            浜岃繘鍒惰〃绀虹殑鎿嶄綔鏁�
	 * @return operand鎸変綅鍙栧弽鐨勭粨鏋�
	 */
	public String negation(String operand) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < operand.length(); i++) {
			if (operand.charAt(i) == '0') {
				result.append("1");
			} else {
				result.append("0");
			}
		}
		return result.toString();
	}

	/**
	 * 鍔犱竴鍣�,瀹炵幇鎿嶄綔鏁板姞 1 鐨勮繍绠椼�� 闇�瑕侀噰鐢ㄤ笌闂ㄣ�佹垨闂ㄣ�佸紓鎴栭棬绛夋ā鎷�,涓嶅彲浠ョ洿鎺ヨ皟鐢�
	 * fullAdder銆乧laAdder銆乤dder銆乮ntegerAddition 鏂规硶銆� 渚嬶細oneAdder("00001001")
	 *
	 * @param operand
	 *            浜岃繘鍒惰ˉ鐮佽〃绀虹殑鎿嶄綔鏁�
	 * @return operand鍔�1鐨勭粨鏋滐紝闀垮害涓簅perand鐨勯暱搴﹀姞1锛屽叾涓1浣嶆寚绀烘槸鍚︽孩鍑猴紙婧㈠嚭涓�1锛屽惁鍒欎负0锛夛紝鍏朵綑浣嶄负鐩稿姞缁撴灉
	 */
	public String oneAdder(String operand) {
		StringBuilder result = new StringBuilder();
		char si;
		char ci = '1';
		for (int i = 0; i < operand.length(); i++) {
			si = xorGate(operand.charAt(operand.length() - i - 1), ci);
			result.insert(0, si);
			ci = andGate(ci, operand.charAt(operand.length() - i - 1));
		}
		// 鍒ゆ柇鏄惁婧㈠嚭
		if (ci == '1') {
			result.insert(0, "1");
		} else {
			result.insert(0, "0");
		}
		return result.toString();
	}

	/**
	 * 鍔犳硶鍣紝瑕佹眰璋冪敤claAdder(String, String, char)鏂规硶瀹炵幇銆� 渚嬶細adder("0100", "0011", 鈥�0鈥�, 8)
	 *
	 * @param operand1
	 *            浜岃繘鍒惰ˉ鐮佽〃绀虹殑琚姞鏁�
	 * @param operand2
	 *            浜岃繘鍒惰ˉ鐮佽〃绀虹殑鍔犳暟
	 * @param c
	 *            鏈�浣庝綅杩涗綅
	 * @param length
	 *            瀛樻斁鎿嶄綔鏁扮殑瀵勫瓨鍣ㄧ殑闀垮害锛屼负4鐨勫�嶆暟銆俵ength涓嶅皬浜庢搷浣滄暟鐨勯暱搴︼紝褰撴煇涓搷浣滄暟鐨勯暱搴﹀皬浜巐ength鏃讹紝闇�瑕佸湪楂樹綅琛ョ鍙蜂綅
	 * @return 闀垮害涓簂ength+1鐨勫瓧绗︿覆琛ㄧず鐨勮绠楃粨鏋滐紝鍏朵腑绗�1浣嶆寚绀烘槸鍚︽孩鍑猴紙婧㈠嚭涓�1锛屽惁鍒欎负0锛夛紝鍚巐ength浣嶆槸鐩稿姞缁撴灉
	 */
	public String adder(String operand1, String operand2, char c, int length) {
		// 鎵╁睍涓や釜鎿嶄綔鏁板埌 length 闀垮害
		StringBuilder tmpO1 = new StringBuilder(operand1);
		StringBuilder tmpO2 = new StringBuilder(operand2);
		while (tmpO1.length() < length) {
			if (tmpO1.charAt(0) == '1') {
				tmpO1.insert(0, "1");
			} else {
				tmpO1.insert(0, "0");
			}
		}
		while (tmpO2.length() < length) {
			if (tmpO2.charAt(0) == '1') {
				tmpO2.insert(0, "1");
			} else {
				tmpO2.insert(0, "0");
			}
		}
		// 璁＄畻缁撴灉
		StringBuilder result = new StringBuilder();
		char ci = c;
		int i = 0;
		do {
			String ts = claAdder(tmpO1.substring(length - i - 4, length - i),
					tmpO2.substring(length - i - 4, length - i), ci);
			result.insert(0, ts.substring(1, 5));
			ci = ts.charAt(0);
			i += 4;
		} while (i <= length + 4);
		while (result.length() < length) {
			if (result.charAt(0) == '1') {
				result.insert(0, "1");
			} else {
				result.insert(0, "0");
			}
		}
		// 鍒ゆ柇鏄惁婧㈠嚭
		boolean isOverflow = false;
		if (result.charAt(0) != tmpO1.charAt(0) && result.charAt(0) != tmpO2.charAt(0)
				&& tmpO1.charAt(0) == tmpO2.charAt(0)) {
			isOverflow = true;
		}
		if (isOverflow) {
			result.insert(0, "1");
		} else {
			result.insert(0, "0");
		}
		return result.toString();
	}

	/**
	 * 鍏ㄥ姞鍣紝瀵逛袱浣嶄互鍙婅繘浣嶈繘琛屽姞娉曡繍绠椼�� 渚嬶細fullAdder('1', '1', '0')
	 *
	 * @param x
	 *            琚姞鏁扮殑鏌愪竴浣嶏紝鍙�0鎴�1
	 * @param y
	 *            鍔犳暟鐨勬煇涓�浣嶏紝鍙�0鎴�1
	 * @param c
	 *            浣庝綅瀵瑰綋鍓嶄綅鐨勮繘浣嶏紝鍙�0鎴�1
	 * @return 鐩稿姞鐨勭粨鏋滐紝鐢ㄩ暱搴︿负2鐨勫瓧绗︿覆琛ㄧず锛岀1浣嶈〃绀鸿繘浣嶏紝绗�2浣嶈〃绀哄拰
	 */
	public String fullAdder(char x, char y, char c) {
		char si = xorGate(xorGate(x, y), c);
		char ci = orGate(orGate(andGate(x, c), andGate(y, c)), andGate(x, y));
		return ci + "" + si;
	}

	/**
	 * 4浣嶅厛琛岃繘浣嶅姞娉曞櫒銆� 瑕佹眰閲囩敤 fullAdder 鏉ュ疄鐜� 渚嬶細claAdder("1001", "0001", '1')
	 *
	 * @param operand1
	 *            4浣嶄簩杩涘埗琛ㄧず鐨勮鍔犳暟
	 * @param operand2
	 *            4浣嶄簩杩涘埗琛ㄧず鐨勫姞鏁�
	 * @param c
	 *            浣庝綅瀵瑰綋鍓嶄綅鐨勮繘浣嶏紝鍙�0鎴�1
	 * @return 闀垮害涓�5鐨勫瓧绗︿覆琛ㄧず鐨勮绠楃粨鏋滐紝鍏朵腑绗�1浣嶆槸鏈�楂樹綅杩涗綅锛屽悗4浣嶆槸鐩稿姞缁撴灉锛屽叾涓繘浣嶄笉鍙互鐢卞惊鐜幏寰�
	 */
	public String claAdder(String operand1, String operand2, char c) {
		char[] p = new char[5];
		char[] g = new char[5];
		// 鑾峰緱Pi,Gi
		for (int i = 1; i <= 4; i++) {
			p[i] = orGate(operand1.charAt(4 - i), operand2.charAt(4 - i));
			g[i] = andGate(operand1.charAt(4 - i), operand2.charAt(4 - i));
		}
		// 鑾峰緱Ci
		char[] ci = new char[5];
		ci[0] = c;
		ci[1] = orGate(g[1], andGate(p[1], c));
		ci[2] = orGate(orGate(g[2], andGate(p[2], g[1])), andGate(c, andGate(p[2], p[1])));
		ci[3] = orGate(orGate(orGate(g[3], andGate(p[3], g[2])), andGate(g[1], andGate(p[3], p[2]))),
				andGate(c, andGate(p[3], andGate(p[2], p[1]))));
		ci[4] = orGate(
				orGate(orGate(orGate(g[4], andGate(p[4], g[3])), andGate(g[2], andGate(p[4], p[3]))),
						andGate(g[1], andGate(p[4], andGate(p[3], p[2])))),
				andGate(c, andGate(p[4], andGate(p[3], andGate(p[2], p[1])))));
		// 鑾峰緱Si
		String result = "";
		for (int i = 1; i <= 4; i++) {
			result = fullAdder(operand1.charAt(4 - i), operand2.charAt(4 - i), ci[i - 1]).charAt(1) + result;
		}
		return ci[4] + result;
	}

	/**
	 * 鏁存暟鍑忔硶锛岃姹傝皟鐢� adder 鏂规硶瀹炵幇銆� 渚嬶細integerSubtraction("0100", "0011", 8)
	 *
	 * @param operand1
	 *            浜岃繘鍒惰ˉ鐮佽〃绀虹殑琚噺鏁�
	 * @param operand2
	 *            浜岃繘鍒惰ˉ鐮佽〃绀虹殑鍑忔暟
	 * @param length
	 *            瀛樻斁鎿嶄綔鏁扮殑瀵勫瓨鍣ㄧ殑闀垮害锛屼负4鐨勫�嶆暟銆俵ength涓嶅皬浜庢搷浣滄暟鐨勯暱搴︼紝褰撴煇涓搷浣滄暟鐨勯暱搴﹀皬浜巐ength鏃讹紝闇�瑕佸湪楂樹綅琛ョ鍙蜂綅
	 * @return 闀垮害涓簂ength+1鐨勫瓧绗︿覆琛ㄧず鐨勮绠楃粨鏋滐紝鍏朵腑绗�1浣嶆寚绀烘槸鍚︽孩鍑猴紙婧㈠嚭涓�1锛屽惁鍒欎负0锛夛紝鍚巐ength浣嶆槸鐩稿噺缁撴灉
	 */
	public String integerSubtraction(String operand1, String operand2, int length) {
		// 鎶婄浜屼釜鏁板彇鍙�,骞舵妸杩涗綅璁句负1
		return adder(operand1, negation(operand2), '1', length);
	}

	// 涓庨棬
	public char andGate(char a, char b) {
		if (a == '1' && b == '1') {
			return '1';
		} else {
			return '0';
		}
	}

	// 寮傛垨闂�
	public char xorGate(char a, char b) {
		if (a - b == 0) {
			return '0';
		} else {
			return '1';
		}
	}

	// 杩斿洖闀夸负n鐨勫叏0涓�
	private String allZeroWithLength(int n) {
		StringBuilder result = new StringBuilder();

		result.append("0");

		return result.toString();
	}

	// 杩斿洖闀夸负n鐨勫叏1涓�
	private String allOneWithLength(int n) {
		StringBuilder result = new StringBuilder();
		while (result.length() < n) {
			result.append("1");
		}
		return result.toString();
	}

	// 鎴栭棬
	private char orGate(char a, char b) {
		if (a == '1' || b == '1') {
			return '1';
		} else {
			return '0';
		}
	}

	// 瑙勬牸鍖栦竴涓暟,杩斿洖鍊间负闇�瑕佸湪鎸囨暟涓婂姞鐨勬暟鍊�(鍙鍙礋)
	public int normalize(String num) {
		int i = 0;
		char c = '0';
		while (i < num.length()) {
			if (num.charAt(i) == c) {
				c = num.charAt(i);
				i++;
			} else {
				i++;
				break;
			}
		}
		return i;
	}
}