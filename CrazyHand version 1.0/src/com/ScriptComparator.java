package com;

import java.util.Comparator;

public class ScriptComparator implements Comparator<Script>, Comparable<Script> {

	@Override
	public int compareTo(Script arg0)
	{
		return 0;
	}

	@Override
	public int compare(Script arg0, Script arg1)
	{
		return arg0.location < arg1.location ? 1 : 0;
	}

}
