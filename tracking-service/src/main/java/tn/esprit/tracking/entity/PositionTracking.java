package tn.esprit.tracking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "positions_tracking")
public class PositionTracking {

    @Id
    private String id;
    private String livraisonId;
    private Double latitude;
    private Double longitude;
    private LocalDateTime horodatage;
}
