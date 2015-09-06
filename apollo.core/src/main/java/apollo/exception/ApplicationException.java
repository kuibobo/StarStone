package apollo.exception;

public class ApplicationException extends CoreException {

	private int mResid;
	
	public ApplicationException(int resid) {
		this(resid, "");
	}

	public ApplicationException(int resid, String message) {
		super(message);
		
		this.mResid = resid;
	}
	
	public int getResid() {
		return this.mResid;
	}
}
