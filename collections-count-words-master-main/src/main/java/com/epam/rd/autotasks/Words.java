package com.epam.rd.autotasks;

import java.text.Collator;
import java.text.Normalizer;
import java.util.*;

public class Words {

    public String countWords(List<String> lines) {
        Map<String, Integer> wordFrequency = new HashMap<>();
        Collator collator = Collator.getInstance(new Locale("en", "US"));

        for (String line : lines) {
            String[] words = line.split("\\s+");
            for (String word : words) {
                word = word.replaceAll("[^a-zA-Zа-яА-Я]", "").toLowerCase();
                String originalWord = normalize(word);
                if (originalWord.length() >= 4) {
                    wordFrequency.put(originalWord, wordFrequency.getOrDefault(originalWord, 0) + 1);
                }
            }
        }

        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(wordFrequency.entrySet());
        sortedEntries.removeIf(entry -> entry.getValue() < 10); // Remove entries with frequency < 10
        sortedEntries.sort((entry1, entry2) -> {
            int freqComparison = entry2.getValue().compareTo(entry1.getValue());
            if (freqComparison != 0) {
                return freqComparison;
            }
            return collator.compare(entry1.getKey(), entry2.getKey());
        });

        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            result.append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");
        }

        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }

        return result.toString();
    }
    private String normalize(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

}