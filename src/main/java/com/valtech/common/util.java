package com.valtech.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.valtech.file.exception.StorageException;
import com.valtech.file.object.StreetReport;

/**
 * @author CHOUDHAD
 *
 */
public class util {
	
	/**
	 * This method takes input from file and return arraylist based on delimeter
	 * @param fileData
	 * @param delimeter
	 * @return
	 */
	public static ArrayList<Integer> splitString(String fileData, String delimeter) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		String[] temp = fileData.split(delimeter);
		for (int i = 0; i < temp.length; i++) {

			result.add(Integer.parseInt(temp[i]));
		}
		return result;

	}
	/**
	 * This method check if first housenumber in fils is 1
	 * @param houseList
	 * @return
	 */

	public static boolean hasFirstHouseNumberOne(List<Integer> houseList) {
		if (1 == houseList.get(0))
			return true;
		return false;
	}
	/**
	 * This checks for duplicates 
	 * @param houseList
	 * @return
	 */
	public static boolean hasDuplicate(List<Integer> houseList) {
		java.util.Set<Integer> seenValues = new java.util.HashSet();
		for (Integer value : houseList) {
			if (seenValues.contains(value)) {
				return true;
			} else {
				seenValues.add(value);
			}
		}
		return false;
	}
	/**
	 * This seperates odd house numbers and even house numbers
	 * @param streetReport
	 * @return
	 */
	public static StreetReport generateEvenOddList(StreetReport streetReport) {
		List<Integer> oddList = new ArrayList<Integer>();
		List<Integer> evenList = new ArrayList<Integer>();
		for (int number : streetReport.getAllHouseNumber()) {
			if (number % 2 == 0) {
				evenList.add(number);
			} else {
				oddList.add(number);
			}

		}
		streetReport.setLeftOddNorthSideHouses(oddList);
		oddList = QuickSort.quicksort(oddList);

		streetReport.setRightEvenSouthSideHouses(evenList);
		evenList = QuickSort.quicksort(evenList);

		if (isConsicutive(oddList) && isConsicutive(evenList)) {
			streetReport.setLeftOddNorthSideOrderedList(oddList);
			streetReport.setRightEvenSouthSideOrderedList(evenList);
		} else {
			throw new StorageException("Odds and even not consicutive ");
		}

		return streetReport;
	}

	/**
	 * This method checks if housenumbers are missing
	 * @param houseNumbers
	 * @return
	 */
	public static Boolean isConsicutive(List<Integer> houseNumbers) {
		// this method with work if numbers are non negative
		int diff = 0;
		int allowedDiff = 2;
		if (houseNumbers.size() == 0)
			return false;

		for (int i = 0; i < houseNumbers.size() - 1; ++i) {
			diff = Math.abs(houseNumbers.get(i) - houseNumbers.get(i + 1));
			if (allowedDiff != diff)
				return false;
		}
		return true;
	}

	public static List<Integer> setDeliverySequenceOpt2(List<Integer> north, List<Integer>south) {
	 Iterator<Integer> northHouses = north.iterator();
	 Iterator<Integer> southHouses = south.iterator();
	 List<Integer> deliveryOrder = new ArrayList<Integer>(); 
	    while (northHouses.hasNext() || southHouses.hasNext()) {
	        if (northHouses.hasNext()) {
	        	deliveryOrder.add(northHouses.next());
	        }
	        if (southHouses.hasNext()) {
	        	deliveryOrder.add(southHouses.next());
	        }
	    }
	  
		return deliveryOrder;
	    }

	    
	public static int getCrossoverCount(int leftCount, int rightCount)
	{	
		int crossoverCount = 0;
		if(leftCount <= rightCount)
			crossoverCount = (2*leftCount)-1;
		else
			crossoverCount=  2*rightCount;
		
		return crossoverCount;
	}
}