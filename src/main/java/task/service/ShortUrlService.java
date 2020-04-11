package task.service;

import task.model.UrlDto;

public interface ShortUrlService {

    /**
     * Retrieve original link for provided key if exists
     *
     * @param key short link to retrieve original one
     *
     * @return original link
     */
    String getOriginalUrl(String key);

    /**
     * Create key for provided link with expiration date
     *
     * @param dto with original link
     *
     * @return dto with short key and expiration date
     */
    UrlDto createUrl(UrlDto dto);

    /**
     * delete link associated with provided key and release the key
     *
     * @param key to find original link
     */
    void deleteUrl(String key);

    /**
     * Removes expired links and refresh booked keys once a day according to cron in "keys.check.expired"
     */
    void checkExpired();
}
