package application;

/*
 * Класс-файл чтобы обернуть наш класс с данными о юбках в обычный дженерик List
 * и потом маршаллить его в Xml-файл
 */

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="models")
public class SkirtsModelWrapper {

	private List<LeatherSkirtModel> models;

	@XmlElement(name="model")
	public List<LeatherSkirtModel> getModels() {
		return models;
	}

	public void setModels(List<LeatherSkirtModel> models) {
		this.models = models;
	}


}
