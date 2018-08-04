package sv.com.tienda.business.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(schema = "teo", name = "monstruos", uniqueConstraints = {
        @UniqueConstraint(name = "unq_carta", columnNames = {"idcarta"})
})
@SequenceGenerator(schema = "teo", name = "Monstruo_seq_id", sequenceName = "monstruos_id_seq", allocationSize = 1)
public class Monstruo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Monstruo_seq_id")
    @NotNull
    @Column(name = "id")
    private Long id;
    @NotNull
    @Min(0)
    @Column(name = "ataque")
    private Integer ataque;
    @Min(0)
    @Column(name = "defensa")
    private Integer defensa;
    @NotNull
    @Column(name = "escala")
    private Short escala;
    @NotNull
    @Lob
    @Column(name = "materialinvocacion")
    private String materialInvocacion;
    @Column(name = "estado", columnDefinition = "boolean default true")
    private boolean estado = true;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = AtributoMonstruo.class)
    @JoinColumn(name = "idatributo", referencedColumnName = "id")
    private AtributoMonstruo atributo;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = TipoMounstro.class)
    @JoinTable(schema = "teo", name = "monstruos_tipos",
            joinColumns = @JoinColumn(name = "idmonstruo", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "idtipomonstruo", referencedColumnName = "id"))
    private List<TipoMounstro> tipos;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Carta.class)
    @JoinColumn(name = "idcarta", referencedColumnName = "id")
    private Carta carta;
    @OneToOne(mappedBy = "monstruo", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Pendulo.class)
    private Pendulo penduloAtributos;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = FlechaLink.class)
    @JoinTable(name = "links", schema = "teo",
            joinColumns = @JoinColumn(name = "idmonstruo", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "idflecha", referencedColumnName = "id"))
    private List<FlechaLink> flechasLinks;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = TipoEscala.class)
    @JoinColumn(name = "idtipoescala", referencedColumnName = "id")
    private TipoEscala tipoEscala;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAtaque() {
        return ataque;
    }

    public void setAtaque(Integer ataque) {
        this.ataque = ataque;
    }

    public Integer getDefensa() {
        return defensa;
    }

    public void setDefensa(Integer defensa) {
        this.defensa = defensa;
    }

    public Short getEscala() {
        return escala;
    }

    public void setEscala(Short escala) {
        this.escala = escala;
    }

    public String getMaterialInvocacion() {
        return materialInvocacion;
    }

    public void setMaterialInvocacion(String materialInvocacion) {
        this.materialInvocacion = materialInvocacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public AtributoMonstruo getAtributo() {
        return atributo;
    }

    public void setAtributo(AtributoMonstruo atributo) {
        this.atributo = atributo;
    }

    public List<TipoMounstro> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoMounstro> tipo) {
        this.tipos = tipo;
    }

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
    }

    public Pendulo getPenduloAtributos() {
        return penduloAtributos;
    }

    public void setPenduloAtributos(Pendulo penduloAtributos) {
        this.penduloAtributos = penduloAtributos;
    }

    public List<FlechaLink> getFlechasLinks() {
        return flechasLinks;
    }

    public void setFlechasLinks(List<FlechaLink> flechasLinks) {
        this.flechasLinks = flechasLinks;
    }

    public TipoEscala getTipoEscala() {
        return tipoEscala;
    }

    public void setTipoEscala(TipoEscala tipoEscala) {
        this.tipoEscala = tipoEscala;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Monstruo)) return false;

        Monstruo monstruo = (Monstruo) o;

        return id.equals(monstruo.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Monstruo{");
        sb.append("id=").append(id);
        sb.append(", carta=").append(carta.getNombre());
        sb.append(", atributo=").append(atributo.getNombre());
        sb.append(", tipo=").append(carta.getCategoria().getNombre());
        sb.append(", ataque=").append(ataque);
        sb.append(", defensa=").append(defensa);
        sb.append(", tipoescala=").append(tipoEscala.getNombre());
        sb.append(", escala=").append(escala);
        sb.append(", materialInvocacion='").append(materialInvocacion);
        sb.append(", estado=").append(estado);
        sb.append('}');
        return sb.toString();
    }
}