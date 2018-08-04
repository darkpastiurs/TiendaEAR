package sv.com.tienda.business.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(schema = "cat", name = "componentes_deck")
@SequenceGenerator(schema = "cat", name = "ComponenteDeck_seq_id", sequenceName = "componentes_deck_id_seq", allocationSize = 1)
public class ComponenteDeck implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ComponenteDeck_seq_id")
    @NotNull
    @Column(name = "id")
    private Integer id;
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "seccion")
    private String seccion;
    @NotNull
    @Column(name = "numero_minimo_carta")
    private Short numero_minimo;
    @NotNull
    @Column(name = "numero_maximo_carta")
    private Short numero_maximo;
    @Column(name = "estado", columnDefinition = "boolean default true")
    private boolean estado = true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public Short getNumero_minimo() {
        return numero_minimo;
    }

    public void setNumero_minimo(Short numero_minimo) {
        this.numero_minimo = numero_minimo;
    }

    public Short getNumero_maximo() {
        return numero_maximo;
    }

    public void setNumero_maximo(Short numero_maximo) {
        this.numero_maximo = numero_maximo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComponenteDeck)) return false;

        ComponenteDeck that = (ComponenteDeck) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ComponenteDeck{");
        sb.append("id=").append(id);
        sb.append(", seccion='").append(seccion);
        sb.append(", numero_minimo=").append(numero_minimo);
        sb.append(", numero_maximo=").append(numero_maximo);
        sb.append(", estado=").append(estado);
        sb.append('}');
        return sb.toString();
    }
}
