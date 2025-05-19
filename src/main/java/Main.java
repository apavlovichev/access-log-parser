import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 1;
        boolean pathCorrect = false;
        while(!pathCorrect){
            System.out.println("Введите путь к файлу:");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (isDirectory){
                System.out.println("Указанный путь является путём к папке, а не к файлу" + "\n");
                count++;
                continue;
            }
            if (!fileExists) {
                System.out.println("Указанного файла не существует" + "\n");
                count++;
                continue;
            }
            System.out.println("Путь указан верно. Это файл номер " + count);
            pathCorrect = true;
        }
    }
}
