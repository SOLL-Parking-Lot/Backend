package com.example.soll.backend.entitiy;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class) // 엔티티의 변경 사항 추적
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {

    // Java 8 date/time type java.time.LocalDateTime not supported by default
    // LocalDataTime 역직렬화하지 못해서 생기는 문제 발생
    // 추가적으로 만약 캐시로 사용할 객체에 LocalDateTime 타입의 값이
    // 존재한다면 위처럼 @JsonSerialize, @JsonDeserialize 어노테이션을 기입이 필요
    // 그렇지 않으면 오류 발생
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @CreatedDate
    @Column(name = "CREATED_DATE_TIME")
    private LocalDateTime createdDateTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @LastModifiedDate
    @Column(name = "MODIFIED_DATE_TIME")
    private LocalDateTime modifiedDateTime;

}
