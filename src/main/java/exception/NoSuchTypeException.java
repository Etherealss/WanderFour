package exception;

/**
 * @author wtk
 * @description 没有这种类型的异常
 * @date 2021-03-12
 */
public class NoSuchTypeException extends RuntimeException {

	public NoSuchTypeException() {
		super("没有匹配的类型");
	}

	public NoSuchTypeException(String message) {
		super(message);
	}
}
