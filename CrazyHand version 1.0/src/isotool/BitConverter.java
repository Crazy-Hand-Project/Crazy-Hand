package isotool;


/**
 * Converted from c#
 *
 */
public class BitConverter {

	public static long ToUInt32(byte[] bytes, int offset) {
		long result = (int) bytes[offset] & 0xff;
		result |= ((int) bytes[offset + 1] & 0xff) << 8;
		result |= ((int) bytes[offset + 2] & 0xff) << 16;
		result |= ((int) bytes[offset + 3] & 0xff) << 24;

		return result & 0xFFFFFFFFL;
	}

}
