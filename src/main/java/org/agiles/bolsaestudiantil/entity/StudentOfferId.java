package org.agiles.bolsaestudiantil.entity;

import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

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
