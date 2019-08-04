package be.hackinthewoods.childfocus.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
public class Mission {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private WebUser webUser;
    @OneToOne
    private MissingPerson missingPerson;

    private Mission.Status status;

    public enum Status {
        ACCEPTED,
        PENDING,
        REFUSED
    }

    public Mission() {
    }

    public Mission(Long id, WebUser webUser, MissingPerson missingPerson, Status status) {
        this.id = id;
        this.webUser = webUser;
        this.missingPerson = missingPerson;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WebUser getWebUser() {
        return webUser;
    }

    public void setWebUser(WebUser webUser) {
        this.webUser = webUser;
    }

    public MissingPerson getMissingPerson() {
        return missingPerson;
    }

    public void setMissingPerson(MissingPerson missingPerson) {
        this.missingPerson = missingPerson;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mission mission = (Mission) o;
        return Objects.equals(id, mission.id) &&
          Objects.equals(webUser, mission.webUser) &&
          Objects.equals(missingPerson, mission.missingPerson) &&
          status == mission.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, webUser, missingPerson, status);
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", webUser=" + webUser +
                ", missingPerson=" + missingPerson +
                ", missonAccepted=" + status +
                '}';
    }
}
