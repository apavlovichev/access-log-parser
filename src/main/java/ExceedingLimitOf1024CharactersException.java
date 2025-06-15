
public class ExceedingLimitOf1024CharactersException extends RuntimeException {
    public ExceedingLimitOf1024CharactersException() {
        super("В файле лога присутствует строка длинной свыше 1024 символов");

    }
}
