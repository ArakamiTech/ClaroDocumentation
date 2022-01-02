package co.com.cat.clarodocumentation.model;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Cristhian
 */
@Getter
@Setter
public class RowOfFile {

    private String description;
    private String url;
    private String operation;
    private String headers;
    private String input;
    private String output;
    private String requestExample;
    private String responseExample;
    private String responseExceptionsExample;
    private String supplies;
    private String requestExampleMultiple;
    private String observations;
    private String state;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getHeaders() {
		return headers;
	}
	public void setHeaders(String headers) {
		this.headers = headers;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getRequestExample() {
		return requestExample;
	}
	public void setRequestExample(String requestExample) {
		this.requestExample = requestExample;
	}
	public String getResponseExample() {
		return responseExample;
	}
	public void setResponseExample(String responseExample) {
		this.responseExample = responseExample;
	}
	public String getResponseExceptionsExample() {
		return responseExceptionsExample;
	}
	public void setResponseExceptionsExample(String responseExceptionsExample) {
		this.responseExceptionsExample = responseExceptionsExample;
	}
	public String getSupplies() {
		return supplies;
	}
	public void setSupplies(String supplies) {
		this.supplies = supplies;
	}
	public String getRequestExampleMultiple() {
		return requestExampleMultiple;
	}
	public void setRequestExampleMultiple(String requestExampleMultiple) {
		this.requestExampleMultiple = requestExampleMultiple;
	}
	public String getObservations() {
		return observations;
	}
	public void setObservations(String observations) {
		this.observations = observations;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

}
