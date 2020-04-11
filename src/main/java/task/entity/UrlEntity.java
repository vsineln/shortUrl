package task.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "urls")
@EntityListeners(AuditingEntityListener.class)
public class UrlEntity {

    @Id
    @Column(name = "short_url", nullable = false)
    private String shortUrl;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "date_created", updatable = false)
    @CreatedDate
    private Timestamp dateCreated;

    @Column(name = "date_expired", nullable = false)
    private Timestamp dateExpired;
}
