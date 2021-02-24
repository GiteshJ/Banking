package com.banking.utils;

import java.util.List;
import java.util.Map;

public class StatisticsUtils {
	public static int maxId(Map<Integer, String> objectStorage) {
	    return objectStorage.keySet().stream()
	            .max(Integer::compareTo)
	            .orElse(0);
	}
	
	public static long totalContentSize(List<Float> objectStorage) {
	    
		return objectStorage.stream().mapToInt(i -> i.intValue()).sum();
	}
}
