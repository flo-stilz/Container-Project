public class responseObject {

    private int errorCode;
    private String errorMessage;
    public responseObject(int errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
}
 

