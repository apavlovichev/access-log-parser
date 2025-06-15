import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 1;
        boolean endlessLoop = true;
        while (endlessLoop) {
            System.out.println("Введите путь к файлу:");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (isDirectory) {
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
            count++;

            int countLines = 0;
            int minLine = 1024;
            int maxLine = 0;
            try (FileReader fileReader = new FileReader(path);
                 BufferedReader reader = new BufferedReader(fileReader)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    int length = line.length();
                    if (length > 1024) {
                        throw new ExceedingLimitOf1024CharactersException();
                    }
                    if (length > maxLine) {
                        maxLine = line.length();
                    }
                    if (length < minLine) {
                        minLine = length;
                    }
                    countLines++;
                }
                if (minLine == 1024) {
                    minLine = 0;
                }
                System.out.println("Общее количество строк в файле : " + countLines);
                System.out.println("Длина самой короткой строки в файле : " + minLine);
                System.out.println("Длина самой длинной строки в файле : " + maxLine);
            } catch (IOException ex) {
                System.err.println("Ошибка при чтении файла: " + ex.getMessage());
            } catch (ExceedingLimitOf1024CharactersException ex){
                System.err.println("Ошибка: " + ex.getMessage());
            }
        }
    }
}
