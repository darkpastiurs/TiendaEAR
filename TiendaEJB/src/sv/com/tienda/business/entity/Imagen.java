package sv.com.tienda.business.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(schema = "teo", name = "imagenes")
@SequenceGenerator(name = "Imagen_seq_id", schema = "teo", sequenceName = "imagenes_id_seq")
public class Imagen implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Imagen_seq_id")
    @NotNull
    @Column(name = "id")
    private Long id;
    @NotNull
    @Lob
    @Column(name = "archivo")
    private String archivo;

    @NotNull
    public Long getId() {
        return id;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(@NotNull String archivo) {
        this.archivo = archivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Imagen)) return false;

        Imagen imagen = (Imagen) o;

        return id.equals(imagen.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Imagen{");
        sb.append("id=").append(id);
        sb.append(", archivo='").append(archivo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
