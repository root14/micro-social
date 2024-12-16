package com.root14.postvalidatorservice.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AnalyzeService {

    /**
     * extract hashtags
     *
     * @param content
     * @return hashtag list
     */
    public List<String> extractHashtags(String content) {
        List<String> hashtags = new ArrayList<>();

        // Regex to match words starting with #
        String hashtagPattern = "#(\\w+|\\p{L}+)";
        Pattern pattern = Pattern.compile(hashtagPattern);
        Matcher matcher = pattern.matcher(content);

        // Find all matches and add to the list
        while (matcher.find()) {
            hashtags.add(matcher.group(1));
        }

        return hashtags;
    }

}
