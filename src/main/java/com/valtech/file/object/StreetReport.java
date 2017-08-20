package com.valtech.file.object;

import java.util.ArrayList;
import java.util.List;

public class StreetReport { 

	 private List<Integer> allHouseNumber;
	 private int totalCount;
	 private List<Integer> allHouseNumberOrderedList;
	 private List<Integer> leftOddNorthSideHouses;
	 private int leftOddNorthCount;
	 private List<Integer> rightEvenSouthSideHouses;
	 private int rightEvenSouthCount;
	 private int crossoverCount;
	 private List<Integer>  leftOddNorthSideOrderedList;
	 private List<Integer> deliveryOrderOpt2;
	 
	 
	 
	 public List<Integer> getLeftOddNorthSideOrderedList() {
		return leftOddNorthSideOrderedList;
	}
	public void setLeftOddNorthSideOrderedList(List<Integer> leftOddNorthSideOrderedList) {
		this.leftOddNorthSideOrderedList = leftOddNorthSideOrderedList;
	}
	public List<Integer> getRightEvenSouthSideOrderedList() {
		return rightEvenSouthSideOrderedList;
	}
	public void setRightEvenSouthSideOrderedList(List<Integer> rightEvenSouthSideOrderedList) {
		this.rightEvenSouthSideOrderedList = rightEvenSouthSideOrderedList;
	}
	public void setAllHouseNumber(List<Integer> allHouseNumber) {
		this.allHouseNumber = allHouseNumber;
	}
	private List<Integer>  rightEvenSouthSideOrderedList;
	 
	public List<Integer> getAllHouseNumber() {
		return allHouseNumber;
	}
	public void setAllHouseNumber(ArrayList<Integer> allHouseNumber) {
		this.allHouseNumber = allHouseNumber;
	}
	public int getTotalCount() {
		return this.getAllHouseNumber().size();
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<Integer> getAllHouseNumberOrderedList() {
		return allHouseNumberOrderedList;
	}
	public void setAllHouseNumberOrderedList(List<Integer> allHouseNumberOrderedList) {
		this.allHouseNumberOrderedList = allHouseNumberOrderedList;
	}
	public List<Integer> getLeftOddNorthSideHouses() {
		return leftOddNorthSideHouses;
	}
	public void setLeftOddNorthSideHouses(List<Integer> leftOddNorthSideHouses) {
		this.leftOddNorthSideHouses = leftOddNorthSideHouses;
		this.setLeftOddNorthCount(leftOddNorthSideHouses.size());
	}
	
	public void setLeftOddNorthCount(int leftOddNorthCount) {
		this.leftOddNorthCount = leftOddNorthCount;
	}
	public int getLeftOddNorthCount() {
		return leftOddNorthCount;
	}
	public List<Integer> getRightEvenSouthSideHouses() {
		return rightEvenSouthSideHouses;
	}
	public void setRightEvenSouthSideHouses(List<Integer> rightEvenSouthSideHouses) {
		this.rightEvenSouthSideHouses = rightEvenSouthSideHouses;
		this.setRightEvenSouthCount(rightEvenSouthSideHouses.size());
	}
	
	public void setRightEvenSouthCount(int rightEvenSouthCount) {
		this.rightEvenSouthCount = rightEvenSouthCount;
	}
	public int getRightEvenSouthCount() {
		return rightEvenSouthCount;
	}
	public int getCrossoverCount() {
		return crossoverCount ;
	}
	
	public void setCrossoverCount(int crossoverCount) {
		this.crossoverCount = crossoverCount;
	}
	
	/**
	 * @return the deliveryOrderOpt2
	 */
	public List<Integer> getDeliveryOrderOpt2() {
		return deliveryOrderOpt2;
	}
	/**
	 * @param deliveryOrderOpt2 the deliveryOrderOpt2 to set
	 */
	public void setDeliveryOrderOpt2(List<Integer> deliveryOrderOpt2) {
		this.deliveryOrderOpt2 = deliveryOrderOpt2;
	}
	
}
