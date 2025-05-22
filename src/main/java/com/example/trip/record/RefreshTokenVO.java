package com.example.trip.record;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity

public record RefreshTokenVO(  @Id
@Column(name = "refresh_token")
                               String key, String value

) {
    public RefreshTokenVO updateValue(String value) {
        return new RefreshTokenVO(this.key, value);
    }
}
