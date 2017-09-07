package ua.rd.ioc;

/**
 * Created by Oleksandr_Tkachov on 9/7/2017.
 */
public class NoSuchBeanException extends RuntimeException {

    public NoSuchBeanException() {
        super("NoSuchBean");
    }
}
