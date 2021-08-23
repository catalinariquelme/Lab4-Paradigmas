
package Social;

public class Reaction {
    private int idReaction; //Id de la reacci?n
    private String authorReaction; //Autor de la reacci?n
    private String dateReaction; //Fecha de la reacci?n
    private String contentReaction; //Contenido de la reacci?n
    private String typeReaction; //Tipo de reacci?n (TEXTO)

    /**
     * Constructor Clase Reaction
     *
     * @param idReaction id reaccion
     * @param authorReaction autor reaccion
     * @param dateReaction fecha de registro reaccion
     * @param contentReaction contenido reaccion
     * @param typeReaction tipo de reaccion
     */
    public Reaction(int idReaction, String authorReaction, String dateReaction, String contentReaction, String typeReaction) {
        this.idReaction = idReaction;
        this.authorReaction = authorReaction;
        this.dateReaction = dateReaction;
        this.contentReaction = contentReaction;
        this.typeReaction = typeReaction;
    }

    public int getIdReaction() {
        return idReaction;
    }

    public void setIdReaction(int idReaction) {
        this.idReaction = idReaction;
    }

    public String getAuthorReaction() {
        return authorReaction;
    }

    public void setAuthorReaction(String authorReaction) {
        this.authorReaction = authorReaction;
    }

    public String getDateReaction() {
        return dateReaction;
    }

    public void setDateReaction(String dateReaction) {
        this.dateReaction = dateReaction;
    }

    public String getContentReaction() {
        return contentReaction;
    }

    public void setContentReaction(String contentReaction) {
        this.contentReaction = contentReaction;
    }

    public String getTypeReaction() {
        return typeReaction;
    }

    public void setTypeReaction(String typeReaction) {
        this.typeReaction = typeReaction;
    }
    @Override
    public String toString() {
        return idReaction +
                " Autor: " + authorReaction +
                " Fecha: " + dateReaction  +
                " Contenido: " + contentReaction +
                " Tipo: " + typeReaction;
    }
}

