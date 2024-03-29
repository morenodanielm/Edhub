package com.edhub.services;

import com.edhub.exceptions.EdhubExceptions;
import com.edhub.mapper.ComentarioDTOToComentario;
import com.edhub.mapper.IMapper;
import com.edhub.models.*;
import com.edhub.repositories.ComentarioRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
// clase test para calificacion service
class ComentarioServiceTest {

    // objeto bajo test
    private ComentarioService underTest;

    @Mock
    // dependencia que usa el comentario service
    private ComentarioRepository comentarioRepository;

    @Mock
    private ComentarioDTOToComentario comentarioMapper;

    // se ejecutará antes de cada test para inicializar el service
    @BeforeEach
    void setUp() {
        underTest = new ComentarioService(comentarioRepository, comentarioMapper);
    }

    @Test
    void itShouldAgregarComentario() {
        // given
        Comentario c = Comentario.builder()
                .comentario("Su contenido es de buena calidad")
                .fechaCreacion(LocalDateTime.now())
                .calificacion(Calificacion.builder().build())
                .build();

        // when
       // underTest.agregarComentario(c);

        // then
        ArgumentCaptor<Comentario> comentarioArgumentCaptor = ArgumentCaptor.forClass(Comentario.class);
        verify(comentarioRepository).save(comentarioArgumentCaptor.capture());
        Comentario actual = comentarioArgumentCaptor.getValue();
        assertThat(actual).isEqualTo(c);
    }

    @Test
    void itShouldObtenerTodos() {
        // given
        given(comentarioRepository.findAll())
                .willReturn(List.of(Comentario.builder().build()));

        // when
        List<Comentario> comentarios = underTest.obtenerTodos();

        // then
        assertThat(comentarios.size()).isEqualTo(1);
    }

    @Test
    void itShouldEliminarComentario() {
        // given
        Comentario c = Comentario.builder()
                .idComentario(15L)
                .comentario("Su contenido es de buena calidad")
                .fechaCreacion(LocalDateTime.now())
                .calificacion(Calificacion.builder().build())
                .build();
        given(comentarioRepository.existsById(anyLong())).willReturn(true);

        // when
        underTest.eliminarComentario(c.getIdComentario());

        // then
        ArgumentCaptor<Long> comentarioArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(comentarioRepository).deleteById(comentarioArgumentCaptor.capture());
        Long actual = comentarioArgumentCaptor.getValue();
        assertThat(actual).isEqualTo(c.getIdComentario());
    }

    @Test
    void itShouldThrowExceptionWhenComentarioDoesNotExists() {
        // given
        Comentario comentario = Comentario.builder()
                .idComentario(2L)
                .build();
        given(comentarioRepository.existsById(anyLong())).willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> underTest.eliminarComentario(comentario.getIdComentario()))
                .isInstanceOf(EdhubExceptions.class)
                .hasMessageContaining("El comentario no existe", HttpStatus.NOT_FOUND);
    }
}