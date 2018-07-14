package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LeatherSkirtModel {
	private StringProperty brandName; //brand or name of skirt
	private DoubleProperty waist; //length of waist
	private DoubleProperty length; //length of skirt
	private StringProperty notaBene; //Туре оf skirt or another information

	public LeatherSkirtModel() {
		this(null,0,0,null);
	}

	public LeatherSkirtModel(String brandName, double waist, double length, String notaBene) {
		this.brandName = new SimpleStringProperty(brandName);
		this.length =  new SimpleDoubleProperty(length);
		this.waist = new SimpleDoubleProperty(waist);
		this.notaBene = new SimpleStringProperty(notaBene);

	}

	public String getBrandName() {
		return this.brandName.get();
	}
	public void setBrandName(String brandName) {
		this.brandName.set(brandName);
	}

	public StringProperty brandNameProperty() {
		return brandName;

	}

	public double getWaist() {
		return this.waist.get();
	}

	public void setWaist(double waist) {
		this.waist.set(waist);
	}

	public DoubleProperty waistProperty() {
		return waist;
	}

	public double getLength() {
		return this.length.get();
	}

	public void setLength(double length) {
		this.length.set(length);
	}

	public DoubleProperty lengthProperty() {
		return length;
	}

	public String getNotaBene() {
		return this.notaBene.get();
	}

	public void setNotaBene(String notaBene) {
		this.notaBene.set(notaBene);
	}

	public StringProperty notaBeneProperty() {
		return notaBene;
	}


}
