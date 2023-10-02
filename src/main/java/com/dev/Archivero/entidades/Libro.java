package com.dev.Archivero.entidades;





import javax.persistence.*;
import java.util.Date;

@Entity
public class Libro {
@Id
 private Long isbn;

private String titulo;
private Integer ejemplares;

@Temporal(TemporalType.DATE)
private Date alta;

@ManyToOne
@JoinColumn(name = "autor_id")
private Autor autor;

@ManyToOne
@JoinColumn(name = "editorial_id")
private Editorial editorial;

 public Editorial getEditorial() {
  return editorial;
 }

 public void setEditorial(Editorial editorial) {
  this.editorial = editorial;
 }

 public Autor getAutor() {
  return autor;
 }

 public void setAutor(Autor autor) {
  this.autor = autor;
 }

 public Libro() {
 }

 public Long getIsbn() {
  return isbn;
 }

 public void setIsbn(Long isbn) {
  this.isbn = isbn;
 }

 public String getTitulo() {
  return titulo;
 }

 public void setTitulo(String titulo) {
  this.titulo = titulo;
 }

 public Integer getEjemplares() {
  return ejemplares;
 }

 public void setEjemplares(Integer ejemplares) {
  this.ejemplares = ejemplares;
 }

 public Date getAlta() {
  return alta;
 }

 public void setAlta(Date alta) {
  this.alta = alta;
 }
}
