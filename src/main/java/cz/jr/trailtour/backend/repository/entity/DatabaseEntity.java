package cz.jr.trailtour.backend.repository.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by Jiří Rýdel on 4/19/20, 1:22 PM
 */
@MappedSuperclass
public class DatabaseEntity {

    @Id
    @Column(name = "id")
    private Long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
