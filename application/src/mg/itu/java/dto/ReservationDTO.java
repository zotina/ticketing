package mg.itu.java.dto;

import mg.itu.java.model.Classe;
import mg.itu.java.model.Type_siege;

public class ReservationDTO {
    private Classe classe;
    private Type_siege type_siege;

    public Classe getClasse() {
        return classe;
    }
    public void setClasse(Classe classe) {
        this.classe = classe;
    }
    public ReservationDTO() {
    }
    public Type_siege getType_siege() {
        return type_siege;
    }
    public void setType_siege(Type_siege type_siege) {
        this.type_siege = type_siege;
    }
    public ReservationDTO( Classe classe, Type_siege type_siege) {
        this.classe = classe;
        this.type_siege = type_siege;
    }
}
