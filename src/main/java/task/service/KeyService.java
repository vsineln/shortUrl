package task.service;

public interface KeyService {

    /**
     * Generate new free keys if their number decreased more than half of the capacity.
     * Executed during start and at time intervals indicated in "keys.update.delay"
     */
    void generateKeys();

    /**
     * Find free key in repository or generate new one
     *
     * @return free key
     */
    String orderFreeKey();

    /**
     * Release key for further reuse
     *
     * @param key which should be free
     */
    void setFree(String key);
}
