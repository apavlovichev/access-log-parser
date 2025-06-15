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
            int yandexBotCount = 0;
            int googleBotCount = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    countLines++;
                    if (line.length() > 1024) {
                        throw new ExceedingLimitOf1024CharactersException();
                    }

                    try {
                        int lastQuoteIndex = line.lastIndexOf('"');
                        int prevQuoteIndex = line.lastIndexOf('"', lastQuoteIndex - 1);

                        if (lastQuoteIndex == -1 || prevQuoteIndex == -1) continue;
                        String userAgent = line.substring(prevQuoteIndex + 1, lastQuoteIndex).trim();

                        if (userAgent.startsWith("\"") && userAgent.endsWith("\"")) {
                            userAgent = userAgent.substring(1, userAgent.length() - 1).trim();
                        }

                        int openBracket = userAgent.indexOf('(');
                        int closeBracket = userAgent.indexOf(')');
                        if (openBracket == -1 || closeBracket == -1) continue;

                        String bracketContent = userAgent.substring(openBracket + 1, closeBracket);
                        String[] parts = bracketContent.split(";");

                        if (parts.length >= 2) {
                            String fragment = parts[1].trim();
                            int slashIndex = fragment.indexOf('/');
                            String programName;
                            if (slashIndex != -1) {
                                programName = fragment.substring(0, slashIndex);
                            } else {
                                programName = fragment;
                            }
                            if ("YandexBot".equals(programName)) {
                                yandexBotCount++;
                            } else if ("Googlebot".equals(programName)) {
                                googleBotCount++;
                            }
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
                System.out.println("Общее количество строк в файле: " + countLines);
                if (countLines > 0) {
                    System.out.println("Запросов от YandexBot: " + yandexBotCount +
                            " (" + Math.round((double) yandexBotCount / countLines * 100) + "%)");
                    System.out.println("Запросов от Googlebot: " + googleBotCount +
                            " (" + Math.round((double) googleBotCount / countLines * 100) + "%)");
                }

            } catch (IOException ex) {
                System.err.println("Ошибка при чтении файла: " + ex.getMessage());
            } catch (ExceedingLimitOf1024CharactersException ex) {
                System.err.println("Ошибка: " + ex.getMessage());
            }
        }
    }
}
