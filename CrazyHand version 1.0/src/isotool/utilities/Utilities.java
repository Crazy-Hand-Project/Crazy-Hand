package isotool.utilities;

import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Utilities {

	/**
	 * 
	 * <p>
	 * Reverses the order of the given array.
	 * </p>
	 * 
	 * <p>
	 * There is no special handling for multi-dimensional arrays.
	 * </p>
	 * 
	 * <p>
	 * This method does nothing for a <code>null</code> input array.
	 * </p>
	 * 
	 * @param array
	 *            the array to reverse, may be <code>null</code>
	 * 
	 */
	public static void reverse(byte[] array) {
		if (array == null) {
			return;
		}
		int i = 0;
		int j = array.length - 1;
		byte tmp;
		while (j > i) {
			tmp = array[j];
			array[j] = array[i];
			array[i] = tmp;
			j--;
			i++;
		}
	}

	public static void checkByteOrder(byte[] buffer) {
		if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN))
			reverse(buffer);
	}

	public static void unmap(FileChannel fc, MappedByteBuffer bb)
			throws Exception {
		Class<?> fcClass = fc.getClass();
		java.lang.reflect.Method unmapMethod = fcClass.getDeclaredMethod(
				"unmap", new Class[] { java.nio.MappedByteBuffer.class });
		unmapMethod.setAccessible(true);
		unmapMethod.invoke(null, new Object[] { bb });
	}

}
