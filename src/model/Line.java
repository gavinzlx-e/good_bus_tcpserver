package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Line {
	
	private String LineNO;
	//一个站点一般50站点以内
	private List <String>  stations=new ArrayList<String>(60); ///一条线路的所有站点
	private List <Integer>  stationIndexs=new ArrayList<Integer>(60); //每个站点在该线路中的序号
		
		

		public void addStation(int index, String station){
			stations.add(station);
			stationIndexs.add(index);
		}


		public String getStationByIndex(int index){
			assert(this.stationIndexs.contains(index));
			return stations.get(index);
		}
		
		public int getIndexByStation(String station){
			assert(this.stations.contains(station));
			int i=stations.indexOf(station);
			return this.stationIndexs.get(i);
		}
		
		
	
	
	public String getLineNO() {
		return LineNO;
	}


	public void setLineNO(String lineNO) {
		LineNO = lineNO;
	}


	public List<String> getStations() {
		return stations;
	}


	public void setStations(List<String> stations) {
		this.stations = stations;
	}


	public List<Integer> getStationIndexs() {
		return stationIndexs;
	}


	public void setStationIndexs(List<Integer> stationIndexs) {
		this.stationIndexs = stationIndexs;
	}

	
	
	
}
