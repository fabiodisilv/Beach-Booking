#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model;

import java.util.List;

import ${package}.model.accuweather.forecast.Forecast;

public class BeachParkingWeather {

	private List<Beach> beaches;
	private Forecast forecast;

	public List<Beach> getBeaches() {
		return beaches;
	}

	public void setBeaches(List<Beach> beaches) {
		this.beaches = beaches;
	}

	public Forecast getForecast() {
		return forecast;
	}

	public void setForecast(Forecast forecast) {
		this.forecast = forecast;
	}

}
