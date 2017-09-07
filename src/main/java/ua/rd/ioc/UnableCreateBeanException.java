package ua.rd.ioc;

/**
 * Created by Oleksandr_Tkachov on 9/7/2017.
 */
public class UnableCreateBeanException extends RuntimeException {
    public UnableCreateBeanException(Exception e) {
        super(e);
    }
}
