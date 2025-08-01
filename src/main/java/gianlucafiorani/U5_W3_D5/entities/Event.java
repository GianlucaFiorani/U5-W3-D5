package gianlucafiorani.U5_W3_D5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String description;
    private LocalDate date;
    private String location;
    private int capacity;
    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;


    public Event(String title, String description, LocalDate date, String location, int capacity, User organizer) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.capacity = capacity;
        this.organizer = organizer;
    }
}
