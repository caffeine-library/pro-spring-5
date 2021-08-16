package ThrowsAdvice.Book;

public class ErrorBean {
    public void errorProneMethod() throws Exception{
        throw new Exception("Generic Exception");
    }
    public void otherErrorProneMethod() throws IllegalArgumentException{
        throw new IllegalArgumentException("IllegalArgument Exception");
    }
}
