package cz.jr.trailtour.backend.repository.entities.stage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.jr.trailtour.backend.config.Constants;

import java.time.LocalDateTime;

/**
 * Created by Jiří Rýdel on 5/27/20, 4:40 PM
 */
public class StageInfo {

    @JsonProperty(value = "author")
    private String author;
    @JsonProperty(value = "content")
    private String content;
    @JsonProperty(value = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime created;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
