package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Line {
	
	private String LineNO;
	//һ��վ��һ��50վ������
	private List <String>  stations=new ArrayList<String>(60); ///һ����·������վ��
	private List <Integer>  stationIndexs=new ArrayList<Integer>(60); //ÿ��վ���ڸ���·�е����
		
		

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
