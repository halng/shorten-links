package com.shortentlinks.app.backend.service;

import com.shortentlinks.app.backend.entity.Link;
import com.shortentlinks.app.backend.repositories.LinkRepository;
import jakarta.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BackendService.class);
    private static final String ALGORITHM = "MD5";

    private final LinkRepository linkRepository;
    private final RedisService redisService;
    private static final String BASE_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int MAX_LENGTH = 10;
    private static final int BASE_LENGTH = 62;
    private static final String ERROR_MESSAGE = "URL_IS_NOT_VALID";
    private static final String REGEX_URL = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
    private final Pattern pattern = Pattern.compile(REGEX_URL);
    private final Random random = new Random();

    @Autowired
    public BackendService(LinkRepository linkRepository, RedisService redisService) {
        this.linkRepository = linkRepository;
        this.redisService = redisService;
    }

    private boolean isNotValidURL(String url) {
        return url == null || url.isEmpty() || !pattern.matcher(url).matches();
    }

    private String getUniqueStr() {
        StringBuilder uniqueStr = new StringBuilder();

        for (int i = 0; i < MAX_LENGTH; i++) {
            int randomInt = random.nextInt(BASE_LENGTH);
            uniqueStr.append(BASE_ALPHABET.charAt(randomInt));
        }
        return uniqueStr.toString();
    }

    private String generateHash(String creator, String originLink) {
        try {
            String strToHashBuff = creator + originLink;
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] encodedHash = md.digest(strToHashBuff.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            // Should throw an exception here
            return "DO_NOT_HASH";
        }
    }

    /**
     * Handle the request to create a shorten link
     *
     * @param originLink: input from user, must match with regex pattern
     * @return unique string or error message
     */

    public String createShortenLinkHandler(String originLink) {
        LOGGER.info("Get unique string from origin link: {}", originLink);

        if (isNotValidURL(originLink)) {
            return ERROR_MESSAGE;
        }

        String hashLinkAuthor = generateHash("SYSTEM", originLink);
        Link link = this.linkRepository.findLinkByHash(hashLinkAuthor).orElse(null);
        if (link != null) {
            LOGGER.info("Link {} already exists", link.getOriginLink());
            return link.getShortLinkId();
        }

        String linkId = "";
        do {
            linkId = getUniqueStr();
        } while (linkRepository.existsById(linkId));

        link = new Link(originLink, linkId, "SYSTEM", hashLinkAuthor);
        LOGGER.info("Save link: {} with id: {}", link.getOriginLink(), link.getShortLinkId());
        linkRepository.save(link);
        redisService.set(linkId, originLink);
        return linkId;
    }

    public String getOriginLinkHandler(String shortLink) {
        LOGGER.info("Get origin link from short link: {}", shortLink);
        String originLink = redisService.get(shortLink);
        if (originLink != null) {
            return originLink;
        }

        Link link = linkRepository.findById(shortLink).orElse(null);
        redisService.set(shortLink, link.getOriginLink());
        return link.getOriginLink();
    }
}