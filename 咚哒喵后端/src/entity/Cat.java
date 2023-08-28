package entity;

public class Cat {
	
	private int catID;
	private String species;
	private String name;
	private boolean sex;
	private String color;
	private String zone;
	private String characterristicValue;
	
	public int getCatID() {
		return catID;
	}
	public void setCatID(int catID) {
		this.catID = catID;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getCharacterristicValue() {
		return characterristicValue;
	}
	public void setCharacterristicValue(String characterristicValue) {
		this.characterristicValue = characterristicValue;
	}
	
	public Cat() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
