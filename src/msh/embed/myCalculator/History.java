package msh.embed.myCalculator;

public class History {
	public String expression;
	public String result;
	public String dateAndTime;
	

	public History(){
		this.expression = "";
		this.result = "";
		this.dateAndTime = "";
	}
	
	public void setExpression(String expression){
		this.expression = expression;
	}
	public void  setResult(String result){
		this.result = result;
	}
	public void  setDateAndTime(String dateAndTime){
		this.dateAndTime = dateAndTime;
	}
	
	public String getExpression(){
		return expression;
	}
	public String getResult(){
		return result;
	}
	public String getDateAndTime(){
		return dateAndTime;
	}
}
