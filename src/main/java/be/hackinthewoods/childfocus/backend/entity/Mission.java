package be.hackinthewoods.childfocus.backend.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;

public class Mission {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private WebUser webUser;
    @OneToOne
    private MissingPerson missingPerson;

    private Mission.Status missonAccepted;

    public enum Status {
        ACCEPTED,
        PENDING,
        REFUSED
    }

    public Mission() {
    }

    public Mission(Long id, WebUser webUser, MissingPerson missingPerson, Status missonAccepted) {
        this.id = id;
        this.webUser = webUser;
        this.missingPerson = missingPerson;
        this.missonAccepted = missonAccepted;
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

    public Status getMissonAccepted() {
        return missonAccepted;
    }

    public void setMissonAccepted(Status missonAccepted) {
        this.missonAccepted = missonAccepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mission mission = (Mission) o;
        return id.equals(mission.id) &&
                webUser.equals(mission.webUser) &&
                missingPerson.equals(mission.missingPerson) &&
                missonAccepted == mission.missonAccepted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, webUser, missingPerson, missonAccepted);
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", webUser=" + webUser +
                ", missingPerson=" + missingPerson +
                ", missonAccepted=" + missonAccepted +
                '}';
    }
}
