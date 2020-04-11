package task.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "keys")
public class KeyEntity {

    @Id
    @Column(name = "key", nullable = false)
    private String key;

    @Column(name = "free", nullable = false)
    private boolean free;
}
