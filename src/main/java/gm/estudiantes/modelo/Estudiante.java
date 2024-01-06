package gm.estudiantes.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
//boilerplate - repetitivo
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer idEstudiante;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;


}
