package com.alura.literalura.principal;

import java.util.Scanner;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.RespuestaLibros;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.model.Libro;


public class Principal {

    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;
    private List<Libro> libros;


    public Principal(LibroRepository repository) {
        this.repositorio = repository;

    }
    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println("----- Menú -----");
            System.out.println("1. Buscar libros por titulo");
            System.out.println("2. Listar todos los libros");
            System.out.println("3. Listar todos los autores");
            System.out.println("4. Listar autores vivos en determinado año:");
            System.out.println("5. Listar libros por lenguaje:");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    buscarLibros();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorLenguaje();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private DatosLibro getDatosLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = sc.nextLine();
        var nombreCodificado = URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8);
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreCodificado);
        //System.out.println(json);
        RespuestaLibros respuesta = conversor.obtenerDatos(json, RespuestaLibros.class);
        if(respuesta.getResults().isEmpty()) {
            return null;
        }
        DatosLibro datos = respuesta.getResults().get(0);
        return datos;
    }

    private void buscarLibros() {
        DatosLibro datosLibro = getDatosLibro();
        if (datosLibro != null) {
                System.out.println("Título: " + datosLibro.titulo());
                System.out.println("Autor: " + datosLibro.autores());
                System.out.println("Lenguaje: " + datosLibro.lenguaje());
                System.out.println("Número de descargas: " + datosLibro.numDescargas());
                repositorio.save(new Libro(datosLibro));
            }
        else {
            System.out.println("No se encontraron resultados para la búsqueda.");
        }

        
    }

    private void listarLibros() {
        libros = repositorio.findAll();
        libros.stream()
            .sorted(Comparator.comparing(Libro::getTitulo))
            .forEach(System.out::println);
        
    }

    private void listarAutores() {
        libros = repositorio.findAll();
        libros.stream()
            .flatMap(libro -> libro.getAutores().stream())
            .map(autor -> autor.getNombre())
            .distinct()
            .sorted()
            .forEach(System.out::println);
    }

    private void listarAutoresVivos() {
        System.out.println("Escribe el año para listar autores vivos en ese año:");
        var anio = Integer.parseInt(sc.nextLine());
        libros = repositorio.findAll();
        libros.stream()
            .flatMap(libro -> libro.getAutores().stream())
            .filter(autor -> autor.getFechaNacimiento() <= anio && (autor.getFechaFallecimiento() == null || autor.getFechaFallecimiento() > anio))
            .map(autor -> autor.getNombre())
            .distinct()
            .sorted()
            .forEach(System.out::println);
    }

    private void listarLibrosPorLenguaje() {
        System.out.println("Escribe el lenguaje para listar los libros escritos en ese lenguaje[en - es]:");
        var lenguaje = sc.nextLine().toLowerCase();
        libros = repositorio.findByLenguaje(lenguaje);
        libros.stream()
            .sorted(Comparator.comparing(Libro::getTitulo))
            .forEach(System.out::println);
    }

    
}
