package cmpesweng2014.group1.nutty.model;
 
public class Mail{
	
	private String to;
	private String subject;
	private String content;
	
	public Mail() {
		super();
	}

	public Mail(String to, String subject, String content) {
		super();
		this.to = to;
		this.subject = subject;
		this.content = content;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}