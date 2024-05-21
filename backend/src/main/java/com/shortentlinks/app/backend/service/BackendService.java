package com.shortentlinks.app.backend.service;

import java.util.Random;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackendService.class);

    private static final String BASE_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int MAX_LENGTH = 10;
    private static final int BASE_LENGTH = 62;
    private static final String ERROR_MESSAGE = "NaSNaSnaS";
    private static final String REGEX_URL = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";

    /**
     * Get unique string from origin link
     *
     * @param originLink: input from user, must match with regex pattern
     * @return unique string or error message
     */
    public String getUniqueStr(String originLink) {
        LOGGER.info("Get unique string from origin link: {}", originLink);
        Pattern pattern = Pattern.compile(REGEX_URL);
        if (originLink == null || originLink.isEmpty() || !pattern.matcher(originLink).matches()) {
            return ERROR_MESSAGE;
        }

        Random random = new Random();
        StringBuilder uniqueStr = new StringBuilder();

        for (int i = 0; i < MAX_LENGTH; i++) {
            int randomInt = random.nextInt(BASE_LENGTH);
            uniqueStr.append(BASE_ALPHABET.charAt(randomInt));
        }

        // TODO: need to check is this unique exist on db before return
        return uniqueStr.toString();
    }
}
