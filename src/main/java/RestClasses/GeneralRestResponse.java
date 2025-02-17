package RestClasses;

public class GeneralRestResponse {
	private boolean success;
	private String message;
	
	public GeneralRestResponse() {
		success = false;
		message = "";
	}
	
	public GeneralRestResponse(boolean succsessStatus, String responseMessage) {
		success = succsessStatus;
		message = responseMessage;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
