package model;


public class LastInfo {
	private String  lineStr;	
	private Station station;
	private int stationindex;
	private String busid;
	private boolean arriveOrLeft;
	private boolean resend;
	
	
	public String getLineStr() {
		return lineStr;
	}

	public void setLineStr(String lineStr) {
		this.lineStr = lineStr;
	}

	
	
	public String getBusid() {
		return busid;
	}

	public void setBusid(String busid) {
		this.busid = busid;
	}

	
	
	
	public int getStationindex() {
		return stationindex;
	}

	public void setStationindex(int stationindex) {
		this.stationindex = stationindex;
	}

	

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public boolean isArriveOrLeft() {
		return arriveOrLeft;
	}

	public void setArriveOrLeft(boolean arriveOrLeft) {
		this.arriveOrLeft = arriveOrLeft;
	}


	
}
