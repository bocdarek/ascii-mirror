package asciimirror;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static Map<Character, Character> mirroredChars = new HashMap<>();

    static {
        mirroredChars.put('\\', '/');
        mirroredChars.put('/', '\\');
        mirroredChars.put('(', ')');
        mirroredChars.put(')', '(');
        mirroredChars.put('<', '>');
        mirroredChars.put('>', '<');
        mirroredChars.put('[', ']');
        mirroredChars.put(']', '[');
        mirroredChars.put('{', '}');
        mirroredChars.put('}', '{');
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Input the file path:");
        String path = sc.nextLine();

        List<String> lines = new ArrayList<>();
        try (Scanner reader = new Scanner(new File(path))) {
            while (reader.hasNext()) {
                lines.add(reader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            return;
        }

        int longest = lines.stream().mapToInt(String::length)
                .max()
                .orElse(0);

        List<String> modifiedLines = lines.stream()
                .map(s -> s + " ".repeat(longest - s.length()))
                .collect(Collectors.toList());

        List<String> mirroredLines = modifiedLines.stream()
                .map(Main::mirrorLine)
                .collect(Collectors.toList());

        IntStream.range(0, modifiedLines.size())
                        .mapToObj(i -> modifiedLines.get(i) + " | " + mirroredLines.get(i))
                        .forEach(System.out::println);
    }

    public static String mirrorLine(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            Character ch = input.charAt(i);
            sb.insert(0, mirroredChars.getOrDefault(ch, ch));
        }
        return sb.toString();
    }
}