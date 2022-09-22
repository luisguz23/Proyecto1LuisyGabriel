package com.example.proyecto1luisygabriel;

public class File {
    private String artista;
    private String genero;
    private String album;
    private int year;
    private String lyrics;
    private String url;



    public File(String artista, String genero, String album, int year, String lyrics, String url){
        this.artista = artista;
        this.setGenero(genero);
        this.setAlbum(album);
        this.setYear(year);
        this.setLyrics(lyrics);
        this.setUrl(url);

    }

    public String getArtista() {
        return this.artista;
    }

    public void setArtista(String artista){
        this.artista = artista;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
