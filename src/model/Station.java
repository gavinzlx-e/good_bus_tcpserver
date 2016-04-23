package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;


public class Station {
	private String stationName;
	private String stationNO;
	
	//һ��վ��һ��20����·����
	private List <String>  lines=new ArrayList<String>(20);  //����������·����·��
	private List <Integer>  distances=new ArrayList<Integer>(20);  //����·�ϵĳ�����վ����̾��룬�����շ��͵ľ���վ��
	
	
	
	
	public int getDistanceByLine(String line){
		
		assert(this.lines.contains(line));
		int i=lines.indexOf(line);
		if(this.distances.size()<i ||i <0){
			System.out.println("error: "+ stationNO+i+ "  "  + line + "   "+ JSON.toJSONString(lines)+ ""+ JSON.toJSONString(distances));
			return -1;
		}
		
		return this.distances.get(i);
	}
	
	public void addLine(int index, String line){
		lines.add(line);
		distances.add(index);
	}

	
	
	
	
	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public List<Integer> getStationDistances() {
		return distances;
	}

	public void setStationDistance(List<Integer> stationIndexs) {
		this.distances = stationIndexs;
	}
	
	
	
	
	public String getStationNO() {
		return stationNO;
	}
	public void setStationNO(String stationNO) {
		this.stationNO = stationNO;
	}
	
	
		
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
}
