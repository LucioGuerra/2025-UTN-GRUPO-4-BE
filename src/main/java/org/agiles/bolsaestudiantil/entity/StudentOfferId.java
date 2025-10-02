package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentOfferId implements Serializable {
    private Long studentId;
    private Long offerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentOfferId)) return false;
        StudentOfferId that = (StudentOfferId) o;
        return Objects.equals(studentId, that.studentId) &&
                Objects.equals(offerId, that.offerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, offerId);
    }
}
