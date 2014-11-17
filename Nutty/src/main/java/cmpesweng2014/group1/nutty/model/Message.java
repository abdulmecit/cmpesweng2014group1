package cmpesweng2014.group1.nutty.model;

public class Message {
	private int isSuccess;
	private Object data;
	private String message;

	public Message() {
		super();
	}

	public Message(int isSuccess, Object data, String message) {
		super();
		this.setIsSuccess(isSuccess);
		this.setData(data);
		this.setMessage(message);
	}

	public int getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
