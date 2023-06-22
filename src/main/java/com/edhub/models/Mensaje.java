package com.edhub.models;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
@Entity
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private Long idMensaje;

    @Column(name = "contenido")
    @NotBlank
    private String contenido;

    @ManyToOne
    @JoinColumn(name = "usuarioRemitente_id", referencedColumnName = "id_usuario")
    private Usuario usuarioRemitente;

    @ManyToOne
    @JoinColumn(name = "usuarioDestinatario_id", referencedColumnName = "id_usuario")
    private Usuario usuarioDestinatario;

    @ManyToOne
    @JoinColumn(name = "chat", referencedColumnName = "id")
    private Chat chat;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaCreacion;


    public Mensaje() {
    }

    public Mensaje(@NotBlank String contenido, Usuario usuarioRemitente, Usuario usuarioDestinatario, Chat chat, LocalDateTime fechaCreacion) {
        this.contenido = contenido;
        this.usuarioRemitente = usuarioRemitente;
        this.usuarioDestinatario = usuarioDestinatario;
        this.chat = chat;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(Long idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Usuario getUsuarioeRemitente() {
        return usuarioRemitente;
    }

    public void setUsuarioeRemitente(Usuario usuarioeRemitente) {
        this.usuarioRemitente = usuarioeRemitente;
    }

    public Usuario getUsuarioDestinatario() {
        return usuarioDestinatario;
    }

    public void setUsuarioDestinatario(Usuario usuarioDestinatario) {
        this.usuarioDestinatario = usuarioDestinatario;
    }

     public Usuario getUsuarioRemitente() {
        return usuarioRemitente;
    }

    public void setUsuarioRemitente(Usuario usuarioRemitente) {
        this.usuarioRemitente = usuarioRemitente;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensaje mensaje = (Mensaje) o;
        return Objects.equals(idMensaje, mensaje.idMensaje);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMensaje);
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "idMensaje=" + idMensaje +
                ", contenido='" + contenido + '\'' +
                ", usuarioeRemitente=" + usuarioRemitente +
                ", usuarioDestinatario=" + usuarioDestinatario +
                ", chat=" + chat +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }

    @PrePersist
    public void prePersist() {
        fechaCreacion = LocalDateTime.now();
    }

}
