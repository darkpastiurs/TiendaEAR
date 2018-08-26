package sv.com.tienda.business.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(schema = "teo", name = "cartas")
@NamedQueries({
        @NamedQuery(name = "Cartas.findAll", query = "SELECT c FROM Carta c"),
        @NamedQuery(name = "Cartas.findAllEstado", query = "SELECT c FROM Carta c WHERE c.estado = :estado"),
        @NamedQuery(name = "Cartas.findByIdActivo", query = "SELECT c FROM Carta c WHERE c.id = :id AND c.estado = true")
})
@SequenceGenerator(schema = "teo", name = "Carta_seq_id", sequenceName = "cartas_id_seq", allocationSize = 1)
public class Carta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Carta_seq_id")
    @NotNull
    @Column(name = "id")
    private Long id;
    @NotNull
    @Size(min = 1, max = 200)
    private String nombre;
    @NotNull
    @Lob
    @Column(name = "efecto")
    private String efecto;
    @NotNull
    @Column(name = "estado", columnDefinition = "boolean default true")
    private boolean estado = true;

    @OneToOne(mappedBy = "carta", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Monstruo.class)
    private Monstruo monstruo;

    @OneToMany(mappedBy = "carta", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = HistorialLimitacion.class)
    private List<HistorialLimitacion> historialLimitaciones;
    @OneToMany(mappedBy = "carta", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Inventario.class)
    private List<Inventario> inventarios;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CategoriaCarta.class, optional = false)
    @JoinColumn(name = "idcategoria", referencedColumnName = "id")
    private CategoriaCarta categoria;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = LimitacionCarta.class, optional = false)
    @JoinColumn(name = "idlimitacion", referencedColumnName = "id")
    private LimitacionCarta limite;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Imagen.class, optional = false)
    @JoinColumn(name = "idimagen", referencedColumnName = "id")
    private Imagen ilustracion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEfecto() {
        return efecto;
    }

    public void setEfecto(String efecto) {
        this.efecto = efecto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public CategoriaCarta getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaCarta categoria) {
        this.categoria = categoria;
    }

    public LimitacionCarta getLimite() {
        return limite;
    }

    public void setLimite(LimitacionCarta limite) {
        this.limite = limite;
    }

    public Imagen getIlustracion() {
        return ilustracion;
    }

    public void setIlustracion(Imagen ilustracion) {
        this.ilustracion = ilustracion;
    }

    public List<HistorialLimitacion> getHistorialLimitaciones() {
        return historialLimitaciones;
    }

    public void setHistorialLimitaciones(List<HistorialLimitacion> historialLimitaciones) {
        this.historialLimitaciones = historialLimitaciones;
    }

    public List<Inventario> getInventarios() {
        return inventarios;
    }

    public void setInventarios(List<Inventario> inventarios) {
        this.inventarios = inventarios;
    }

    public Monstruo getMonstruo() {
        return monstruo;
    }

    public void setMonstruo(Monstruo monstruo) {
        this.monstruo = monstruo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carta)) return false;

        Carta carta = (Carta) o;

        return id.equals(carta.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Carta{");
        sb.append("id=").append(id);
        sb.append(", nombre='").append(nombre);
        sb.append(", efecto='").append(efecto);
        sb.append(", estado=").append(estado);
        sb.append(", categoria=").append(categoria);
        sb.append(", limite=").append(limite.getCantidad());
        sb.append('}');
        return sb.toString();
    }
}
