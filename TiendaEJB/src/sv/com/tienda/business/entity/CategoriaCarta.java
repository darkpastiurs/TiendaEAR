package sv.com.tienda.business.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(schema = "cat", name = "categorias_cartas")
@SequenceGenerator(name = "CategoriaCarta_seq_id", schema = "cat", sequenceName = "categorias_cartas_id_seq", allocationSize = 1)
@NamedQueries({
        @NamedQuery(name = "CategoriaCartas.findAll", query = "SELECT cc FROM CategoriaCarta cc ORDER BY cc.nombre ASC "),
        @NamedQuery(name = "CategoriaCartas.findByEstado", query = "SELECT cc FROM CategoriaCarta cc WHERE cc.estado = :estado ORDER BY cc.nombre ASC "),
        @NamedQuery(name = "CategoriaCartas.findByIdActivo", query = "SELECT cc FROM CategoriaCarta cc WHERE cc.id = :id AND cc.estado = true"),
        @NamedQuery(name = "CategoriaCartas.onlyCategoriaSuperior", query = "SELECT cc FROM CategoriaCarta  cc WHERE cc.categoriaCartaSuperior IS NULL ")
})
public class CategoriaCarta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CategoriaCarta_seq_id")
    @NotNull
    @Column(name = "id")
    private Integer id;
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado", columnDefinition = "boolean default true")
    private boolean estado = true;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = CategoriaCarta.class)
    @JoinColumn(name = "idcategoriasuperior", referencedColumnName = "id")
    private CategoriaCarta categoriaCartaSuperior;

    @OneToMany(mappedBy = "categoriaCartaSuperior", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = CategoriaCarta.class)
    private List<CategoriaCarta> subcategorias;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Carta.class)
    private List<Carta> cartas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CategoriaCarta getCategoriaCartaSuperior() {
        return categoriaCartaSuperior;
    }

    public void setCategoriaCartaSuperior(CategoriaCarta categoriaCartaSuperior) {
        this.categoriaCartaSuperior = categoriaCartaSuperior;
    }

    public List<CategoriaCarta> getSubcategorias() {
        return subcategorias;
    }

    public void setSubcategorias(List<CategoriaCarta> subcategorias) {
        this.subcategorias = subcategorias;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoriaCarta)) return false;

        CategoriaCarta that = (CategoriaCarta) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("sv.com.tienda.business.entity.CategoriaCarta{");
        sb.append("id=").append(id);
        sb.append(", nombre='").append(nombre);
        sb.append(", estado='").append(estado);
        sb.append(", categoriaCartaSuperior=").append(categoriaCartaSuperior);
        sb.append(", subcategorias=").append(subcategorias == null ? 0 : subcategorias.size());
        sb.append('}');
        return sb.toString();
    }
}