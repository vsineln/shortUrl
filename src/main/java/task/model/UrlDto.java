package task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UrlDto {

    @JsonProperty(value = "shortUrl", access = JsonProperty.Access.READ_ONLY)
    private String shortUrl;

    @JsonProperty(value = "originalUrl", access = JsonProperty.Access.WRITE_ONLY)
    private String originalUrl;

    @JsonIgnore
    private Timestamp dateCreated;

    @JsonProperty(value = "dateExpired")
    private Timestamp dateExpired;
}
